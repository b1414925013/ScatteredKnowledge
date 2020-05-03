package net.imyeyu.utils;

import java.util.ResourceBundle;

public class ResourceBundleX {
	
	private ResourceBundle rb;

	public ResourceBundleX(ResourceBundle rb) {
		this.rb = rb;
	}
	
	public String def(String k) {
		return rb.getString(k);
	}
	
	public String def(String k, String v) {
		return rb.getString(k).replaceAll("%v%", v);
	}
	
	public String def(String k, String... v) {
		String str = rb.getString(k);
		for (int i = 0; i < v.length; i++) {
			str = str.replaceAll("%v" + i +"%", v[i]);
		}
		return str;
	}
	
	public String l(String k) {
		return " " + rb.getString(k);
	}
	
	public String r(String k) {
		return rb.getString(k) + " ";
	}
}