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

import org.shield.compiler.util.GlobalUtil;
import org.shield.compiler.util.StreamFileReader;

import java.util.Vector;
 
public class CacheReader
{
	
	private Cache cache;
	
	private Vector<String> rdata;
	
	public CacheReader(String st) {
		rdata = (new StreamFileReader(st)).read();
		cache = new Cache();
		parse();
	}
	
	private void parse() {
		for(int i =0; i < rdata.size(); i++)
		{
			if(rdata.elementAt(i).startsWith("#")) continue;
			
			if(rdata.elementAt(i).startsWith("VER")) {
				String s = rdata.elementAt(i);
				String st = "";
				for(int j = s.indexOf(' ')+1; j < s.length(); j++) st+=s.charAt(j);
				if(!st.equals(GlobalUtil.ver)) {
					System.out.println("Cache version incompliance found! [You may have modified the file or downloaded the wrong file]");
					System.out.print("Proceed (y/n)? ");
					String ch = GlobalUtil.getInput();
					if("y".equals(ch)) continue;
					else {
						GlobalUtil.endProgram();
						return;
					}
				}
			}
			
			if(rdata.elementAt(i).equals("EXTS:")) {
				for(int j = i+1; j < rdata.size(); j++) {
					if(rdata.elementAt(j).equals("END EXTS")) break;
					String ext = "";
					String des = "";
					for(int k = rdata.elementAt(j).indexOf(' ')+1; k < rdata.elementAt(j).length()-1; k++) {
						if(rdata.elementAt(j).charAt(k) == ' ')
							break;
						ext+=rdata.elementAt(j).charAt(k);
					}
					for(int k = rdata.elementAt(j).indexOf("DES")+4; k < rdata.elementAt(j).length(); k++) 
						des+=rdata.elementAt(j).charAt(k);
					if(ext.contains("{")){
						String[] iext = breakToIExt(ext);
						for(int k = 0;k < iext.length; k++) cache.newExtension(iext[k], des);
					}
					cache.newExtension(ext, des);
				}
			}
		}
	}
	
	private String[] breakToIExt(String st) {
		String xst = "";
		for(int i = 1; i < st.length()-1; i++) xst+=st.charAt(i);
		return xst.split(",");
	}
	
	public Cache getCache() {
		return cache;
	}
	
}
