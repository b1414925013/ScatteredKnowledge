package net.imyeyu.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Logger {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss]");
	private File log;
	private StringBuffer sb = new StringBuffer();
	
	/**
	 * 运行日志
	 * 
	 * @param enableLogFile 是否开启每日子时末保存日志到文件
	 */
	public Logger(boolean enableLogFile) {
		if (enableLogFile) {
			(new File("logs")).mkdirs();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			log = new File("logs" + File.separator + format.format(new Date()) + ".log");
			if (log.exists()) sb.append(YeyuUtils.file().fileToString(log, "UTF-8"));
	        Calendar calendar = Calendar.getInstance();  
	        calendar.set(Calendar.HOUR_OF_DAY, 1);
	        calendar.set(Calendar.MINUTE, 0);
	        calendar.set(Calendar.SECOND, 0);
	        Date date = calendar.getTime();
	        date = date.before(new Date()) ? this.addDay(date, 1) : date;
	        Timer timer = new Timer();
	        timer.schedule(new TimerTask() {
				public void run() {
					info("保存服务端运行日志 -> " + log.getName());
					YeyuUtils.file().stringToFile(log, sb.toString());
					log = new File("logs" + File.separator + format.format(new Date()) + ".log");
					sb.setLength(0);
				}
			}, date, 86400000);
		}
	}
	
    private Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }
    
    public void shutdown() {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		info("保存服务端运行日志 -> " + log.getName());
		YeyuUtils.file().stringToFile(log, sb.toString());
		log = new File("logs" + File.separator + format.format(new Date()) + ".log");
    }
	
	private void log(String data) {
		sb.append(data + "\r\n");
		System.out.println(data);
	}
	
	private void err(String data) {
		sb.append(data + "\r\n");
		System.err.println(data);
	}
	
	public void info(Object obj) {
		log(dateFormat.format(new Date()) + "[信息] " + obj.toString());
	}
	
	public void warning(Object obj) {
		log(dateFormat.format(new Date()) + "[警告] " + obj.toString());
	}
	
	public void error(Object obj) {
		err(dateFormat.format(new Date()) + "[错误] " + obj.toString());
	}
	
	public void info(Exception exception) {
		log(dateFormat.format(new Date()) + "[信息] " + exception.toString());
	}
	
	public void warning(Exception exception) {
		log(dateFormat.format(new Date()) + "[警告] " + exception.toString());
	}
	
	public void error(Exception exception) {
		err(dateFormat.format(new Date()) + "[错误] " + exception.toString());
	}
}
