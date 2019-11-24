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

    public void execute() throws MojoExecutionException {
        Log log = getLog();

        log.info("Start generate Nodes.");

        List<Node> nodes = loadNodes();

        Map<String, Object> templateParametersMap = new HashMap<>();

        templateParametersMap.put("title", "Vogella example");

        Template template = getTemplate("__Config.java.ftl");

        Writer consoleWriter = new OutputStreamWriter(System.out);

        try {
            template.process(templateParametersMap, consoleWriter);
        } catch (TemplateException | IOException e) {
            throw new MojoExecutionException("Can't process Knime java template.");
        }

        Writer fileWriter = null;

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
            } catch (IOException e) {
                throw new MojoExecutionException("Can't save template generation results.");
            }
        }

        log.info("End generate Nodes.");
    }

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
