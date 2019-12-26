package com.rationalinsights.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.rationalinsights.generator.knime.Category;
import com.rationalinsights.generator.knime.Extension;
import com.rationalinsights.generator.knime.KnimeNode;
import com.rationalinsights.generator.knime.KnimePlugin;
import com.rationalinsights.generator.model.Catalog;
import com.rationalinsights.generator.model.Node;
import com.rationalinsights.generator.model.RINodes;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static java.util.stream.Collectors.toList;

/**
 * Read nodes.json file and generate Nodes source files.
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.INITIALIZE)
public class RIPythonNodesGenerationMojo extends AbstractMojo {

    private static final String CONFIG_JAVA_TEMPLATE_NAME = "__Config.java.ftl";
    private static final String DIALOG_JAVA_TEMPLATE_NAME = "__Dialog.java.ftl";
    private static final String FACTORY_JAVA_TEMPLATE_NAME = "__Factory.java.ftl";
    private static final String MODEL_JAVA_TEMPLATE_NAME = "__Model.java.ftl";
    private static final String VIEW_JAVA_TEMPLATE_NAME = "__View.java.ftl";
    private static final String FACTORY_XML_TEMPLATE_NAME = "__Factory.xml.ftl";
    private static final String ACTIVATOR_JAVA_TEMPLATE_NAME = "__Activator.java.ftl";
    private static final String FEATURE_XML_TEMPLATE_NAME = "__Feature.xml.ftl";

    @Parameter(property = "nodes.json.path", required = true)
    private String nodesJsonPath;

    @Parameter(property = "generation.result.path", required = true)
    private String generationResultsDirPath;

    @Parameter(property = "plugin.xml.path", required = true)
    private String pluginXmlPath;

    public void execute() throws MojoExecutionException {
        Log log = getLog();

        log.info("Start generate Nodes.");

        File rootDirectory = new File("");

        log.info("Base directory: " + rootDirectory.getAbsolutePath());

        RINodes riNodes = loadRINodes();

        File generationResultsDir = getGenerationResultsDirectory();

        log.info("Generation results directory: " + generationResultsDir.getAbsolutePath());

        clearPreviousGeneratedPackages(generationResultsDir);

        String rootPackageName = riNodes.getName().toLowerCase();

        File rootPackageDir = new File(generationResultsDir, rootPackageName);

        rootPackageDir.mkdir();

        log.info("Root package directory: " + rootPackageDir.getAbsolutePath());

        generateNodeActivatorJavaFile(rootPackageDir);

        File pluginXmlFile = getPluginXml();

        Category rootCategory = buildRootCategory(riNodes);

        updatePluginXml(pluginXmlFile, riNodes, rootPackageDir, rootCategory, rootPackageName);

        updatePluginManifest(rootDirectory, rootPackageName);

        generateFeatureXmlFile(rootDirectory, rootPackageName);

        for (Node node : riNodes.getNodes()) {
            File nodePackageDirectory = createNodePackageDirectory(rootPackageDir, node);

            generateNodeConfigJavaFile(node, nodePackageDirectory, rootPackageName);
            generateNodeDialogJavaFile(node, nodePackageDirectory, rootPackageName);
            generateNodeFactoryJavaFile(node, nodePackageDirectory, rootPackageName);
            generateNodeModelJavaFile(node, nodePackageDirectory, rootPackageName);
            generateNodeViewJavaFile(node, nodePackageDirectory, rootPackageName);
            generateNodeFactoryXmlFile(node, nodePackageDirectory, rootPackageName);

            copyPythonFileToNodePackage(new File(node.getPythonScriptPath()), nodePackageDirectory);
            copyIconFileToNodePackage(new File(node.getIcon()), nodePackageDirectory);
        }

        log.info("End generate Nodes.");
    }

    /**
     * Generate feature/feature.xml file with plugin description.
     *
     * @param rootDirectory     - project root directory
     * @param pluginName        - name of the plugin
     */
    private void generateFeatureXmlFile(File rootDirectory, String pluginName) throws MojoExecutionException {
        getLog().info("Start generate Feature Xml File.");

        File featureDirectory = new File(rootDirectory.getAbsolutePath()
                + File.separator
                + "/feature/");

        if (!featureDirectory.exists()) {
            throw new MojoExecutionException("Feature directory does not exists.");
        }

        File featureXmlFile = new File(featureDirectory, "feature.xml");

        getLog().info("feature.xml: " + featureXmlFile.getAbsolutePath());

        if (featureXmlFile.exists()) {
            featureXmlFile.delete();
        }

        Map<String, Object> defaultTemplateParametersMap = new HashMap<>();
        defaultTemplateParametersMap.put("pluginName", pluginName);

        generateNodeFile(FEATURE_XML_TEMPLATE_NAME, "feature.xml",
                defaultTemplateParametersMap,
                featureDirectory);

        getLog().info("End generate Feature Xml File.");
    }

    /**
     * Update plugin MANIFEST.MF file.
     *
     * @param rootDirectory         - root directory for plugin project
     * @param rootPackageName       - base package name for Activator class
     *
     * @throws MojoExecutionException -
     */
    private void updatePluginManifest(File rootDirectory, String rootPackageName) throws MojoExecutionException {
        File pluginManifestFile = new File(rootDirectory.getAbsolutePath()
                + File.separator
                + "/plugin/META-INF/MANIFEST.MF");

        getLog().info("MANIFEST.MF: " + pluginManifestFile.getAbsolutePath());

        Manifest manifest;

        try {
            manifest = new Manifest(new FileInputStream(pluginManifestFile));

            manifest.getMainAttributes().put(new Attributes.Name("Bundle-Activator"), "com." + rootPackageName + ".Activator");

            manifest.write(new FileOutputStream(pluginManifestFile));
        } catch (IOException ioException) {
            ioException.printStackTrace();

            throw new MojoExecutionException("Can't read plugin MANIFEST.MF file: " + ioException.getMessage());
        }
    }

    /**
     * Generate Activator.java file and place to base package of plugin extension.
     *
     * @param rootPackageDir        - source root package of plugin
     *
     * @throws MojoExecutionException -
     */
    private void generateNodeActivatorJavaFile(File rootPackageDir) throws MojoExecutionException {
        getLog().info("Start generate Node Activator Java File.");

        Map<String, Object> templateParametersMap = new HashMap<>();

        String name = rootPackageDir.getName();

        templateParametersMap.put("rootPackageName", name);
        templateParametersMap.put("pluginName", name);

        generateNodeFile(ACTIVATOR_JAVA_TEMPLATE_NAME,
                "Activator.java",
                templateParametersMap,
                rootPackageDir);

        getLog().info("End generate Node Activator Java File.");
    }

    /**
     * Clear all previous generated packages which can remain as garbage if the node names have changed.
     *
     * @param generationResultsDir - result packages directory
     */
    private void clearPreviousGeneratedPackages(File generationResultsDir) {
        for (File file : Objects.requireNonNull(generationResultsDir.listFiles())) {
            getLog().info("Delete: " + file.getName());

            file.delete();
        }
    }

    /**
     * Copy icon file to node package directory.
     *
     * @param sourceIconFile        - source icon file
     * @param nodePackageDirectory  - destination node package directory
     *
     * @throws MojoExecutionException   -
     */
    private void copyIconFileToNodePackage(File sourceIconFile, File nodePackageDirectory) throws MojoExecutionException {
        if (!sourceIconFile.exists()) {
            throw new MojoExecutionException("Can't find source icon file: " + sourceIconFile.getAbsolutePath());
        }

        File destinationFile = new File(nodePackageDirectory, "default.png");

        try {
            FileUtils.copyFile(sourceIconFile, destinationFile);
        } catch (IOException ioException) {
            throw new MojoExecutionException("Can't copy icon to node package directory. Error messaage: " + ioException.getMessage());
        }
    }

    /**
     * Copy source icon file to destination icon file.
     *
     * @param sourceIconFile        - source icon file
     * @param destinationIconFile   - destination icon file
     *
     * @throws MojoExecutionException -
     */
    private void copyIconFile(File sourceIconFile, File destinationIconFile) throws MojoExecutionException {
        if (!sourceIconFile.exists()) {
            throw new MojoExecutionException("Can't find source icon file: " + sourceIconFile.getAbsolutePath());
        }

        if (destinationIconFile.exists()) {
            destinationIconFile.delete();
        }

        try {
            FileUtils.copyFile(sourceIconFile, destinationIconFile);
        } catch (IOException ioException) {
            throw new MojoExecutionException("Can't copy icon to node package directory. Error messaage: " + ioException.getMessage());
        }
    }

    /**
     * Copy python file to node package directory.
     *
     * @param sourcePythonFile      - source python file
     * @param nodePackageDirectory  - destination node package directory
     *
     * @throws MojoExecutionException   -
     */
    private void copyPythonFileToNodePackage(File sourcePythonFile, File nodePackageDirectory) throws MojoExecutionException {
        if (!sourcePythonFile.exists()) {
            throw new MojoExecutionException("Can't find source python file: " + sourcePythonFile.getAbsolutePath());
        }

        File destinationPythonFile = new File(nodePackageDirectory, sourcePythonFile.getName());

        try {
            FileUtils.copyFile(sourcePythonFile, destinationPythonFile);
        } catch (IOException ioException) {
            throw new MojoExecutionException("Can't copy python script to node package directory. Error messaage: " + ioException.getMessage());
        }
    }

    /**
     * Get plugin.xml file by {@link #pluginXmlPath}.
     *
     * @throws MojoExecutionException -
     *
     * @return File
     */
    private File getPluginXml() throws MojoExecutionException {
        if (Objects.isNull(pluginXmlPath) || pluginXmlPath.length() == 0) {
            throw new MojoExecutionException("Can't find plugin.xml path.");
        }

        File pluginXmlFile = new File(pluginXmlPath);

        if (!pluginXmlFile.exists()) {
            throw new MojoExecutionException("Can't find plugin.xml path.");
        }

        return pluginXmlFile;
    }

    /**
     * Update plugin.xml file information.
     *  - update catalogs information
     *  - update nodes information
     *
     * @param pluginXmlFile             - source plugin.xml file
     * @param riNodes                   - RationalInsights nodes object
     * @param generationResultsDir      - base package for generated node packages
     *
     * @param rootCategory              - root category
     * @param rootPackageName           - root package name
     *
     * @throws MojoExecutionException   -
     */
    private void updatePluginXml(File pluginXmlFile, RINodes riNodes, File generationResultsDir, Category rootCategory, String rootPackageName) throws MojoExecutionException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(KnimePlugin.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            KnimePlugin knimePlugin = (KnimePlugin) jaxbUnmarshaller.unmarshal(pluginXmlFile);

            for (Extension extension : knimePlugin.getExtensions()) {
                if (extension.isNodesExtension()) {
                    extension.setNodes(new ArrayList<>());

                    for (Node node : riNodes.getNodes()) {
                        KnimeNode knimeNode = new KnimeNode();

                        knimeNode.setCategoryPath("/" + rootPackageName + "/" + node.getParentCatalog().toLowerCase() + "/");
                        knimeNode.setFactoryClass("com." + rootPackageName + "." + node.getName().toLowerCase() + "." + node.getName() + "Factory");

                        extension.addNode(knimeNode);
                    }
                } else if (extension.isCatalogExtension()) {
                    extension.setCategories(new ArrayList<>());

                    File rootCatalogIconFile = new File(riNodes.getIcon());
                    String rootCatalogIconFileName = rootCatalogIconFile.getName();

                    copyIconFile(rootCatalogIconFile, new File(generationResultsDir, rootCatalogIconFileName));

                    extension.addCategory(rootCategory);

                    for (Catalog catalog : riNodes.getCatalogs()) {
                        File categoryIconFile = new File(catalog.getIcon());
                        String categoryIconFileName = categoryIconFile.getName();

                        copyIconFile(categoryIconFile, new File(generationResultsDir, categoryIconFileName));

                        Category subCategory = new Category();

                        subCategory.setDescription(catalog.getDescription());
                        subCategory.setIcon("com/" + rootPackageName + "/" + categoryIconFileName);
                        subCategory.setLevelId(catalog.getName().toLowerCase());
                        subCategory.setName(catalog.getName());
                        subCategory.setPath("/" + rootPackageName);

                        extension.addCategory(subCategory);
                    }
                }
            }

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            jaxbMarshaller.marshal(knimePlugin, pluginXmlFile);
        } catch (JAXBException jaxbException) {
            throw new MojoExecutionException("Can't update plugin.xml file. Error message: " + jaxbException.getMessage());
        }
    }

    private File createNodePackageDirectory(File generationResultsDir, Node node) {
        File nodePackageDirectory = new File(generationResultsDir, node.getName().toLowerCase());

        if (nodePackageDirectory.exists()) {
            nodePackageDirectory.delete();
        }

        nodePackageDirectory.mkdir();

        return nodePackageDirectory;
    }

    /**
     * Get directory by {@link #generationResultsDirPath}.
     *
     * @throws MojoExecutionException -
     *
     * @return File
     */
    private File getGenerationResultsDirectory() throws MojoExecutionException {
        if (Objects.isNull(generationResultsDirPath) || generationResultsDirPath.length() == 0) {
            throw new MojoExecutionException("Can't find generation node results directory path.");
        }

        File generationResultsDir = new File(generationResultsDirPath);

        if (!generationResultsDir.exists()) {
            throw new MojoExecutionException("Can't find generation node results directory path.");
        }

        return generationResultsDir;
    }

    /**
     * Generate Knime node factory xml file.
     *
     * @param node                  - RI node
     * @param nodePackageDirectory  - destination package directory
     * @param rootPackageName       - name of root package
     *
     * @throws MojoExecutionException -
     */
    private void generateNodeFactoryXmlFile(Node node, File nodePackageDirectory, String rootPackageName) throws MojoExecutionException {
        getLog().info("Start generate Node Factory Xml File.");

        Map<String, Object> defaultTemplateParametersMap = getDefaultTemplateParametersMap(node, rootPackageName);
        defaultTemplateParametersMap.put("shortDescription", node.getShortDescription());
        defaultTemplateParametersMap.put("fullDescription", node.getFullDescription());
        defaultTemplateParametersMap.put("inputPorts", node.getInputPorts());
        defaultTemplateParametersMap.put("outputPorts", node.getOutputPorts());

        generateNodeFile(FACTORY_XML_TEMPLATE_NAME,
                node.getName() + "Factory.xml",
                defaultTemplateParametersMap,
                nodePackageDirectory);

        getLog().info("End generate Node Factory Xml File.");
    }

    /**
     * Generate Knime node view java file.
     *
     * @param node                  - RI node
     * @param nodePackageDirectory  - destination package directory
     * @param rootPackageName       - name of root package
     *
     * @throws MojoExecutionException -
     */
    private void generateNodeViewJavaFile(Node node, File nodePackageDirectory, String rootPackageName) throws MojoExecutionException {
        getLog().info("Start generate Node View Java File.");

        generateNodeFile(VIEW_JAVA_TEMPLATE_NAME,
                node.getName() + "View.java",
                getDefaultTemplateParametersMap(node, rootPackageName),
                nodePackageDirectory);

        getLog().info("End generate Node View Java File.");
    }

    /**
     * Generate Knime node model java file.
     *
     * @param node                  - RI node
     * @param nodePackageDirectory  - destination package directory
     * @param rootPackageName       - name of root package
     *
     * @throws MojoExecutionException -
     */
    private void generateNodeModelJavaFile(Node node, File nodePackageDirectory, String rootPackageName) throws MojoExecutionException {
        getLog().info("Start generate Node Model Java File.");

        Map<String, Object> defaultTemplateParametersMap = getDefaultTemplateParametersMap(node, rootPackageName);

        defaultTemplateParametersMap.put("pythonScript", new File(node.getPythonScriptPath()).getName());
        defaultTemplateParametersMap.put("inputPorts", node.getInputPorts());
        defaultTemplateParametersMap.put("outputPorts", node.getOutputPorts());

        generateNodeFile(MODEL_JAVA_TEMPLATE_NAME,
                node.getName() + "Model.java",
                defaultTemplateParametersMap,
                nodePackageDirectory);

        getLog().info("End generate Node Model Java File.");
    }

    /**
     * Generate Knime node factory java file.
     *
     * @param node                  - RI node
     * @param nodePackageDirectory  - destination package directory
     * @param rootPackageName       - name of root package
     *
     * @throws MojoExecutionException -
     */
    private void generateNodeFactoryJavaFile(Node node, File nodePackageDirectory, String rootPackageName) throws MojoExecutionException {
        getLog().info("Start generate Node Factory Java File.");

        generateNodeFile(FACTORY_JAVA_TEMPLATE_NAME,
                node.getName() + "Factory.java",
                getDefaultTemplateParametersMap(node, rootPackageName),
                nodePackageDirectory);

        getLog().info("End generate Node Factory Java File.");
    }

    /**
     * Generate Knime node dialog java file.
     *
     * @param node                  - RI node
     * @param nodePackageDirectory  - destination package directory
     * @param rootPackageName       - name of root package
     *
     * @throws MojoExecutionException -
     */
    private void generateNodeDialogJavaFile(Node node, File nodePackageDirectory, String rootPackageName) throws MojoExecutionException {
        getLog().info("Start generate Node Dialog Java File.");

        generateNodeFile(DIALOG_JAVA_TEMPLATE_NAME,
                node.getName() + "Dialog.java",
                getDefaultTemplateParametersMap(node, rootPackageName),
                nodePackageDirectory);

        getLog().info("End generate Node Dialog Java File.");
    }

    /**
     * Generate Knime node config java file.
     *
     * @param node                  - RI node
     * @param nodePackageDirectory  - destination package directory
     * @param rootPackageName       - name of root package
     *
     * @throws MojoExecutionException -
     */
    private void generateNodeConfigJavaFile(Node node, File nodePackageDirectory, String rootPackageName) throws MojoExecutionException {
        getLog().info("Start generate Node Config Java File.");

        Map<String, Object> defaultTemplateParametersMap = getDefaultTemplateParametersMap(node, rootPackageName);
        defaultTemplateParametersMap.put("inputPorts", node.getInputPorts());
        defaultTemplateParametersMap.put("outputPorts", node.getOutputPorts());

        generateNodeFile(CONFIG_JAVA_TEMPLATE_NAME,
                node.getName() + "Config.java",
                defaultTemplateParametersMap,
                nodePackageDirectory);

        getLog().info("End generate Node Config Java File.");
    }

    /**
     * Generate file by template and save it to destination package directory by specified name.
     *
     * @param templateFileName      - freemarker tempalte name
     * @param resultFileName        - result file name
     * @param templateParametersMap - freemarker template parameters map
     * @param packageDirectory      - destination package directory
     *
     * @throws MojoExecutionException -
     */
    private void generateNodeFile(String templateFileName,
                                  String resultFileName,
                                  Map<String, Object> templateParametersMap,
                                  File packageDirectory) throws MojoExecutionException {
        getLog().info("Start generate Node File.");

        Template template = getTemplate(templateFileName);

        saveTemplateResult(template, templateParametersMap, new File(packageDirectory, resultFileName));

        getLog().info("End generate Node File.");
    }

    /**
     * Generate map with default parameters (node name).
     *
     * @param node - source RI node
     *
     * @param rootPackageName
     * @return Map<String, Object>
     */
    private Map<String, Object> getDefaultTemplateParametersMap(Node node, String rootPackageName) {
        Map<String, Object> templateParametersMap = new HashMap<>();

        templateParametersMap.put("nodeName", node.getName());
        templateParametersMap.put("nodePackageName", node.getName().toLowerCase());
        templateParametersMap.put("rootPackageName", rootPackageName);

        return templateParametersMap;
    }

    /**
     * Generate result from template, display in console and save to file.
     *
     * @param template                  - source template
     * @param templateParametersMap     - template parameters map
     * @param resultFile                - output file
     *
     * @throws MojoExecutionException   -
     */
    private void saveTemplateResult(Template template, Map<String, Object> templateParametersMap, File resultFile) throws MojoExecutionException {
        Writer consoleWriter = new OutputStreamWriter(System.out);

        try {
            template.process(templateParametersMap, consoleWriter);
        } catch (TemplateException | IOException exception) {
            throw new MojoExecutionException("Can't process Knime java template. Error message: " + exception.getMessage());
        }

        Writer fileWriter;

        try {
            fileWriter = new FileWriter(resultFile);
        } catch (IOException ioException) {
            throw new MojoExecutionException("Can't save template generation results. Error message: " + ioException.getMessage());
        }

        try {
            template.process(templateParametersMap, fileWriter);
        } catch (TemplateException | IOException exception) {
            throw new MojoExecutionException("Can't process Knime java template. Error message: " + exception.getMessage());
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Get Freemarker template instance by template name.
     *
     * @param templateName  - source template name
     *
     * @throws MojoExecutionException -
     *
     * @return Template
     */
    private Template getTemplate(String templateName) throws MojoExecutionException {
        Configuration configuration = new Configuration(); // FIXME: replace with new implementation.

        configuration.setClassForTemplateLoading(this.getClass(), "/");

        // Some other recommended settings:
        //configuration.setIncompatibleImprovements(new Version(2, 3, 20));
        //configuration.setDefaultEncoding("UTF-8");
        //configuration.setLocale(Locale.US);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template template;

        try {
            template = configuration.getTemplate(templateName);
        } catch (IOException ioException) {
            throw new MojoExecutionException("Can't find Knime java templates. Error message: " + ioException.getMessage());
        }

        return template;
    }

    /**
     * Build root category by {@link RINodes}
     *
     * @param riNodes - source data
     *
     * @return Category
     */
    private Category buildRootCategory(RINodes riNodes) {
        File rootCatalogIconFile = new File(riNodes.getIcon());
        String rootCatalogIconFileName = rootCatalogIconFile.getName();

        Category rootCategory = new Category();

        String rootPackageName = riNodes.getName().toLowerCase();

        rootCategory.setDescription(riNodes.getDescription());
        rootCategory.setIcon("com/" + rootPackageName + "/" + rootCatalogIconFileName);
        rootCategory.setLevelId(riNodes.getName().toLowerCase());
        rootCategory.setName(riNodes.getName());
        rootCategory.setPath("/");

        return rootCategory;
    }

    /**
     * Load nodes information from {@link #nodesJsonPath}.
     *
     * @throws MojoExecutionException -
     *
     * @return List<Node>
     */
    private RINodes loadRINodes() throws MojoExecutionException {
        if (Objects.isNull(nodesJsonPath)) {
            throw new MojoExecutionException("Can't find nodes.json");
        }

        File nodesJsonFile = new File(nodesJsonPath);

        getLog().info("Nodes json file path: " + nodesJsonFile.getAbsolutePath());

        if (!nodesJsonFile.exists()) {
            throw new MojoExecutionException("Can't find nodes.json");
        }

        try {
            Gson gson = new GsonBuilder().create();
            JsonReader jsonReader = new JsonReader(new FileReader(nodesJsonFile));

            Type listType = new TypeToken<RINodes>(){}.getType();
            RINodes riNodes = gson.fromJson(jsonReader, listType);

            if (Objects.isNull(riNodes)) {
                throw new MojoExecutionException("RI Nodes information is empty.");
            }

            List<Catalog> catalogs = riNodes.getCatalogs();

            if (Objects.isNull(catalogs) || catalogs.size() == 0) {
                throw new MojoExecutionException("Catalogs information is empty.");
            }

            List<Node> nodes = riNodes.getNodes();

            if (Objects.isNull(nodes) || nodes.size() == 0) {
                throw new MojoExecutionException("Nodes information is empty.");
            }

            List<String> catalogNames = catalogs.stream()
                    .filter(Objects::nonNull)
                    .map(Catalog::getName)
                    .collect(toList());

            for (Node node: nodes) {
                if (!catalogNames.contains(node.getParentCatalog())) {
                    throw new MojoExecutionException("Node  '" + node.getName()
                            + "' reference to not existing parent catalog '" + node.getParentCatalog() + "'.");
                }
            }

            return riNodes;
        } catch (FileNotFoundException exception) {
            throw new MojoExecutionException("Error on nodes.json file read. Error message: " + exception.getMessage());
        }
    }
}
