package com.jfbian.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mindbright.nio.NetworkConnection;
import com.mindbright.ssh2.SSH2ConsoleRemote;
import com.mindbright.ssh2.SSH2Preferences;
import com.mindbright.ssh2.SSH2SimpleClient;
import com.mindbright.ssh2.SSH2Transport;
import com.mindbright.util.Crypto;

public class SHHUtil {
    public static final Logger logger = Logger.getLogger(SHHUtil.class);
    //SSH2变量
    public static SSH2ConsoleRemote client_ssh2 = null;
    public static boolean echo = false;

    public boolean isEcho() {
        return echo;
    }

    public void setEcho(boolean echo) {
        SHHUtil.echo = echo;
    }

    /**
     *
     * @Title: login
     * @Description: 连接linux
     * @param serverIP
     * @param port
     * @param user
     * @param password
     * @return: boolean
     */
    public static boolean login(String serverIP, int port, String user, String password) {
        try {
            final SSH2Preferences prefs = new SSH2Preferences();
            prefs.setPreference("log-level", "1");
            final NetworkConnection socket = NetworkConnection.open(serverIP, port);
            final SSH2Transport transport = new SSH2Transport(socket, prefs, Crypto.getSecureRandomAndPad());
            SSH2SimpleClient Ssh2 = new SSH2SimpleClient(transport, user, password);
            client_ssh2 = new SSH2ConsoleRemote(Ssh2.getConnection());
            if (client_ssh2.shell(true)) {
                echo = true;
                logger.info("SSH2登录成功");
                return true;
            } else {
                echo = true;
                return false;
            }
        } catch (Exception e) {
            logger.error("登录失败：" + e.getMessage());
            echo = false;
            return false;
        }
    }

    /**
     *
     * @Title: exeCommand
     * @Description: 执行命令
     * @param cmd
     * @return: InputStream
     */
    public static InputStream exeCommand(String cmd) {
        try {

            OutputStream os = client_ssh2.getStdIn();
            os.write(cmd.getBytes());
            os.flush();
            Thread.sleep(1000);
            return client_ssh2.getStdOut();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     *
     * @Title: getString
     * @Description: 获取命令返回值
     * @param inputStream
     * @throws Exception
     * @return: String
     */
    public static String getString(InputStream inputStream) throws Exception {
        String returnString = "";
        while (true) {
            int count = inputStream.available();
            if (count > 0) {
                byte[] tmp = new byte[count];
                inputStream.read(tmp);
                String temp = new String(tmp);
                returnString += temp;
            }
            if (returnString != null && returnString.length() >= 0) {
                return returnString;
            }
        }
    }

    public static boolean getConnectioned() {
        return echo;
    }

    /**
     *
     * @Title: disconnect
     * @Description: 断开连接
     * @return: void
     */
    public static void disconnect() {
        try {
            if (client_ssh2 != null) {
                client_ssh2.close();
                echo = false;
            }
            logger.debug("断开SSH连接！");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     *
     * @Title: exeCommand
     * @Description: 执行linux命令
     * @param serverIP  ip地址
     * @param serverPort 端口号
     * @param username 用户名
     * @param password 密码
     * @param cmds  命令数组
     * @param maxWait 最大等待次数（需大于等于1）
     * @throws Exception
     * @return: Map<String,String>
     */
    public static Map<String, String> exeCommand(String serverIP, int serverPort, String username, String password,
        String[] cmds, int maxWait) throws Exception {
        final Map<String, String> result = new HashMap<>();
        login(serverIP, serverPort, username, password);
        for (int i = 0; i < cmds.length; i++) {
            final StringBuilder sb = new StringBuilder();
            String outInfo = "";
            boolean isExe = true;
            int count = 0;
            while (count < maxWait) {
                if (isExe) {
                    outInfo = SHHUtil.getString(exeCommand(cmds[i] + "\n"));
                    isExe = false;
                } else {
                    outInfo = SHHUtil.getString(client_ssh2.getStdOut());
                }
                if (outInfo.length() > 0) {
                    result.put("cmd_" + i, sb.append(outInfo).toString());
                }
                Thread.sleep(1000);
                count++;
            }
        }
        disconnect();
        return result;
    }

    /**
     *
     * @Title: execCommand
     * @Description: 执行linux命令
     * @param map  连接信息，命令，root信息等
     * @param suRoot 是否切换root
     * @param maxWait 最大等待次数（需大于等于1）
     * @throws Exception
     * @return: Map<String,String>
     */
    public static Map<String, String> exeCommand(Map<String, Object> map, boolean suRoot, int maxWait)
        throws Exception {
        final Map<String, String> result = new HashMap<>();
        final String serverIP = String.valueOf(map.get("serverIP"));
        final String serverPort = String.valueOf(map.get("serverPort"));
        final String username = String.valueOf(map.get("username"));
        final String password = String.valueOf(map.get("password"));
        final String[] cmds = (String[])map.get("cmds");
        try {
            if (login(serverIP, Integer.valueOf(serverPort), username, password)) {
                // 是否切换root
                if (suRoot) {
                    exeCommand("su - " + (String)map.get("root_user") + "\n");
                    // root权限登录
                    exeCommand(String.valueOf(map.get("root_passwd")) + "\n");
                }
                System.out.println("isLogin:success");
                result.put("isLogin", "success");
            } else {
                System.out.println("isLogin:failed");
                result.put("isLogin", "failed");
            }
            for (int i = 0; i < cmds.length; i++) {
                final StringBuilder sb = new StringBuilder();
                String outInfo = "";
                boolean isExe = true;
                int count = 0;
                while (count < maxWait) {
                    if (isExe) {
                        outInfo = SHHUtil.getString(exeCommand(cmds[i] + "\n"));
                        isExe = false;
                    } else {
                        outInfo = SHHUtil.getString(client_ssh2.getStdOut());
                    }
                    if (outInfo.length() > 0) {
                        result.put("cmd_" + i, sb.append(outInfo).toString());
                    }
                    Thread.sleep(1000);
                    count++;
                }
            }
        } finally {
            disconnect();
        }
        return result;
    }
}