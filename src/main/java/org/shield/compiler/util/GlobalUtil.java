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

import org.shield.compiler.util.cache.Cache;

import java.util.Scanner;

/**
 * GlobalUtil class for Realtime Centralized Management
 * 
 **/
public class GlobalUtil {
	
	private static boolean state = false;
	
	private static Scanner sc = new Scanner(System.in);
	
	public static String ver = "0.3-alpha";
	
	public static Cache gCache;
	
	public static void startProgram() {
		state = true;
	}
	
	public static boolean isRunning() {
		return state;
	}
	
	public static void regCache(Cache cache) {
		gCache = cache;
	}
	
	public static Cache getCache() {
		return gCache;
	}
	
	public static void endProgram() {
		state = false;
		postTerminiationProcess();
	}
	
	public static String getInput() {
		return sc.nextLine();
	}
	
	public static void postTerminiationProcess() {
		sc.close();
		if(Settings.pconf != null) Settings.pconf.rewrite();
	}
}