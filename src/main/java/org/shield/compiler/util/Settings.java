/*
 * Part of BrainShip Projects
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

import java.net.URL;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * Centralized class for GlobalUtil settings
 * 
 **/

public class Settings {
	
	public static String path = "";
		
	public static ProjectConfigaration pconf = null;	
	
	public static String pathToEditor = null;
	
	public static void init() {
		path = System.getProperty("user.dir");
		try {
			ReadableByteChannel readChannel = Channels.newChannel(new URL("https://raw.githubusercontent.com/brain-ship/brainship-cli-cache/master/shield.cache").openStream());
			FileOutputStream fileOS = new FileOutputStream(path+"\\shield.cache");
			FileChannel writeChannel = fileOS.getChannel();
			writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
		} catch(Exception e) {
			e.printStackTrace();
		}
		CacheReader reader = new CacheReader(path+"\\shield.cache");
		Cache cache = reader.getCache();
		GlobalUtil.regCache(cache);
	}
	
}