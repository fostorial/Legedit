package LegendaryCardMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import LegendaryCardMaker.CardMakerToolbar;
import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryCardMakerFrame;
import LegendaryCardMaker.LegendaryHeroMaker.Hero;

public class TeamIconSelectorMenu extends JMenu implements ActionListener{
	
	JMenuItem newHero = new JMenuItem("New Team...");
	JMenuItem rename = new JMenuItem("Rename Team...");
	JMenuItem edit = new JMenuItem("Change Icon...");
	JMenuItem underlay = new JMenuItem("Change Underlay...");
	JMenuItem delete = new JMenuItem("Delete Team...");
	
	public LegendaryCardMakerFrame lcmf;
	
	static CardMakerToolbar tb = null;
	
	public TeamIconSelectorMenu(LegendaryCardMakerFrame lcmf, CardMakerToolbar tb)
	{
		this.tb = tb;
		this.lcmf = lcmf;
		
		this.setText("Edit");
		
		newHero.addActionListener(this);
		add(newHero);
		
		addSeparator();
		
		rename.addActionListener(this);
		add(rename);
		
		edit.addActionListener(this);
		add(edit);
		
		underlay.addActionListener(this);
		add(underlay);
		
		delete.addActionListener(this);
		add(delete);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(newHero))
		{
			String name = JOptionPane.showInputDialog(lcmf, "Enter the team tag", null);
			if (name == null) { return; }
			if (name != null && name.isEmpty()) { return; }
			
			String filePath = null;
			JFileChooser chooser = new JFileChooser();
			int outcome = chooser.showOpenDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				filePath = chooser.getSelectedFile().getAbsolutePath();
			}
			else
			{
				return;
			}
			
			boolean underlay = false;
			outcome = JOptionPane.showConfirmDialog(this, "Draw underlay?", "Draw Underlay?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (outcome == JOptionPane.YES_OPTION)
			{
				underlay = true;
			}
			else
			{
				underlay = false;
			}
			
			File newFile = new File("legendary"+File.separator+"Teams"+File.separator+chooser.getSelectedFile().getName());
			try {
				copyFile(chooser.getSelectedFile(), newFile);
			} catch (IOException e1) {
				return;
			}
			
			Icon icon = new Icon(name.toUpperCase(), newFile.getPath(), underlay, Icon.ICON_TYPE.TEAM);
			Icon.values().add(icon);
			
			getTeamListModel().addElement(icon);
			
			Icon.saveIconDefinitions();
		}
		
		if (e.getSource().equals(rename))
		{
			if (getCurrentTeam() == null)
			{
				return;
			}
			
			String name = JOptionPane.showInputDialog(lcmf, "Enter the team tag", getCurrentTeam().getEnumName());
			if (name == null) { return; }
			if (name != null && name.isEmpty()) { return; }
			
			getCurrentTeam().setTagName(name);
			
			Icon.saveIconDefinitions();
		}
		
		if (e.getSource().equals(edit))
		{
			if (getCurrentTeam() == null)
			{
				return;
			}
			
			String filePath = null;
			JFileChooser chooser = new JFileChooser();
			int outcome = chooser.showOpenDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				filePath = chooser.getSelectedFile().getAbsolutePath();
			}
			else
			{
				return;
			}
			
			File newFile = new File("legendary"+File.separator+"Teams"+File.separator+chooser.getSelectedFile().getName());
			try {
				copyFile(chooser.getSelectedFile(), newFile);
			} catch (IOException e1) {
				return;
			}
			
			getCurrentTeam().setImagePath(newFile.getPath());
			
			Icon.saveIconDefinitions();
			
		}
		
		if (e.getSource().equals(underlay))
		{
			if (getCurrentTeam() == null)
			{
				return;
			}
			
			boolean underlay = false;
			int outcome = JOptionPane.showConfirmDialog(this, "Draw underlay?", "Draw Underlay?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (outcome == JOptionPane.YES_OPTION)
			{
				underlay = true;
			}
			else
			{
				underlay = false;
			}
			
			getCurrentTeam().setUnderlayMinimized(underlay);
			
			Icon.saveIconDefinitions();
		}
		
		if (e.getSource().equals(delete))
		{
			if (getCurrentTeam() == null)
			{
				return;
			}
			
			int outcome = JOptionPane.showOptionDialog(lcmf, "Delete Icon?", "Delete Icon?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (outcome == JOptionPane.YES_OPTION)
			{
				Icon.values().remove(getCurrentTeam());
				getTeamListModel().removeElement(getCurrentTeam());
				
				Icon.saveIconDefinitions();
			}
		}
	}
	
	public JList getTeamList()
	{
		return lcmf.teamList;
	}
	
	public DefaultListModel getTeamListModel()
	{
		return lcmf.teamListModel;
	}
	
	public Icon getCurrentTeam()
	{
		Icon i = null;
		
		if (lcmf.teamListModel.size() > 0 && lcmf.teamList.getSelectedIndex() >= 0)
		{
			i = (Icon)lcmf.teamListModel.get(lcmf.teamList.getSelectedIndex());
		}
		
		return i;
	}
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
}
