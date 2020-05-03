package net.imyeyu.desktopnote.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.imyeyu.utils.gui.AnchorPaneX;
import net.imyeyu.utils.gui.BorderX;

public class ViewMain extends Stage {
	
	private Stage stage;
	private Label time;
	private TextArea textArea;
	private AnchorPane mainBox, header;

	public ViewMain() {
		// 嵌套舞台去除边框的同时不显示在任务栏
        initStyle(StageStyle.UTILITY);
        setOpacity(0);
        show();
        // 主容器
		mainBox = new AnchorPane();
		// 主面板
		BorderPane main = new BorderPane();
		// 头部
        header = new AnchorPane();
        
        Label title = new Label("夜雨便签");
        AnchorPaneX.exRight(title);
        title.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 13));
        time = new Label("0000-00-00 00:00:00");
        time.setFont(Font.font("Consolas"));
        time.setTextFill(Paint.valueOf("#8F7B55"));
        AnchorPaneX.exLeft(time);
        
        AnchorPaneX.exBottom(header);
        header.setBorder(new BorderX("#D6BE96", BorderX.SOLID, 1).bottom());
        header.setPadding(new Insets(3, 6, 3, 6));
        header.setStyle("-fx-background-color: #FCF0D1");
        header.getChildren().addAll(title, time);
        // 文本区
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setFocusTraversable(false);
        textArea.setFont(Font.font("Consolas", FontWeight.BOLD, 13));
        textArea.setBorder(new BorderX("#fbf2db", BorderX.SOLID, 1).def());
        AnchorPaneX.def(textArea);
        
		AnchorPaneX.def(main);
		DropShadow dropshadow = new DropShadow();
		dropshadow.setRadius(6);
		dropshadow.setOffsetX(0);
		dropshadow.setOffsetY(0);
		dropshadow.setSpread(.05);
		dropshadow.setColor(Color.valueOf("#0007"));
		main.setStyle("-fx-background-color: #000");
		main.setEffect(dropshadow);
		main.setTop(header);
		main.setCenter(textArea);
		
		mainBox.setPadding(new Insets(10));
		mainBox.setBackground(Background.EMPTY);
		mainBox.getChildren().add(main);
		
		Scene scene = new Scene(mainBox);
		scene.getStylesheets().add(this.getClass().getResource("/net/imyeyu/desktopnote/res/style.css").toExternalForm());
		scene.setFill(Color.TRANSPARENT);
        stage = new Stage();
        stage.initOwner(this);
        stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(scene);
		stage.setWidth(320);
		stage.setHeight(520);
	}

	public Stage getStage() {
		return stage;
	}

	public AnchorPane getMainBox() {
		return mainBox;
	}

	public AnchorPane getHeader() {
		return header;
	}
	
	public Label getTime() {
		return time;
	}

	public TextArea getTextArea() {
		return textArea;
	}
}