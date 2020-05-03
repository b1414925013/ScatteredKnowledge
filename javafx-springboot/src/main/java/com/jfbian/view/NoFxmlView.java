package com.jfbian.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;

@FXMLView
public class NoFxmlView extends AbstractFxmlView {
    private Pane myJavaCodedPane;

    @PostConstruct
    public void init() {
        Pane pane = new Pane();
        Button button = new Button("A Button");

        pane.getChildren().add(button);
        myJavaCodedPane = pane;

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("===========点击了button=============");
            }
        });
    }

    @Override
    public Parent getView() {
        return myJavaCodedPane;
    }

}
