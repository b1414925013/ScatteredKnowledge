package net.imyeyu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Base64.Encoder;

public class Email {

	private Socket socket;
	private boolean needAuth;
	private boolean debug = false;
	Encoder encode = Base64.getEncoder();

	/**
	 * New a smtp email
	 * Demo: Email smtp = new Email("smtp.qq.com", true, 25);
	 * 
	 * @param server smtp server address
	 * @param needAuth set true for need auth
	 * @param port smtp server port
	 * @throws UnknownHostException host error
	 * @throws IOException io error
	 */
	public Email(String server, boolean needAuth, int port) throws UnknownHostException, IOException {
		try {
			socket = new Socket(server, port);
			this.needAuth = needAuth;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * Send a email
	 * Demo: sendMail(message, "smtp.qq.com");
	 * 
	 * Message object
	 * EmailMessage message = new EmailMessage();
	 * message.setFrom("imyeyu@qq.com"); 
	 * message.setTo("443226652@qq.com");
	 * message.setSubject("title");
	 * message.setContent("content test");
	 * message.setDatafrom("imyeyu@qq.com");
	 * message.setDatato("443226652@qq.com");
	 * message.setUser("imyeyu@qq.com");
	 * message.setPassword("adfgertyusdffytryt");
	 * 
	 * @param message message object
	 * @param server smtp server address
	 * @return true for success, false for fail
	 */
	public boolean sendMail(EmailMessage message, String server) {
		try {
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			helo(server, in, out);
			if (needAuth) {
				authLogin(message, in, out);
			}
			mailfrom(message.getFrom(), in, out);
			String[] str = message.getTo().split(",");
			for (int i = 0; i < str.length; i++) {
				rcpt(str[i], in, out);
			}
			data(message.getDatafrom(), message.getDatato(), message.getSubject(), message.getContent(), in, out);
			quit(in, out);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void helo(String server, InputStream in, OutputStream out) throws IOException {
		int result;
		result = response(in);
		if (result != 220) {
			throw new IOException("failed");
		}
		result = sendServer("HELO " + server, in, out);
		if (result != 250) {
			throw new IOException("failed");
		}
	}

	private int sendServer(String str, InputStream in, OutputStream out) throws IOException {
		out.write(str.getBytes());
		sendNewline(out);
		out.flush();
		if (debug) {
			System.out.println("command:" + str);
		}
		return response(in);
	}

	private void authLogin(EmailMessage message, InputStream in, OutputStream out) throws IOException {
		int result;
		result = sendServer("AUTH LOGIN", in, out);
		if (result != 334) {
			throw new IOException("AUTH failed");
		}

		result = sendServer(encode.encodeToString(message.getUser().getBytes()), in, out);
		if (result != 334) {
			throw new IOException("wrong username");
		}
		result = sendServer(encode.encodeToString(message.getPassword().getBytes()), in, out);

		if (result != 235) {
			throw new IOException("auth failed");
		}
	}

	public void mailfrom(String sourceMail, InputStream in, OutputStream out) throws IOException {
		int result;
		result = sendServer("MAIL FROM:<" + sourceMail + ">", in, out);
		if (result != 250) {
			throw new IOException("exception.....");
		}
	}

	public void rcpt(String toMail, InputStream in, OutputStream out) throws IOException {
		int result;
		result = sendServer("RCPT TO:<" + toMail + ">", in, out);
		if (result != 250) {
			throw new IOException("exception");
		}
	}

	private void data(String from, String to, String subject, String content, InputStream in, OutputStream out) throws IOException {
		int result;
		result = sendServer("DATA", in, out);
		if (result != 354) {
			throw new IOException("exception data");
		}

		String charset = Charset.defaultCharset().displayName();
		sendString("From: " + from, out);
		sendNewline(out);

		sendString("To: " + to, out);
		sendNewline(out);

		sendString("Subject: =?" + charset + "?B?" + encode.encodeToString(subject.getBytes()) + "?=", out);
		sendNewline(out);

		sendString("MIME-Version: 1.0", out);
		sendNewline(out);

		sendString("Content-Type: text/html; charset=\"" + charset + "\"", out);
		sendNewline(out);

		sendString("Content-Transfer-Encoding: base64", out);
		sendNewline(out);
		sendNewline(out);

		byte[] data = (content != null ? content : "").getBytes();
		sendString(encode.encodeToString(data), out);
		sendNewline(out);

		result = sendServer(".", in, out);
		if (result != 250) {
			throw new IOException("wrong data");
		}
	}

	private void quit(InputStream in, OutputStream out) throws IOException {
		int result;
		result = sendServer("QUIT", in, out);
		if (result != 221) {
			throw new IOException("quit failed");
		}
	}

	private static void sendNewline(OutputStream out) throws IOException {
		out.write('\r');
		out.write('\n');
		out.flush();
	}

	private int response(InputStream in) throws IOException {
		byte[] buffer = new byte[1024];
		int k = in.read(buffer);
		if (k == -1) {
			return -1;
		}
		String response = new String(buffer, 0, k).trim();
		return Integer.parseInt(response.substring(0, 3));
	}

	private void sendString(String str, OutputStream out) throws IOException {
		if (str == null) {
			str = "";
		}
		out.write(str.getBytes());
		out.flush();
	}
}