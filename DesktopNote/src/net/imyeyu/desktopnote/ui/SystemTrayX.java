package net.imyeyu.desktopnote.ui;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.ImageIcon;

import javafx.application.Platform;
import net.imyeyu.desktopnote.Entrance;
import net.imyeyu.desktopnote.ctrl.Main;
import net.imyeyu.utils.Configer;
import net.imyeyu.utils.YeyuUtils;

public class SystemTrayX implements ActionListener, ItemListener {
	
	private Map<String, Object> config = Entrance.getConfig();
	
	private Main main;
	private static TrayIcon icon;
	private MenuItem name, show, back, exit;
	private SystemTray tray = SystemTray.getSystemTray();
	private CheckboxMenuItem statiz;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

	public SystemTrayX(Main main) {
		this.main = main;
		
		PopupMenu menu = new PopupMenu();
		name = new MenuItem("DesktopNote - 夜雨便签");
		show = new MenuItem("显示便签");
		show.addActionListener(this);
		statiz = new CheckboxMenuItem("固定便签位置");
		statiz.setState(Boolean.valueOf(config.get("isStatiz").toString()));
		statiz.addItemListener(this);
		back = new MenuItem("回滚一小时前的便签");
		back.addActionListener(this);
		exit = new MenuItem("关闭");
		exit.addActionListener(this);
		menu.add(name);
		menu.addSeparator();
		menu.add(show);
		menu.addSeparator();
		menu.add(statiz);
		menu.add(back);
		menu.add(exit);

		ImageIcon img = new ImageIcon(getClass().getResource("/net/imyeyu/desktopnote/res/icon.png"));
		icon = new TrayIcon(img.getImage(), "DesktopNote - 夜雨便签", menu);
		dbClickEvent();
		try {
			tray.add(icon);
		} catch (AWTException e) {
			YeyuUtils.gui().exception(e);
		}
	}
	
	public void removeIcon() {
		tray.remove(icon);
	}
	
	private void dbClickEvent() {
		icon.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if (!e.isMetaDown()) Platform.runLater(() -> main.getView().requestFocus());
			}
		});
	}

	public void actionPerformed(ActionEvent eelement) {
		MenuItem menu = (MenuItem) eelement.getSource();
		if (menu == show) {
			Platform.runLater(() -> main.getView().requestFocus());
		}
		if (menu == back) {
			Platform.runLater(() -> {
				File backup = new File("DesktopNote.backup");
				main.getView().getTextArea().setText(YeyuUtils.file().fileToString(backup, "UTF-8"));
				main.getView().getTime().setText(dateFormat.format(new Date(backup.lastModified())));
			});
		}
		if (menu == exit) {
			removeIcon();
			Platform.runLater(() -> Platform.exit());
		}
	}
	
	public static TrayIcon getIcon() {
		return icon;
	}

	public void itemStateChanged(ItemEvent e) {
		CheckboxMenuItem checkbox = (CheckboxMenuItem) e.getSource();
		main.setStatiz(checkbox.getState());
		config.put("isStatiz", checkbox.getState());
		new Configer("DesktopNote").set(config);
	}
}