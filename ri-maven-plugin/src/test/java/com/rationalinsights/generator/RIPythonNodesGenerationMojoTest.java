package com.rationalinsights.generator;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

/**
 * Test for {@link RIPythonNodesGenerationMojo}.
 */
public class RIPythonNodesGenerationMojoTest {

    @Rule
    public TestResources resources = new TestResources();

    @Rule
    public MojoRule rule = new MojoRule() {

        @Override
        protected void before() throws Throwable {
            super.before();
        }

        @Override
        protected void after() {
            super.after();
        }
    };

    @Test
    public void testExecute() throws Exception {
        File projectDir = resources.getBasedir("");
        File pomFile = new File(projectDir, "pom.xml");

        Assert.assertNotNull(pomFile);
        Assert.assertTrue(pomFile.exists());

        RIPythonNodesGenerationMojo pythonNodesGenerationMojo =
                (RIPythonNodesGenerationMojo) rule.lookupMojo("generate", pomFile);

        Assert.assertNotNull(pythonNodesGenerationMojo);

        pythonNodesGenerationMojo.execute();
    }
}
