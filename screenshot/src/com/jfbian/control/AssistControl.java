package com.jfbian.control;

import org.apache.log4j.Logger;

import com.jfbian.stage.CommonStage;
import com.jfbian.utils.imgUtil;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * @ClassName:  AssistControl
 * @Description:辅助舞台控制类
 * @author: bianjianfeng
 * @date:   2020年4月8日 下午11:24:03
 */
public class AssistControl {
    private static Logger logger = Logger.getLogger(AssistControl.class);
    private Stage mainstage = CommonStage.mainstage;
    private Stage assistStage = CommonStage.assistStage;
    private HBox hBox;
    private double start_x; //切图区域的起始位置x
    private double start_y; //切图区域的起始位置y
    private double w; //切图区域宽
    private double h; //切图区域高

    private AnchorPane assistRoot = CommonStage.assistRoot;

    /**
    *
    * @Title: initialize
    * @Description: 类加载时候运行的方法
    * @return: void
    * @throws
    */
    public void initialize() {}

    /**
     *
     * @Title: mousePressed
     * @Description: 鼠标按下
     * @return: void
     * @throws
     */
    public void mousePressed() {
        logger.info("鼠标按下事件绑定");
        assistRoot.setOnMousePressed(event -> {
            //清除锚点布局中所有子元素
            assistRoot.getChildren().clear();
            //创建切图区域
            hBox = new HBox();
            //设置背景保证能看到切图区域桌面
            hBox.setBackground(null);
            //设置边框
            hBox.setBorder(new Border(
                new BorderStroke(Paint.valueOf("#c03700"), BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
            assistRoot.getChildren().add(hBox);
            //记录并设置起始位置
            start_x = event.getSceneX();
            start_y = event.getSceneY();
            AnchorPane.setLeftAnchor(hBox, start_x);
            AnchorPane.setTopAnchor(hBox, start_y);
        });

    }

    /**
     *
     * @Title: mouseDragged
     * @Description: 鼠标拖动
     * @return: void
     * @throws
     */
    public void mouseDragged() {
        logger.info("鼠标拖动事件绑定");
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
     * @throws
     */
    public void mouseReleased() {
        logger.info("鼠标抬起事件绑定");
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
                    imgUtil.capterImg((int)start_x, (int)start_y, (int)w, (int)h);
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
     * @throws
     */
    public void keyPressed() {
        logger.info("按下ESC键绑定");
        assistRoot.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                assistStage.close();
                mainstage.setIconified(false);
            }
        });
    }
}
