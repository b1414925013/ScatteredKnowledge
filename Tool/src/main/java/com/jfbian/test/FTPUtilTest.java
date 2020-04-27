package com.jfbian.test;

import java.io.File;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jfbian.utils.FTPUtil;

/**
* @ClassName:  FTPUtilTest
* @Description:
* @author: bianjianfeng
* @date:   2020-04-27 23:30:19
*/
public class FTPUtilTest {

    public static void main(String[] args) {
        File file = new File("D://36.txt");
        FTPUtil ft = new FTPUtil();
        Session s = ft.getSession("10.10.81.60", 22, "username", "password");
        Channel channel = ft.getChannel(s);
        ChannelSftp sftp = (ChannelSftp)channel;
        String upload = ft.uploadFile(sftp, "hot_Imgs", file);
        System.out.println(upload);
        ft.closeAll(sftp, channel, s); //关闭连接
    }
}
