package com.rationalinsights.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.rationalinsights.generator.model.Node;
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

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Read nodes.json file and generate Nodes source files.
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.INITIALIZE)
public class RIPythonNodesGenerationMojo extends AbstractMojo {

    @Parameter(property = "nodes.json.path", required = true)
    private String nodesJsonPath;

    @Parameter(property = "generation.result.path", required = true)
    private String generationResultsDirPath;

    public void execute() throws MojoExecutionException {
        Log log = getLog();

        log.info("Start generate Nodes.");
        log.info("Base directory: " + new File("").getAbsolutePath());

        List<Node> nodes = loadNodes();

        if (Objects.isNull(generationResultsDirPath) || generationResultsDirPath.length() == 0) {
            throw new MojoExecutionException("Can't find generation node results directory path.");
        }

        File generationResultsDir = new File(generationResultsDirPath);

        if (!generationResultsDir.exists()) {
            throw new MojoExecutionException("Can't find generation node results directory path.");
        }

        for (Node node : nodes) {
            generateNodeConfigJavaFile(node);
            generateNodeDialogJavaFile(node);
            generateNodeFactoryJavaFile(node);
            generateNodeModelJavaFile(node);
            generateNodeViewJavaFile(node);
            generateNodeFactoryXmlFile(node);
        }

        log.info("End generate Nodes.");
    }

    /**
     * TODO:
     *
     * @param node  -
     *
     * @throws MojoExecutionException -
     */
    private void generateNodeFactoryXmlFile(Node node) throws MojoExecutionException {
        getLog().info("Start generate Node Factory Xml File.");

        Map<String, Object> templateParametersMap = new HashMap<>();

        templateParametersMap.put("nodeName", node.getName());
        templateParametersMap.put("shortDescription", node.getShortDescription());
        templateParametersMap.put("fullDescription", node.getFullDescription());

        Template template = getTemplate("__Factory.xml.ftl");

        saveTemplateResult(template, templateParametersMap, node.getName() + "Factory.xml");

        getLog().info("End generate Node Factory Xml File.");
    }

    private void generateNodeViewJavaFile(Node node) throws MojoExecutionException {
        getLog().info("Start generate Node View Java File.");

        Map<String, Object> templateParametersMap = new HashMap<>();
        templateParametersMap.put("nodeName", node.getName());

        Template template = getTemplate("__View.java.ftl");

        saveTemplateResult(template, templateParametersMap, node.getName() + "View.java");

        getLog().info("End generate Node View Java File.");
    }

    private void generateNodeModelJavaFile(Node node) throws MojoExecutionException {
        getLog().info("Start generate Node Model Java File.");

        Map<String, Object> templateParametersMap = new HashMap<>();

        templateParametersMap.put("nodeName", node.getName());
        templateParametersMap.put("pythonScript", node.getPythonScriptPath());

        Template template = getTemplate("__Model.java.ftl");

        saveTemplateResult(template, templateParametersMap, node.getName() + "Model.java");

        getLog().info("End generate Node Model Java File.");
    }

    private void generateNodeFactoryJavaFile(Node node) throws MojoExecutionException {
        getLog().info("Start generate Node Factory Java File.");

        Map<String, Object> templateParametersMap = new HashMap<>();
        templateParametersMap.put("nodeName", node.getName());

        Template template = getTemplate("__Factory.java.ftl");

        saveTemplateResult(template, templateParametersMap, node.getName() + "Factory.java");

        getLog().info("End generate Node Factory Java File.");
    }

    private void generateNodeDialogJavaFile(Node node) throws MojoExecutionException {
        getLog().info("Start generate Node Dialog Java File.");

        Map<String, Object> templateParametersMap = new HashMap<>();
        templateParametersMap.put("nodeName", node.getName());

        Template template = getTemplate("__Dialog.java.ftl");

        saveTemplateResult(template, templateParametersMap, node.getName() + "Dialog.java");

        getLog().info("End generate Node Dialog Java File.");
    }

    private void generateNodeConfigJavaFile(Node node) throws MojoExecutionException {
        getLog().info("Start generate Node Config Java File.");

        Map<String, Object> templateParametersMap = new HashMap<>();
        templateParametersMap.put("nodeName", node.getName());

        Template template = getTemplate("__Config.java.ftl");

        saveTemplateResult(template, templateParametersMap, node.getName() + "Config.java");

        getLog().info("End generate Node Config Java File.");
    }

    /**
     * Generate result from template, display in console and save to file.
     *
     * @param template                  - source template
     * @param templateParametersMap     - template parameters map
     * @param outputFileName            - output file name
     *
     * @throws MojoExecutionException   -
     */
    private void saveTemplateResult(Template template, Map<String, Object> templateParametersMap, String outputFileName) throws MojoExecutionException {
        Writer consoleWriter = new OutputStreamWriter(System.out);

        try {
            template.process(templateParametersMap, consoleWriter);
        } catch (TemplateException | IOException e) {
            throw new MojoExecutionException("Can't process Knime java template.");
        }

        Writer fileWriter;

        try {
            fileWriter = new FileWriter(new File("output.html"));
        } catch (IOException e) {
            throw new MojoExecutionException("Can't save template generation results.");
        }

        try {
            template.process(templateParametersMap, fileWriter);
        } catch (TemplateException | IOException e) {
            throw new MojoExecutionException("Can't save template generation results.");
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
        } catch (IOException e) {
            throw new MojoExecutionException("Can't find Knime java templates.");
        }

        return template;
    }

    /**
     * Load nodes information from {@link #nodesJsonPath}.
     *
     * @throws MojoExecutionException -
     *
     * @return List<Node>
     */
    private List<Node> loadNodes() throws MojoExecutionException {
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

            Type listType = new TypeToken<ArrayList<Node>>(){}.getType();
            List<Node> nodes = gson.fromJson(jsonReader, listType);

            if (Objects.isNull(nodes) || nodes.size() == 0) {
                throw new MojoExecutionException("Nodes information is empty.");
            }

            return nodes;
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException("Error on nodes.json file read.");
        }
    }
}
