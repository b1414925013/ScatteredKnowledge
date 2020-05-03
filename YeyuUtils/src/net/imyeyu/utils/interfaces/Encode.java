package net.imyeyu.utils.interfaces;

/**
 * Encode interfaces
 * 
 * @author Yeyu
 *
 */
public interface Encode {

	/**
	 * Change data charset
	 * Demo: changeCharset("test", "GBK", "UTF-8");
	 * 
	 * @param data change charset string
	 * @param oldCharset old charset
	 * @param newCharset new charset
	 * @return change result data
	 */
	public String changeCharset(String data, String oldCharset, String newCharset);

	/**
	 * String to unicode
	 * Demo: enUnicode("test");
	 * 
	 * @param data encode string
	 * @return encoded string, like \u2000
	 */
	public String enUnicode(String data);
	
	/**
	 * String to unicode(Chinese)
	 * Demo: enCNUnicode("中文123");
	 * 
	 * @param data encode string
	 * @return encoded string, like \u4e2d\u6587123
	 */
	public String enUnicodeByCN(String data);

	/**
	 * Unicode to string
	 * Demo: deUnicode("\u2000");
	 * 
	 * @param data decode string, like \u2000
	 * @return decoded string
	 */
	public String deUnicode(String data);

	/**
	 * String to Base64 code
	 * Demo: enCodeBase64("test");
	 * 
	 * @param data encode string
	 * @return encoded string, like 9df8g4df==
	 */
	public String enCodeBase64(String data);
	
	/**
	 * Base64 code to string
	 * Demo: deCodeBase64("9df8g4df==");
	 * 
	 * @param data decode string, like 9df8g4df==
	 * @return decoded string
	 */
	public String deCodeBase64(String data);

	/**
	 * Encode string to md5 and base64
	 * This is a outdated function
	 * 
	 * @param data encode string
	 * @return encode result
	 */
	public String generateBase(String data);

	/**
	 * Calculate string MD5
	 * Demo: enCodeMD5("test");
	 * 
	 * @param data encode string
	 * @return encode result (MD5)
	 */
	public String enCodeMD5(String data);
	
	/**
	 * Check string for chinese
	 * Demo: hasChinese("中文abc");
	 * return true
	 * 
	 * @param data string
	 * @return true for has, else for not
	 */
	public boolean hasChinese(String data);
	
	/**
	 * Check string for number
	 * Demo: isNumber("123");
	 * return true
	 * 
	 * @param data string
	 * @return true for is number, else for not
	 */
	public boolean isNumber(String data);
	
	/**
	 * Check char for chinese
	 * Demo: isChinese('abc');
	 * return false
	 * 
	 * @param data char
	 * @return true for is chinese, else for not
	 */
	public boolean isChinese(char data);
}
