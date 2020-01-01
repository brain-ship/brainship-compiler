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
import org.shield.compiler.util.cache.CacheReader;

/**
 * Centralized class for GlobalUtil settings
 * 
 **/

public class Settings {
	
	public static String path = "";
		
	public static ProjectConfigaration pconf = null;	
	
	public static String pathToEditor = null;
	
	public static void init() {
		StreamFileReader sfr = new StreamFileReader("bpath.shieldsettings");
		path = sfr.singleRead()+"\\";
		CacheReader reader = new CacheReader("shield.cache");
		Cache cache = reader.getCache();
		GlobalUtil.regCache(cache);
	}
	
}