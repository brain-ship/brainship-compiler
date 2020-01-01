package org.shield.compiler.util;

import java.io.File;
import java.util.Vector;

public class ExtensionManager
{
	private static boolean state;
	
	public static void eman() {
		state = true;
		System.out.println("Extension Manager");
		while(state) {
			System.out.print("\nEMAN >> ");
			String st = GlobalUtil.getInput();
			switchInput(st);
		}
	}
	
	private static void switchInput(String st) {
		if("exit".equals(st) || "end".equals(st)) state = false;
		
		else if("read-cache".equals(st)) readCache();
		
		else if(st.startsWith("export-cache")) exportCache(st);
		
		else if(st.startsWith("setd")) setd(st);
		
		else ShieldSwitch.limitedSwitchProcess(st);
	}

	private static void exportCache(String f) {
		String st = "";
		for(int i = f.indexOf(" ")+1; i < f.length(); i++) st+=f.charAt(i);
		if(!(new File(st)).isDirectory() || !(new File(st)).exists()) {
			System.out.println("Invalid Directory Entry");
			return;
		}
		st+="\\shield.cache";
		st = ShieldSwitch.multiReplace(st, '\\');
		Vector<String> vec = (new StreamFileReader("shield.cache")).read();
		StreamFileWriter sfw = new StreamFileWriter(st, vec);
		sfw.write();
	}

	// Set Default Variable Values
	private static void setd(String st) {
		int fw = st.indexOf(' ');
		String vname = "";
		int sw = 0;
		for(int i = fw+1; true; i++) {
			if(st.charAt(i) == ' ') {
				sw = i;
				break;
			}
			vname+=st.charAt(i);
		}
		String vval = "";
		for(int i = sw+1; i < st.length(); i++) vval+=st.charAt(i);
		if("editor".equals(vname)) Settings.pathToEditor = vval;
	}

	private static void readCache() {
		ShieldSwitch.sread("shield.cache", "CACHE: ");
	}
}
