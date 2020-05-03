package net.imyeyu.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Configer {
	
	private String appName;
	
	/**
	 * 配置文件处理器
	 * <br />
	 * 第一次调用（启动）时应传入 jar 内置配置路径表示生成默认配置文件
	 * <br />
	 * 第 N 次调用时应传入应用程序名称表示加载已生成得配置文件
	 * <br />
	 * 
	 * @param v 路径或文件名
	 */
	public Configer(String v) {
		this.appName = v + ".ini";
		if (v.indexOf("/") != -1) {
			appName = v.substring(v.lastIndexOf("/") + 1);
			if (!new File(appName).exists()) YeyuUtils.file().jarFileToDisk(v, appName);
		}
	}

	public void set(Map<String, Object> map) {
		StringBuffer sb = new StringBuffer();
		String d = YeyuUtils.file().fileToString(new File(appName), "UTF-8");
		String[] c = d.split("\r\n|[\r\n]");
		String k;
		for (int i = 0, l = c.length; i < l; i++) {
			if (c[i].equals("")) {
				sb.append("\r\n");
				continue;
			}
			if (!c[i].startsWith("#")) {
				k = c[i].substring(0, c[i].indexOf("="));
				if (map.get(k).toString().equals("")) {
					sb.append(k + "=\r\n");
				} else {
					sb.append(k + "=" + map.get(k) + "\r\n");
				}
			} else {
				sb.append(c[i] + "\r\n");
			}
		}
		YeyuUtils.file().stringToFile(new File(appName), sb.toString());
	}

	public Map<String, Object> get() {
		Map<String, Object> map = new HashMap<String, Object>();
		String d = YeyuUtils.file().fileToString(new File(appName), "UTF-8");
		String[] c = d.split("\r\n|[\r\n]");
		String k, v;
		for (int i = 0, l = c.length; i < l; i++) {
			c[i] = c[i].trim();
			if (c[i].length() != 0 && !c[i].startsWith("#")) {
				k = c[i].substring(0, c[i].indexOf("="));
				if (c[i].length() - 1 != c[i].indexOf("=")) {
					v = c[i].substring(c[i].indexOf("=") + 1, c[i].length());
				} else {
					v = "";
				}
				map.put(k, v);
			}
		}
		return map;
	}
}
