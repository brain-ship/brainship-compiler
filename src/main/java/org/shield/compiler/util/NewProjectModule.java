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
import java.util.Vector;

/**
 * Module to Handle New Project 
 * Requests
 *
**/
public class NewProjectModule {
	
	// Don't let anyone instantiate this class.
	private NewProjectModule() {}
	
	
	// New CLI Project
	public static void commandLineNewProject(String st) 
	{
		if(st.indexOf(' ') == -1) {
			
			System.out.println("\nNew eXtensible SHIELD Project Wizard");
			System.out.print("\tProject Name : ");
			String pname = GlobalUtil.getInput();
			System.out.print("\tSource Root              [Java] : "+pname+"\\");
			String jsrc = GlobalUtil.getInput();
			System.out.print("\tBinaries Root            [Java] : "+pname+"\\");
			String jbin = GlobalUtil.getInput();
			System.out.print("\tCommon Root Package Name [Java] : ");
			String proot = GlobalUtil.getInput();
			newProject(pname, jsrc, jbin, proot);
			ShieldSwitch.loadProject(pname);
			
		} else {
			String args = "";
			
			for(int i = st.indexOf(" ")+1; i < st.length(); i++) {
				args+=st.charAt(i);
			}
			
			System.out.println("\nNew eXtensible SHIELD Project Wizard");
			System.out.println("\tProject Name : "+args);
			System.out.print("\tSource Root              [Java] : "+args+"\\");
			String jsrc = GlobalUtil.getInput();
			System.out.print("\tBinaries Root            [Java] : "+args+"\\");
			String jbin = GlobalUtil.getInput();
			System.out.print("\tCommon Root Package Name [Java] : ");
			String proot = GlobalUtil.getInput();
			newProject(args, jsrc, jbin, proot);
			ShieldSwitch.loadProject(args);
		}
	}
	
	// New Java Source File
	public static void newFile(String st)
	{
		if(Settings.pconf == null) {
			System.out.println("\tNo Project Loaded");
			return;
		}
		
		String fname="";
		if(st.indexOf(" ") == -1) {
			System.out.println("\nNew Source File");
			System.out.print("Name: ");
			fname = GlobalUtil.getInput();
		} else {
			for(int i = st.indexOf(" ")+1; i < st.length(); i++) {
				fname+=st.charAt(i);
			}
		}
		
		String cf = fname;
		fname+=".java";
		System.out.print("\tPackage : "+Settings.pconf.getPRoot()+".");
		String pack = GlobalUtil.getInput();
		String cp = pack;
		
		if("".equals(pack)) {
			pack = Settings.pconf.getPRoot();
			pack = pack.replace(".", "\\");
			(new File(Settings.pconf.getJSource()+"\\"+pack)).mkdirs();
		} else {
			Settings.pconf.newSubPackage(pack);
			pack = Settings.pconf.getPRoot()+"."+pack;
			pack = pack.replace(".", "\\");
			(new File(Settings.pconf.getJSource()+"\\"+pack)).mkdirs();
		}
		
		try {
			(new File(Settings.pconf.getJSource()+"\\"+pack+"\\"+fname)).createNewFile();
			NewFileManager nfm = new NewFileManager(cf, Settings.pconf.getJSource()+"\\"+pack, Settings.pconf.getPRoot()+"."+cp);
			nfm.create();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		Settings.pconf.newJavaClass(Settings.pconf.getJSource()+"\\"+pack+"\\"+fname);
		
		if(Settings.pathToEditor == null || "".equals(Settings.pathToEditor)) {
			System.out.println("\tDefault Editor not set : Set using 'setd editor path\\to\\editor'");
		} else {
			Vector<String> vect = new Vector<String>(1,1);
			vect.addElement("@echo off");
			vect.addElement("\""+Settings.pathToEditor+"\" \""+Settings.pconf.getJSource()+"\\"+pack+"\\"+fname+"\"");
			
			StreamFileWriter sfw = new StreamFileWriter("open.bat", vect);
			sfw.write();
			
			try {
				ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "open.bat");
				File dir = new File(Settings.path);
				pb.directory(dir);
				pb.start();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	// Internal Method
	public static void newProject(String pname, String jsrc, String jbin, String proot) 
	{
		String tpath = Settings.path;
		tpath+=pname+"\\";
		(new File(tpath)).mkdir();
		(new File(tpath+jsrc)).mkdir();
		(new File(tpath+jbin)).mkdir();
		String proot2 = proot.replace(".", "\\");
		(new File(tpath+jsrc+"\\"+proot2)).mkdirs();
		tpath = "$DEF_PATH\\"+pname+"\\";
		Vector<String> vec = new Vector<String>(1,1);
		vec.addElement("PROJECT_NAME:"+pname);
		vec.addElement("PROJECT_PATH:"+tpath);
		vec.addElement("PROJECT_JAVA_SRC_PATH:"+(tpath+jsrc+"\\"));
		vec.addElement("PROJECT_JAVA_BIN_PATH:"+(tpath+jbin+"\\"));
		vec.addElement("PROJECT_JAVA_TRPACK:"+proot);
		StreamFileWriter srw = new StreamFileWriter(pname+".shieldproject", vec);
		srw.write();
	}
}
