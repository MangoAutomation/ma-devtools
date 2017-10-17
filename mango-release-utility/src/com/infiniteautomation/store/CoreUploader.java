/**
 * Copyright (C) 2015 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.infiniteautomation.store;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

/**
 * @author Terry Packer
 *
 */
public class CoreUploader extends BulkModuleUploader{

	/**
	 * 5 Arguments:
	 * storeUrl - String
	 * username - String
	 * password - String
	 * path-to-core-zip - String
	 * verbose - String ['true'/'false']
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if((args == null)||(args.length < 4)){
			throw new RuntimeException("Expected params: url email password path-to-core-zip [verbose]");
		}
		
		if((args.length == 5)&&(args[4].equals("true")))
			setupLogging();
		
		CoreUploader uploader = new CoreUploader(args[0]);
		
		String email = args[1];
		String password = args[2];
		String core = args[3];
		try {
			HttpClient httpclient = uploader.login(email, password);
			System.out.println("Logged in as: " + email);
			
			//Upload Modules
			File coreFile = new File(core);
			if(coreFile.exists()) {
                System.out.println("**** Uploading " + coreFile.getAbsolutePath() + "... ****");
                uploader.startUploadMonitor(httpclient, "core");
                uploader.postFile(httpclient, coreFile, "core");
                System.out.println("**** " + coreFile.getAbsolutePath() + " Uploaded ****");
			}else {
			    throw new RuntimeException("No core file found!");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

	/**
	 * 
	 * @param url without ending slash ie https://store.infiniteautomation.com
	 */
	public CoreUploader(String url){
	    super(url);
	}
	
}
