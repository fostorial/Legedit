package LegendaryCardMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryHeroMaker.CardRarity;
import LegendaryCardMaker.LegendaryHeroMaker.Hero;
import LegendaryCardMaker.LegendaryHeroMaker.HeroCard;
import LegendaryCardMaker.LegendaryHeroMaker.HeroSelectorMenu;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCard;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeSelectorMenu;
import LegendaryCardMaker.LegendaryVillainMaker.BystanderSelectorMenu;
import LegendaryCardMaker.LegendaryVillainMaker.Villain;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCard;
import LegendaryCardMaker.LegendaryVillainMaker.VillainSelectorMenu;

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
	
	JMenuItem viewAsText = new JMenuItem("View as Text...");
	JMenuItem viewStatistics = new JMenuItem("View Statistics...");
	
	HeroSelectorMenu heroSelectorMenu = null;
	VillainSelectorMenu villainSelectorMenu = null;
	SchemeSelectorMenu schemeSelectorMenu = null;
	TeamIconSelectorMenu teamSelectorMenu = null;
	BystanderSelectorMenu bystanderSelectorMenu = null;
	
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
		
		//exportPrinterStudioPNG.addActionListener(this);
		//file.add(exportPrinterStudioPNG);
		
		file.addSeparator();
		
		viewAsText.addActionListener(this);
		file.add(viewAsText);
		
		viewStatistics.addActionListener(this);
		file.add(viewStatistics);
		
		file.addSeparator();
		
		exit.addActionListener(this);
		file.add(exit);
		
		add(file);
		
		
		heroSelectorMenu = new HeroSelectorMenu(lcmf, tb);
		this.add(heroSelectorMenu);
		
		villainSelectorMenu = new VillainSelectorMenu(lcmf, tb);
		this.add(villainSelectorMenu);
		
		schemeSelectorMenu = new SchemeSelectorMenu(lcmf, tb);
		this.add(schemeSelectorMenu);
		
		teamSelectorMenu = new TeamIconSelectorMenu(lcmf, tb);
		this.add(teamSelectorMenu);
		
		bystanderSelectorMenu = new BystanderSelectorMenu(lcmf, tb);
		this.add(bystanderSelectorMenu);
		
		removeEditMenus();
		setEditMenu();
		
		
		JMenuItem version = new JMenuItem("Version: " + LegendaryCardMaker.version);
		version.setEnabled(false);
		help.add(version);
		this.add(help);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(open))
		{
			JFileChooser chooser = new JFileChooser();
			int outcome = chooser.showOpenDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				lcmf.lcm.processInput(chooser.getSelectedFile().getAbsolutePath());
				
				lcmf.heroListModel.removeAllElements();
				for (Hero h : lcmf.lcm.heroes)
				{
					lcmf.heroListModel.addElement(h);
				}
				
				lcmf.villainListModel.removeAllElements();
				for (Villain h : lcmf.lcm.villains)
				{
					lcmf.villainListModel.addElement(h);
				}
				
				lcmf.schemeListModel.removeAllElements();
				for (SchemeCard h : lcmf.lcm.schemes)
				{
					lcmf.schemeListModel.addElement(h);
				}
				
				lcmf.applicationProps.put("lastExpansion", chooser.getSelectedFile().getAbsolutePath());
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
				JFileChooser chooser = new JFileChooser();
				int outcome = chooser.showSaveDialog(this);
				if (outcome == JFileChooser.APPROVE_OPTION)
				{
					lcmf.lcm.currentFile = chooser.getSelectedFile().getAbsolutePath();
					
					try
					{
						lcmf.lcm.saveExpansion();
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
			JFileChooser chooser = new JFileChooser();
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				lcmf.lcm.currentFile = chooser.getSelectedFile().getAbsolutePath();
				
				try
				{
					lcmf.lcm.saveExpansion();
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
						JFileChooser chooser = new JFileChooser();
						int outcome2 = chooser.showSaveDialog(this);
						if (outcome2 == JFileChooser.APPROVE_OPTION)
						{
							lcmf.lcm.currentFile = chooser.getSelectedFile().getAbsolutePath();
							
							try
							{
								lcmf.lcm.saveExpansion();
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
		}
		
		if (e.getSource().equals(exportPNG))
		{
			JFileChooser chooser = new JFileChooser(lcmf.lcm.exportFolder);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				File f = chooser.getSelectedFile();
				lcmf.lcm.exportFolder = f.getAbsolutePath();
				
				f.mkdirs();
				
				try
				{
					ExportProgressBarDialog exporter = new ExportProgressBarDialog(lcmf.lcm.getCardCount(), lcmf.lcm, f);
					exporter.createAndShowGUI();
					
					//lcmf.lcm.exportToPng(f);
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
				str += v.name + "\n\n";
				
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
		
		removeEditMenus();
		
		if (str.equals("Heroes"))
		{
			heroSelectorMenu.setVisible(true);
		}
		if (str.equals("Villains"))
		{
			villainSelectorMenu.setVisible(true);
		}
		if (str.equals("Schemes"))
		{
			schemeSelectorMenu.setVisible(true);
		}
		if (str.equals("Teams"))
		{
			teamSelectorMenu.setVisible(true);
		}
		if (str.equals("Bystanders"))
		{
			bystanderSelectorMenu.setVisible(true);
		}
	}
	
	public void removeEditMenus()
	{
		heroSelectorMenu.setVisible(false);
		villainSelectorMenu.setVisible(false);
		schemeSelectorMenu.setVisible(false);
		teamSelectorMenu.setVisible(false);
		bystanderSelectorMenu.setVisible(false);
	}
}
