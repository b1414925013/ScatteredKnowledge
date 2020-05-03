package net.imyeyu.utils.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Debug extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;

	public Debug() {
		setTitle("Debug");
		setSize(450, 300);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setTabSize(3);
		textArea.setFont(new Font("Simsun", Font.PLAIN, 14));
		scrollPane.setViewportView(textArea);
	}
	
	public void setDebug(String s) {
		textArea.setText(s);
	}
}
