package net.imyeyu.utils.implement;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.imyeyu.utils.YeyuUtils;
import net.imyeyu.utils.interfaces.Encode;

public class EncodeImp implements Encode {

	public String changeCharset(String data, String oldCharset, String newCharset) {
		try {
			if (data != null) {
				byte[] bs = data.getBytes(oldCharset);
				return new String(bs, newCharset);
			}
		} catch (UnsupportedEncodingException e) {
			YeyuUtils.gui().exception(e);
		}
		return null;
	}

	public String enUnicode(String data) {
		StringBuffer sb = new StringBuffer();
		char[] c = data.toCharArray();
		for (int i = 0, l = c.length; i < l; i++) {
			sb.append("\\u" + Integer.toHexString(c[i]));
		}
		return sb.toString();
	}

	public String enUnicodeByCN(String data) {
		StringBuilder sb = new StringBuilder();
		char[] c = data.toCharArray();
		for (int i = 0, l = c.length; i < l; i++) {
			if (YeyuUtils.encode().isChinese(c[i])) {
				sb.append("\\u" + Integer.toHexString(c[i]));
			} else {
				sb.append(c[i]);
			}
		}
		return sb.toString();
	}

	public String deUnicode(String data) {
		StringBuffer sb = new StringBuffer();
		String[] hex = data.split("\\\\u");
		int index = -1;
		for (int i = 1, l = hex.length; i < l; i++) {
			if (4 < hex[i].length()) {
				index = Integer.parseInt(hex[i].substring(0, 4), 16);
				sb.append((char) index);
				sb.append(hex[i].substring(4, hex[i].length()));
			} else {
				index = Integer.parseInt(hex[i], 16);
				sb.append((char) index);
			}
		}
		return sb.toString();
	}

	public String enCodeBase64(String data) {
		String result;
		try {
			result = Base64.getEncoder().encodeToString(data.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			YeyuUtils.gui().exception(e);
			return null;
		}
		return result;
	}
	
	public String deCodeBase64(String data) {
		String result;
		try {
			result = new String(Base64.getDecoder().decode(data), "UTF-8");
		} catch (IOException e) {
			YeyuUtils.gui().exception(e);
			return null;
		}
		return result;
	}

	public String generateBase(String data) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = Base64.getEncoder().encodeToString(md.digest(data.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			YeyuUtils.gui().exception(e);
		} catch (UnsupportedEncodingException e) {
			YeyuUtils.gui().exception(e);
		}
		return result;
	}

	public String enCodeMD5(String data) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = new BigInteger(1, md.digest(data.getBytes())).toString(16);
			for (int i = 0; i < 32 - result.length(); i++) {
				result = "0" + result;
			}
		} catch (NoSuchAlgorithmException e) {
			YeyuUtils.gui().exception(e);
		}
		return result;
	}

	public boolean hasChinese(String data) {
		Matcher m = Pattern.compile("[\u4e00-\u9fa5]").matcher(data);
		return m.find();
	}

	public boolean isNumber(String data) { 
		return Pattern.compile("[0-9]*").matcher(data).matches();
	}

	public boolean isChinese(char data) {
		return Pattern.compile("[^\\x00-\\xff]").matcher(String.valueOf(data)).matches();
	}
}
