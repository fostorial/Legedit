package LegendaryCardMaker.LegendaryHeroMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import LegendaryCardMaker.CardTextDialog;
import LegendaryCardMaker.Icon;

public class HeroMakerToolbar extends JMenuBar implements ActionListener{

	HeroMaker hm;
	HeroMakerFrame hmf;
	
	JMenu file = new JMenu("File");
	
	JMenuItem exportJPG = new JMenuItem("Export to JPEG...");
	JMenuItem exportPNG = new JMenuItem("Export to PNG...");
	JMenuItem exportPrinterStudioPNG = new JMenuItem("Export to Printer Studio PNG...");
	JMenuItem close = new JMenuItem("Close");
	
	JMenu edit = new JMenu("Edit");
	
	JMenu rarity = new JMenu("Card Rarity");
	JCheckBoxMenuItem rarityCommon = new JCheckBoxMenuItem("Common");
	JCheckBoxMenuItem rarityUncommon = new JCheckBoxMenuItem("Uncommon");
	JCheckBoxMenuItem rarityRare = new JCheckBoxMenuItem("Rare");
	
	JMenu team = new JMenu("Team");
	List<JCheckBoxMenuItem> teamItems = new ArrayList<JCheckBoxMenuItem>();
	
	JMenu power = new JMenu("Power");
	List<JCheckBoxMenuItem> powerItems = new ArrayList<JCheckBoxMenuItem>();
	
	JMenuItem setCost = new JMenuItem("Set Cost...");
	JMenuItem setRecruit = new JMenuItem("Set Recruit...");
	JMenuItem setAttack = new JMenuItem("Set Attack...");
	JMenuItem setCardName = new JMenuItem("Set Card Name...");
	JMenuItem setCardNameSize = new JMenuItem("Set Card Name Size...");
	JMenuItem setAbilityText = new JMenuItem("Set Ability Text...");
	JMenuItem setAbilityTextSize = new JMenuItem("Set Ability Text Size...");
	JMenuItem setBackgroundImage = new JMenuItem("Set Background Image...");
	JMenuItem setBackgroundZoom = new JMenuItem("Set Background Zoom...");
	
	static HeroMakerToolbar tb = null;
	
	public HeroMakerToolbar(HeroMaker hm, HeroMakerFrame hmf)
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
		
		if (hm.card.rarity.equals(CardRarity.COMMON)) { rarityCommon.setSelected(true); }
		rarityCommon.addActionListener(this);
		rarity.add(rarityCommon);
		if (hm.card.rarity.equals(CardRarity.UNCOMMON)) { rarityUncommon.setSelected(true); }
		rarityUncommon.addActionListener(this);
		rarity.add(rarityUncommon);
		if (hm.card.rarity.equals(CardRarity.RARE)) { rarityRare.setSelected(true); }
		rarityRare.addActionListener(this);
		rarity.add(rarityRare);
		edit.add(rarity);
		
