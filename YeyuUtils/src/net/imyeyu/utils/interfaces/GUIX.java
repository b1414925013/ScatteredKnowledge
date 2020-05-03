package net.imyeyu.utils.interfaces;

import java.io.File;

import javax.swing.ButtonGroup;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

/**
 * GUI interfaces
 * 
 * @author Yeyu
 *
 */
public interface GUIX {

	public final Paint RED = Paint.valueOf("#F00");
	public final Paint BLACK = Paint.valueOf("#000");
	public final Paint ORANGE = Paint.valueOf("#F60");
	public final Paint GREEN = Paint.valueOf("#393");
	public final Paint GRAY = Paint.valueOf("#333");
	public final Paint LIGHT_GRAY = Paint.valueOf("#9B9B9B");
	public final Paint PINK = Paint.valueOf("#FF7A9B");
	public final Paint TRANSPARENT = Paint.valueOf("#FFFFFF00");

	/**
	 * JavaFX 标签文本提示<br />
	 * 例：tips(label, "error", 3000, Tips.ERROR);
	 * 
	 * @param tips    JavaFX Label 标签
	 * @param content 文本内容
	 * @param time    持续时间，单位：毫秒
	 * @param mode    文本样式，Tips 类静态变量：DEFAULT（默认），WARNING（橙色警告），ERROR（红色错误） 
	 */
	public void tips(Label tips, String content, int time, int mode);
	public void tips(Label tips, String content);
	
	/**
	 * Show debug panel
	 * Demo: debug("test");
	 * 
	 * @param s debug content
	 */
	public void debug(String s);
	
	/**
	 * 当程序发生致命错误时把错误以视图界面形式呈现<br />
	 * 用户可以反馈错误或结束程序<br />
	 * 存在安全隐患，绝对不要把关键错误抛出<br />
	 * 示例：exception(new NullPointerException());
	 * 
	 * @param e exception
	 */
	public void exception(Exception e);
	
	/**
	 * Get file in explorer
	 * Demo: getFileInExplorer("C:\\", "exe");
	 * 
	 * @param path init open position
	 * @param format select file format
	 * @return file object
	 */
	public File getFileInExplorer(String path, String format);
	
	/**
	 * Get file in explorer
	 * Demo: getFileInExplorer("C:\\", true, "exe");
	 * 
	 * @param path init open position
	 * @param parent change path to parent dir
	 * @param format select file format
	 * @return file object
	 */
	public File getFileInExplorer(String path, boolean parent, String format);
	
	/**
	 * Get files in explorer
	 * Demo: getFilesInExplorer("C:\\", formats);
	 * 
	 * @param path init open position
	 * @param format select files format
	 * @return selected files
	 */
	public File[] getFilesInExplorer(String path, String[] formats);
	
	/**
	 * Get files in explorer
	 * Demo: getFilesInExplorer("C://", true, formats);
	 * 
	 * @param path init open position
	 * @param parent change path to parent dir
	 * @param format select files format
	 * @return selected files
	 */
	public File[] getFilesInExplorer(String path, boolean parent, String[] formats);
	
	/**
	 * Select folder in explorer
	 * Demo: getFolderInExplorer("C:\\");
	 * 
	 * @param path init open position
	 * @return selected folder
	 */
	public String getFolderInExplorer(String path);
	
	/**
	 * Select folder in explorer
	 * Demo: getFolderInExplorer("C:\\", true);
	 * 
	 * @param path init open position
	 * @param parent change path to parent dir
	 * @return selected folder
	 */
	public String getFolderInExplorer(String path, boolean parent);
	
	/**
	 * Select folders in explorer
	 * Demo: getFoldersInExplorer("C:\\");
	 * 
	 * @param path init open position
	 * @return selected folders
	 */
	public File[] getFoldersInExplorer(String path);
	
	/**
	 * Select folders in explorer
	 * Demo: getFoldersInExplorer("C:\\", true);
	 * 
	 * @param path init open position
	 * @param parent change path to parent dir
	 * @return selected folders
	 */
	public File[] getFoldersInExplorer(String path, boolean parent);
	
	/**
	 * Get selected tag from button group
	 * Demo:  getSelectedByButtonGroup(group)
	 * 
	 * @param group button group
	 * @return selected tag
	 */
	public String getSelectedByButtonGroup(ButtonGroup group);
	
	/**
	 * 设置组件背景，底色为默认
	 * 
	 * @param node  节点
	 * @param url   背景路径
	 * @param width 宽度
	 * @param x     X 轴偏移
	 * @param y     Y 轴偏移
	 */
	public void setBg(Node node, String url, int width, int x, int y);
	
	/**
	 * 设置组件背景，底色为透明
	 * 
	 * @param node  节点
	 * @param url   背景路径
	 * @param width 宽度
	 * @param x     X 轴偏移
	 * @param y     Y 轴偏移
	 */
	public void setBgTp(Node node, String url, int width, int x, int y);
}
