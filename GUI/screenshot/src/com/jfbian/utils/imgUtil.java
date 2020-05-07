package com.jfbian.utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * @ClassName:  imgUtil
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date:   2020-04-12 08:53:05
 */
public class ImgUtil {
    private static Logger logger = Logger.getLogger(ImgUtil.class);

    /**
    * @param h
    * @param w
    * @param start_y
    * @param start_x
    * @Title: capterImg
    * @Description: 截图方法
    * @return: void
    */
    public static void capterImg(int start_x, int start_y, int w, int h) throws Exception {
        //利用awt中的方法，通过记录的起始点和长宽完成屏幕截图
        Robot robot = new Robot();
        Rectangle re = new Rectangle(start_x, start_y, w, h);
        BufferedImage bufferedImage = robot.createScreenCapture(re);

        //转换图片格式展示在主舞台的场景中
        WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);

        //将截图内容，放入系统剪切板
        Clipboard cb = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putImage(writableImage);
        cb.setContent(content);
    }

    /**
     *
     * @Title: ocrImg
     * @Description: ocr识图方法
     * @param start_x
     * @param start_y
     * @param w
     * @param h
     * @return: String
     */
    public static String ocrImg(int start_x, int start_y, int w, int h) throws Exception {
        //利用awt中的方法，通过记录的起始点和长宽完成屏幕截图
        Robot robot = new Robot();
        Rectangle re = new Rectangle(start_x, start_y, w, h);
        BufferedImage bufferedImage = robot.createScreenCapture(re);

        // 识别图片的路径（修改为自己的图片路径）
        String ocrImgPath = PathUtil.getTemporaryFolder() + "ocrImg.png";

        //将截取图片放入到系统固定位置
        ImageIO.write(bufferedImage, "png", new File(ocrImgPath));

        String rootPath = PathUtil.getRootPath();

        // 语言库位置（修改为跟自己语言库文件夹的路径）
        String lagnguagePath = rootPath + "tessdata";
        logger.info(lagnguagePath);

        File file = new File(ocrImgPath);
        ITesseract instance = new Tesseract();

        //设置训练库的位置
        instance.setDatapath(lagnguagePath);

        //chi_sim ：简体中文， eng ：英文    根据需求选择语言库
        instance.setLanguage("chi_sim");
        String chiSimResult = "";
        String engResult = "";
        try {
            chiSimResult = instance.doOCR(file);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        instance.setLanguage("eng");
        try {
            engResult = instance.doOCR(file);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        String result = RegexUtil.CheckContainChinese(chiSimResult) || RegexUtil.CheckContainChinese(engResult)
            ? chiSimResult : chiSimResult;
        FileUtil.deleteEveryThing(ocrImgPath);
        return result;
    }
}
