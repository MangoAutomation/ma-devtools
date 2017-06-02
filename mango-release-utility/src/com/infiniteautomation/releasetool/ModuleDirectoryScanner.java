/**
 * Copyright (C) 2015 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.infiniteautomation.releasetool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Terry Packer
 *
 */
public abstract class ModuleDirectoryScanner {

	protected List<String> moduleDirectories;
	protected String fileNameToSearch;
	protected boolean verbose;
	
	public ModuleDirectoryScanner(String fileNameToSearch, boolean verbose){
		this.moduleDirectories = new ArrayList<String>();
		this.moduleDirectories.add("/Users/tpacker/Documents/Work/Infinite/dev/development/git/infiniteautomation/ma-modules-public");
		this.moduleDirectories.add("/Users/tpacker/Documents/Work/Infinite/dev/development/git/infiniteautomation/ma-modules-private");
		this.moduleDirectories.add("/Users/tpacker/Documents/Work/Infinite/dev/development/git/infiniteautomation/ma-modules-proprietary");
		this.moduleDirectories.add("/Users/tpacker/Documents/Work/Infinite/dev/development/git/infiniteautomation/ma-dashboards");
		
		this.fileNameToSearch = fileNameToSearch;
		this.verbose = verbose;
	}
	
	public void scan() throws Exception{
		for(String moduleDirectory : moduleDirectories){
			File dir = new File(moduleDirectory);
			if (dir.isDirectory() && dir.exists()) {
				searchRecursively(dir, fileNameToSearch);
			}
		}
	}

	/**
	 * Method called when matching file is found
	 * @param file
	 * @throws Exception
	 */
	protected abstract void foundFile(File file) throws Exception;
	
	/**
	 * Recursive Search in a directory
	 * @param file
	 * @param fileNameToSearch
	 * @param coreVersion
	 */
	protected void searchRecursively(File file, String fileNameToSearch) {

		//Are we a directory AND we don't start with . (hidden) AND it's not the build dir
		if (file.isDirectory()&&!file.getName().startsWith(".")&&(!file.getName().equals("maven-target"))) {
			if(verbose)
				System.out.println("Searching directory ... " + file.getAbsoluteFile());
			// do you have permission to read this directory?
			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						searchRecursively(temp, fileNameToSearch);
					} else {
						if(verbose)
							System.out.println("Processing " + temp.getAbsolutePath());
						if (fileNameToSearch.equals(temp.getName())) {
							try {
								//Do the work
								if(verbose)
									System.out.println("Found file: " + temp.getAbsolutePath());
								this.foundFile(temp);
							} catch (Exception e) {
								System.out.println("Problem with file: " + temp.getAbsolutePath());
								e.printStackTrace();
							}
						}
					}
				}
			} else {
				System.out.println(file.getAbsoluteFile() + "Permission Denied");
			}
		}
	}
	
	
	
}
