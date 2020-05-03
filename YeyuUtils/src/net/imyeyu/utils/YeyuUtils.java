package net.imyeyu.utils;

import net.imyeyu.utils.implement.EncodeImp;
import net.imyeyu.utils.implement.FileManagerImp;
import net.imyeyu.utils.implement.GUIImp;
import net.imyeyu.utils.implement.NetworkImp;
import net.imyeyu.utils.implement.ToolsImp;
import net.imyeyu.utils.interfaces.Encode;
import net.imyeyu.utils.interfaces.FileManager;
import net.imyeyu.utils.interfaces.GUIX;
import net.imyeyu.utils.interfaces.Network;
import net.imyeyu.utils.interfaces.Tools;

/**
 * Main interface
 * 
 * @author Yeyu
 *
 */
public class YeyuUtils {
	
	/**
	 * Some en/de code function
	 * 
	 * @return Encode interface
	 */
	public static Encode encode() {
		return new EncodeImp();
	}
	
	/**
	 * Some file manager function
	 * Use of control disk file and application data 
	 * 
	 * @return File manager interface
	 */
	public static FileManager file() {
		return new FileManagerImp();
	}
	
	/**
	 * Some GUI function
	 * In common use GUI
	 * 
	 * @return GUI interface
	 */
	public static GUIX gui() {
		return new GUIImp();
	}
	
	/**
	 * Some network function
	 * 
	 * @return Network interface
	 */
	public static Network network() {
		return new NetworkImp();
	}
	
	/**
	 * Some tools function
	 * 
	 * @return tools interface
	 */
	public static Tools tools() {
		return new ToolsImp();
	}
}
