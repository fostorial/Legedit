package LegendaryCardMaker.LegendaryDividerMaker;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import LegendaryCardMaker.CardTextDialog;
import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryCardMakerFrame;

public class HeroDividerMakerToolbar extends JMenuBar implements ActionListener{

	HeroDividerMaker hm;
	HeroDividerMakerFrame hmf;
	
	JMenu file = new JMenu("File");
	
	JMenuItem exportJPG = new JMenuItem("Export to JPEG...");
	JMenuItem exportPNG = new JMenuItem("Export to PNG...");
	JMenuItem exportPrinterStudioPNG = new JMenuItem("Export to Printer Studio PNG...");
	JMenuItem close = new JMenuItem("Close");
	
	JMenu edit = new JMenu("Edit");
	
	JMenuItem setBackgroundImage = new JMenuItem("Set Background Image...");
	JMenuItem setBackgroundZoom = new JMenuItem("Set Background Zoom...");
	
	JMenuItem setForegroundImage = new JMenuItem("Set Foreground Image...");
	JMenuItem setForegroundZoom = new JMenuItem("Set Foreground Zoom...");
	
	JMenuItem resetImage = new JMenuItem("Reset Image");
	
	JCheckBoxMenuItem opaqueTitleBar = new JCheckBoxMenuItem("Opaque Title Bar");
	JMenuItem opaqueTitleColour = new JMenuItem("Opaque Title Bar Colour...");
	
	static HeroDividerMakerToolbar tb = null;
	
	public HeroDividerMakerToolbar(HeroDividerMaker hm, HeroDividerMakerFrame hmf)
	{
		tb = this;
		
		this.hm = hm;
		this.hmf = hmf;
		
		exportJPG.addActionListener(this);
		//file.add(exportJPG);
		exportPNG.addActionListener(this);
		file.add(exportPNG);
		
		exportPrinterStudioPNG.addActionListener(this);
		file.add(exportPrinterStudioPNG);
		
		file.addSeparator();
		
		close.addActionListener(this);
		file.add(close);
		
		add(file);
		
		if (hmf.templateMode)
		{
			setBackgroundImage.addActionListener(this);
			edit.add(setBackgroundImage);
		
			setBackgroundZoom.addActionListener(this);
			edit.add(setBackgroundZoom);
			
			opaqueTitleBar.addActionListener(this);
			opaqueTitleBar.setSelected(LegendaryCardMakerFrame.lcmf.lcm.dividerTitleBarVisible);
			edit.add(opaqueTitleBar);
			
			opaqueTitleColour.addActionListener(this);
			edit.add(opaqueTitleColour);
		}
		else
		{
			setForegroundImage.addActionListener(this);
			edit.add(setForegroundImage);
			
			setForegroundZoom.addActionListener(this);
			edit.add(setForegroundZoom);
		}
		
		resetImage.addActionListener(this);
		edit.add(resetImage);
		
		add(edit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(exportJPG))
		{
			JFileChooser chooser = new JFileChooser();
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				BufferedImage bi = hm.generateDivider();
				try
				{
					hm.exportToJPEG(bi, chooser.getSelectedFile());
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		if (e.getSource().equals(exportPNG))
		{
			JFileChooser chooser = new JFileChooser();
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				BufferedImage bi = hm.generateDivider();
				try
				{
					hm.exportToPNG(bi, chooser.getSelectedFile());
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		
		
		if (e.getSource().equals(close))
		{
			hmf.setVisible(false);
		}
		
		if (e.getSource().equals(setForegroundImage))
		{
			JFileChooser chooser = new JFileChooser();
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			int outcome = chooser.showOpenDialog(hmf);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				
				try
				{
					//ImageIcon ii = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
					//hmf.imageLabel.setIcon(ii);
					hm.hero.imagePath = chooser.getSelectedFile().getAbsolutePath();
					hm.hero.imageZoom = 1.0d;
					hm.hero.imageOffsetX = 0;
					hm.hero.imageOffsetY = 0;
					
					hmf.reRenderCard();
					hm.hero.changed = true;
					
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getSource().equals(setForegroundZoom))
		{
			String s = JOptionPane.showInputDialog(hmf, "Enter the Background Zoom", hm.hero.imageZoom);
			if (s == null) { s = "" + hm.hero.imageZoom; }
			if (s != null && s.isEmpty()) { s = "" + hm.hero.imageZoom; }
			try
			{
				hm.hero.imageZoom = Double.parseDouble(s);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			hmf.reRenderCard();
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			hm.hero.changed = true;
		}
		
		if (e.getSource().equals(setBackgroundImage))
		{
			JFileChooser chooser = new JFileChooser();
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			int outcome = chooser.showOpenDialog(hmf);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				
				try
				{
					//ImageIcon ii = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
					//hmf.imageLabel.setIcon(ii);
					LegendaryCardMakerFrame.lcmf.lcm.dbImagePath = chooser.getSelectedFile().getAbsolutePath();
					LegendaryCardMakerFrame.lcmf.lcm.dbImageZoom = 1.0d;
					LegendaryCardMakerFrame.lcmf.lcm.dbImageOffsetX = 0;
					LegendaryCardMakerFrame.lcmf.lcm.dbImageOffsetY = 0;
					
					hmf.reRenderCard();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
			}
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getSource().equals(setBackgroundZoom))
		{
			String s = JOptionPane.showInputDialog(hmf, "Enter the Background Zoom", LegendaryCardMakerFrame.lcmf.lcm.dbImageZoom);
			if (s == null) { s = "" + LegendaryCardMakerFrame.lcmf.lcm.dbImageZoom; }
			if (s != null && s.isEmpty()) { s = "" + LegendaryCardMakerFrame.lcmf.lcm.dbImageZoom; }
			try
			{
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				LegendaryCardMakerFrame.lcmf.lcm.dbImageZoom = Double.parseDouble(s);
				hmf.reRenderCard();
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			hmf.reRenderCard();
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getSource().equals(opaqueTitleBar))
		{
			LegendaryCardMakerFrame.lcmf.lcm.dividerTitleBarVisible = opaqueTitleBar.isSelected();
			
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			hmf.reRenderCard();
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getSource().equals(opaqueTitleColour))
		{
			JColorChooser chooser = new JColorChooser();
			Color outcome = chooser.showDialog(hmf, "Select title bar colour...", LegendaryCardMakerFrame.lcmf.lcm.dividerTitleBarColour);
			if (outcome != null)
			{
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				try
				{
					LegendaryCardMakerFrame.lcmf.lcm.dividerTitleBarColour = outcome;
					
					hmf.reRenderCard();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		if (e.getSource().equals(resetImage))
		{
			if (hmf.templateMode)
			{
				LegendaryCardMakerFrame.lcmf.lcm.dbImageOffsetX = 0;
				LegendaryCardMakerFrame.lcmf.lcm.dbImageOffsetY = 0;
				LegendaryCardMakerFrame.lcmf.lcm.dbImagePath = null;
				LegendaryCardMakerFrame.lcmf.lcm.dbImageZoom = 1.0d;
			}
			else
			{
				hm.hero.imageOffsetX = 0;
				hm.hero.imageOffsetY = 0;
				hm.hero.imagePath = null;
				hm.hero.imageZoom = 1.0d;
			}
			
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			hmf.reRenderCard();
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public HeroDividerMakerToolbar getHeroMakerToolbar()
	{
		return tb;
	}
}
