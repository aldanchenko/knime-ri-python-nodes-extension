package com.rationalinsights.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.rationalinsights.generator.model.Node;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        if (Objects.isNull(nodesJsonPath)) {
            throw new MojoExecutionException("Can't find nodes.json");
        }

        File nodesJsonFile = new File(nodesJsonPath);
        log.info(nodesJsonFile.getAbsolutePath());
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

            for (Node node : nodes) {
                log.info(node.getName());
            }
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException("Error on nodes.json file read.");
        }

        log.info("End generate Nodes.");
    }
}
