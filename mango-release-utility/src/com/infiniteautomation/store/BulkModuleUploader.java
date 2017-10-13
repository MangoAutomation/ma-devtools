/**
 * Copyright (C) 2015 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.infiniteautomation.store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author Terry Packer
 *
 */
public class BulkModuleUploader {

	/**
	 * 5 Arguments:
	 * storeUrl - String
	 * username - String
	 * password - String
	 * path-to-module-zip-dir - String
	 * verbose - String ['true'/'false']
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if((args == null)||(args.length < 4)){
			throw new RuntimeException("Expected params: url email password path-to-modules-dir [verbose]");
		}
		
		if((args.length == 5)&&(args[4].equals("true")))
			setupLogging();
		
		BulkModuleUploader uploader = new BulkModuleUploader(args[0]);
		
		String email = args[1];
		String password = args[2];
		String moduleDir = args[3];
		try {
			HttpClient httpclient = uploader.login(email, password);
			System.out.println("Logged in as: " + email);
			
			//Upload Modules
			File dir = new File(moduleDir);
			if(dir.isDirectory()){
				File[] modules = dir.listFiles(new FilenameFilter(){
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".zip");
					}
				});
				
				for(File module : modules){
					System.out.println("**** Uploading " + module.getAbsolutePath() + "... ****");
					uploader.startUploadMonitor(httpclient, "modules");
					uploader.postFile(httpclient, module);
					System.out.println("**** Module " + module.getAbsolutePath() + " Uploaded ****");
				}
				
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}
	
	protected String sessionId;
	protected final String loginUrl;
	protected final String uploadMonitorUrl;
	protected final String moduleUploadUrl;
	protected final String cookieDomain;

	/**
	 * 
	 * @param url without ending slash ie https://store.infiniteautomation.com
	 */
	public BulkModuleUploader(String url){
		this.loginUrl = url + "/login";
		this.uploadMonitorUrl = url + "/dwr/call/plaincall/AccountDwr.startUpload.dwr";
		this.moduleUploadUrl = url + "/account/modules";
		
		String[] parts = url.split("//");
		this.cookieDomain = parts[1];
	}
	
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpClient login(String email, String password) throws ClientProtocolException, IOException{
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClient httpclient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

		HttpPost httppost = new HttpPost(loginUrl);

		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("submit", "Login"));
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		boolean locationHeader = false;
		if (entity != null) {
		    InputStream instream = entity.getContent();
		    try {
		        // do something useful
		    	if(response.getStatusLine().getStatusCode() > 399){
		    		for(Header header : response.getAllHeaders()){
		    			System.out.println(header.getName() + "-->" + header.getValue());
		    		}
		    		printContent(instream);
		    		throw new IOException("Login failed: " + response.getStatusLine().getReasonPhrase());
		    	}else{
		    		for(Header header : response.getAllHeaders()){
		    			System.out.println(header.getName() + "-->" + header.getValue());
		    			if(header.getName().equals("Location"))
		    				locationHeader = true; //we should be redirected to /account/licenses on success
		    			if(header.getName().equals("Set-Cookie")){
		    				//Set our cookie here
		    				String[] cookiePath = header.getValue().split(";");
		    				//Now we have JSESSIONID=stuff , Path=/
		    				String[] cookie = cookiePath[0].split("=");
		    				String[] path = cookiePath[1].split("=");
		    				this.sessionId = cookie[1];
		    				BasicClientCookie c = new BasicClientCookie(cookie[0], cookie[1]);
		    				c.setDomain(cookieDomain);
		    				c.setSecure(loginUrl.startsWith("https"));
		    				c.setPath(path[1]);
		    				c.setVersion(0);
		    				cookieStore.addCookie(c);	
		    			}
		    		}
		    	}
		    } finally {
		        instream.close();
		    }
		}
		if(!locationHeader)
			System.out.println("\n\n===== !!!! ### !! $ NO LOCATION HEADER LOGIN LIKELY FAILED $ !! ### !!!! =====\n\n");
		return httpclient;
	}
	
	/*
	 * We may need to compute a new script session id
	 *  The original page id sent from the server 
		dwr.engine._origScriptSessionId = "46EAA8AE7D0393F38917E4B0FF0D07C3";
		
		The function that we use to fetch/calculate a session id 
		dwr.engine._getScriptSessionId = function() {
		  if (dwr.engine._scriptSessionId == null) {
		    dwr.engine._scriptSessionId = dwr.engine._origScriptSessionId + Math.floor(Math.random() * 1000);
		  }
		  return dwr.engine._scriptSessionId;
		};

	 */
	
	/**
	 * 
	 * @param httpclient
	 * @param page
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void startUploadMonitor(HttpClient httpclient, String page) throws ClientProtocolException, IOException{
		
		HttpPost httppost = new HttpPost(uploadMonitorUrl);

		StringBuilder payload = new StringBuilder();
		payload.append("callCount=1\n");
		payload.append("page=/account/" + page + "\n");
		payload.append("httpSessionId=" + sessionId + "\n");
		payload.append("scriptSessionId=46EAA8AE7D0393F38917E4B0FF0D07C3280\n");
		payload.append("c0-scriptName=AccountDwr\n");
		payload.append("c0-methodName=startUpload\n");
		payload.append("c0-id=0\n");
		payload.append("batchId=0\n");
				
		httppost.setEntity(new StringEntity(payload.toString()));
		
		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
		    InputStream instream = entity.getContent();
		    try {
		    	if(response.getStatusLine().getStatusCode() > 300){
		    		for(Header header : response.getAllHeaders()){
		    			System.out.println(header.getName() + "-->" + header.getValue());
		    		}
			    	printContent(instream);
		    		throw new IOException("Upload Failed: " + response.getStatusLine().getReasonPhrase());
		    	}
		    } finally {
		        instream.close();
		    }
		}
	}
	
	/**
	 * 
	 * @param module
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void postFile(HttpClient httpclient, File file) throws ClientProtocolException, IOException{
		
		HttpPost httppost = new HttpPost(moduleUploadUrl);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addTextBody("clobber", "true");
		builder.addBinaryBody("uploadFile", file, ContentType.create("application/zip"), file.getName());
		httppost.setEntity(builder.build());
		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		
		if (entity != null) {
		    InputStream instream = entity.getContent();
		    try {
		    	if(response.getStatusLine().getStatusCode() > 300){
		    		for(Header header : response.getAllHeaders()){
		    			System.out.println(header.getName() + "-->" + header.getValue());
		    		}
			    	printContent(instream);
		    		throw new IOException("Upload Failed: " + response.getStatusLine().getReasonPhrase());
		    	}
		    } finally {
		        instream.close();
		    }
		}
	}
	
	/**
	 * 
	 */
	protected static void setupLogging() {
		System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire", "DEBUG");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.impl.conn", "DEBUG");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.impl.client", "DEBUG");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.client", "DEBUG");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");
	}
	
	/**
	 * Helper to print out the response
	 * @param is
	 */
	protected void printContent(InputStream is){
	    String inputLine;
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    try {
	          while ((inputLine = br.readLine()) != null) {
	                 System.out.println(inputLine);
	          }
	          br.close();
	     } catch (IOException e) {
	          e.printStackTrace();
	     }

	}
	
}
