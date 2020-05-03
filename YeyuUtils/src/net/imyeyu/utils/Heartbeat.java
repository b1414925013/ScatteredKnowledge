package net.imyeyu.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Heartbeat extends Thread {
	
	private String content, callBack = "NULL";
	private Socket socket;
	private int interval;
	private boolean isShutdown = false;

	public Heartbeat(Socket socket, int interval) {
		this.socket = socket;
		this.interval = interval;
	}
	
	public void say(String content) {
		this.content = content;
	}
	
	public void shutdown() {
		this.isShutdown = true;
	}
	
	public String getCallBack() {
		return callBack;
	}
	
	public void run() {
		InputStream is = null;
		OutputStream os = null;
		BufferedReader br = null;
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while (true) {
				os.write((content + "\r\n").getBytes("UTF-8"));
				os.flush();
				callBack = br.readLine();
				sleep(interval);
				if (isShutdown) break;
			}
		} catch (SocketException e) {
			isShutdown = true;
			// disconnect
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) { is.close(); }
				if (os != null) { os.close(); }
				if (br != null) { br.close(); }
			} catch (IOException e) {
				e.printStackTrace();
			}
			isShutdown = true;
		}
	}
}