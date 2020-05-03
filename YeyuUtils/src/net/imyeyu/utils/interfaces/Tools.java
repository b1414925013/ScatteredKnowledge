package net.imyeyu.utils.interfaces;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Map;

public interface Tools {
	
	/**
	 * Cur string
	 * Demo: cutString("text", 2);
	 * Return "te"
	 * 
	 * @param data text
	 * @param length cut length
	 * @param dot string last has "..." for set true, else or noting
	 * @return cut result
	 */
	public String cutString(String data, int length, boolean dot);
	
	/**
	 * Random the map
	 * 
	 * @param map map object
	 * @param limit return size limit(return all for use map.size())
	 * @return random result
	 */
	public Map<Object, Object> randomMap(Map<Object, Object> map, int limit);
	
	/**
	 * Random the file map
	 * 
	 * @param map map object
	 * @return random result
	 */
	public Map<String, File> randomFileMap(Map<String, File> map);
	
	/**
	 * Sort map by string key
	 * 
	 * @param map map object
	 * @return sort result
	 */
	public Map<String, Object> sortMapByStringKey(Map<String, Object> map);
	
	/**
	 * Sort map by long key
	 * 
	 * @param map map object
	 * @return sort result
	 */
	public Map<Long, String> sortMapByLongKey(Map<Long, String> map);
	
	/**
	 * Remove an item by key in file map
	 * 
	 * @param map remove map
	 * @param key remove key
	 * @return remove result map
	 */
	public Map<String, File> removeFileMapByKey(Map<String, File> map, String key);
	
	/**
	 * Get system memory max size
	 * 
	 * @return memory max size, unit MB
	 */
	public int getSystemMemorySize();
	
	/**
	 * Find the process (Windows os)
	 * Demo: findProcess("javaw.exe", "Minecraft 1.14.2");
	 * 
	 * @param processName process name
	 * @param threadName thread name
	 * @return true is exist, else false
	 */
	public boolean findProcess(String processName, String threadName);
	
	/**
	 * Set text into system clip board
	 * Demo: setIntoClipboard("text");
	 * 
	 * @param content text
	 */
	public void setIntoClipboard(String content);
	
	/**
	 * Format the storage value of byte unit
	 * <br />
	 * when value == -1, return ""
	 * <br />
	 * when value < 1 KB, return 999 B <- has a space
	 * <br />
	 * when value < 10 MB, return 9,999 KB
	 * <br />
	 * when 10MB < value < 1GB, return 9,999 MB
	 * <br />
	 * when 1000GB < value, return 1.111 TB (Really ?)
	 * 
	 * @param byteValue
	 * @param format if insert the ',' in number, new DecimalFormat("#,###")
	 * @return value Unit
	 */
	public String storageFormat(double byteValue, DecimalFormat format);
	
	/**
	 * Format the net speed value of byte unit
	 * <br />
	 * when value == -1, return "0"
	 * <br />
	 * when value < 1 KB, return 999 B <- has a space
	 * <br />
	 * when value < 1.1 MB, return 1,199 KB
	 * <br />
	 * when 1.1 MB < value, return 9,999 MB
	 * 
	 * @param byteValue
	 * @param format if insert the ',' in number, new DecimalFormat("#,###")
	 * @return value Unit
	 */
	public String netSpeedFormat(double byteValue, DecimalFormat format);
}
