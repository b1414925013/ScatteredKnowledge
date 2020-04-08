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
    public void screenShotAction() {
        System.out.println("截图按键");
        //将主舞台缩放到任务栏
        mainstage.setIconified(true);
        CommonStage.assistStage();
    }

    @FXML
    public void ocrAction() {
        System.out.println("ocr按键");
        mainstage.setIconified(true);
        CommonStage.textStage();
    }
}
