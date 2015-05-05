package LegendaryCardMaker;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextOutputDialog extends JDialog {
	
	private JTextArea textArea;
	private JScrollPane scroll;
	
	public TextOutputDialog(String text)
	{
		this.setModal(true);
		this.setSize(500, 700);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setText(text);
		scroll = new JScrollPane();
		scroll.setViewportView(textArea);
		this.add(scroll, BorderLayout.CENTER);
	}
}
