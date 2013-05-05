package org.jenkins.plugins.jenkinsrest;

import java.io.IOException;

import hudson.model.AbstractProject;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;

/* {@link Notifier}
 * @author Yusuke Tsutsumi
 */
public class JenkinsRestNotifier extends Notifier {

    public final boolean requestIsPost;
    public final boolean onSuccessOnly;
    public final String restURL;
    public final String requestContentType;
    public final String postString;

	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}
	
	@Override
	public boolean needsToRunAfterFinalized() { return false; }
	
	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        if(build.getResult() == Result.SUCCESS ||!onSuccessOnly) {
            String postString = (requestIsPost ?
                Utils.buildPostString(this.postString, build) : null);
            Utils.sendRest(requestIsPost, restURL,
                    requestContentType, postString);
            JenkinsRest.LOG.info("Rest request was sent:" + postString);
        }
		return true;
	}

	@DataBoundConstructor
	public JenkinsRestNotifier(boolean requestIsPost,
                               boolean onSuccessOnly,
                               String restURL,
                               String requestContentType,
                               String postString) {
        this.requestIsPost = requestIsPost;
        this.onSuccessOnly = onSuccessOnly;
        this.restURL = restURL;
        this.requestContentType = requestContentType;
        this.postString = postString;
	}

    @Extension
    public static final class DescriptorImpl
            extends BuildStepDescriptor<Publisher> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Jenkins Rest Notifier";
        }
    }
}
