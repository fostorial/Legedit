package LegendaryCardMaker;

import java.awt.Color;
import java.io.File;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class LegendaryCardMakerBasicFrame extends JFrame {

	public LegendaryCardMaker lcm;
	
	public LegendaryCardMakerBasicFrame(LegendaryCardMaker lcm)
	{
		this.lcm = lcm;
		
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new JLabel("Working..."));
		setVisible(true);
		
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int outcome = chooser.showOpenDialog(this);
		if (outcome == JFileChooser.APPROVE_OPTION)
		{
			lcm.inputFile = chooser.getSelectedFile().getAbsolutePath();
			lcm.textOutputFile = chooser.getSelectedFile().getParent() + File.separator + "output" + File.separator + "output.txt";
			lcm.exportFolder = chooser.getSelectedFile().getParent() + File.separator + "output" + File.separator;
			lcm.textErrorFile = chooser.getSelectedFile().getParent() + File.separator + "output" + File.separator + "log.txt";
			lcm.textDividerFile = chooser.getSelectedFile().getParent() + File.separator + "output" + File.separator + "dividers.txt";
			File f = new File(lcm.exportFolder);
			f.mkdirs();
		}
		else
		{
			JOptionPane.showMessageDialog(this, "No File Selected");
			System.exit(1);
		}
		
		lcm.processInput(lcm.inputFile);
		
		JOptionPane.showMessageDialog(this, "Done!");
		
		System.exit(0);
	}
}
