package com.jfbian.control;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jfbian.Main;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
* @ClassName:  MainControl
* @Description:
* @author: bianjianfeng
* @date:   2020年4月8日 下午8:28:59
*/
public class MainControl {

    private static Stage textStage;
    private Stage mainstage = Main.mainstage;
    private Stage stage; //切图时候的辅助舞台
    private double start_x; //切图区域的起始位置x
    private double start_y; //切图区域的起始位置y
    private double w; //切图区域宽
    private double h; //切图区域高
    private HBox hBox; //切图区域

    @FXML
    Button screenShotButton;
    @FXML
    Button ocrButton;

    @FXML
    public void screenShotAction() {
        System.out.println("截图按键");
        show();
    }

    public void show() {
        //将主舞台缩放到任务栏
        mainstage.setIconified(true);
        //创建辅助舞台，并设置场景与布局
        stage = new Stage();
        //锚点布局采用半透明
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: #85858522");
        //场景设置白色全透明
        Scene scene = new Scene(anchorPane);
        scene.setFill(Paint.valueOf("#ffffff00"));
        stage.setScene(scene);
        //清楚全屏中间提示文字
        stage.setFullScreenExitHint("");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setFullScreen(true);
        stage.show();

        //切图窗口绑定鼠标按下事件
        anchorPane.setOnMousePressed(event -> {
            //清除锚点布局中所有子元素
            anchorPane.getChildren().clear();
            //创建切图区域
            hBox = new HBox();
            //设置背景保证能看到切图区域桌面
            hBox.setBackground(null);
            //设置边框
            hBox.setBorder(new Border(
                new BorderStroke(Paint.valueOf("#c03700"), BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
            anchorPane.getChildren().add(hBox);
            //记录并设置起始位置
            start_x = event.getSceneX();
            start_y = event.getSceneY();
            AnchorPane.setLeftAnchor(hBox, start_x);
            AnchorPane.setTopAnchor(hBox, start_y);
        });
        //绑定鼠标按下拖拽的事件
        anchorPane.setOnMouseDragged(event -> {
            //用label记录切图区域的长宽
            Label label = new Label();
            label.setAlignment(Pos.CENTER);
            label.setPrefHeight(30);
            label.setPrefWidth(170);
            anchorPane.getChildren().add(label);
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

        //绑定鼠标松开事件
        anchorPane.setOnMouseReleased(event -> {
            //记录最终长宽
            w = Math.abs(event.getSceneX() - start_x);
            h = Math.abs(event.getSceneY() - start_y);
            anchorPane.setStyle("-fx-background-color: #00000000");
            //添加剪切按钮，并显示在切图区域的底部
            Button b = new Button("剪切");
            hBox.setBorder(new Border(
                new BorderStroke(Paint.valueOf("#85858544"), BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
            hBox.getChildren().add(b);
            hBox.setAlignment(Pos.BOTTOM_RIGHT);
            //为切图按钮绑定切图事件
            b.setOnAction(event1 -> {
                //切图辅助舞台小时
                stage.close();
                try {
                    //切图具体方法
                    capterImg();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //主舞台还原
                mainstage.setIconified(false);
            });
        });

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.close();
                mainstage.setIconified(false);
            }
        });
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

    //====================================================
    @FXML
    public void ocrAction() {
        System.out.println("ocr按键");
        Main.mainstage.setIconified(true);
        textView();
    }

    /**
    *
    * @Title: textView
    * @Description: 识图页面
    * @param mainstage
    * @return: void
    * @throws
    */
    public void textView() {
        try {
            textStage = new Stage();
            Parent root = FXMLLoader.load(this.getClass().getResource("/com/jfbian/view/TextView.fxml"));
            Scene scene = new Scene(root);
            textStage.setScene(scene);
            textStage.setTitle("识图界面");
            textStage.setResizable(false);
            textStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
