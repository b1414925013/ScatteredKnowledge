package com.jfbian.stage;

import java.io.IOException;

import com.jfbian.control.AssistControl;

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
    public static Stage mainstage;
    public static Stage textStage;
    public static Stage assistStage;
    public static AnchorPane assistRoot;

    /**
     *
     * @Title: mainStage
     * @Description: 主页面
     * @param stage
     * @return: void
     * @throws
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
            System.out.println("主页面开启");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: textStage
     * @Description: 文本框页面
     * @return: void
     * @throws
     */
    public static void textStage() {
        try {
            textStage = new Stage();
            Parent root = FXMLLoader.load(CommonStage.class.getResource("/com/jfbian/view/TextView.fxml"));
            Scene scene = new Scene(root);
            textStage.setScene(scene);
            textStage.setTitle("识图界面");
            textStage.setResizable(false);
            textStage.show();
            System.out.println("文本框页面开启");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    *
    * @Title: assistStage
    * @Description: 辅助页面
    * @return: void
    * @throws
    */
    public static void assistStage() {
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
        System.out.println("辅助页面开启");
        AssistControl assistControl = new AssistControl();
        assistControl.mousePressed();
        assistControl.mouseReleased();
        assistControl.mouseDragged();
        assistControl.keyPressed();
    }
}
