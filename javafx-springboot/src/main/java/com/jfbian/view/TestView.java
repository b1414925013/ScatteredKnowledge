package com.jfbian.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import de.felixroske.jfxsupport.GUIState;

import javax.annotation.PostConstruct;

@FXMLView(value = "/test.fxml")
public class TestView extends AbstractFxmlView {
    @PostConstruct
    public void init() {
        System.out.println("窗体初始化执行方法");
        //设置窗口名称
        GUIState.getStage().setTitle("测试的窗口");
    }
}
