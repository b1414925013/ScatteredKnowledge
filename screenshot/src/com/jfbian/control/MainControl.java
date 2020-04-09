package com.jfbian.control;

import com.jfbian.stage.CommonStage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
* @ClassName:  MainControl
* @Description:
* @author: bianjianfeng
* @date:   2020年4月8日 下午8:28:59
*/
public class MainControl {

    private Stage mainstage = CommonStage.mainstage;

    @FXML
    Button screenShotButton;
    @FXML
    Button ocrButton;

    @FXML
    Button pathTFButton;

    @FXML
    Button ocrButton1;

    @FXML
    public void screenShotAction() {
        System.out.println("截图按键");
        //将主舞台缩放到任务栏
        mainstage.setIconified(true);
        CommonStage.assistStage();
    }

    @FXML
    public void ocrAction() {
        System.out.println("ocr按键");
        //将主舞台缩放到任务栏
        mainstage.setIconified(true);
        CommonStage.textStage();
    }

    @FXML
    public void pathTFAction() {
        System.out.println("路径转换按键");
        //将主舞台缩放到任务栏
        mainstage.setIconified(true);
        CommonStage.pathTransformationStage();
    }
}
