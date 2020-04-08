package com.jfbian;

import com.jfbian.stage.CommonStage;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        CommonStage.mainStage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
