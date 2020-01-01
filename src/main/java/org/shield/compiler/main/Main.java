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
 
 /**
  * Basic Start-Point for Program
  *
 **/
 
package org.shield.compiler.main;
 
import org.shield.compiler.util.ShieldSwitch;
import org.shield.compiler.util.GlobalUtil;
import org.shield.compiler.util.Settings;
 
public class Main
{
	public static void main(String[] args) {
		
		// GlobalUtil Init Method
		GlobalUtil.startProgram();
		
		// Init
		Settings.init();
		
		// Program Loop
		if(!GlobalUtil.isRunning()) return;
		while(GlobalUtil.isRunning()) {
			if(!GlobalUtil.isRunning()) break;
			System.out.print("\n "+Settings.path + " >> ");
			String com = GlobalUtil.getInput();
			ShieldSwitch.switchProcess(com);
		}
	}
}