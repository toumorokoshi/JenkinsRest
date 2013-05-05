package org.jenkins.plugins.jenkinsrest;

import hudson.Extension;
import hudson.model.TaskListener;
import hudson.model.Run;
import hudson.model.listeners.RunListener;

@Extension
public class JenkinsRestListener extends RunListener<Run> {
	
	public JenkinsRestListener() {
		super(Run.class);
	}
	
	@Override
	public void onCompleted(Run r, TaskListener listener) {
		if(JenkinsRest.getSendGlobalRests()) {
			String postString = JenkinsRest.getGlobalRestString();
			postString = postString.replace("%DATETIME%", r.getTime().toString());
			postString = postString.replace("%BUILD_NUMBER%", Integer.toString(r.getNumber()));
			postString = postString.replace("%BUILD_ID%", r.getId());
			postString = postString.replace("%JOB_NAME%", r.getParent().getDisplayName());
			postString = postString.replace("%BUILD_MESSAGE%", 
								r.getBuildStatusSummary().message);
			JenkinsRest.sendRest(postString);
			JenkinsRest.LOG.info(postString);
			JenkinsRest.LOG.info("Rest request was sent");
		}
	}

}
