package net.imyeyu.utils.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.UnknownHostException;

import net.imyeyu.utils.vo.HTTPInfo;

/**
 * Network interfaces
 * 
 * @author Yeyu
 *
 */
public interface Network {

	/**
	 * Send a http post
	 * The url must has "http" or "https"
	 * Demo: sendPost("https://www.imyeyu.net/api", "key0=value&amp;key1=value1");
	 * 
	 * @param url request url
	 * @param param request params
	 * @return response from request server
	 * @throws UnknownHostException request url has error
	 * @throws ConnectException computer or server network can not connect
	 * @throws Exception code has error
	 */
	public String sendPost(String url, String param) throws UnknownHostException, ConnectException, Exception;

	/**
	 * Send a http get
	 * The url must has "http" or "https"
	 * Demo: sendGet("https://www.imyeyu.net/api", "key0=value&amp;key1=value1");
	 * 
	 * @param url request url
	 * @param param request params
	 * @return response from request server
	 * @throws UnknownHostException request url has error
	 * @throws ConnectException computer or server network can not connect
	 * @throws Exception code has error
	 */
	public String sendGet(String url, String param) throws UnknownHostException, ConnectException, Exception;
	
	/**
	 * Send a http get
	 * The url must has "http" or "https"
	 * Demo: sendGet(http);
	 * 
	 * @param url http info object
	 * @return response from request server
	 * @throws UnknownHostException request url has error
	 * @throws ConnectException computer or server network can not connect
	 * @throws Exception code has error
	 */
	public String sendGet(HTTPInfo http) throws UnknownHostException, ConnectException, Exception;
	
	/**
	 * Use user's default browser open url
	 * Demo: openURLInBrowser("http://www.imyeyu.net");
	 * 
	 * @param uri web site url
	 */
	public void openURIInBrowser(URI uri);
	
	/**
	 * Ping host by CMD (Windows os)
	 * Demo0: pingHostByCMD("www.imyeyu.net");
	 * Demo1: pingHostByCMD("127.1.0.1");
	 * 
	 * @param host ip or url
	 * @return ping result value
	 */
	public int pingHostByCMD(String host);
	
	/**
	 * Download file from internet url
	 * Demo: downloadFile("https://www.imyeyu.net/downloads/test.jar", "D://TempFile", "file.jar");
	 * 
	 * @param internetURL internet url
	 * @param path download finish file path
	 * @param fileName download finish file name
	 * @throws MalformedURLException url error
	 * @throws FileNotFoundException not found file
	 * @throws IOException io error
	 * @throws Exception other error
	 */
	public void downloadFile(String internetURL, String path, String fileName) throws MalformedURLException, FileNotFoundException, IOException, Exception;
	
	/**
	 * Get machine network ip, if unfind network ip, will return a local ip
	 * 
	 * @return ip string, likes 192.168.1.1
	 * @throws Exception get error
	 */
	public String getNetworkIp() throws Exception;
}
