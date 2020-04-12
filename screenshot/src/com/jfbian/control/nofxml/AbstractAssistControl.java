package com.jfbian.control.nofxml;

import com.jfbian.stage.CommonStage;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
* @ClassName:  AbstractAssistControl
* @Description:辅助页面控制器抽象类
* @author: bianjianfeng
* @date:   2020-04-12 22:37:44
*/
public abstract class AbstractAssistControl {

    public Stage mainstage = CommonStage.mainstage;
    public Stage assistStage = CommonStage.assistStage;
    /**
     * 切图区域组件
     */
    public HBox hBox;
    /**
     *开始X坐标
     */
    public double start_x;
    /**
     *开始Y坐标
     */
    public double start_y;
    /**
     *切图区域宽度
     */
    public double w;
    /**
     *切图区域长度
     */
    public double h;
    /**
     * 辅助页面顶级组件
     */
    public AnchorPane assistRoot = CommonStage.assistRoot;

    /**
     *
     * @Title: mousePressed
     * @Description: 鼠标按下
     * @return: void
     */
    public void mousePressed() {
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
     * @Description: 鼠标拖动事件
     * @return: void
     */
    public abstract void mouseDragged();

    /**
     *
     * @Title: mouseReleased
     * @Description: 鼠标抬起事件
     * @return: void
     */
    public abstract void mouseReleased();

    /**
     *
     * @Title: keyPressed
     * @Description: 键盘按下事件
     * @return: void
     */
    public abstract void keyPressed();

}