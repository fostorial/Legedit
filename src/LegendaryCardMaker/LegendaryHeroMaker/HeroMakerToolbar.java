package LegendaryCardMaker.LegendaryHeroMaker;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import LegendaryCardMaker.CardTextDialog;
import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryCardMakerFrame;

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
	JMenuItem setHeroNameSize = new JMenuItem("Set Hero Name Size...");
	JMenuItem setAbilityText = new JMenuItem("Set Ability Text...");
	JMenuItem setAbilityTextSize = new JMenuItem("Set Ability Text Size...");
	JMenuItem setBackgroundImage = new JMenuItem("Set Background Image...");
	JMenuItem setBackgroundZoom = new JMenuItem("Set Background Zoom...");
	
	JMenuItem setTeamPowerColour = new JMenuItem("Set Team/Power Underlay Colour..");
	JMenuItem setTeamPowerRadius = new JMenuItem("Set Team/Power Blur Radius..");
	JMenuItem setCardNameColour = new JMenuItem("Set Card Name Colour..");
	JMenuItem setHeroNameColour = new JMenuItem("Set Hero Name Colour..");
	JMenuItem setAbilityTextColour = new JMenuItem("Set Ability Text Colour..");
	
	JMenu nameHighlightType = new JMenu("Name Highlight Type");
	JCheckBoxMenuItem highlightBlur = new JCheckBoxMenuItem("Blur");
	JCheckBoxMenuItem highlightBanner = new JCheckBoxMenuItem("Banner");
	
	JMenuItem resetTemplate = new JMenuItem("Reset Template");
	
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
		
		if (!hmf.templateMode)
		{
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
				if (hm.card.cardTeam != null && item.getText().replace(" ", "_").toUpperCase().equals(hm.card.cardTeam.toString()))
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
				if (hm.card.cardPower != null && item.getText().replace(" ", "_").toUpperCase().equals(hm.card.cardPower.toString()))
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
			
			setHeroNameSize.addActionListener(this);
			edit.add(setHeroNameSize);
			
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
		}
		else
		{
			edit.addSeparator();
			
			setTeamPowerColour.addActionListener(this);
			edit.add(setTeamPowerColour);
			
			setTeamPowerRadius.addActionListener(this);
			edit.add(setTeamPowerRadius);
			
			edit.addSeparator();
			
			if (hm.nameHighlight.equals("Blur")) { highlightBlur.setSelected(true); }
			highlightBlur.addActionListener(this);
			nameHighlightType.add(highlightBlur);
			if (hm.nameHighlight.equals("Banner")) { highlightBanner.setSelected(true); }
			highlightBanner.addActionListener(this);
			nameHighlightType.add(highlightBanner);
			edit.add(nameHighlightType);
			
			setCardNameSize.addActionListener(this);
			edit.add(setCardNameSize);
			
			setCardNameColour.addActionListener(this);
			edit.add(setCardNameColour);

			setHeroNameSize.addActionListener(this);
			edit.add(setHeroNameSize);
			
			setHeroNameColour.addActionListener(this);
			edit.add(setHeroNameColour);
			
			edit.addSeparator();
			
			setAbilityTextSize.addActionListener(this);
			edit.add(setAbilityTextSize);
			
			setAbilityTextColour.addActionListener(this);
			edit.add(setAbilityTextColour);
			
			edit.addSeparator();
			
			resetTemplate.addActionListener(this);
			edit.add(resetTemplate);
		}
		
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
				
				if (hmf.templateMode)
				{
					HeroMaker.abilityTextSizeTemplate = hm.textSize;
				}
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
				
				if (hmf.templateMode)
				{
					HeroMaker.cardNameSizeTemplate = hm.cardNameSize;
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		
		if (e.getSource().equals(setHeroNameSize))
		{
			String s = JOptionPane.showInputDialog(hmf, "Enter the Hero Name Size", hm.heroNameSize);
			if (s == null) { s = "" + hm.heroNameSize; }
			if (s != null && s.isEmpty()) { s = "" + hm.heroNameSize; }
			try
			{
				hm.heroNameSize = Integer.parseInt(s);
				hm.card.heroNameSize = Integer.parseInt(s);
				
				if (hmf.templateMode)
				{
					HeroMaker.heroNameSizeTemplate = hm.heroNameSize;
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			hmf.reRenderCard();
			hm.card.changed = true;
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getSource().equals(close))
		{
			hmf.setVisible(false);
		}
		
		if (e.getSource().equals(setBackgroundImage))
		{
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Image files", "png", "jpeg", "jpg", "bmp");
		    chooser.addChoosableFileFilter(filter1);
		    chooser.setFileFilter(filter1);
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
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			hmf.reRenderCard();
			hm.card.changed = true;
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getSource().equals(resetTemplate))
		{
			HeroMaker.resetTemplateValues();
			hm.resetTemplateValuesInstance();
			
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			hmf.reRenderCard();
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getSource().equals(setCardNameColour))
		{
			JColorChooser chooser = new JColorChooser();
			Color outcome = chooser.showDialog(hmf, "Select card name colour...", hm.cardNameColor);
			if (outcome != null)
			{
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				try
				{
					HeroMaker.cardNameColorTemplate = outcome;
					hm.cardNameColor = outcome;
					
					hmf.reRenderCard();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		if (e.getSource().equals(setHeroNameColour))
		{
			JColorChooser chooser = new JColorChooser();
			Color outcome = chooser.showDialog(hmf, "Select hero name colour...", hm.heroNameColor);
			if (outcome != null)
			{
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				try
				{
					HeroMaker.heroNameColorTemplate = outcome;
					hm.heroNameColor = outcome;
					
					hmf.reRenderCard();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		if (e.getSource().equals(setAbilityTextColour))
		{
			JColorChooser chooser = new JColorChooser();
			Color outcome = chooser.showDialog(hmf, "Select hero name colour...", hm.textColor);
			if (outcome != null)
			{
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				try
				{
					HeroMaker.abilityTextColorTemplate = outcome;
					hm.textColor = outcome;
					
					hmf.reRenderCard();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		if (e.getSource().equals(setTeamPowerColour))
		{
			JColorChooser chooser = new JColorChooser();
			Color outcome = chooser.showDialog(hmf, "Select Team/Power underlay colour...", hm.teamPowerUnderlayColor);
			if (outcome != null)
			{
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				try
				{
					HeroMaker.teamPowerUnderlayColorTemplate = outcome;
					hm.teamPowerUnderlayColor = outcome;
					
					hmf.reRenderCard();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
				hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		if (e.getSource().equals(setTeamPowerRadius))
		{
			String s = JOptionPane.showInputDialog(hmf, "Enter the Ability Text Size", hm.teamBlurRadius);
			if (s == null) { s = "" + hm.teamBlurRadius; }
			if (s != null && s.isEmpty()) { s = "" + hm.teamBlurRadius; }
			try
			{
				hm.teamBlurRadius = Integer.parseInt(s);
				hm.powerBlurRadius = Integer.parseInt(s);
				
				if (hmf.templateMode)
				{
					HeroMaker.teamPowerBlurRadiusTemplate = hm.teamBlurRadius;
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			hmf.reRenderCard();
			hm.card.changed = true;
		}
		
		if (e.getSource().equals(highlightBlur))
		{
			highlightBlur.setSelected(true);
			highlightBanner.setSelected(false);
			HeroMaker.nameHighlightTemplate = "Blur";
			hm.nameHighlight = "Blur";
			
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			hmf.reRenderCard();
			hm.card.changed = true;
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getSource().equals(highlightBanner))
		{
			highlightBlur.setSelected(false);
			highlightBanner.setSelected(true);
			HeroMaker.nameHighlightTemplate = "Banner";
			hm.nameHighlight = "Banner";
			
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			hmf.reRenderCard();
			hm.card.changed = true;
			hmf.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public HeroMakerToolbar getHeroMakerToolbar()
	{
		return tb;
	}
}
