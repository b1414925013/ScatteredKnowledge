package net.imyeyu.utils.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

public class LabelTextField extends HBox {
	
	private String labelText;
	private TextField textField;
	private double width = 64, radius = 2;
	
	private void init() {
		HBox box = new HBox();
		BorderX border = new BorderX("#BFBFBF", BorderX.SOLID, 1);

		border.setRadius(radius, 0, 0, radius, false);
		Label label = new Label(labelText);
		label.setPrefHeight(27);
		label.setBorder(border.exRight());
		label.setTextFill(Paint.valueOf("#999"));
		label.setPadding(new Insets(0, 6, 0, 6));
		
		border.setRadius(0, radius, radius, 0, false);
		textField = new TextField();
		textField.setPrefWidth(width);
		textField.setBorder(border.def());
		textField.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFF"), CornerRadii.EMPTY, Insets.EMPTY)));
		
		box.setAlignment(Pos.CENTER_LEFT);
		box.setBackground(new Background(new BackgroundFill(Paint.valueOf("#F4F4F4"), CornerRadii.EMPTY, Insets.EMPTY)));
		box.getChildren().addAll(label, textField);
		
		getChildren().add(box);
	}
	
	public LabelTextField(String labelText) {
		this.labelText = labelText;
		init();
	}

	public LabelTextField(String labelText, double width) {
		this.labelText = labelText;
		this.width = width;
		init();
	}

	public LabelTextField(String labelText, double width, double radius) {
		this.labelText = labelText;
		this.width = width;
		this.radius = radius;
		init();
	}
	
	public void setText(String text) {
		this.textField.setText(text);
	}
	
	public String getText() {
		return textField.getText();
	}
	
	public TextField getTextField() {
		return textField;
	}
}
