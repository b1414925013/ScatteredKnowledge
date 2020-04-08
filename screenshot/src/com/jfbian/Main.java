package com.jfbian;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage mainstage;

    @Override
    public void start(Stage stage) {
        mainstage = stage;

        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/com/jfbian/view/MainView.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("截图小工具");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
