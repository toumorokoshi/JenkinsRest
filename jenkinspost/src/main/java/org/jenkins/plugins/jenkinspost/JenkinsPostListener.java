package org.jenkins.plugins.jenkinspost;

import hudson.Extension;
import hudson.model.TaskListener;
import hudson.model.Run;
import hudson.model.listeners.RunListener;

@Extension
public class JenkinsPostListener extends RunListener<Run> {
	
	public JenkinsPostListener() {
		super(Run.class);
	}
	
	@Override
	public void onCompleted(Run r, TaskListener listener) {
		if(JenkinsPost.getSendGlobalPosts()) {
			JenkinsPost.LOG.info(r.getId());
			JenkinsPost.LOG.info(r.getFullDisplayName());
			JenkinsPost.LOG.info(r.getUrl());
			JenkinsPost.LOG.info(r.getTimestamp().toString());
			JenkinsPost.LOG.info(r.getSearchName());
			JenkinsPost.LOG.info(r.getParent().getDisplayName());
			JenkinsPost.LOG.info(r.getBuildStatusSummary().message);
			JenkinsPost.LOG.info("Listened!");
		}
	}

}
