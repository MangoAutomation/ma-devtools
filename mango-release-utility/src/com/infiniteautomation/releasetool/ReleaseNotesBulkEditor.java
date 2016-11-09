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
 * Class to add release notes for modules during major a core upgrade.
 * 
 * Properties 
 * coreVersion - 2.8.x used in the notes
 * notes - A list of string notes that will be applied to all RELEASE-NOTES files
 * newVersion - is this a new module version number (will increase the version number in the release notes)
 * newCore - is this a new core version (will add updated for core... note)
 * 
 * @author Terry Packer
 *
 */
public class ReleaseNotesBulkEditor extends ModuleDirectoryScanner{

	private static final String NEWLINE = "\n";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> notes = new ArrayList<String>();
		//notes.add("Adding Cron pattern polling option");
		//notes.add("Adding optional quantization for polls");
		boolean newMinorVersion = true;
		boolean newMicroVersion = false;
		boolean newCore = true;
		ReleaseNotesBulkEditor tool = new ReleaseNotesBulkEditor("2.8.x",notes, newMinorVersion, newMicroVersion ,newCore);
		try {
			tool.scan();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	private String coreVersion;
	private List<String> notes;
	private boolean newMinorVersion;
	private boolean newMicroVersion;
	private boolean newCore;
	
	public ReleaseNotesBulkEditor(String coreVersion, List<String> notes, boolean newMinorVersion, boolean newMicroVersion, boolean newCore){
		super("RELEASE-NOTES", false);
		this.coreVersion = coreVersion;
		this.notes = notes;
		this.newMinorVersion = newMinorVersion;
		this.newMicroVersion = newMicroVersion;
		this.newCore = newCore;
	}
	
	/* (non-Javadoc)
	 * @see com.infiniteautomation.releasetool.DirectoryScanner#foundFile(java.io.File)
	 */
	@Override
	protected void foundFile(File file) throws Exception {
		
		//Scan for the coreVersion line
		BufferedReader br = null;
		BufferedWriter bw = null;
		boolean modified = true;
		List<String> allLines = new ArrayList<String>(100);
		try{
			br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null){
				line = line.trim();
				allLines.add(line);
			}
			
			String newVersionString = null;
			boolean versionEdited = false;
			if(newMinorVersion || newMicroVersion){
				//Modify the contents
				//Get the latest version number
				String latestVersionLine = allLines.get(0);
				String[] parts = latestVersionLine.split("Version");
				//Should have *Version, {version}*
				if(parts.length < 2){
					System.out.println("Bad line : " + latestVersionLine);
				}
				String version = parts[1].replace("*", "");
				//Should have x.x.x
				String versionParts[] = version.split("\\.");
				if(versionParts.length != 3){
					System.out.println("Unable to edit version for file: " + file.getAbsolutePath());
				}else{
					if(newMinorVersion){
						Integer newMinorVersion = Integer.parseInt(versionParts[1].trim());
						newMinorVersion++;
						newVersionString = "*Version " + versionParts[0].trim() + "." + newMinorVersion + ".0*";
					}else if(newMicroVersion){
						Integer newMicroVersion = Integer.parseInt(versionParts[2].trim());
						newMicroVersion++;
						newVersionString = "*Version " + versionParts[0].trim() + "." + versionParts[1].trim() + "." + newMicroVersion + "*";
					}
					versionEdited = true;
				}
				
			}
			
			//Ensure we have space between the version notes
			boolean breakSpace = false;
			
			//Add the notes
			for(String note : notes){
				if(!breakSpace){
					breakSpace = true;
					allLines.add(0, "* " + note + NEWLINE);
				}else
					allLines.add(0,"* " + note);
			}
			if(newCore){
				if(!breakSpace){
					breakSpace = true;
					allLines.add(0, "* Upgraded to work with core version " + coreVersion + NEWLINE);
				}else
					allLines.add(0, "* Upgraded to work with core version " + coreVersion);
			}
			//Add the version line
			if(versionEdited)
				allLines.add(0, newVersionString);
			
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
