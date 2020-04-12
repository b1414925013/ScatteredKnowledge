package com.jfbian.stage;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.jfbian.control.nofxml.AssistControl;
import com.jfbian.control.nofxml.OcrContorl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
* @ClassName:  CommonStage
* @Description:舞台公共类
* @author: bianjianfeng
* @date:   2020年4月9日 上午12:11:56
*/
public class CommonStage {
    private static Logger logger = Logger.getLogger(CommonStage.class);
    public static Stage mainstage;
    public static Stage textStage;
    public static Stage assistStage;
    public static Stage pathTransformationStage;
    public static AnchorPane assistRoot;

    /**
     *
     * @Title: mainStage
     * @Description: 主页面
     * @param stage
     * @return: void
     */
    public static void mainStage(Stage stage) {
        mainstage = stage;
        try {
            Parent root = FXMLLoader.load(CommonStage.class.getResource("/com/jfbian/view/MainView.fxml"));
            Scene scene = new Scene(root);
            mainstage.setScene(scene);
            mainstage.setTitle("截图小工具");
            mainstage.setResizable(false);
            mainstage.show();
            logger.info("主页面开启");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: textStage
     * @Description: 文本框页面
     * @return: void
     */
    public static void textStage() {
        showAssistStage();
        logger.info("辅助页面开启");
        OcrContorl ocrContorl = new OcrContorl();
        ocrContorl.keyPressed();
        ocrContorl.mousePressed();
        ocrContorl.mouseDragged();
        ocrContorl.mouseReleased();
    }

    /**
    *
    * @Title: assistStage
    * @Description: 辅助页面
    * @return: void
    */
    public static void assistStage() {
        showAssistStage();
        logger.info("辅助页面开启");
        AssistControl assistControl = new AssistControl();
        assistControl.keyPressed();
        assistControl.mousePressed();
        assistControl.mouseReleased();
        assistControl.mouseDragged();
    }

    /**
     * @Title: showAssistStage
     * @Description: 开启辅助页面
     * @return: void
     */
    public static void showAssistStage() {
        //创建辅助舞台，并设置场景与布局
        assistStage = new Stage();
        assistRoot = new AnchorPane();
        assistRoot.setStyle("-fx-background-color: #85858522");
        //场景设置白色全透明
        Scene scene = new Scene(assistRoot);
        scene.setFill(Paint.valueOf("#ffffff00"));
        assistStage.setScene(scene);
        //清楚全屏中间提示文字
        assistStage.setFullScreenExitHint("");
        assistStage.initStyle(StageStyle.TRANSPARENT);
        assistStage.setFullScreen(true);
        assistStage.show();
    }

    /**
    *
    * @Title: pathTransformationStage
    * @Description: 路径转换页面
    * @param stage
    * @return: void
    */
    public static void pathTransformationStage() {
        try {
            pathTransformationStage = new Stage();
            Parent root =
                FXMLLoader.load(CommonStage.class.getResource("/com/jfbian/view/PathTransformationView.fxml"));
            Scene scene = new Scene(root);
            pathTransformationStage.setScene(scene);
            pathTransformationStage.setTitle("路径转换界面");
            pathTransformationStage.setResizable(false);
            pathTransformationStage.show();
            logger.info("路径转换页面开启");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
