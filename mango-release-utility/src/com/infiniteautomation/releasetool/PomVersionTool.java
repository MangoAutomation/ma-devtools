/**
 * Copyright (C) 2014 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.infiniteautomation.releasetool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.infiniteautomation.maven.Dependency;
import com.infiniteautomation.maven.Model;
import com.infiniteautomation.maven.Model.Dependencies;

/**
 * Very simple class to help re-version all module pom files when a new release is done
 * 
 * 
 * @author Terry Packer
 *
 */
public class PomVersionTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String pomName = "pom.xml";
//		File pom = new File(pomName);
		String coreVersion = "2.6.2";
		PomVersionTool tool = new PomVersionTool();

		List<String> moduleDirectories = new ArrayList<String>();
		moduleDirectories.add("/Users/tpacker/Documents/Work/Infinite/dev/git/infiniteautomation/ma-modules-public");
		moduleDirectories.add("/Users/tpacker/Documents/Work/Infinite/dev/git/infiniteautomation/ma-modules-private");
		moduleDirectories.add("/Users/tpacker/Documents/Work/Infinite/dev/git/infiniteautomation/ma-modules-proprietary");

		for(String moduleDirectory : moduleDirectories){
			File dir = new File(moduleDirectory);
			if (dir.exists())
				tool.changeAllPomsCoreVersion(dir, pomName, coreVersion);
		}

//		try {
//			tool.alterCoreVersion(pom, coreVersion);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}

	}

	/**
	 * Alter the core version of a POM File
	 * 
	 * @param pom
	 * @param coreVersion
	 * @throws JAXBException
	 */
	public void alterCoreVersion(File pom, String coreVersion)
			throws JAXBException {
		if (!pom.exists())
			return;

		Model model;

		JAXBContext jc = JAXBContext.newInstance(Model.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		JAXBElement<?> element = (JAXBElement<?>) unmarshaller.unmarshal(pom);
		model = (Model) element.getValue();
		Dependencies deps = model.getDependencies();
		Dependency coreDep = null;
		if(deps != null){
			for (Dependency dep : deps.getDependency()) {
				if (dep.getGroupId().equals("com.infiniteautomation")
						&& (dep.getArtifactId().equals("mango"))) {
					coreDep = dep;
					break;
				}
			}
		}
		if (coreDep != null) {
			System.out.println("Core Version Number: " + coreDep.getVersion());
			coreDep.setVersion(coreVersion);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller
					.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
							"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");

			marshaller.marshal(element, pom);

		} else
			System.out.println("No Core Dep Found!");
	}

	/**
	 * Change all Poms in a directory
	 * @param directory
	 * @param fileNameToSearch
	 * @param coreVersion
	 */
	public void changeAllPomsCoreVersion(File directory,
			String fileNameToSearch, String coreVersion) {

		if (directory.isDirectory()) {
			searchRecursively(directory, fileNameToSearch, coreVersion);
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
	private void searchRecursively(File file, String fileNameToSearch,
			String coreVersion) {

		//Are we a directory AND we don't start with . (hidden)
		if (file.isDirectory()&&!file.getName().startsWith(".")) {
			System.out.println("Searching directory ... "
					+ file.getAbsoluteFile());
			// do you have permission to read this directory?
			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						searchRecursively(temp, fileNameToSearch, coreVersion);
					} else {
						if (fileNameToSearch.equals(temp.getName()
								.toLowerCase())) {
							try {
								alterCoreVersion(temp, coreVersion);
							} catch (JAXBException e) {
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
