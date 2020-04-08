package com.jfbian.control;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import com.jfbian.stage.CommonStage;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date:   2020年4月8日 下午11:24:03
 */
public class AssistControl {
    private Stage mainstage = CommonStage.mainstage;
    private Stage assistStage = CommonStage.assistStage;
    private HBox hBox;
    private double start_x; //切图区域的起始位置x
    private double start_y; //切图区域的起始位置y
    private double w; //切图区域宽
    private double h; //切图区域高
    @FXML
    public AnchorPane assist;

    @FXML
    public void mousePressed(MouseEvent event) {
        System.out.println("鼠标按下");
        //清除锚点布局中所有子元素
        assist.getChildren().clear();
        //创建切图区域
        hBox = new HBox();
        //设置背景保证能看到切图区域桌面
        hBox.setBackground(null);
        //设置边框
        hBox.setBorder(
            new Border(new BorderStroke(Paint.valueOf("#c03700"), BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
        assist.getChildren().add(hBox);
        //记录并设置起始位置
        start_x = event.getSceneX();
        start_y = event.getSceneY();
        AnchorPane.setLeftAnchor(hBox, start_x);
        AnchorPane.setTopAnchor(hBox, start_y);
    }

    @FXML
    public void mouseDragged(MouseEvent event) {
        System.out.println("鼠标拖动");
        //用label记录切图区域的长宽
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setPrefHeight(30);
        label.setPrefWidth(170);
        assist.getChildren().add(label);
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

    }

    @FXML
    public void mouseReleased(MouseEvent event) {
        System.out.println("鼠标抬起");
        //记录最终长宽
        w = Math.abs(event.getSceneX() - start_x);
        h = Math.abs(event.getSceneY() - start_y);
        assist.setStyle("-fx-background-color: #00000000");
        //添加剪切按钮，并显示在切图区域的底部
        Button b = new Button("剪切");
        hBox.setBorder(new Border(
            new BorderStroke(Paint.valueOf("#85858544"), BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
        hBox.getChildren().add(b);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        //为切图按钮绑定切图事件
        b.setOnAction(event1 -> {
            //切图辅助舞台小时
            assistStage.close();
            try {
                //切图具体方法
                capterImg();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //主舞台还原
            mainstage.setIconified(false);
        });

    }

    @FXML
    public void keyPressed(KeyEvent event) {
        System.out.println("按下esc键");
        if (event.getCode() == KeyCode.ESCAPE) {
            assistStage.close();
            mainstage.setIconified(false);
        }

    }

    /**
    *
    * @Title: capterImg
    * @Description: 截图方法
    * @throws Exception
    * @return: void
    * @throws
    */
    public void capterImg() throws Exception {
        //利用awt中的方法，通过记录的起始点和长宽完成屏幕截图
        Robot robot = new Robot();
        Rectangle re = new Rectangle((int)start_x, (int)start_y, (int)w, (int)h);
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
