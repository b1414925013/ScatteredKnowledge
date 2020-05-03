package net.imyeyu.utils.gui;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ExplorerFile extends JDialog {

	private static final long serialVersionUID = 1L;
	JFileChooser fileChooser;
	
	public File open(String path, String formats) {
		fileChooser = new JFileChooser(path);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.showOpenDialog(this);
		return fileChooser.getSelectedFile();
	}
	
	public File open(String path, boolean parent, String formats) {
		fileChooser = new JFileChooser(path);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (parent) {
			fileChooser.changeToParentDirectory();
		}
		fileChooser.showOpenDialog(this);
		return fileChooser.getSelectedFile();
	}
	
	public File[] open(String path, String[] formats) {
		fileChooser = new JFileChooser(path);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		setFilter(fileChooser, formats);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.showOpenDialog(this);
		return fileChooser.getSelectedFiles();
	}
	
	public File[] open(String path, boolean parent, String[] formats) {
		fileChooser = new JFileChooser(path);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		setFilter(fileChooser, formats);
		fileChooser.setMultiSelectionEnabled(true);
		if (parent) {
			fileChooser.changeToParentDirectory();
		}
		fileChooser.showOpenDialog(this);
		return fileChooser.getSelectedFiles();
	}

	private void setFilter(JFileChooser fileChooser, String[] formats) {
		StringBuffer sb = new StringBuffer();
		sb.append("Support File(");
		for (int i = 0, l = formats.length; i < l; i++) {
			if (i != l - 1) {
				sb.append(formats[i] + ", ");
			} else {
				sb.append(formats[i]);
			}
		}
		sb.append(")");
		switch (formats.length) {
			case 1:
				fileChooser.setFileFilter(new FileNameExtensionFilter(sb.toString(), formats[0]));
				break;
			case 2:
				fileChooser.setFileFilter(new FileNameExtensionFilter(sb.toString(), formats[0], formats[1]));
				break;
			case 3:
				fileChooser.setFileFilter(new FileNameExtensionFilter(sb.toString(), formats[0], formats[1], formats[2]));
				break;
			case 4:
				fileChooser.setFileFilter(new FileNameExtensionFilter(sb.toString(), formats[0], formats[1], formats[2], formats[3]));
				break;
			case 5:
				fileChooser.setFileFilter(new FileNameExtensionFilter(sb.toString(), formats[0], formats[1], formats[2], formats[3], formats[4]));
				break;
			case 6:
				fileChooser.setFileFilter(new FileNameExtensionFilter(sb.toString(), formats[0], formats[1], formats[2], formats[3], formats[4], formats[5]));
				break;
			case 7:
				fileChooser.setFileFilter(new FileNameExtensionFilter(sb.toString(), formats[0], formats[1], formats[2], formats[3], formats[4], formats[5], formats[6]));
				break;
		}
	}
}