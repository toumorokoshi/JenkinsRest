package org.jenkins.plugins.jenkinspost;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;

@Extension
public class JenkinsPostDescriptor extends BuildStepDescriptor<Publisher> {
	
	public JenkinsPostDescriptor() {
		super(JenkinsPostNotifier.class);
		load();
	}

	@Override
	public boolean isApplicable(Class<? extends AbstractProject> jobType) {
		return true;
	}

	@Override
	public String getDisplayName() {
		return "Jenkins Post Plugin";
	}
}
