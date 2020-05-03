package net.imyeyu.utils;

public class Lang {

	public static String toCode(String lang) {
		switch (lang) {
			case "English":
				return "en_US";
			case "繁体中文":
				return "zh_TW";
			default:
				return "zh_CN";
		}
	}
	
	public static String toString(String code) {
		switch (code) {
			case "en_US":
				return "English";
			case "zh_TW":
				return "繁体中文";
			default:
				return "简体中文";
		}
	}
}