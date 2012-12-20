package org.jenkins.plugins.jenkinspost;

import java.io.IOException;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

import hudson.Plugin;

public class JenkinsPost extends Plugin {
	public final static Logger LOG = Logger.getLogger(JenkinsPost.class.getName());
	private static boolean sendGlobalPosts;
	private static String globalPostURL;
	
	public void start() throws IOException {
		LOG.info("Starting JenkinsPOST plugin...");
		load();
	}
	
	@Override
	public void configure(StaplerRequest req, JSONObject formData) throws IOException {
		globalPostURL = formData.optString("globalPostURL");
		if(formData.has("sendGlobalPosts")) {
			sendGlobalPosts = true;
			globalPostURL = formData.getJSONObject("sendGlobalPosts").getString("globalPostURL");
		} else {
			sendGlobalPosts = false;
		}
		save();
	}
	
	public static boolean getSendGlobalPosts() { return sendGlobalPosts; }
	public static String getGlobalPostURL() { return globalPostURL; }
}
