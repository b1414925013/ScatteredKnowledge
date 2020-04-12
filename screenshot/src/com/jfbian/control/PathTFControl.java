package com.jfbian.control;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
* @ClassName:  PathTFControl
* @Description:路径转换控制类
* @author: bianjianfeng
* @date:   2020-04-09 21:28:33
*/
public class PathTFControl {
    private static Logger logger = Logger.getLogger(PathTFControl.class);
    @FXML
    TextField textField1;
    @FXML
    TextField textField2;

    /**
     *
     * @Title: initialize
     * @Description: 类加载时候运行的方法
     * @return: void
     * @throws
     */
    public void initialize() {}

    @FXML
    public void transFormationAction() {
        logger.info("路径转换");
        String text = textField1.getText();
        String replaceText = text.replaceAll("\\\\", "/");
        if (text.contains("\\\\")) {
            replaceText = replaceText.replaceAll("//", "/");
        }
        textField2.setText(replaceText);
    }
}
