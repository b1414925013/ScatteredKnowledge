
package com.jfbian.control.fxml.imp;

import com.jfbian.control.fxml.IFxmlControl;
import com.jfbian.control.nofxml.OcrContorl;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
* @ClassName:  TextControl
* @Description:文本窗口控制类
* @author: bianjianfeng
* @date:   2020年4月8日 下午8:43:21
*/
public class TextControl implements IFxmlControl {
    private String ocrImgStr = OcrContorl.ocrImgStr;
    @FXML
    TextArea t1;
    @FXML
    AnchorPane textAnchorPane;

    /**
    *
    * @Title: initialize
    * @Description: 类加载时候运行的方法
    * @return: void
    */
    @Override
    public void initialize() {
        //关闭自动换行
        t1.setWrapText(false);
        t1.setText(ocrImgStr);
    }
}
