package net.imyeyu.desktopnote.ctrl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.stage.Stage;
import net.imyeyu.desktopnote.Entrance;
import net.imyeyu.desktopnote.ui.SystemTrayX;
import net.imyeyu.desktopnote.util.BackupService;
import net.imyeyu.desktopnote.view.ViewMain;
import net.imyeyu.utils.Configer;
import net.imyeyu.utils.YeyuUtils;

public class Main extends Application {
	
	private Map<String, Object> config = Entrance.getConfig();
	
	private File file = new File("DesktopNote.data");
	private Stage stage;
	private double ox = 0, oy = 0;
	private boolean isStatiz = false;
	private ViewMain view;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

	public void start(Stage stage) throws Exception {
		view = new ViewMain();
		isStatiz = Boolean.valueOf(config.get("isStatiz").toString());
		new BackupService().start();
		new SystemTrayX(this);
		
		this.stage = view.getStage();
		// 头部 点击
		view.getHeader().setOnMousePressed(event -> {
			view.getHeader().requestFocus();
			view.getHeader().setCursor(Cursor.CLOSED_HAND);
			ox = event.getX();
			oy = event.getY();
		});
		// 头部 - 拖动
		view.getHeader().setOnMouseDragged(event -> {
			if (!isStatiz) {
				this.stage.setX(event.getScreenX() - ox - 10);
				this.stage.setY(event.getScreenY() - oy - 10);
			}
		});
		// 头部 - 释放
		view.getHeader().setOnMouseReleased(event -> {
			view.getHeader().setCursor(Cursor.DEFAULT);
			config.put("x", this.stage.getX());
			config.put("y", this.stage.getY());
			new Configer("DesktopNote").set(config);
		});
		// 方向键微调窗体位置
		this.stage.getScene().setOnKeyPressed(event -> {
			if (!isStatiz && !view.getTextArea().isFocused()) {
				switch (event.getCode()) {
					case UP:
						this.stage.setY(this.stage.getY() - 1);
						break;
					case DOWN:
						this.stage.setY(this.stage.getY() + 1);
						break;
					case LEFT:
						this.stage.setX(this.stage.getX() - 1);
						break;
					case RIGHT:
						this.stage.setX(this.stage.getX() + 1);
						break;
					default:
						break;
				}
			}
		});
		// 调整动作后保存窗体坐标
		this.stage.getScene().setOnKeyReleased(event -> {
			if (!isStatiz && !view.getTextArea().isFocused()) {
				switch (event.getCode()) {
					case UP:
					case DOWN:
					case LEFT:
					case RIGHT:
						config.put("x", this.stage.getX());
						config.put("y", this.stage.getY());
						new Configer("DesktopNote").set(config);
						break;
					default:
						break;
				}
			}
		});
		// 失去焦点保存
		view.getTextArea().focusedProperty().addListener((tmp, o, focus) -> {
			if (!focus) {
				YeyuUtils.file().stringToFile(file, view.getTextArea().getText());
				view.getTime().setText(dateFormat.format(new Date()));
			}
		});
		// 加载已储存数据
		view.getTextArea().setText(YeyuUtils.file().fileToString(file, "UTF-8"));
		view.getTime().setText(dateFormat.format(new Date(file.lastModified())));

		String x = config.get("x").toString(), y = config.get("y").toString();
		if (!x.equals("default") && !y.equals("default")) {
			this.stage.setX(Double.valueOf(config.get("x").toString()));
			this.stage.setY(Double.valueOf(config.get("y").toString()));
		}
		this.stage.show();
	}

	public ViewMain getView() {
		return view;
	}

	public void setStatiz(boolean isStatiz) {
		this.isStatiz = isStatiz;
	}
}