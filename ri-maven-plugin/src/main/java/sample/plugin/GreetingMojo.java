package sample.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;

/**
 * Says "Hi" to the user.
 */
@Mojo(name = "sayhi", defaultPhase = LifecyclePhase.INITIALIZE)
public class GreetingMojo extends AbstractMojo {
    public void execute() throws MojoExecutionException {
        getLog().info("Generate Python code for RI");
    }
}
