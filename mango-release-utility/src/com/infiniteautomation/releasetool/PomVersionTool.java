/**
 * Copyright (C) 2014 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.infiniteautomation.releasetool;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.infiniteautomation.maven.Dependency;
import com.infiniteautomation.maven.Model;
import com.infiniteautomation.maven.Model.Dependencies;
import com.infiniteautomation.maven.Parent;

/**
 * Very simple class to help re-version all module pom files when a new release is done
 * 
 * 
 * @author Terry Packer
 *
 */
public class PomVersionTool extends ModuleDirectoryScanner{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PomVersionTool tool = new PomVersionTool("2.8.0");
		try {
			tool.scan();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	private String coreVersion;
	
	public PomVersionTool(String coreVersion){
		super("pom.xml", false);
		this.coreVersion = coreVersion;
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
		boolean found = false;
		
		JAXBContext jc = JAXBContext.newInstance(Model.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		JAXBElement<?> element = (JAXBElement<?>) unmarshaller.unmarshal(pom);
		model = (Model) element.getValue();
		Parent parent = model.getParent();
		
		
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
			coreDep.setVersion(coreVersion);
			found = true;
		} else if(parent != null){
			parent.setVersion(coreVersion);
			found = true;
		}
		
		if(found){
			System.out.println("Changing " + pom.getAbsolutePath() + " to use core version " + coreVersion);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
							"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");

			marshaller.marshal(element, pom);
		}else{
			System.out.println("No Core Dep Found in " + pom.getAbsolutePath());
		}
	}

	@Override
	protected void foundFile(File file) throws Exception{
		alterCoreVersion(file, coreVersion);
	}


}
