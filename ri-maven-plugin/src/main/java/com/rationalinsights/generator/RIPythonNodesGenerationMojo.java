package com.rationalinsights.generator;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

        Gson gson = new Gson();

        try {
            JsonReader jsonReader = new JsonReader(new FileReader(nodesJsonFile));

            List<Node> nodes = gson.fromJson(jsonReader, List.class);

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
