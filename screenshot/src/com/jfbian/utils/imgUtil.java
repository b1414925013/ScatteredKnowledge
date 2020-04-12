package com.jfbian.utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * @ClassName:  imgUtil
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date:   2020-04-12 08:53:05
 */
public class imgUtil {
    /**
    * @param h
    * @param w
    * @param start_y
    * @param start_x
    * @Title: capterImg
    * @Description: 截图方法
    * @throws Exception
    * @return: void
    * @throws
    */
    public static void capterImg(int start_x, int start_y, int w, int h) throws Exception {
        //利用awt中的方法，通过记录的起始点和长宽完成屏幕截图
        Robot robot = new Robot();
        Rectangle re = new Rectangle(start_x, start_y, w, h);
        BufferedImage screenCapture = robot.createScreenCapture(re);
        //截图图片背景透明处理
        //BufferedImage bufferedImage = Picture4.transferAlpha(screenCapture);
        //不进行背景透明处理
        BufferedImage bufferedImage = screenCapture;
        //转换图片格式展示在主舞台的场景中
        WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
        //iv.setImage(writableImage);

        //将截图内容，放入系统剪切板
        Clipboard cb = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putImage(writableImage);
        cb.setContent(content);

        //将截取图片放入到系统固定位置
        // ImageIO.write(bufferedImage, "png", new File("E:/capter.png"));
    }
}
