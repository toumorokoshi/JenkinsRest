package org.jenkins.plugins.jenkinsrest;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;

@Extension
public class JenkinsRestDescriptor extends BuildStepDescriptor<Publisher> {
	
	public JenkinsRestDescriptor() {
		super(JenkinsRestNotifier.class);
		load();
	}

	@Override
	public boolean isApplicable(Class<? extends AbstractProject> jobType) {
		return true;
	}

	@Override
	public String getDisplayName() {
		return "Call or send data to a rest api";
	}
}
