package org.jenkins.plugins.jenkinspost;

import java.util.logging.Logger;

import hudson.Plugin;

public class JenkinsPost extends Plugin {
	public final static Logger LOG = Logger.getLogger(JenkinsPost.class.getName());
	
	public void start() {
		LOG.info("Starting JenkinsPOST plugin...");
	}
}
