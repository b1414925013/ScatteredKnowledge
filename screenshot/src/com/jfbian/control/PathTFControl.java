package com.jfbian.control;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
* @ClassName:  PathTFControl
* @Description:路径转换控制类
* @author: bianjianfeng
* @date:   2020-04-09 21:28:33
*/
public class PathTFControl {

    @FXML
    TextField textField1;
    @FXML
    TextField textField2;

    @FXML
    public void transFormationAction() {
        System.out.println("路径转换");
        String text = textField1.getText();
        String replaceText = text.replaceAll("\\\\", "/");
        if (text.contains("\\\\")) {
            replaceText = replaceText.replaceAll("//", "/");
        }
        textField2.setText(replaceText);
    }
}
