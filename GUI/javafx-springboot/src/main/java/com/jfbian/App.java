package com.jfbian;

import com.jfbian.view.TestView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        // launch(App.class, MainView.class, args);
        launch(App.class, TestView.class, args);
        //  launch(App.class, NoFxmlView.class, args);
    }
}
