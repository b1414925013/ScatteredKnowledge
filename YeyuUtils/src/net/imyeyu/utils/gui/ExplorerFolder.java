package net.imyeyu.utils.gui;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

public class ExplorerFolder extends JDialog {

	private static final long serialVersionUID = 1L;
	JFileChooser fileChooser;

	public String open(String path) {
		fileChooser = new JFileChooser(path);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.showOpenDialog(this);
		int flag = fileChooser.showOpenDialog(this);
		return flag == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile().getPath() : null;
	}
	
	public String open(String path, boolean parent) {
		fileChooser = new JFileChooser(path);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(true);
		if (parent) {
			fileChooser.changeToParentDirectory();
		}
		int flag = fileChooser.showOpenDialog(this);
		return flag == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile().getPath() : null;
	}

	public File[] opens(String path) {
		fileChooser = new JFileChooser(path);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.showOpenDialog(this);
		int flag = fileChooser.showOpenDialog(this);
		return flag == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFiles() : null;
	}
	
	public File[] opens(String path, boolean parent) {
		fileChooser = new JFileChooser(path);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(true);
		if (parent) {
			fileChooser.changeToParentDirectory();
		}
		int flag = fileChooser.showOpenDialog(this);
		return flag == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFiles() : null;
	}
}
