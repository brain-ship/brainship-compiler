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

import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
* A class used to switch the input to process
* programs
*
*/
public class ShieldSwitch
{

	// Don't let anyone instantiate this class.
	private ShieldSwitch() {}
	
	// Actual process called by other classes
	public static void switchProcess(String st) 
	{
		/*
		 exit / end  - Terminate
		 dir / ls    - Directory Listing
		 newp        - New Project
		 newf        - New File
		 loadp       - Load Project
		 setd        - Set Defaults for variables
		 compile     - Compiling the project
		 eman		 - Extension Manager
		 sread 		 - Stream Read
		 swrite      - Stream Write
		 help 		 - Synopsis of available commands
		*/
		if("exit".equals(st) || "end".equals(st)) {
			GlobalUtil.endProgram();
		}
		else if(st.startsWith("dir")) dirSwitch(st);
		
		else if(st.startsWith("ls")) lsSwitch(st);
		
		else if(st.startsWith("cd")) cdSwitch(st);
		
		else if(st.startsWith("newp")) NewProjectModule.commandLineNewProject(st);
		
		else if(st.startsWith("newf")) NewProjectModule.newFile(st);
		
		else if(st.startsWith("loadp")) loadProject(st);
		
		else if(st.startsWith("sread")) sread(st);
		
		else if(st.startsWith("swrite")) swrite(st);
		
		else if(st.startsWith("compile")) ShieldCompiler.compile(st);
		
		else if(st.startsWith("eman")) ExtensionManager.eman();
		
		else if("help".equals(st)) help();
		
		else System.out.println("\n\tInvalid Command! Type 'help' for a list of valid commands");
	} 
	
	public static void limitedSwitchProcess(String st) 
	{
		/*
		 dir / ls    - Directory Listing
		 setd        - Set Defaults for variables
		 sread 		 - Stream Read
		 swrite      - Stream Write
		*/
		if(st.startsWith("dir")) dirSwitch(st);
		 
		else if(st.startsWith("ls")) lsSwitch(st);
		
		else if(st.startsWith("cd")) cdSwitch(st);
		
		else if(st.startsWith("sread")) sread(st);
		
		else if(st.startsWith("swrite")) swrite(st);
		
		else System.out.println("\n\tInvalid Command! Type 'help' for a list of valid commands");
	}
	
	// Analagous to "cd" for change directory
	private static void cdSwitch(String st) {
		
		// "cd" -> .. to previous directory
		if(st.contains("..")) {
			int nplaces = occuranceOf(st, '\\') + 2;
			String tpath = Settings.path;
			
			for(int i = 0; i < nplaces; i++) {
				int idxo = tpath.lastIndexOf('\\');
				if(idxo == -1) break;
				tpath = tpath.substring(0, idxo);
			}
			
			if(!tpath.endsWith("\\")) tpath+="\\";
			Settings.path = tpath;
			return;
		}
		
		// Forward Directory Move
		int indexd = st.indexOf(' ');
		String args = st.substring(indexd+1);
		File file = new File(Settings.path+args);
		if(!file.isDirectory()) {
			System.out.println("\tInvalid Directory Specification!!");
			return;
		}
		Settings.path = Settings.path+args+"\\";
	}		
	
	// Analagous to "dir" for directory listing
	private static void dirSwitch(String st) {
		System.out.println();
		
		IndentationManager im = new IndentationManager();
		// General File Listing
		if("dir".equals(st)) {
			File[] f = listFiles(Settings.path);
			
			for(int i = 0; i < f.length; i++) {
				im.add(f[i]);
			}
			print(im.getIndentedStrings());
			return;
		}
		
		// File Listing of a specific extension
		int indexd = st.indexOf(' ');
		String args = st.substring(indexd+1);
		if("*".equals(args)) {
			
			File[] f = listFiles(Settings.path);
			for(int i = 0; i < f.length; i++) {
				im.add(f[i]);
			}
			print(im.getIndentedStrings());
			return;
			
		}
		
		if(args.startsWith("*")) {
			String extension = args.substring(args.indexOf('.')+1);
			File[] f = listFiles(Settings.path);
			
			for(int i = 0; i < f.length; i++) {
				if(f[i].getName().endsWith(extension))
					im.add(f[i]);
			}
			print(im.getIndentedStrings());
			return;
		}
		
		// File Listing of a subfolder
		String tpath = Settings.path + "/" + args;
		File ndir= new File(tpath);
		if(!ndir.isDirectory() || !ndir.exists()) {
			System.out.println("\tInvalid Directory Specification!!");
			return;
		}
		
		File[] f = listFiles(tpath);
		for(int i = 0; i < f.length; i++) {
			im.add(f[i]);
		}
		print(im.getIndentedStrings());
	}	
	
