package org.jenkins.plugins.jenkinsrest;

import java.io.IOException;

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
public class JenkinsRestNotifier extends Notifier {

	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}
	
	public String getMyString() {
        return "";
	}
	
	@Override
	public boolean needsToRunAfterFinalized() { return false; }
	
	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
		JenkinsRest.LOG.info("Jenkins REST Notified!");
		return true;
	}

	@DataBoundConstructor
	public JenkinsRestNotifier() {
	}

}
