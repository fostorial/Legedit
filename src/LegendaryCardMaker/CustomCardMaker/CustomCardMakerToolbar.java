package LegendaryCardMaker.CustomCardMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import LegendaryCardMaker.CustomCardMaker.components.BackgroundImageMenuItem;
import LegendaryCardMaker.CustomCardMaker.components.CardNameMenuItem;
import LegendaryCardMaker.CustomCardMaker.components.CustomIconMenu;
import LegendaryCardMaker.CustomCardMaker.components.PropertyMenuItem;
import LegendaryCardMaker.CustomCardMaker.components.TextAreaMenuItem;
import LegendaryCardMaker.CustomCardMaker.components.TextMenuItem;
import LegendaryCardMaker.CustomCardMaker.structure.CustomElement;
import LegendaryCardMaker.CustomCardMaker.structure.CustomTemplate;
import LegendaryCardMaker.CustomCardMaker.structure.ElementBackgroundImage;
import LegendaryCardMaker.CustomCardMaker.structure.ElementCardName;
import LegendaryCardMaker.CustomCardMaker.structure.ElementIcon;
import LegendaryCardMaker.CustomCardMaker.structure.ElementProperty;
import LegendaryCardMaker.CustomCardMaker.structure.ElementText;
import LegendaryCardMaker.CustomCardMaker.structure.ElementTextArea;

public class CustomCardMakerToolbar extends JMenuBar implements ActionListener{

	CustomCardMaker hm;
	CustomCardMakerFrame hmf;
	
	JMenu file = new JMenu("File");
	
	JMenuItem exportJPG = new JMenuItem("Export to JPEG...");
	JMenuItem exportPNG = new JMenuItem("Export to PNG...");
	JMenuItem exportPrinterStudioPNG = new JMenuItem("Export to Bordered PNG...");
	JMenuItem close = new JMenuItem("Close");
	
	JMenu edit = new JMenu("Edit");
	
	static CustomCardMakerToolbar tb = null;
	
	public CustomCardMakerToolbar(CustomCardMaker hm, CustomCardMakerFrame hmf)
	{
		tb = this;
		
		this.hm = hm;
		this.hmf = hmf;
		
		exportJPG.addActionListener(this);
		file.add(exportJPG);
		exportPNG.addActionListener(this);
		file.add(exportPNG);
		
		exportPrinterStudioPNG.addActionListener(this);
		file.add(exportPrinterStudioPNG);
		
		file.addSeparator();
		
		close.addActionListener(this);
		file.add(close);
		
		add(file);
		
		
		populateEditMenu(hm.template);
		
	}
	
	public void populateEditMenu(CustomTemplate t)
	{
		edit.removeAll();
		
		for (CustomElement e : t.elements)
		{
			if (e instanceof ElementIcon)
			{
				edit.add(new CustomIconMenu(hmf, (ElementIcon)e));
			}
			
			if (e instanceof ElementProperty)
			{
				edit.add(new PropertyMenuItem(hmf, (ElementProperty)e));
			}
			
			if (e instanceof ElementText)
			{
				if (((ElementText)e).linkedElement == null)
				{
					edit.add(new TextMenuItem(hmf, (ElementText)e));
				}
			}
			
			if (e instanceof ElementCardName && ((ElementCardName)e).allowChange)
			{
				edit.add(new CardNameMenuItem(hmf, (ElementCardName)e));
			}
			
			if (e instanceof ElementTextArea && ((ElementTextArea)e).allowChange)
			{
				edit.add(new TextAreaMenuItem(hmf, (ElementTextArea)e));
			}
			
			if (e instanceof ElementBackgroundImage && ((ElementBackgroundImage)e).allowChange)
			{
				edit.add(new BackgroundImageMenuItem(hmf, (ElementBackgroundImage)e));
			}
		}
		
		add(edit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(exportJPG))
		{
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
		    chooser.addChoosableFileFilter(filter1);
		    chooser.setFileFilter(filter1);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				BufferedImage bi = hm.generateCard();
				try
				{
					if (!chooser.getSelectedFile().getName().toLowerCase().endsWith(".jpg") 
							&& !chooser.getSelectedFile().getName().toLowerCase().endsWith(".jpeg"))
					{
						chooser.setSelectedFile(new File(chooser.getSelectedFile().getAbsolutePath() + ".jpg"));
					}
					if (chooser.getSelectedFile().exists())
					{
						int confirm = JOptionPane.showConfirmDialog(hmf, "Overwrite File?", "File Exists", JOptionPane.YES_OPTION);
						if (confirm == JOptionPane.YES_OPTION)
						{
							hm.exportToJPEG(bi, chooser.getSelectedFile());
						}
					}
					else
					{
						hm.exportToJPEG(bi, chooser.getSelectedFile());
					}
					
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		}
		
		if (e.getSource().equals(exportPNG))
		{
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter("PNG file", "png");
		    chooser.addChoosableFileFilter(filter1);
		    chooser.setFileFilter(filter1);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				BufferedImage bi = hm.generateCard();
				try
				{
					if (!chooser.getSelectedFile().getName().toLowerCase().endsWith(".png"))
					{
						chooser.setSelectedFile(new File(chooser.getSelectedFile().getAbsolutePath() + ".png"));
					}
					if (chooser.getSelectedFile().exists())
					{
						int confirm = JOptionPane.showConfirmDialog(hmf, "Overwrite File?", "File Exists", JOptionPane.YES_OPTION);
						if (confirm == JOptionPane.YES_OPTION)
						{
							hm.exportToPNG(bi, chooser.getSelectedFile());
						}
					}
					else
					{
						hm.exportToPNG(bi, chooser.getSelectedFile());
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		}
		
		if (e.getSource().equals(exportPrinterStudioPNG))
		{
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter("PNG file", "png");
		    chooser.addChoosableFileFilter(filter1);
		    chooser.setFileFilter(filter1);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				BufferedImage bi = hm.generateCard();
				bi = hm.resizeImagePS(bi);
				try
				{
					if (!chooser.getSelectedFile().getName().toLowerCase().endsWith(".png"))
					{
						chooser.setSelectedFile(new File(chooser.getSelectedFile().getAbsolutePath() + ".png"));
					}
					if (chooser.getSelectedFile().exists())
					{
						int confirm = JOptionPane.showConfirmDialog(hmf, "Overwrite File?", "File Exists", JOptionPane.YES_OPTION);
						if (confirm == JOptionPane.YES_OPTION)
						{
							hm.exportToPNG(bi, chooser.getSelectedFile());
						}
					}
					else
					{
						hm.exportToPNG(bi, chooser.getSelectedFile());
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		}
	}

	public CustomCardMakerToolbar getHeroMakerToolbar()
	{
		return tb;
	}
}
