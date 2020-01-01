/*
 * Part of Rubidium Projects
 * General Public Licence v3.0, 2019
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.shield.compiler.util;

import java.io.*;
import java.util.*;

/**
* Holds Currently opened Project Configaration
*
*/
public class ProjectConfigaration
{
	// Actual Read Data
	private Vector<String> conf;
	
	// Configaration File Name Reference
	private String pconf;
	
	// Project Name
	private String pname;
	
	// Project Root
	private String proot;
	
	// Java Source
	private String jsrc;
	
	// Java Source Actual Reference
	private String jsrct;
	
	// Java Binaries
	private String jbin;
	
	// Java Binaries Actual Reference
	private String jbint;
	
	// Package Root
	private String packroot;
	
	// Subpackages
	private Vector<String> subpack;
	
	// Java Files
	private Vector<String> jfiles;
	
	// Main Java File
	private String executable;
	
	// Default Constructor
	public ProjectConfigaration(String conf) throws IOException{
		if(!(new File(conf)).exists()) {
			System.out.println("\tProject Does not exist");
			throw new IOException();
		}
		pconf=conf;
		StreamFileReader srd = new StreamFileReader(conf);
		this.conf = srd.read();
		this.subpack = new Vector<String>(1,1);
		this.jfiles = new Vector<String>(1,1);
		parse();
	}
	
	// Get Methods
	
	public String getName() {
		return pname;
	}
	
	public String getProjectRoot() {
		return ShieldSwitch.multiReplace(proot, '\\');
	}
	
	public String getJSource() {
		return ShieldSwitch.multiReplace(jsrc, '\\');
	}
	
	public String getJBin() {
		return ShieldSwitch.multiReplace(jbin, '\\');
	}
	
	public String getPRoot() {
		return ShieldSwitch.multiReplace(packroot, '\\');
	}
	
	public Vector<String> getSubPackages() {
		return subpack;
	}
	
	public Vector<String> getJavaFiles() {
		return jfiles;
	}
	
	public String getExecutable() {
		return executable;
	}
	
	// Exposed new class method
	public void newJavaClass(String st) {
		jfiles.addElement(st);
	}
	
	// Exposed new subpackage method
	public void newSubPackage(String st) {
		subpack.addElement(st);
	}
	
	// Default Method
	private void parse() {
		String[] arr = new String[conf.size()];
		
		for(int i = 0; i < arr.length; i++)
			arr[i] = conf.elementAt(i);
		
		for(int i = 0; i < arr.length; i++)
		{
			String t = arr[i];
			if(t.startsWith("PROJECT_JAVA_SRC_PATH")) jsrct = t.substring(t.indexOf(":")+1);
			if(t.startsWith("PROJECT_JAVA_BIN_PATH")) jbint = t.substring(t.indexOf(":")+1);
		}
		
		for(int i = 0; i < arr.length; i++)
			arr[i] = arr[i].replace("$DEF_PATH", Settings.path);
		
		for(int i = 0; i < arr.length; i++) {
			String t = arr[i];
			if(t.startsWith("PROJECT_NAME")) pname = t.substring(t.indexOf(":")+1);
			
			if(t.startsWith("PROJECT_PATH")) proot = t.substring(t.indexOf(":")+1);
			
			if(t.startsWith("PROJECT_JAVA_SRC_PATH")) jsrc = t.substring(t.indexOf(":")+1);
			
			if(t.startsWith("PROJECT_JAVA_BIN_PATH")) jbin = t.substring(t.indexOf(":")+1);
			
			if(t.startsWith("PROJECT_JAVA_TRPACK")) packroot = t.substring(t.indexOf(":")+1);
			
			if("<spackage>".equals(t)) {
				Vector<String> sp = new Vector<String>(1,1);
				int j = 0;
				for(j = i+1; j < arr.length; j++) {
					if("</spackage>".equals(arr[j])) break;
					sp.addElement(arr[j]);
				}
				subpack = sp;
				i = j;
			}
			
			if("<jfiles>".equals(t)) {
				Vector<String> sp = new Vector<String>(1,1);
				int j = 0;
				for(j = i+1; j < arr.length; j++) {
					if("</jfiles>".equals(arr[j])) break;
					sp.addElement(arr[j]);
				}
				jfiles = sp;
				i = j;
			}
			
			if(t.startsWith("EXECUTABLE")) executable = t.substring(t.indexOf(":"));
		}
	}
	
	// Rewrite New Configaration to file
	public void rewrite() {
		Vector<String> nconf = new Vector<String>(1,1);
		String tpath = "$DEF_PATH";
		
		nconf.addElement("PROJECT_NAME:"+pname);
		
		nconf.addElement("PROJECT_PATH:"+tpath+"\\"+pname+"\\");
		
		nconf.addElement("PROJECT_JAVA_SRC_PATH:"+(jsrct+"\\"));
		
		nconf.addElement("PROJECT_JAVA_BIN_PATH:"+(jbint+"\\"));
		
		nconf.addElement("PROJECT_JAVA_TRPACK:"+packroot);
		
		nconf.addElement("<spackage>");
		
		if(subpack!=null) for(int i =0; i < subpack.size(); i++) nconf.addElement(subpack.elementAt(i));
		
		nconf.addElement("</spackage>");
		
		nconf.addElement("<jfiles>");
		
		if(jfiles!=null) for(int i =0; i < jfiles.size(); i++) nconf.addElement(jfiles.elementAt(i));
		
		nconf.addElement("</jfiles>");
		
		nconf.addElement("EXECUTABLE:"+executable);
		
		StreamFileWriter sfw = new StreamFileWriter(pconf, nconf);
		
		sfw.write();
	}
}
