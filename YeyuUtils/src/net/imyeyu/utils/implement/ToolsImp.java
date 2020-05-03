package net.imyeyu.utils.implement;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.sun.management.OperatingSystemMXBean;

import net.imyeyu.utils.YeyuUtils;
import net.imyeyu.utils.interfaces.Tools;

public class ToolsImp implements Tools {

	public String cutString(String data, int length, boolean dot) {
		if (data.length() < length / 2) {
			return data;
		}
		int count = 0;
		StringBuffer sb = new StringBuffer();
		String[] array = data.split("");
		for (int i = 0; i < array.length; i++) {
			count += array[i].getBytes().length > 1 ? 2 : 1;
			sb.append(array[i]);
			if (count >= length) {
				break;
			}
		}
		if (dot) {
			return (sb.toString().length() < data.length()) ? sb.append("...").toString() : data;
		}
		return sb.toString();
	}

	public Map<Object, Object> randomMap(Map<Object, Object> map, int limit){
		Map<Object, Object> resultTemporary = new LinkedHashMap<Object, Object>();
		List<Map.Entry<Object, Object>> list = new ArrayList<Map.Entry<Object, Object>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Object, Object>>(){
			public int compare(Entry<Object, Object> lhs, Entry<Object, Object> rhs) {
				int randomOne = (int) (Math.random() * 10);
				int randomTwo = (int) (Math.random() * 10);
				return randomOne - randomTwo;
			}
		});
		for (int i = 0, l = list.size(); i < l; i++){
			Map.Entry<Object, Object> mapEntry = list.get(i);
			if (resultTemporary.size() < limit) {
				resultTemporary.put(mapEntry.getKey(), mapEntry.getValue());
			}
		}
		return resultTemporary;
	}

	public Map<String, File> randomFileMap(Map<String, File> map){
		Map<String, File> resultTemporary = new LinkedHashMap<String, File>();
		List<Map.Entry<String, File>> list = new ArrayList<Map.Entry<String, File>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, File>>(){
			public int compare(Entry<String, File> lhs, Entry<String, File> rhs) {
				int randomOne = (int) (Math.random() * 10);
				int randomTwo = (int) (Math.random() * 10);
				return randomOne - randomTwo;
			}
		});
		for (int i = 0, l = list.size(); i < l; i++){
			Map.Entry<String, File> mapEntry = list.get(i);
			resultTemporary.put(mapEntry.getKey(), mapEntry.getValue());
		}
		return resultTemporary;
	}

	public Map<String, Object> sortMapByStringKey(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		class MapKeyComparator implements Comparator<String> {
			public int compare(String str0, String str1) {
				return str0.compareTo(str1);
			}
		}
		Map<String, Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());
		sortMap.putAll(map);
		return sortMap;
	}

	public Map<Long, String> sortMapByLongKey(Map<Long, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		class MapKeyComparator implements Comparator<Long> {
			public int compare(Long arg0, Long arg1) {
				return arg0.compareTo(arg1);
			}
		}
		Map<Long, String> sortMap = new TreeMap<Long, String>(new MapKeyComparator());
		sortMap.putAll(map);
		return sortMap;
	}

	public Map<String, File> removeFileMapByKey(Map<String, File> map, String key) {
		Iterator<String> iterator = map.keySet().iterator();
		String k;
		while (iterator.hasNext()) {
			k = iterator.next();
			if (key.equals(k)) {
				iterator.remove();
			}
		}
		return map;
	}

	public int getSystemMemorySize() {
        OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long size = osmb.getTotalPhysicalMemorySize();
        size = size / 1024 / 1024;
        return (int) size;
	}

	public boolean findProcess(String processName, String threadName) {
		BufferedReader bufferedReader = null;
		try {
			Process proc = Runtime.getRuntime().exec("tasklist -v -fi " + '"' + "imagename eq " + processName + '"');
			bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith(processName) && line.indexOf(threadName) != -1) {
					return true;
				}
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		} catch (Exception e) {
			YeyuUtils.gui().exception(e);
		}
		return false;
	}

	public void setIntoClipboard(String content) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(content), null);
	}

	public String storageFormat(double byteValue, DecimalFormat format) {
		if (byteValue == -1) return "";
		double value = byteValue;
		if (value < 1024) return format.format(value) + " B ";
		value = value / 1024;
		if (value < 10240) return format.format(value) + " KB";
		value = value / 1024;
		if (value < 10240) return format.format(value) + " MB";
		value = value / 1024;
		if (value < 1024) return format.format(value) + " GB";
		return format.format(value / 1024) + " TB";
	}
	
	public String netSpeedFormat(double byteValue, DecimalFormat format) {
		if (byteValue == -1) return "0 B";
		double value = byteValue;
		if (value < 1024) return format.format(value) + " B ";
		value = value / 1024;
		if (value < 1124) return format.format(value) + " KB";
		return format.format(value / 1024) + " MB";
	}
}
