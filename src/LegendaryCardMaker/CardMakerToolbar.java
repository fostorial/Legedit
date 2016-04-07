package LegendaryCardMaker;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import LegendaryCardMaker.Icon;
import LegendaryCardMaker.CampaignManager.CampaignManagerFrame;
import LegendaryCardMaker.CustomCardMaker.CustomCardMakerFrame;
import LegendaryCardMaker.CustomCardMaker.gui.CustomTemplateList;
import LegendaryCardMaker.CustomCardMaker.structure.CustomCard;
import LegendaryCardMaker.LegendaryDividerMaker.HeroDividerMakerFrame;
import LegendaryCardMaker.LegendaryDividerMaker.VillainDividerMakerFrame;
import LegendaryCardMaker.LegendaryHeroMaker.CardRarity;
import LegendaryCardMaker.LegendaryHeroMaker.Hero;
import LegendaryCardMaker.LegendaryHeroMaker.HeroCard;
import LegendaryCardMaker.LegendaryHeroMaker.HeroMaker;
import LegendaryCardMaker.LegendaryHeroMaker.HeroMakerFrame;
import LegendaryCardMaker.LegendaryHeroMaker.HeroSelectorMenu;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCard;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeMaker;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeSelectorMenu;
import LegendaryCardMaker.LegendaryVillainMaker.BystanderSelectorMenu;
import LegendaryCardMaker.LegendaryVillainMaker.Villain;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCard;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCardType;
import LegendaryCardMaker.LegendaryVillainMaker.VillainMaker;
import LegendaryCardMaker.LegendaryVillainMaker.VillainSelectorMenu;
import LegendaryCardMaker.LegendaryVillainMaker.WoundSelectorMenu;
import LegendaryCardMaker.LegendaryVillainMaker.BindingsSelectorMenu;
import LegendaryCardMaker.exporters.ExportDividersHomeprintProgressBarDialog;
import LegendaryCardMaker.exporters.ExportFullProgressBarDialog;
import LegendaryCardMaker.exporters.ExportHomeprintProgressBarDialog;
import LegendaryCardMaker.exporters.ExportProgressBarDialog;
import LegendaryCardMaker.exporters.ItemSelectorDialog;
import LegendaryCardMaker.tools.bleeder.LegeditBleeder;

public class CardMakerToolbar extends JMenuBar implements ActionListener{

	JMenu file = new JMenu("File");
	
	JMenuItem newExpansion = new JMenuItem("New");
	JMenuItem open = new JMenuItem("Open...");
	JMenuItem save = new JMenuItem("Save");
	JMenuItem saveAs = new JMenuItem("Save As...");
	JMenuItem exit = new JMenuItem("Exit");
	
	JMenuItem exportJPG = new JMenuItem("Export to JPEG...");
	JMenuItem exportPNG = new JMenuItem("Export to PNG...");
	JMenuItem exportPrinterStudioPNG = new JMenuItem("Export to Printer Studio PNG...");
	JMenuItem exportJPEGHomeprint = new JMenuItem("Export to JPEG for Homeprint...");
	JMenuItem exportSelectedJPEGHomeprint = new JMenuItem("Export Selected Items to JPEG for Homeprint...");
	JMenuItem exportDividersJPEGHomeprint = new JMenuItem("Export Dividers to JPEG for Homeprint...");
	JMenuItem exportFull = new JMenuItem("Export Full Expansion...");
	
	JMenuItem viewAsText = new JMenuItem("View as Text...");
	JMenuItem viewStatistics = new JMenuItem("View Statistics...");
	
	HeroSelectorMenu heroSelectorMenu = null;
	VillainSelectorMenu villainSelectorMenu = null;
	SchemeSelectorMenu schemeSelectorMenu = null;
	TeamIconSelectorMenu teamSelectorMenu = null;
	BystanderSelectorMenu bystanderSelectorMenu = null;
	WoundSelectorMenu woundSelectorMenu = null;
	BindingsSelectorMenu bindingsSelectorMenu = null;
	SchemeTypeSelectorMenu schemeTypeSelectorMenu = null;
	
	JMenu editMenu = new JMenu("Edit");
	
	
	JMenu divider = new JMenu("Templates");
	
	JMenu orientation = new JMenu("Divider Orientation");
	JCheckBoxMenuItem orientationHorizontal = new JCheckBoxMenuItem("Horizontal");
	JCheckBoxMenuItem orientationVertical = new JCheckBoxMenuItem("Vertical");
	JMenuItem editHeroDividerTemplate = new JMenuItem("Edit Hero Divider Template...");
	JMenuItem editVillainDividerTemplate = new JMenuItem("Edit Villain Divider Template...");
	JMenuItem editHeroTemplate = new JMenuItem("Edit Hero Template...");
	
