package com.jfbian.control.nofxml;

import org.apache.log4j.Logger;

import com.jfbian.utils.ImgUtil;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Paint;

/**
 * @ClassName:  AssistControl
 * @Description:辅助舞台控制类
 * @author: bianjianfeng
 * @date:   2020年4月8日 下午11:24:03
 */
public class AssistControl extends AbstractAssistControl {
    private static Logger logger = Logger.getLogger(AssistControl.class);

    /**
     *
     * @Title: mouseDragged
     * @Description: 鼠标拖动
     * @return: void
     */
    @Override
    public void mouseDragged() {
        assistRoot.setOnMouseDragged(event -> {
            //用label记录切图区域的长宽
            Label label = new Label();
            label.setAlignment(Pos.CENTER);
            label.setPrefHeight(30);
            label.setPrefWidth(170);
            assistRoot.getChildren().add(label);
            AnchorPane.setLeftAnchor(label, start_x + 30);
            AnchorPane.setTopAnchor(label, start_y);
            label.setTextFill(Paint.valueOf("#ffffff"));//白色填充
            label.setStyle("-fx-background-color: #000000");//黑背景
            //计算宽高并且完成切图区域的动态效果
            w = Math.abs(event.getSceneX() - start_x);
            h = Math.abs(event.getSceneY() - start_y);
            hBox.setPrefWidth(w);
            hBox.setPrefHeight(h);
            label.setText("宽：" + w + " 高：" + h);
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
                    //切图具体方法
                    ImgUtil.capterImg((int)start_x, (int)start_y, (int)w, (int)h);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //主舞台还原
                mainstage.setIconified(false);
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
