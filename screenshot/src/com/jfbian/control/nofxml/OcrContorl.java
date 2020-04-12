package com.jfbian.control.nofxml;

import org.apache.log4j.Logger;

import com.jfbian.stage.CommonStage;
import com.jfbian.utils.ImgUtil;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
* @ClassName:  ocrContorl
* @Description:ocr控制类
* @author: bianjianfeng
* @date:   2020-04-12 18:58:28
*/
public class OcrContorl extends AbstractAssistControl {
    private static Logger logger = Logger.getLogger(OcrContorl.class);
    private Stage textStage = CommonStage.textStage;
    public static String ocrImgStr;

    /**
     *
     * @Title: mouseDragged
     * @Description: 鼠标拖动
     * @return: void
     */
    @Override
    public void mouseDragged() {
        assistRoot.setOnMouseDragged(event -> {
            //计算宽高并且完成切图区域的动态效果
            w = Math.abs(event.getSceneX() - start_x);
            h = Math.abs(event.getSceneY() - start_y);
            hBox.setPrefWidth(w);
            hBox.setPrefHeight(h);
        });
    }

    /**
     *
     * @Title: mouseReleased
     * @Description: 鼠标抬起
     * @return: void
     */
    @Override
    public void mouseReleased() {
        assistRoot.setOnMouseReleased(event -> {
            //记录最终长宽
            w = Math.abs(event.getSceneX() - start_x);
            h = Math.abs(event.getSceneY() - start_y);
            assistRoot.setStyle("-fx-background-color: #00000000");
            //添加剪切按钮，并显示在切图区域的底部
            Button b = new Button("剪切");
            hBox.setBorder(new Border(
                new BorderStroke(Paint.valueOf("#85858544"), BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
            hBox.getChildren().add(b);
            hBox.setAlignment(Pos.BOTTOM_RIGHT);
            //为切图按钮绑定切图事件
            b.setOnAction(event1 -> {
                //切图辅助舞台消失
                assistStage.close();
                try {
                    //识图具体方法
                    ocrImgStr = ImgUtil.ocrImg((int)start_x, (int)start_y, (int)w, (int)h);
                    textStage = new Stage();
                    Parent root = FXMLLoader.load(CommonStage.class.getResource("/com/jfbian/view/TextView.fxml"));
                    Scene scene = new Scene(root);
                    textStage.setScene(scene);
                    textStage.setTitle("识图界面");
                    textStage.setResizable(false);
                    textStage.show();
                    logger.info("文本框页面开启");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    /**
     *
     * @Title: keyPressed
     * @Description: 键盘按下
     * @return: void
     */
    @Override
    public void keyPressed() {
        assistRoot.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                assistStage.close();
                mainstage.setIconified(false);
            }
        });
    }
}
