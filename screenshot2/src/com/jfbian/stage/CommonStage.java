package com.jfbian.stage;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
* @ClassName:  CommonStage
* @Description:TODO(描述这个类的作用)
* @author: bianjianfeng
* @date:   2020年4月9日 上午12:11:56
*/
public class CommonStage {
    public static Stage mainstage;
    public static Stage textStage;
    public static Stage assistStage;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    *
    * @Title: assistStage
    * @Description: 识图页面
    * @return: void
    * @throws
    */
    public static void assistStage() {
        try {
            assistStage = new Stage();
            Parent root = FXMLLoader.load(CommonStage.class.getClass().getResource("/com/jfbian/view/AssistView.fxml"));
            Scene scene = new Scene(root);
            scene.setFill(Paint.valueOf("#ffffff00"));
            assistStage.setScene(scene);
            assistStage.setFullScreenExitHint("");
            assistStage.initStyle(StageStyle.TRANSPARENT);
            assistStage.setFullScreen(true);
            assistStage.show();
            System.out.println("辅助视图开启");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
