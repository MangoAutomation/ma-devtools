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
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author Terry Packer
 *
 */
public class BulkModuleUploader {

	public static void main(String[] args) {

		if((args == null)||(args.length != 3)){
			throw new RuntimeException("Expected params: email password path-to-modules-dir");
		}
		
		BulkModuleUploader uploader = new BulkModuleUploader();
		
		String email = args[0];
		String password = args[1];
		String moduleDir = args[2];
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
					uploader.startUploadMonitor(httpclient);
					uploader.postModule(httpclient, module);
					System.out.println("**** Module " + module.getAbsolutePath() + " Uploaded ****");
				}
				
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}
	private final static String HTTP_BASE = "https://";
	private final static String BASE_STORE_URL = "mangoautomation.net";
	private final static String STORE_PORT = "8443";
	private final static String STORE_LOGIN_URL = HTTP_BASE + BASE_STORE_URL + ":" + STORE_PORT + "/login";
	private final static String UPLOAD_MONITOR_URL = HTTP_BASE + BASE_STORE_URL + ":" + STORE_PORT + "/dwr/call/plaincall/AccountDwr.startUpload.dwr";
	private final static String MODULE_UPLOAD_URL = HTTP_BASE + BASE_STORE_URL + ":" + STORE_PORT + "/account/modules";
	
	private String sessionId;
	
	
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

		HttpPost httppost = new HttpPost(STORE_LOGIN_URL);

		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("submit", "Login"));
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
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
		    			if(header.getName().equals("Set-Cookie")){
		    				//Set our cookie here
		    				String[] cookiePath = header.getValue().split(";");
		    				//Now we have JSESSIONID=stuff , Path=/
		    				String[] cookie = cookiePath[0].split("=");
		    				String[] path = cookiePath[1].split("=");
		    				this.sessionId = cookie[1];
		    				BasicClientCookie c = new BasicClientCookie(cookie[0], cookie[1]);
		    				c.setDomain(BASE_STORE_URL);
		    				c.setPath(path[1]);
		    				cookieStore.addCookie(c);	
		    			}
		    		}
		    	}
		    } finally {
		        instream.close();
		    }
		}
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
	
	public void startUploadMonitor(HttpClient httpclient) throws ClientProtocolException, IOException{
		
		HttpPost httppost = new HttpPost(UPLOAD_MONITOR_URL);

		StringBuilder payload = new StringBuilder();
		payload.append("callCount=1\n");
		payload.append("page=/account/modules\n");
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
		    	if(response.getStatusLine().getStatusCode() > 399){
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
	public void postModule(HttpClient httpclient, File module) throws ClientProtocolException, IOException{
		
		HttpPost httppost = new HttpPost(MODULE_UPLOAD_URL);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addTextBody("clobber", "true");
		builder.addBinaryBody("uploadFile", module, ContentType.create("application/zip"), module.getName());
		httppost.setEntity(builder.build());
		
		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
		    InputStream instream = entity.getContent();
		    try {
		    	if(response.getStatusLine().getStatusCode() > 399){
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
	 * Helper to print out the response
	 * @param is
	 */
	private void printContent(InputStream is){
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
