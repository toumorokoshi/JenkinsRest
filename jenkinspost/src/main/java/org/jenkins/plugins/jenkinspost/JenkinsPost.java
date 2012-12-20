package org.jenkins.plugins.jenkinspost;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

import hudson.Plugin;

public class JenkinsPost extends Plugin {
	public final static Logger LOG = Logger.getLogger(JenkinsPost.class.getName());
	private static boolean sendGlobalPosts; // send posts globally 
	private static String globalPostURL; // global URL to post to
	private static String globalPostContentType; // content type to send the request as 
	private static String globalPostString; // String to post to the globalPostURL
	
	public void start() throws IOException {
		LOG.info("Starting JenkinsPOST plugin...");
		load();
	}

	/*
	 * The format parsing here is strongly tied with the config.jelly file under 
	 * the corresponding path. The form is returned as a json object through 
	 * formData.
	 */
	@Override
	public void configure(StaplerRequest req, JSONObject formData) throws IOException {
		globalPostURL = formData.optString("globalPostURL");
		if(formData.has("sendGlobalPosts")) {
			sendGlobalPosts = true;
			globalPostURL = formData.getJSONObject("sendGlobalPosts").getString("globalPostURL");
			globalPostContentType = formData.getJSONObject("sendGlobalPosts").getString("globalPostContentType");
			globalPostString = formData.getJSONObject("sendGlobalPosts").getString("globalPostString");
		} else {
			sendGlobalPosts = false;
		}
		save();
	}
	
	public static boolean getSendGlobalPosts() { return sendGlobalPosts; }
	public static String getGlobalPostURL() { return globalPostURL; }
	public static String getGlobalPostContentType() { return globalPostContentType; }
	public static String getGlobalPostString() { return globalPostString; }
	
	public static void sendPost(String postString) {
		sendPost(globalPostURL, globalPostContentType, postString);
	}
	
	public static void sendPost(String postURL, String contentType, String postString) {
			URL url;
			try {
				url = new URL(postURL);
				HttpURLConnection connection;
				connection = (HttpURLConnection) url.openConnection();
				connection.setUseCaches(false);
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
			} catch (MalformedURLException e) {
				LOG.info("Malformed url: " + postURL);
				e.printStackTrace();
			} catch (IOException e) {
				LOG.info("Couldn't open connection to url: " + postURL);
				e.printStackTrace();
			}
	}
}
