package org.jenkins.plugins.jenkinspost;

import java.io.IOException;
import java.util.*;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;

/* {@link Notifier}
 * 
 */
public class JenkinsPostNotifier extends Notifier {

	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}
	
	public String getMyString() {
		return "Hello Jenkins!";
	}
	
	@Override
	public boolean needsToRunAfterFinalized() { return false; }
	
	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
		JenkinsPost.LOG.info("Jenkins Post Notified!");
		return true;
	}

	@DataBoundConstructor
	public JenkinsPostNotifier() {
	}

}
