package com.jfbian.controller;

import com.jfbian.App;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class TestController implements Initializable {
    @FXML
    public Button buttonId;
    @FXML
    public Label labelId;
    @FXML
    public TextField textFieldId;
    @FXML
    public AnchorPane apId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("控制类初始化执行方法");
        labelId.setText("初始的显示值");
    }

    @FXML
    public void clickButton(ActionEvent actionEvent) {
        System.out.println("点击了button按钮");
        //给text框赋值
        //demo2();
        //生成新的场景
        //demo1();
        //点击网址打开浏览器
        //demo3();
        //测试单选框
        //demo4();

    }

    /**
     * 测试单选框
     */
    private void demo4() {
        AnchorPane anchorPane = new AnchorPane();
        CheckBox checkBox = new CheckBox("点选框");
        anchorPane.getChildren().add(checkBox);
        Scene scene = new Scene(anchorPane, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        App.getStage().hide();
        stage.show();
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("新的状态：" + newValue + " 旧的状态： " + oldValue);
            }
        });
    }

    /**
     * 点击网址打开浏览器
     */
    private void demo3() {
        AnchorPane anchorPane = new AnchorPane();
        Hyperlink link = new Hyperlink("www.baidu.com");
        anchorPane.getChildren().add(link);
        Scene scene = new Scene(anchorPane, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        App.getStage().hide();
        stage.show();
        link.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HostServices hostServices = App.getAppHostServices();
                hostServices.showDocument(link.getText());
            }
        });
    }

    /**
     * 给text框赋值
     */
    private void demo2() {
        String text = textFieldId.getText();
        labelId.setText(text);
    }

    /**
     * 生成新的场景
     */
    private void demo1() {
        AnchorPane anchorPane = new AnchorPane();
        Label label = new Label();
        Button button = new Button();
        label.setText("使用Java代码生成的窗口内容");

        anchorPane.getChildren().addAll(label, button);
        Scene scene = new Scene(anchorPane, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        App.getStage().hide();
        stage.show();

        //监听button点击事件
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                App.getStage().show();
                stage.close();
            }
        });

        //监听窗口关闭事件
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                App.getStage().show();
                stage.close();
            }
        });
    }
}
