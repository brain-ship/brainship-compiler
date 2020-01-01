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
 
import java.util.Vector;

/**
 * Module use for handling new file requests
 * with default values
 * 
 **/
public class NewFileManager {
	
	private String fn;
	
	private String p;
	
	private String pc;
	
	public NewFileManager(String fname, String path, String pck) {
		fn = fname;
		p = path;
		pc = pck;
	}
	
	public void create() {
		String[] contents = {
			"package "+pc+";",
			"",
			"public class "+fn+" { ",
			"	",
			"}"
		};
		Vector<String> vcon = new Vector<String>(1,1);
		for(int i = 0; i < contents.length; i++)
			vcon.addElement(contents[i]);
		String temp = p+"\\"+fn+".java";
		StreamFileWriter sfw = new StreamFileWriter(ShieldSwitch.multiReplace(temp, '\\'), vcon);
		sfw.write();
	}
}