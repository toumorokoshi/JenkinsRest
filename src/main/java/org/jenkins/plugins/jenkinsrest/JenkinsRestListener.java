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
        JenkinsRest.notifyRun(r);
	}

}