	// Analagous to "ls" for directory listing
	private static void lsSwitch(String st) {
		
		System.out.println();
		
		IndentationManager im = new IndentationManager();
		
		if("ls".equals(st)) {
			File[] f = listFiles(Settings.path);
			for(int i = 0; i < f.length; i++) {
				im.add(f[i]);
			}
			print(im.getIndentedStrings());
			return;
		}
		
		int indexd = st.indexOf(' ');
		String args = st.substring(indexd+1);
		
		if("*".equals(args)) {
			File[] f = listFiles(Settings.path);
			for(int i = 0; i < f.length; i++) {
				im.add(f[i]);
			}
			print(im.getIndentedStrings());
			return;
		}
		
		if(args.startsWith("*")) {
			String extension = args.substring(args.indexOf('.')+1);
			File[] f = listFiles(Settings.path);
			for(int i = 0; i < f.length; i++) {
				if(f[i].getName().endsWith(extension))
					im.add(f[i]);
			}
			print(im.getIndentedStrings());
			return;
		}
		
		String tpath = Settings.path + "/" + args;
		File ndir= new File(tpath);
		
		if(!ndir.isDirectory() || !ndir.exists()) {
			System.out.println("\tInvalid Directory Specification!!");
			return;
		}
		
		File[] f = listFiles(tpath);
		
		for(int i = 0; i < f.length; i++) {
			im.add(f[i]);
		}
		print(im.getIndentedStrings());
	}
	
	// Stream Read External Function
	public static void sread(String st) {
		String s = st.split(" ")[1];
		System.out.println("\n\tReading "+s+"\n");
		StreamFileReader sfr = new StreamFileReader(s);
		Vector<String> vec = sfr.read();
		for(int i =0; i < vec.size(); i++)
			System.out.println(s+"["+(i+1)+"]: "+vec.elementAt(i));
	}
	
	// Stream Read Internal Function
	public static void sread(String st, String format) {
		System.out.println();
		StreamFileReader sfr = new StreamFileReader(st);
		Vector<String> vec = sfr.read();
		for(int i =0; i < vec.size(); i++)
			System.out.println(format+vec.elementAt(i));
	}
	
	// Stream Write
	public static void swrite(String st) {
		String fn = st.split(" ")[1];
		Vector<String> d = new Vector<String>(1,1);
		for(int i = 1; true; i++) {
			System.out.print(fn+"["+i+"]: ");
			String s = GlobalUtil.getInput();
			if("EOF".equals(s)) break;
			d.addElement(s);
		}
		StreamFileWriter sfw = new StreamFileWriter(fn,d);
		sfw.write();
	}
	
	// Synopsis of available commands
	public static void help() {
		System.out.println();
		System.out.println(" SHIELD Compiler "+GlobalUtil.ver+" ");
		System.out.println("        GNU GPL v3");
		System.out.println();
		System.out.println("Valid commands: ");
		System.out.println("exit | end                             Terminates Program");
		System.out.println("dir | ls                               Directory listing");
		System.out.println("newp <new-project> | newp              New SHIELD Project CLI Wizard");
		System.out.println("newf <filename> | newf                 New SHIELD Project Source File CLI Wizard");
		System.out.println("loadp <project-name>                   Open a project");
		System.out.println("eman                                   Extension Manager");
		System.out.println("sread <filename>                       Stream Read");
		System.out.println("swrite <filename>                      Stream Write");
	}
	
	// Load Project to Memory
	public static void loadProject(String st) {
		
		boolean flag = false;
		String pname = "";
		String lp = "";
		
		try {
			pname = st.substring(st.indexOf(" ")+1)+".shieldproject";
			ProjectConfigaration temp = (new ProjectConfigaration(pname));
		} catch(IOException ioe) {
			Settings.pconf = null;
			flag = true;
			return;
		} 
		
		if(flag) return;
		
		try {
			lp = (new ProjectConfigaration(pname)).getProjectRoot();
			if(!(new File(lp)).isDirectory()) {
				System.out.println("\tProject "+(new ProjectConfigaration(pname)).getName()+" not found !!");
				return;
			}
			Settings.pconf = new ProjectConfigaration(pname);
		} catch(IOException ioe) {
			Settings.pconf = null;
			return;
		} 
	}
	
	// Occurance of 
	public static int occuranceOf(String string, char c) 
	{
		int cd = 0;
		for(int i = 0; i < string.length(); i++) if(string.charAt(i) == c) cd++;
		return cd;
	}
	
	// Replace Multiple Simultaneous Occurances
	public static String multiReplace(String st, char ch) {
		String r=" ";
		
		for(int i = 0; i < st.length(); i++) {
			char tobe = st.charAt(i);
			char last = r.charAt(r.length()-1);
			if(tobe == last) continue;
			r+=tobe;
		}
		
		r=r.trim();
		return r;
	}
	
	// Print array
	private static void print(String[] st) {
		for(int i = 0; i < st.length; i++)
			System.out.println(st[i]);
	}
	
	// Get file extension from name
	public static String getExtension(String st) {
		String s = "";
		if(!st.contains(".")) return "FILE";
		for(int i = st.lastIndexOf("."); i < st.length(); i++) s+=st.charAt(i);
		return s;
	}
	
	// Internal Method to list files in a given path
	public static File[] listFiles(String path) {
		return (new File(path).listFiles());
	}
}
