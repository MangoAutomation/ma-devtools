/**
 * Copyright (C) 2015 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.infiniteautomation.releasetool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class to re-version modules to match a new Major Core Version.
 * 
 * Basically replaces coreVersion=x.x to coreVersion=new.version
 * 
 * @author Terry Packer
 *
 */
public class ModulePropertiesBulkEditor extends ModuleDirectoryScanner{

	private static final String NEWLINE = "\n";
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ModulePropertiesBulkEditor tool = new ModulePropertiesBulkEditor("2.7", "2.8");
		try {
			tool.scan();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	private String previousVersion;
	private String currentVersion;
	
	public ModulePropertiesBulkEditor(String prev, String current){
		super("module.properties", false);
		this.previousVersion = prev;
		this.currentVersion = current;
	}
	
	/* (non-Javadoc)
	 * @see com.infiniteautomation.releasetool.DirectoryScanner#foundFile(java.io.File)
	 */
	@Override
	protected void foundFile(File file) throws Exception {
		
		//Scan for the coreVersion line
		BufferedReader br = null;
		BufferedWriter bw = null;
		boolean modified = false;
		List<String> allLines = new ArrayList<String>(100);
		try{
			br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null){
				if(line.startsWith("coreVersion")){
					line = line.trim();
					String[] parts = line.split("=");
					if(parts[1].equals(previousVersion)){
						line = "coreVersion=" + this.currentVersion;
						modified = true;
					}
				}
				allLines.add(line);
			}
			
			if(modified){
				System.out.println("Changing file: " + file.getAbsolutePath());
				//We have the lines, now re-write the file
				bw = new BufferedWriter(new FileWriter(file, false));
				for(String newLine : allLines){
					bw.write(newLine);
					bw.write(NEWLINE);
				}
				bw.flush();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
			if(br != null)
				br.close();
			if(bw != null)
				bw.close();
			}catch(Exception e){ }
		}
	}

	
	
}