	JMenu expansion = new JMenu("Expansion");
	JMenu style = new JMenu("Style");
	List<JCheckBoxMenuItem> styleItems = new ArrayList<JCheckBoxMenuItem>();
	JMenuItem expansionName = new JMenuItem("Expansion Name...");
	JMenuItem rules = new JMenuItem("Edit Rules...");
	JMenuItem keywords = new JMenuItem("Edit Keywords...");
	
	JMenu tools = new JMenu("Tools");
	JMenuItem campaignManager = new JMenuItem("Campaign Manager...");
	JMenuItem bleeder = new JMenuItem("Bleeder...");
	
	JMenu help = new JMenu("Help");
	
	LegendaryCardMakerFrame lcmf;
	
	static CardMakerToolbar tb = null;
	
	public CardMakerToolbar(LegendaryCardMakerFrame lcmf)
	{
		tb = this;
		this.lcmf = lcmf;
		
		newExpansion.addActionListener(this);
		file.add(newExpansion);
		
		file.addSeparator();
		
		open.addActionListener(this);
		file.add(open);
		
		file.addSeparator();
		
		save.addActionListener(this);
		file.add(save);
		saveAs.addActionListener(this);
		file.add(saveAs);
		
		file.addSeparator();
		
		exportPNG.addActionListener(this);
		file.add(exportPNG);
		
		exportJPG.addActionListener(this);
		file.add(exportJPG);
		
		exportJPEGHomeprint.addActionListener(this);
		file.add(exportJPEGHomeprint);
		
		exportSelectedJPEGHomeprint.addActionListener(this);
		file.add(exportSelectedJPEGHomeprint);
		
		//exportPrinterStudioPNG.addActionListener(this);
		//file.add(exportPrinterStudioPNG);
		
		file.addSeparator();
		
		exportDividersJPEGHomeprint.addActionListener(this);
		file.add(exportDividersJPEGHomeprint);
		
		file.addSeparator();
		
		exportFull.addActionListener(this);
		file.add(exportFull);
		
		file.addSeparator();
		
		viewAsText.addActionListener(this);
		file.add(viewAsText);
		
		viewStatistics.addActionListener(this);
		file.add(viewStatistics);
		
		file.addSeparator();
		
		exit.addActionListener(this);
		file.add(exit);
		
		add(file);
		
		
		this.add(editMenu);
		
		heroSelectorMenu = new HeroSelectorMenu(lcmf, tb);
		//this.add(heroSelectorMenu);
		
		villainSelectorMenu = new VillainSelectorMenu(lcmf, tb);
		//this.add(villainSelectorMenu);
		
		schemeSelectorMenu = new SchemeSelectorMenu(lcmf, tb);
		//this.add(schemeSelectorMenu);
		
		teamSelectorMenu = new TeamIconSelectorMenu(lcmf, tb);
		//this.add(teamSelectorMenu);
		
		bystanderSelectorMenu = new BystanderSelectorMenu(lcmf, tb);
		//this.add(bystanderSelectorMenu);
		
		woundSelectorMenu = new WoundSelectorMenu(lcmf, tb);
		//this.add(woundSelectorMenu);
		
		bindingsSelectorMenu = new BindingsSelectorMenu(lcmf, tb);
		//this.add(bindingsSelectorMenu);
		
		schemeTypeSelectorMenu = new SchemeTypeSelectorMenu(lcmf, tb);
		//this.add(schemeTypeSelectorMenu);
		
		setEditMenu();
		
		editHeroTemplate.addActionListener(this);
		divider.add(editHeroTemplate);
		
		divider.addSeparator();
		
		orientationHorizontal.addActionListener(this);
		orientationHorizontal.setSelected(LegendaryCardMakerFrame.lcmf.lcm.dividerHorizontal);
		orientation.add(orientationHorizontal);
		orientationVertical.addActionListener(this);
		orientationVertical.setSelected(!LegendaryCardMakerFrame.lcmf.lcm.dividerHorizontal);
		orientation.add(orientationVertical);
		divider.add(orientation);
		
		editHeroDividerTemplate.addActionListener(this);
		divider.add(editHeroDividerTemplate);
		
		editVillainDividerTemplate.addActionListener(this);
		divider.add(editVillainDividerTemplate);
		
		this.add(divider);
		
		
		//populateExpansionMenu();
		//expansion.add(style);
		
		expansionName.addActionListener(this);
		expansion.add(expansionName);
		rules.addActionListener(this);
		expansion.add(rules);
		keywords.addActionListener(this);
		expansion.add(keywords);
		
		this.add(expansion);
		
		//campaignManager.addActionListener(this);
		//tools.add(campaignManager);
		
		bleeder.addActionListener(this);
		tools.add(bleeder);
		
		this.add(tools);
		
		JMenuItem version = new JMenuItem("Version: " + LegendaryCardMaker.version);
		version.setEnabled(false);
		help.add(version);
		this.add(help);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(open))
		{
			JFileChooser chooser = new JFileChooser(lcmf.lcm.lastOpened);
			int outcome = chooser.showOpenDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				HeroMaker.resetTemplateValues();
				
				lcmf.lcm.processInput(chooser.getSelectedFile().getAbsolutePath());
				
				lcmf.setTitle(LegendaryCardMakerFrame.FRAME_NAME + " - " + chooser.getSelectedFile().getName());
				
				lcmf.heroListModel.removeAllElements();
				for (Hero h : lcmf.lcm.heroes)
				{
					lcmf.heroListModel.addElement(h);
				}
				
				lcmf.villainListModel.removeAllElements();
				for (Villain h : lcmf.lcm.villains)
				{
					if (!h.name.equals("system_bystander_villain") && !h.name.equals("system_wound_villain"))
					{
						lcmf.villainListModel.addElement(h);
					}
				}
				
				lcmf.schemeListModel.removeAllElements();
				for (SchemeCard h : lcmf.lcm.schemes)
				{
					lcmf.schemeListModel.addElement(h);
				}
				
				lcmf.bystanderListModel.removeAllElements();
				Collections.sort(lcmf.lcm.villains, new Villain());
				for (Villain v : lcmf.lcm.villains)
				{
					for (VillainCard vc : v.cards)
					{
						if (vc.cardType != null && vc.cardType.equals(VillainCardType.BYSTANDER))
						{
							lcmf.bystanderListModel.addElement(vc);
						}
					}
				}
				
				lcmf.woundListModel.removeAllElements();
				Collections.sort(lcmf.lcm.villains, new Villain());
				for (Villain v : lcmf.lcm.villains)
				{
					for (VillainCard vc : v.cards)
					{
						if (vc.cardType != null && vc.cardType.equals(VillainCardType.WOUND))
						{
							lcmf.woundListModel.addElement(vc);
						}
					}
				}
				
				Set<Entry<String, CustomTemplateList>> entrySet = lcmf.customTemplateListSet.entrySet();
				for (Entry<String, CustomTemplateList> entry : entrySet)
				{
					entry.getValue().cardListModel.removeAllElements();
					Collections.sort(lcmf.lcm.customCards, new CustomCard());
					entry.getValue().loadList(lcmf.lcm.customCards);
				}
				
				lcmf.applicationProps.put("lastExpansion", chooser.getSelectedFile().getAbsolutePath());
				lcmf.applicationProps.put("lastOpenDirectory", chooser.getSelectedFile().getParent());
				lcmf.lcm.lastOpened = chooser.getSelectedFile().getParent();
				lcmf.saveProperties();
			}
		}
		
		if (e.getSource().equals(save))
		{
			if (lcmf.lcm.currentFile != null && new File(lcmf.lcm.currentFile).exists())
			{
				try
				{
					lcmf.lcm.saveExpansion();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
				}
			}
			else
			{
				JFileChooser chooser = new JFileChooser(lcmf.lcm.lastSaved);
				int outcome = chooser.showSaveDialog(this);
				if (outcome == JFileChooser.APPROVE_OPTION)
				{
					lcmf.lcm.currentFile = chooser.getSelectedFile().getAbsolutePath();
					
					try
					{
						lcmf.lcm.saveExpansion();
						lcmf.applicationProps.put("lastExpansion", chooser.getSelectedFile().getAbsolutePath());
						lcmf.applicationProps.put("lastSaveDirectory", chooser.getSelectedFile().getParent());
						lcmf.lcm.lastSaved = chooser.getSelectedFile().getParent();
						lcmf.saveProperties();
						
						lcmf.setTitle(LegendaryCardMakerFrame.FRAME_NAME + " - " + chooser.getSelectedFile().getName());						
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
					}
				}
			}
		}
		
		if (e.getSource().equals(saveAs))
		{
			JFileChooser chooser = new JFileChooser(lcmf.lcm.lastSaved);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				lcmf.lcm.currentFile = chooser.getSelectedFile().getAbsolutePath();
				
				try
				{
					lcmf.lcm.saveExpansion();
					lcmf.applicationProps.put("lastExpansion", chooser.getSelectedFile().getAbsolutePath());
					lcmf.applicationProps.put("lastSaveDirectory", chooser.getSelectedFile().getParent());
					lcmf.lcm.lastSaved = chooser.getSelectedFile().getParent();
					lcmf.saveProperties();
					
					lcmf.setTitle(LegendaryCardMakerFrame.FRAME_NAME + " - " + chooser.getSelectedFile().getName());
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
				}
			}
		}
		
		if (e.getSource().equals(exit))
		{
			if (lcmf.lcm.doChangesExist())
			{
				int outcome = JOptionPane.showOptionDialog(lcmf, "Save Changes?", "Save Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (outcome == JOptionPane.YES_OPTION)
				{
					if (lcmf.lcm.currentFile != null && new File(lcmf.lcm.currentFile).exists())
					{
						try
						{
							lcmf.lcm.saveExpansion();
						}
						catch (Exception ex)
						{
							JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
						}
					}
					else
					{
						JFileChooser chooser = new JFileChooser();
						int outcome2 = chooser.showSaveDialog(this);
						if (outcome2 == JFileChooser.APPROVE_OPTION)
						{
							lcmf.lcm.currentFile = chooser.getSelectedFile().getAbsolutePath();
							
							try
							{
								lcmf.lcm.saveExpansion();
								lcmf.applicationProps.put("lastSaveDirectory", chooser.getSelectedFile().getParent());
								lcmf.lcm.lastSaved = chooser.getSelectedFile().getParent();
								lcmf.saveProperties();
							}
							catch (Exception ex)
							{
								JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
							}
						}
					}
				}
			}
			System.exit(0);
		}
		
		if (e.getSource().equals(newExpansion))
		{
			if (lcmf.lcm.doChangesExist())
			{
				int outcome = JOptionPane.showOptionDialog(lcmf, "Save Changes?", "Save Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (outcome == JOptionPane.YES_OPTION)
				{
					if (lcmf.lcm.currentFile != null && new File(lcmf.lcm.currentFile).exists())
					{
						try
						{
							lcmf.lcm.saveExpansion();
						}
						catch (Exception ex)
						{
							JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
						}
					}
					else
					{
						JFileChooser chooser = new JFileChooser(lcmf.lcm.lastSaved);
						int outcome2 = chooser.showSaveDialog(this);
						if (outcome2 == JFileChooser.APPROVE_OPTION)
						{
							lcmf.lcm.currentFile = chooser.getSelectedFile().getAbsolutePath();
							
							try
							{
								lcmf.lcm.saveExpansion();
								lcmf.applicationProps.put("lastSaveDirectory", chooser.getSelectedFile().getParent());
								lcmf.lcm.lastSaved = chooser.getSelectedFile().getParent();
								lcmf.saveProperties();
							}
							catch (Exception ex)
							{
								JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
							}
						}
					}
				}
			}
			lcmf.createNewExpansion();
			
			HeroMaker.resetTemplateValues();
		}
		
		if (e.getSource().equals(exportPNG))
		{
			
			JFileChooser chooser = new JFileChooser();
			if (lcmf.lcm.exportFolder != null)
			{
				File tf = new File(lcmf.lcm.exportFolder);
				chooser = new JFileChooser(tf.getParent());
			}
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				File f = chooser.getSelectedFile();
				lcmf.lcm.exportFolder = f.getAbsolutePath();
				
				f.mkdirs();
				
				try
				{
					lcmf.applicationProps.put("lastExportDirectory", chooser.getSelectedFile().getAbsolutePath());
					
					ExportProgressBarDialog exporter = new ExportProgressBarDialog(lcmf.lcm.getCardCount(), lcmf.lcm, f);
					exporter.createAndShowGUI();
					
					//lcmf.lcm.exportToPng(f);
					
					lcmf.saveProperties();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
				}
			}
		}
		
		if (e.getSource().equals(exportJPG))
		{
			
			JFileChooser chooser = new JFileChooser();
			if (lcmf.lcm.exportFolder != null)
			{
				File tf = new File(lcmf.lcm.exportFolder);
				chooser = new JFileChooser(tf.getParent());
			}
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				File f = chooser.getSelectedFile();
				lcmf.lcm.exportFolder = f.getAbsolutePath();
				
				f.mkdirs();
				
				try
				{
					lcmf.applicationProps.put("lastExportDirectory", chooser.getSelectedFile().getAbsolutePath());
					
					ExportProgressBarDialog exporter = new ExportProgressBarDialog(lcmf.lcm.getCardCount(), lcmf.lcm, f);
					exporter.setJpegMode(true);
					exporter.createAndShowGUI();
					
					//lcmf.lcm.exportToPng(f);
					
					lcmf.saveProperties();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
				}
			}
		}
		
		if (e.getSource().equals(exportJPEGHomeprint))
		{
			
			JFileChooser chooser = new JFileChooser();
			if (lcmf.lcm.exportFolder != null)
			{
				File tf = new File(lcmf.lcm.exportFolder);
				chooser = new JFileChooser(tf.getParent());
			}
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				File f = chooser.getSelectedFile();
				lcmf.lcm.exportFolder = f.getAbsolutePath();
				
				f.mkdirs();
				
				try
				{
					lcmf.applicationProps.put("lastExportDirectory", chooser.getSelectedFile().getAbsolutePath());
					
					ExportHomeprintProgressBarDialog exporter = new ExportHomeprintProgressBarDialog(lcmf.lcm.getCardCount(), lcmf.lcm, f, null);
					exporter.createAndShowGUI();
					
					//lcmf.lcm.exportToPng(f);
					
					lcmf.saveProperties();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
				}
			}
		}
		
		if (e.getSource().equals(exportDividersJPEGHomeprint))
		{
			
			JFileChooser chooser = new JFileChooser();
			if (lcmf.lcm.exportFolder != null)
			{
				File tf = new File(lcmf.lcm.exportFolder);
				chooser = new JFileChooser(tf.getParent());
			}
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				File f = chooser.getSelectedFile();
				lcmf.lcm.exportFolder = f.getAbsolutePath();
				
				f.mkdirs();
				
				try
				{
					lcmf.applicationProps.put("lastExportDirectory", chooser.getSelectedFile().getAbsolutePath());
					
					ExportDividersHomeprintProgressBarDialog exporter = new ExportDividersHomeprintProgressBarDialog(lcmf.lcm.getCardCount(), lcmf.lcm, f);
					exporter.createAndShowGUI();
					
					//lcmf.lcm.exportToPng(f);
					
					lcmf.saveProperties();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
				}
			}
		}
	
		if (e.getSource().equals(viewAsText))
		{
			String str = "";
			
			str += "KEYWORDS\n\n";
			str += lcmf.lcm.keywords.replace("<k>", "").replace("<r>", "").replace(" <g> ", "\n");
			str += "\n\n\n";
			
			str += "HEROES\n\n"; 
					
			for (Hero h : lcmf.lcm.heroes)
			{
				str += h.name + "\n\n";
				
				for (HeroCard hc : h.cards)
				{
					str += hc.getTextExportString() + "\n\n";
				}
				
				str += "\n";
			}
			
			str += "VILLAINS\n\n";
			
			for (Villain v : lcmf.lcm.villains)
			{
				if (v.name.equals("system_bystander_villain"))
				{
					str += "BYSTANDERS\n\n";
				}
				else if (v.name.equals("system_wound_villain"))
				{
					str += "WOUNDS\n\n";
				}
				else if (v.name.equals("system_bindings_villain"))
				{
					str += "BINDINGS\n\n";
				}
				else
				{
					str += v.name + "\n\n";
				}
				
				for (VillainCard hc : v.cards)
				{
					str += hc.getTextExportString() + "\n";
				}
				
				str += "\n";
			}
			
			str += "SCHEMES\n\n";
			
			for (SchemeCard s : lcmf.lcm.schemes)
			{
				str += s.getTextExportString() + "\n";
				str += "\n";
			}
			
			TextOutputDialog dialog = new TextOutputDialog(str);
			dialog.setVisible(true);
		}
		
		if (e.getSource().equals(viewStatistics))
		{
			String str = analyseExpansion();
			
			TextOutputDialog dialog = new TextOutputDialog(str);
			dialog.setVisible(true);
		}
		
		if (e.getSource().equals(editHeroDividerTemplate))
		{
			HeroDividerMakerFrame dmf = new HeroDividerMakerFrame(null, lcmf.lcm.dividerHorizontal);
		}
		
		if (e.getSource().equals(editVillainDividerTemplate))
		{
			VillainDividerMakerFrame dmf = new VillainDividerMakerFrame(null, lcmf.lcm.dividerHorizontal);
		}
		
		if (e.getSource().equals(editHeroTemplate))
		{
			HeroMakerFrame hmf = new HeroMakerFrame(null);
		}
		
		if (e.getSource().equals(orientationHorizontal))
		{
			orientationHorizontal.setSelected(true);
			orientationVertical.setSelected(false);
			LegendaryCardMakerFrame.lcmf.lcm.dividerHorizontal = true;
		}
		
		if (e.getSource().equals(orientationVertical))
		{
			orientationHorizontal.setSelected(false);
			orientationVertical.setSelected(true);
			LegendaryCardMakerFrame.lcmf.lcm.dividerHorizontal = false;
		}
		
		if (e.getSource().equals(campaignManager))
		{
			new CampaignManagerFrame();
		}
		
		if (e.getSource().equals(exportFull))
		{
			
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter("ZIP file", "zip");
		    chooser.addChoosableFileFilter(filter1);
		    chooser.setFileFilter(filter1);
			if (lcmf.lcm.exportFolder != null)
			{
				File tf = new File(lcmf.lcm.exportFolder);
				chooser = new JFileChooser(tf);
			}
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				File f = chooser.getSelectedFile();
				
				try
				{
					ExportFullProgressBarDialog exporter = new ExportFullProgressBarDialog(lcmf.lcm.getCardCount(), lcmf.lcm, f);
					exporter.createAndShowGUI();
					
					lcmf.saveProperties();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
				}
			}
		}
		
		if (e.getSource().equals(exportSelectedJPEGHomeprint))
		{
			ItemSelectorDialog selector = new ItemSelectorDialog(lcmf);
			List<CardMaker> makers = legendaryItemToCardMaker(selector.getLegendaryItems());
			
			if (makers.size() > 0)
			{
				JFileChooser chooser = new JFileChooser();
				if (lcmf.lcm.exportFolder != null)
				{
					File tf = new File(lcmf.lcm.exportFolder);
					chooser = new JFileChooser(tf.getParent());
				}
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int outcome = chooser.showSaveDialog(this);
				if (outcome == JFileChooser.APPROVE_OPTION)
				{
					File f = chooser.getSelectedFile();
					lcmf.lcm.exportFolder = f.getAbsolutePath();
					
					f.mkdirs();
					
					try
					{
						lcmf.applicationProps.put("lastExportDirectory", chooser.getSelectedFile().getAbsolutePath());
						
						ExportHomeprintProgressBarDialog exporter = new ExportHomeprintProgressBarDialog(lcmf.lcm.getCardCount(), lcmf.lcm, f, makers);
						exporter.createAndShowGUI();
						
						//lcmf.lcm.exportToPng(f);
						
						lcmf.saveProperties();
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(lcmf, "Error! " + ex.getMessage());
					}
				}
			}
		}
		
		if (e.getSource().equals(keywords))
		{
			String s = new CardTextDialog(lcmf.lcm.keywords).showInputDialog();
			if (s == null) { s = lcmf.lcm.keywords; }
			if (s != null && s.isEmpty()) { s = null; }
			lcmf.lcm.keywords = s;
		}
		
		if (e.getSource().equals(rules))
		{
			String s = new CardTextDialog(lcmf.lcm.rules).showInputDialog();
			if (s == null) { s = lcmf.lcm.rules; }
			if (s != null && s.isEmpty()) { s = null; }
			lcmf.lcm.rules = s;
		}
		
		if (e.getSource().equals(expansionName))
		{
			String s = JOptionPane.showInputDialog(lcmf, "Enter the Expansion Name", lcmf.lcm.expansionName);
			if (s == null) { s = lcmf.lcm.expansionName; }
			if (s != null && s.isEmpty()) { s = null; }
			lcmf.lcm.expansionName = s;
		}
		
		if (e.getSource().equals(bleeder))
		{
			new LegeditBleeder();
		}
	}
	
	public String analyseExpansion()
	{
		String str = "";
		
		int totalNumberOfCards = 0;
		int totalNumberOfUniqueCards = 0;
		int totalNumberOfHeroCards = 0;
		
		HashMap<Object, Integer> cardRarityHash = new HashMap<Object, Integer>();
		HashMap<Object, Integer> teamHash = new HashMap<Object, Integer>();
		HashMap<Object, Integer> powerHash = new HashMap<Object, Integer>();
		HashMap<Object, Integer> triggerHash = new HashMap<Object, Integer>();
		
		int totalBaseAttack = 0;
		int totalAttackCards = 0;
		HashMap<Object, Integer> baseAttackHash = new HashMap<Object, Integer>();
		
		int totalBaseRecruit = 0;
		int totalRecruitCards = 0;
		HashMap<Object, Integer> baseRecruitHash = new HashMap<Object, Integer>();
		
		int totalBaseCost = 0;
		int totalCostCards = 0;
		HashMap<Object, Integer> baseCostHash = new HashMap<Object, Integer>();
		
		for (Hero h : lcmf.lcm.heroes)
		{
			for (HeroCard hc : h.cards)
			{
				totalNumberOfCards += hc.rarity.getCount();
				totalNumberOfUniqueCards++;
				totalNumberOfHeroCards += hc.rarity.getCount();
				
				updateCount(cardRarityHash, hc.rarity, hc.rarity.getCount());
				updateCount(teamHash, hc.cardTeam, hc.rarity.getCount());
				updateCount(powerHash, hc.cardPower, hc.rarity.getCount());
				
				for (String tr : hc.getTriggers())
				{
					updateCount(triggerHash, tr, hc.rarity.getCount());
				}
				
				Integer baseAttack = hc.getBaseAttack();
				if (baseAttack != null)
				{
					if (baseAttack > 0)
					{
						totalAttackCards += hc.rarity.getCount();
					}
					totalBaseAttack += hc.getBaseAttack() * hc.rarity.getCount();
					updateCount(baseAttackHash, hc.getBaseAttack(), hc.rarity.getCount());
				}
				
				Integer baseRecruit = hc.getBaseRecruit();
				if (baseRecruit != null)
				{
					if (baseRecruit > 0)
					{
						totalRecruitCards += hc.rarity.getCount();
					}
					totalBaseRecruit += hc.getBaseRecruit() * hc.rarity.getCount();
					updateCount(baseRecruitHash, hc.getBaseRecruit(), hc.rarity.getCount());
				}
				
				Integer baseCost = hc.getBaseCost();
				if (baseCost != null)
				{
					if (baseCost > 0)
					{
						totalCostCards += hc.rarity.getCount();
					}
					totalBaseCost += hc.getBaseCost() * hc.rarity.getCount();
					updateCount(baseCostHash, hc.getBaseCost(), hc.rarity.getCount());
				}
			}
		}
		
		for (Villain v : lcmf.lcm.villains)
		{	
			for (VillainCard hc : v.cards)
			{
				totalNumberOfCards += hc.cardType.getCount();
				totalNumberOfUniqueCards++;
			}
		}
		
		for (SchemeCard s : lcmf.lcm.schemes)
		{
			totalNumberOfUniqueCards++;
		}
		
		DecimalFormat f = new DecimalFormat("##.##");
		
		str += "Number of Unique Cards: " + totalNumberOfUniqueCards + "\n";
		str += "Number of Cards: " + totalNumberOfCards + "\n";
		str += "\n";
		str += "\n";
		
		str += "**************************\n";
		str += "Heroes\n";
		str += "**************************\n";
		str += "\n";
		
		str += "Number of Heroes: " + lcmf.lcm.heroes.size() + "\n";
		str += "Number of Hero Cards: " + totalNumberOfHeroCards + "\n";
		str += "\n";
		
		str += "Card Rarity Counts:\n";
		str += printHash(cardRarityHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Team Counts:\n";
		str += printHash(teamHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Power Counts:\n";
		str += printHash(powerHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Trigger Counts:\n";
		str += printHash(triggerHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Total Base Attack: " + totalBaseAttack + "\n";
		str += "Average Base Attack Per Card (Including 0 Attack): " + f.format((double)((double)totalBaseAttack / (double)totalNumberOfCards)) + "\n";
		str += "Average Base Attack Per Card (Not Including 0 Attack): " + f.format((double)((double)totalBaseAttack / (double)totalAttackCards)) + "\n";
		str += "Cards by Base Attack:\n";
		str += printHash(baseAttackHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Total Base Recruit: " + totalBaseRecruit + "\n";
		str += "Average Base Recruit Per Card (Including 0 Recruit): " + f.format((double)((double)totalBaseRecruit / (double)totalNumberOfCards)) + "\n";
		str += "Average Base Recruit Per Card (Not Including 0 Recruit): " + f.format((double)((double)totalBaseRecruit / (double)totalRecruitCards)) + "\n";
		str += "Cards by Base Recruit:\n";
		str += printHash(baseRecruitHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Total Base Cost: " + totalBaseCost + "\n";
		str += "Average Base Cost Per Card (Including 0 Cost): " + f.format((double)((double)totalBaseCost / (double)totalNumberOfCards)) + "\n";
		str += "Average Base Cost Per Card (Not Including 0 Cost): " + f.format((double)((double)totalBaseCost / (double)totalCostCards)) + "\n";
		str += "Cards by Base Cost:\n";
		str += printHash(baseCostHash, totalNumberOfHeroCards);
		str += "\n";
		
		for (Hero h : lcmf.lcm.heroes)
		{
			str += h.analyseHero();
			str += "\n";
		}
		
		return str;
	}
	
	public void updateCount(HashMap<Object, Integer> hash, Object obj, int value)
	{
		Integer i = hash.get(obj);
		if (i == null) 
		{
			i = new Integer(0);
		}
		i = new Integer(i.intValue() + value);
		hash.put(obj, i);
	}
	
	public String printHash(HashMap<Object, Integer> hash, Integer total)
	{
		DecimalFormat f = new DecimalFormat("##.##");
		
		String str = "";
		
		Set<Map.Entry<Object, Integer>> set = new TreeMap<Object, Integer>(hash).entrySet();
		
		for (Map.Entry<Object, Integer> entry : set)
		{
			str += entry.getKey().toString() + ": " + entry.getValue();
			if (total != null)
			{
				 str += " (" + f.format(getPercentageValue(entry.getValue(), total)) + "%)";
			}
			str += "\n";
		}
		
		return str;
	}
	
	public static double getPercentageValue(int value, int max)
	{
		return (double)((double)(((double)value / (double)max) * 100d));
	}

	public CardMakerToolbar getCardMakerToolbar()
	{
		return tb;
	}
	
	public void setEditMenu()
	{
		String str = lcmf.tabs.getTitleAt(lcmf.tabs.getSelectedIndex());
		
		if (str.equals("Heroes"))
		{
			remove(1);
			add(heroSelectorMenu, 1);
			return;
		}
		if (str.equals("Villains"))
		{
			remove(1);
			add(villainSelectorMenu, 1);
			return;
		}
		if (str.equals("Schemes"))
		{
			remove(1);
			add(schemeSelectorMenu, 1);
			return;
		}
		if (str.equals("Teams"))
		{
			remove(1);
			add(teamSelectorMenu, 1);
			return;
		}
		if (str.equals("Bystanders"))
		{
			remove(1);
			add(bystanderSelectorMenu, 1);
			return;
		}
		if (str.equals("Wounds"))
		{
			remove(1);
			add(woundSelectorMenu, 1);
			return;
		}
		if (str.equals("Bindings"))
		{
			remove(1);
			add(bindingsSelectorMenu, 1);
			return;
		}
		if (str.equals("Scheme Types"))
		{
			remove(1);
			add(schemeTypeSelectorMenu, 1);
			return;
		}
		
		CustomTemplateList list = LegendaryCardMakerFrame.lcmf.customTemplateListSet.get(str);
		if (list != null)
		{
			remove(1);
			add(list.menu, 1);
			return;
		}
	}
	
	public void removeEditMenus()
	{
		heroSelectorMenu.setVisible(false);
		villainSelectorMenu.setVisible(false);
		schemeSelectorMenu.setVisible(false);
		teamSelectorMenu.setVisible(false);
		bystanderSelectorMenu.setVisible(false);
		woundSelectorMenu.setVisible(false);
		bindingsSelectorMenu.setVisible(false);
		schemeTypeSelectorMenu.setVisible(false);
	}
	
	public void populateExpansionMenu()
	{
		String templateFolder = "legendary" + File.separator + "templates";
		File file = new File(templateFolder);
		if (file.exists())
		{
			File[] files = file.listFiles();
			for (File f : files)
			{
				if (f.isDirectory())
				{
					JCheckBoxMenuItem item = new JCheckBoxMenuItem(f.getName());
					if (f.getName().toLowerCase().equals(lcmf.lcm.expansionStyle.toLowerCase()))
					{
						item.setSelected(true);
					}
					
					item.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							String enumValue = ((JCheckBoxMenuItem)e.getSource()).getText().replace(" ", "_").toUpperCase();
							
							for (JCheckBoxMenuItem item : tb.styleItems)
							{
								if (item.getText().replace(" ", "_").toUpperCase().equals(enumValue))
								{
									item.setSelected(true);
									lcmf.lcm.expansionStyle = item.getText();
								}
								else
								{
									item.setSelected(false);
								}
							}
						}
					});
					styleItems.add(item);
					style.add(item);
				}
			}
		}
	}
	
	private List<CardMaker> legendaryItemToCardMaker(List<LegendaryItem> items)
	{
		List<CardMaker> makers = new ArrayList<CardMaker>();
		
		for (LegendaryItem l : items)
		{
			if (l instanceof Hero)
			{
				Hero h = (Hero)l;
				for (HeroCard hc : h.cards)
        		{
        			HeroMaker hm = new HeroMaker();
        			hm.setCard(hc);
        			int count = hc.rarity.getCount();
        			if (hc.numberInDeck > 0)
    				{
    					count = hc.numberInDeck;
    				}
        			for (int i = 0; i < count; i++)
        			{
        				makers.add(hm);
        			}
        		}
			}
			
			if (l instanceof Villain)
			{
				Villain v = (Villain)l;
				for (VillainCard vc : v.cards)
    			{
    				VillainMaker vm = new VillainMaker();
    				vm.setCard(vc);
    				
    				int count = vc.cardType.getCount();
    				if (vc.numberInDeck > 0)
    				{
    					count = vc.numberInDeck;
    				}
    				for (int i = 0; i < count; i++)
        			{
    					makers.add(vm);
        			}
    			}
			}
			
			if (l instanceof VillainCard)
			{
				VillainCard vc = (VillainCard)l;
				VillainMaker vm = new VillainMaker();
				vm.setCard(vc);
				
				int count = vc.cardType.getCount();
				if (vc.numberInDeck > 0)
				{
					count = vc.numberInDeck;
				}
				for (int i = 0; i < count; i++)
    			{
					makers.add(vm);
    			}
			}
			
			if (l instanceof SchemeCard)
			{
				SchemeCard s = (SchemeCard)l;
				SchemeMaker sm = new SchemeMaker();
    			sm.setCard(s);
    			
    			for (int i = 0; i < s.numberInDeck; i++)
    			{
					makers.add(sm);
    			}
			}
		}
		
		return makers;
	}
}
