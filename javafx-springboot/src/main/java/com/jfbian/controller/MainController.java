package com.jfbian.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 页面功能控制器
 */
@FXMLController
public class MainController implements Initializable {
    /**
     * 页面初始化时执行的方法
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("-------------------");
    }
}
