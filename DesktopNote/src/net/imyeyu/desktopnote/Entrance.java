package net.imyeyu.desktopnote;

import java.io.File;
import java.util.Map;

import javafx.application.Application;
import net.imyeyu.desktopnote.ctrl.Main;
import net.imyeyu.utils.Configer;

/**
 * 桌面便签
 * 
 * @author Yeyu
 *
 */
public class Entrance {
	
	private static Map<String, Object> config;
	public static int BACKUP_TIME = 60; // 备份间隔 - 单位：分钟

	public static void main(String[] args) {
		try {
			System.setProperty("prism.allowhidpi", "false");
			config = new Configer("net/imyeyu/desktopnote/res/DesktopNote.ini").get();
			File file = new File("DesktopNote.data");
			if (!file.exists()) file.createNewFile();
			Application.launch(Main.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Object> getConfig() {
		return config;
	}
}