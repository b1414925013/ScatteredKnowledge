package net.imyeyu.utils.implement;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.imyeyu.utils.YeyuUtils;
import net.imyeyu.utils.interfaces.Network;
import net.imyeyu.utils.vo.HTTPInfo;

public class NetworkImp implements Network {

	public String sendPost(String url, String param) throws UnknownHostException, ConnectException, Exception {
		String response = "";
		PrintWriter out = null;
		BufferedReader in = null;
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
		httpUrlConnection.setDoOutput(true);
		httpUrlConnection.setDoInput(true);
		httpUrlConnection.setRequestMethod("POST");
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        httpUrlConnection.connect();
		out = new PrintWriter(httpUrlConnection.getOutputStream());
		out.write(param);
		out.flush();
		in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));
		String line;
		while ((line = in.readLine()) != null) {
			response += line + "\r\n";
		}
		try {
			if (out != null) { out.close(); }
			if (in != null) { in.close(); }
		} catch (Exception e) {
			YeyuUtils.gui().exception(e);
		}
		return response;
	}

	public String sendGet(String url, String param) throws UnknownHostException, ConnectException, Exception {
		String result = "";
		BufferedReader in = null;
		String urlNameString = url + "?" + param;
		URL realUrl = new URL(urlNameString);
		URLConnection connection = realUrl.openConnection();
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Accept-Charset", "UTF-8");
		connection.connect();
		in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		try {
			if (in != null) { in.close(); }
		} catch (Exception e) {
			YeyuUtils.gui().exception(e);
		}
		return result;
	}

	public String sendGet(HTTPInfo http) throws UnknownHostException, ConnectException, Exception {
		String result = "";
		BufferedReader in = null;
		String urlNameString = http.getUrl() + "?" + http.getParam();
		URL realUrl = new URL(urlNameString);
		URLConnection connection = realUrl.openConnection();
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3314.0 Safari/537.36 SE 2.X MetaSr 1.0");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Accept-Charset", http.getCharset());
		connection.setRequestProperty("Cookie", http.getCookie());
		connection.connect();
		in = new BufferedReader(new InputStreamReader(connection.getInputStream(), http.getCharset()));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		try {
			if (in != null) { in.close(); }
		} catch (Exception e) {
			YeyuUtils.gui().exception(e);
		}
		return result;
	}

	public void openURIInBrowser(URI uri) {
		try {
			Desktop dp = Desktop.getDesktop();
			if (dp.isSupported(Desktop.Action.BROWSE)) {
				dp.browse(uri);
			}
		} catch (IOException e) {
			YeyuUtils.gui().exception(e);
		}
	}

	public int pingHostByCMD(String ip) {
		Runtime rt = Runtime.getRuntime();
		int ping = -1;
		try {
			Process process = rt.exec("ping " + ip + " -n 1");
			process.isAlive();
			StringBuffer sb = new StringBuffer();
			InputStreamReader isr = new InputStreamReader(process.getInputStream(), "GB2312");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			line = sb.toString();
			if (!(line.indexOf("请求超时") != -1 || line.indexOf("timed out") != -1)) {
				int start = 0, end = 0;
				if (line.indexOf("平均") != -1) {
					start = line.indexOf("平均") + 5;
					end = line.indexOf("ms", start);
				} else {
					start = line.indexOf("Average") + 10;
					end = line.indexOf("ms", start);
				}
				ping = Integer.parseInt(line.substring(start, end));
			}
			br.close();
			isr.close();
			return ping;
		} catch (Exception e) {
			return ping;
		}
	}

	public void downloadFile(String internetURL, String path, String fileName) throws MalformedURLException, FileNotFoundException, IOException, Exception {
		URL url = new URL(internetURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(3 * 1000);
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		InputStream inputStream = conn.getInputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		byte[] getData = bos.toByteArray();
		if (path.indexOf("/") != -1) {
			path = path.substring(0, path.lastIndexOf("/"));
		}
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir + File.separator + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(getData);
		if (fos != null) {
			fos.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}
		if (bos != null) {
			bos.close();
		}
	}

	public String getNetworkIp() throws Exception {
		String response = sendPost("http://ip.chinaz.com", "");
		Matcher m = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>").matcher(response);
		return m.find() ? m.group(1) : "";
	}
}
