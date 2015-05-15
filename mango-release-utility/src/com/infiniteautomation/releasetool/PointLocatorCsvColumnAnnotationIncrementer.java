/**
 * Copyright (C) 2015 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.infiniteautomation.releasetool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Terry Packer
 *
 */
public class PointLocatorCsvColumnAnnotationIncrementer {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String fileNameRegex = ".*PointLocatorModel.java";
//		File pom = new File(pomName);
		int incrementBy = 5;
		PointLocatorCsvColumnAnnotationIncrementer tool = new PointLocatorCsvColumnAnnotationIncrementer();

		List<String> moduleDirectories = new ArrayList<String>();
		moduleDirectories.add("/Users/tpacker/Documents/Work/Infinite/dev/git/infiniteautomation/ma-modules-public");
		moduleDirectories.add("/Users/tpacker/Documents/Work/Infinite/dev/git/infiniteautomation/ma-modules-private");
		moduleDirectories.add("/Users/tpacker/Documents/Work/Infinite/dev/git/infiniteautomation/ma-modules-proprietary");

		for(String moduleDirectory : moduleDirectories){
			File dir = new File(moduleDirectory);
			if (dir.exists())
				tool.incrementAllColumns(dir, fileNameRegex, incrementBy);
		}
	}
	
	
	/**
	 * @param temp
	 * @param incrementBy
	 */
	private void alterColumns(File temp, int incrementBy) {
		   try {
			   
		        // input the file content to the String "input"
		        BufferedReader file = new BufferedReader(new FileReader(temp));
		        String line;
		        StringBuilder builder = new StringBuilder();
		        String matchColumnGetterRegex = "@CSVColumnGetter\\(order=(.*),";
		        String matchColumnSetterRegex = "@CSVColumnSetter\\(order=(.*),";
		        Pattern getterPattern = Pattern.compile(matchColumnGetterRegex);
		        Pattern setterPattern = Pattern.compile(matchColumnSetterRegex);
		        Matcher getterMatcher, setterMatcher;
		        while ((line = file.readLine()) != null){ 
		        	
		        	getterMatcher = getterPattern.matcher(line);
		        	
		        	if(getterMatcher.find())
		        		line = line.replaceAll(matchColumnGetterRegex, "@CSVColumnGetter(order=" + String.valueOf(Integer.parseInt(getterMatcher.group(1)) + incrementBy)  + ",");
		        	else{
		        		setterMatcher = setterPattern.matcher(line);
		        		if(setterMatcher.find())
			        		line = line.replaceAll(matchColumnSetterRegex, "@CSVColumnSetter(order=" + String.valueOf(Integer.parseInt(setterMatcher.group(1)) + incrementBy)  + ",");
		        	}
		        	
		        	builder.append(line + '\n');
		        }
		        
		        
		        
		        file.close();

		        // write the new String with the replaced line OVER the same file
		        FileOutputStream fileOut = new FileOutputStream(temp);
		        fileOut.write(builder.toString().getBytes());
		        fileOut.close();

		    } catch (Exception e) {
		        System.out.println("Problem reading file.");
		    }
		
	}
	
	
	/**
	 * Change all Poms in a directory
	 * @param directory
	 * @param fileNameToSearch
	 * @param coreVersion
	 */
	public void incrementAllColumns(File directory,
			String fileNameRegex, int incrementBy) {

		if (directory.isDirectory()) {
			searchRecursively(directory, fileNameRegex, incrementBy);
		} else {
			System.out.println(directory.getAbsoluteFile()
					+ " is not a directory!");
		}

	}
	
	/**
	 * Recursive Search in a directory
	 * @param file
	 * @param fileNameToSearch
	 * @param coreVersion
	 */
	private void searchRecursively(File file, String filenameRegex,
			int incrementBy) {

		//Are we a directory AND we don't start with . (hidden)
		if (file.isDirectory()&&!file.getName().startsWith(".")) {
			System.out.println("Searching directory ... "
					+ file.getAbsoluteFile());
			// do you have permission to read this directory?
			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						searchRecursively(temp, filenameRegex, incrementBy);
					} else {
						if (temp.getName().matches(filenameRegex)) {
							try {
								alterColumns(temp, incrementBy);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			} else {
				System.out
						.println(file.getAbsoluteFile() + "Permission Denied");
			}
		}

	}
	
	
}
