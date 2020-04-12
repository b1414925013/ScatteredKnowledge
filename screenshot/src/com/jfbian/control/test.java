package com.jfbian.control;

import java.io.File;

import org.apache.log4j.Logger;

import com.jfbian.utils.pathUtil;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
* @ClassName:  test
* @Description:测试识图功能
* @author: bianjianfeng
* @date:   2020-04-12 08:19:14
*/
public class test {
    private static Logger logger = Logger.getLogger(test.class);

    public static void main(String[] args) throws Exception {
        String rootPath = pathUtil.getRootPath();
        // 识别图片的路径（修改为自己的图片路径）
        //数字
        // String path = "D:\\Desktop\\sp200411_170824.png";
        //汉字
        String path = "D:\\Desktop\\sp200411_092058.png";

        // 语言库位置（修改为跟自己语言库文件夹的路径）
        //String lagnguagePath = "D:\\develop\\eclipse-workspace\\A\\testTess4j\\src\\main\\resources\\tessdata";
        String lagnguagePath = rootPath + "tessdata";
        logger.info(lagnguagePath);

        File file = new File(path);
        ITesseract instance = new Tesseract();

        //设置训练库的位置
        instance.setDatapath(lagnguagePath);

        //chi_sim ：简体中文， eng    根据需求选择语言库
        //instance.setLanguage("eng");
        instance.setLanguage("chi_sim");
        String result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = instance.doOCR(file);
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        System.out.println("result: ");
        System.out.println(result);
    }
}
