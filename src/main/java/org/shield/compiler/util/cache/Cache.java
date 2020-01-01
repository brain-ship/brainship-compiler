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
 
package org.shield.compiler.util.cache;

import java.util.Vector;

public class Cache {
	
	private Vector<String> exts;
	
	public Cache() {
		exts = new Vector<String>(1,1);
	}
	
	public void newExtension(String ext, String des) {
		exts.addElement("EXT "+ext+" DES "+des);
	}
	
	
	
	public String getExtDes(String ext) {
		if("FILE".equals(ext)) return "FILE";
		for(int i = 0; i < exts.size(); i++) 
			if(exts.elementAt(i).contains("EXT "+ext)) {
				String st = "";
				for(int j = 3+1+ext.length()+1+3+1; j < exts.elementAt(i).length(); j++)
					st+=exts.elementAt(i).charAt(j);
				return st;
			}
		return ext.replace(".","").toUpperCase()+" Files";
	}
	
}
