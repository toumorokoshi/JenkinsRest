package org.jenkins.plugins.jenkinsrest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.StringEntity;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Plugin;
import hudson.util.ListBoxModel;

public class JenkinsRest extends Plugin {
	public final static Logger LOG = Logger.getLogger(JenkinsRest.class.getName());
	private static HttpClient httpclient = new DefaultHttpClient();
	private static boolean sendGlobalRests; // send posts globally
    private static boolean globalRestIsPost; // determines whether the request is a get or a post
	private static String globalRestURL; // global URL to post to
	private static String globalRestContentType; // content type to send the request as
	private static String globalRestString; // String to post to the globalRestURL
	
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
		globalRestURL = formData.optString("globalRestURL");
		if(formData.has("sendGlobalRests")) {
			sendGlobalRests = true;
			globalRestURL = formData.getJSONObject("sendGlobalRests").getString("globalRestURL");
            globalRestIsPost = formData.getJSONObject("sendGlobalRests").has("globalRestIsPost");
			globalRestContentType = formData.getJSONObject("sendGlobalRests").getString("globalRestContentType");
			String s = formData.getJSONObject("sendGlobalRests").getString("globalRestString").substring(1);
			globalRestString = s.substring(0, s.length() - 1);
		} else {
			sendGlobalRests = false;
		}
		save();
	}
	
	public static boolean getSendGlobalRests() { return sendGlobalRests; }
    public static boolean getGlobalRestIsPost() { return globalRestIsPost; }
	public static String getGlobalRestURL() { return globalRestURL; }
	public static String getGlobalRestContentType() { return globalRestContentType; }
	public static String getGlobalRestString() { return globalRestString; }

	public static void sendRest(String postString) {
        try {
            if(globalRestIsPost) {
                sendPostRequest(globalRestURL, globalRestContentType, globalRestString);
            } else {
                sendGetRequest(globalRestURL);
            }
        } catch (IOException e) {
            LOG.severe("Unable to send rest api request!: " + e.getMessage());
            e.printStackTrace();
        }
	}
	
	public static ListBoxModel doFillGlobalRestContentTypeItems() {
		ListBoxModel items = new ListBoxModel();
		items.add("Javascript", "application/javascript");
		items.add("Json", "application/json");
		items.add("text", "text/plain");
		items.add("XML", "application/xml");
		return items;
	}

    private static void sendGetRequest(String restURL) throws IOException {
        HttpGet request = new HttpGet(restURL);
        httpclient.execute(request);
        request.releaseConnection();
    }

    private static void sendPostRequest(String restURL, String contentType, String restString) throws IOException {
        HttpPost request = new HttpPost(restURL);
        request.setEntity(new StringEntity(restString));
        request.setHeader("Content-type", contentType);
        httpclient.execute(request);
        request.releaseConnection();
    }
}
