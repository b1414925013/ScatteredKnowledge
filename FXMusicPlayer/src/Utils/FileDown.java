package Utils;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author zhaoyijie（Aquarius_genius）水瓶座鬼才
 * @create 2019/6/13 13:45
 */
public class FileDown {
    /**
     * 说明：根据指定URL将文件下载到指定目标位置
     * @param urlPath 下载路径
     * @return boolean 是否下载成功
     */
    public static boolean downloadFile(String urlPath, String savepath, ProgressIndicator progressIndicator, Label downProgressLabel) {
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            //设置超时
            httpURLConnection.setConnectTimeout(1000*20);
            //设置请求方式，默认是GET
            //httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19");
            // 打开到此 URL引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
            int statusCode =httpURLConnection.getResponseCode();
            System.out.print("statusCode:"+statusCode);
            if (statusCode==404){
                System.out.println("404 not found");
                return false;
            }
            else if (statusCode==403){
                System.out.println("403 服务器拒绝请求");
                return false;
            }
            else if (statusCode==302){
                System.out.println("发生重定向，可能是付费音乐");
                return false;
            }
            else {}
            // 文件大小
            int fileLength = httpURLConnection.getContentLength();

            // 控制台打印文件大小
            System.out.println(" size:"+fileLength / (1024 * 1024) + "MB");
            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            File file = new File(savepath);
            // 校验文件夹目录是否存在，不存在就创建一个目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            float len = 0;
            byte[] buf = new byte[2048];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 控制台打印文件下载的百分比情况
                float finalLen = len/fileLength;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        progressIndicator.setProgress(finalLen);
                        downProgressLabel.setText(Math.round(finalLen*100)+"%");
                    }
                });
            }
            // 关闭资源
            bin.close();
            out.close();
            return true;
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * 根据指定URL将图片下载到指定目标位置
     * @param urlPath 下载路径
     * @param savepath
     * @param width 下载文件保存的宽
     * @param height 下载文件保存的高
     * @return 下载文件的File对象
     */
    public static File downloadImagFile(String urlPath, String savepath,int width,int height) {
        BufferedImage bufferedImage = null;
        try {
            URL url = new URL(urlPath);
            bufferedImage = ImageIO.read(url);
            Image image = bufferedImage.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage outputImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics graphics = outputImage.getGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            File file=new File(savepath);
            ImageIO.write(outputImage, "jpg", file);
            return file;
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }
    /**
     * 说明：根据指定URL将图片下载到指定目标位置
     * @param urlPath 下载路径
     * @return boolean 是否下载成功
     */
    public static boolean downloadImagFile(String urlPath, String savepath) {
        BufferedImage image = null;
        try {

            URL url = new URL(urlPath);
            image = ImageIO.read(url);
            ImageIO.write(image, "jpg", new File(savepath));
            return true;
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
    }
}