		for (Icon icon : Icon.values())
		{
			if (icon.getIconType().equals(Icon.ICON_TYPE.TEAM) || icon.getIconType().equals(Icon.ICON_TYPE.NONE))
			{
				String s = icon.toString().substring(0, 1).toUpperCase() + icon.toString().substring(1).toLowerCase();
				s = s.replace("_", " ");
				JCheckBoxMenuItem m = new JCheckBoxMenuItem(s);
				m.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						String enumValue = ((JCheckBoxMenuItem)e.getSource()).getText().replace(" ", "_").toUpperCase();
						Icon icon = Icon.valueOf(enumValue);
						tb.hm.card.cardTeam = icon;
						
						for (JCheckBoxMenuItem item : tb.teamItems)
						{
							if (item.getText().replace(" ", "_").toUpperCase().equals(enumValue))
							{
								item.setSelected(true);
							}
							else
							{
								item.setSelected(false);
							}
						}
						
						tb.hmf.reRenderCard();
						tb.hm.card.changed = true;
					}
				});
				
				teamItems.add(m);
				team.add(m);
			}
			
			if (icon.getIconType().equals(Icon.ICON_TYPE.POWER) || icon.getIconType().equals(Icon.ICON_TYPE.NONE))
			{
				String s = icon.toString().substring(0, 1).toUpperCase() + icon.toString().substring(1).toLowerCase();
				s.replace("_", " ");
				JCheckBoxMenuItem m = new JCheckBoxMenuItem(s);
				m.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						String enumValue = ((JCheckBoxMenuItem)e.getSource()).getText().replace(" ", "_").toUpperCase();
						Icon icon = Icon.valueOf(enumValue);
						tb.hm.card.cardPower = icon;
						
						for (JCheckBoxMenuItem item : tb.powerItems)
						{
							if (item.getText().replace(" ", "_").toUpperCase().equals(enumValue))
							{
								item.setSelected(true);
							}
							else
							{
								item.setSelected(false);
							}
						}
						
						tb.hmf.reRenderCard();
						tb.hm.card.changed = true;
					}
				});
				
				powerItems.add(m);
				power.add(m);
			}
		}
		
		for (JCheckBoxMenuItem item : teamItems)
		{
			if (item.getText().replace(" ", "_").toUpperCase().equals(hm.card.cardTeam.toString()))
			{
				item.setSelected(true);
			}
			else
			{
				item.setSelected(false);
			}
		}
		edit.add(team);
		
		for (JCheckBoxMenuItem item : powerItems)
		{
			if (item.getText().replace(" ", "_").toUpperCase().equals(hm.card.cardPower.toString()))
			{
				item.setSelected(true);
			}
			else
			{
				item.setSelected(false);
			}
		}
		edit.add(power);
		
		setCardName.addActionListener(this);
		edit.add(setCardName);
		
		setCardNameSize.addActionListener(this);
		edit.add(setCardNameSize);
		
		setRecruit.addActionListener(this);
		edit.add(setRecruit);
		
		setAttack.addActionListener(this);
		edit.add(setAttack);
		
		setCost.addActionListener(this);
		edit.add(setCost);
		
		setAbilityText.addActionListener(this);
		edit.add(setAbilityText);
		
		setAbilityTextSize.addActionListener(this);
		edit.add(setAbilityTextSize);
		
		setBackgroundImage.addActionListener(this);
		edit.add(setBackgroundImage);
		
		setBackgroundZoom.addActionListener(this);
		edit.add(setBackgroundZoom);
		
		add(edit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(rarityCommon))
		{
			rarityCommon.setSelected(true);
			rarityUncommon.setSelected(false);
			rarityRare.setSelected(false);
			hm.card.rarity = CardRarity.COMMON;
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		if (e.getSource().equals(rarityUncommon))
		{
			rarityCommon.setSelected(false);
			rarityUncommon.setSelected(true);
			rarityRare.setSelected(false);
			hm.card.rarity = CardRarity.UNCOMMON;
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		if (e.getSource().equals(rarityRare))
		{
			rarityCommon.setSelected(false);
			rarityUncommon.setSelected(false);
			rarityRare.setSelected(true);
			hm.card.rarity = CardRarity.RARE;
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		
		if (e.getSource().equals(exportJPG))
		{
			JFileChooser chooser = new JFileChooser();
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				BufferedImage bi = hm.generateCard();
				try
				{
					hm.exportToJPEG(bi, chooser.getSelectedFile());
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
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				BufferedImage bi = hm.generateCard();
				try
				{
					hm.exportToPNG(bi, chooser.getSelectedFile());
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
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				BufferedImage bi = hm.generateCard();
				bi = hm.resizeImagePS(bi);
				try
				{
					hm.exportToPNG(bi, chooser.getSelectedFile());
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		}
		
		if (e.getSource().equals(setCost))
		{
			String s = JOptionPane.showInputDialog(hmf, "Enter the Cost", hm.card.cost);
			if (s == null) { s = hm.card.cost; }
			if (s != null && s.isEmpty()) { s = null; }
			hm.card.cost = s;
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		
		if (e.getSource().equals(setRecruit))
		{
			String s = JOptionPane.showInputDialog(hmf, "Enter the Recruit", hm.card.recruit);
			if (s == null) { s = hm.card.recruit; }
			if (s != null && s.isEmpty()) { s = null; }
			hm.card.recruit = s;
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		
		if (e.getSource().equals(setAttack))
		{
			String s = JOptionPane.showInputDialog(hmf, "Enter the Attack", hm.card.attack);
			if (s == null) { s = hm.card.attack; }
			if (s != null && s.isEmpty()) { s = null; }
			hm.card.attack = s;
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		
		if (e.getSource().equals(setAbilityText))
		{
			//String s = JOptionPane.showInputDialog(hmf, "Enter the Ability Text", hm.card.abilityText);
			String s = new CardTextDialog(hm.card.abilityText).showInputDialog();
			if (s == null) { s = hm.card.abilityText; }
			if (s != null && s.isEmpty()) { s = null; }
			hm.card.abilityText = s;
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		
		if (e.getSource().equals(setCardName))
		{
			String s = JOptionPane.showInputDialog(hmf, "Enter the Card Name", hm.card.name);
			if (s == null) { s = hm.card.name; }
			if (s != null && s.isEmpty()) { s = null; }
			hm.card.name = s;
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		
		if (e.getSource().equals(setAbilityTextSize))
		{
			String s = JOptionPane.showInputDialog(hmf, "Enter the Ability Text Size", hm.textSize);
			if (s == null) { s = "" + hm.textSize; }
			if (s != null && s.isEmpty()) { s = "" + hm.textSize; }
			try
			{
				hm.textSize = Integer.parseInt(s);
				hm.card.abilityTextSize = Integer.parseInt(s);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		
		if (e.getSource().equals(setCardNameSize))
		{
			String s = JOptionPane.showInputDialog(hmf, "Enter the Card Name Size", hm.cardNameSize);
			if (s == null) { s = "" + hm.cardNameSize; }
			if (s != null && s.isEmpty()) { s = "" + hm.cardNameSize; }
			try
			{
				hm.cardNameSize = Integer.parseInt(s);
				hm.card.nameSize = Integer.parseInt(s);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		
		if (e.getSource().equals(close))
		{
			hmf.setVisible(false);
		}
		
		if (e.getSource().equals(setBackgroundImage))
		{
			JFileChooser chooser = new JFileChooser();
			int outcome = chooser.showOpenDialog(hmf);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					//ImageIcon ii = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
					//hmf.imageLabel.setIcon(ii);
					hm.card.imagePath = chooser.getSelectedFile().getAbsolutePath();
					hm.card.imageZoom = 1.0d;
					hm.card.imageOffsetX = 0;
					hm.card.imageOffsetY = 0;
					
					hmf.reRenderCard();
					hm.card.changed = true;
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		
		if (e.getSource().equals(setBackgroundZoom))
		{
			String s = JOptionPane.showInputDialog(hmf, "Enter the Background Zoom", hm.card.imageZoom);
			if (s == null) { s = "" + hm.card.imageZoom; }
			if (s != null && s.isEmpty()) { s = "" + hm.card.imageZoom; }
			try
			{
				hm.card.imageZoom = Double.parseDouble(s);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			hmf.reRenderCard();
			hm.card.changed = true;
		}
	}

	public HeroMakerToolbar getHeroMakerToolbar()
	{
		return tb;
	}
}
