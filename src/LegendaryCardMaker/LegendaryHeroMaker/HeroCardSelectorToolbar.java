package LegendaryCardMaker.LegendaryHeroMaker;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import LegendaryCardMaker.CardMaker;
import LegendaryCardMaker.LegendaryDividerMaker.HeroDividerMakerFrame;
import LegendaryCardMaker.exporters.ExportHomeprintProgressBarDialog;

public class HeroCardSelectorToolbar extends JMenuBar implements ActionListener{

	HeroCardSelector hm;
	
	JMenu file = new JMenu("File");
	
	JMenuItem exportJPG = new JMenuItem("Export to JPEG...");
	JMenuItem exportPNG = new JMenuItem("Export to PNG...");
	JMenuItem exportPrinterStudioPNG = new JMenuItem("Export to Printer Studio PNG...");
	JMenuItem close = new JMenuItem("Close");
	
	JMenu edit = new JMenu("Edit");
	JMenuItem newCard = new JMenuItem("New Card");
	JMenuItem editCard = new JMenuItem("Edit Card...");
	JMenuItem delete = new JMenuItem("Delete Card...");
	JMenuItem editDivider = new JMenuItem("Edit Divider...");
	
	JMenuItem exportJPEGHomeprint = new JMenuItem("Export to JPEG for Homeprint...");
	
	static HeroCardSelectorToolbar tb = null;
	
	public HeroCardSelectorToolbar(HeroCardSelector hm)
	{
		this.tb = this;
		
		this.hm = hm;
		
		//exportJPG.addActionListener(this);
		//file.add(exportJPG);
		exportPNG.addActionListener(this);
		file.add(exportPNG);
		
		//exportPrinterStudioPNG.addActionListener(this);
		//file.add(exportPrinterStudioPNG);
		
		file.addSeparator();
		
		close.addActionListener(this);
		file.add(close);
		
		add(file);
		
		newCard.addActionListener(this);
		edit.add(newCard);
		
		edit.addSeparator();
		
		editCard.addActionListener(this);
		edit.add(editCard);
		
		delete.addActionListener(this);
		edit.add(delete);
		
		edit.addSeparator();
		
		editDivider.addActionListener(this);
		edit.add(editDivider);
		
		edit.addSeparator();
		
		exportJPEGHomeprint.addActionListener(this);
		edit.add(exportJPEGHomeprint);
		
		add(edit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(exportPNG))
		{
			JFileChooser chooser = new JFileChooser(hm.lcmf.lcm.exportFolder);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				File f = chooser.getSelectedFile();
				hm.lcmf.lcm.exportFolder = f.getAbsolutePath();
				
				f.mkdirs();
				
				try
				{
					hm.lcmf.lcm.exportHeroToPng(hm.h, f);
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(hm.lcmf, "Error! " + ex.getMessage());
				}
			}
		}
		
		if (e.getSource().equals(close))
		{
			hm.setVisible(false);
		}
		
		if (e.getSource().equals(editCard))
		{
			if (getCurrentHeroCard() == null)
			{
				return;
			}
			
			HeroMakerFrame hmf = new HeroMakerFrame((HeroCard)this.hm.cardList.getSelectedValue());
		}
		
		if (e.getSource().equals(newCard))
		{
			HeroCard hc = HeroMaker.getBlankHeroCard();
			hc.heroName = hm.h.name;
			hc.rarity = CardRarity.COMMON;
			hc.changed = true;
			hm.h.cards.add(hc);
			hm.h.changed = true;
			
			hm.cardListModel.addElement(hc);
		}
		
		if (e.getSource().equals(delete))
		{
			if (getCurrentHeroCard() == null)
			{
				return;
			}
			
			int outcome = JOptionPane.showOptionDialog(hm.lcmf, "Delete Card?", "Delete Card?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (outcome == JOptionPane.YES_OPTION)
			{
				hm.h.cards.remove(getCurrentHeroCard());
				getHeroCardListModel().removeElement(getCurrentHeroCard());
			}
		}
		
		if (e.getSource().equals(editDivider))
		{			
			HeroDividerMakerFrame dmf = new HeroDividerMakerFrame(hm.h, this.hm.lcmf.lcm.dividerHorizontal);
		}
		
		if (e.getSource().equals(exportJPEGHomeprint))
		{
			JFileChooser chooser = new JFileChooser();
			if (hm.lcmf.lcm.exportFolder != null)
			{
				File tf = new File(hm.lcmf.lcm.exportFolder);
				chooser = new JFileChooser(tf.getParent());
			}
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				File f = chooser.getSelectedFile();
				hm.lcmf.lcm.exportFolder = f.getAbsolutePath();
				
				f.mkdirs();
				
				try
				{
					List<CardMaker> cardMakers = new ArrayList<CardMaker>();
					for (HeroCard hc : hm.h.cards)
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
            				cardMakers.add(hm); 
            			}
					}
					
					hm.lcmf.applicationProps.put("lastExportDirectory", chooser.getSelectedFile().getAbsolutePath());
					
					ExportHomeprintProgressBarDialog exporter = new ExportHomeprintProgressBarDialog(hm.lcmf.lcm.getCardCount(), hm.lcmf.lcm, f, cardMakers);
					exporter.createAndShowGUI();
					
					//lcmf.lcm.exportToPng(f);
					
					hm.lcmf.saveProperties();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(hm.lcmf, "Error! " + ex.getMessage());
				}
			}
		}
	}
	
	public JList getHeroCardList()
	{
		return hm.cardList;
	}
	
	public DefaultListModel getHeroCardListModel()
	{
		return hm.cardListModel;
	}
	
	public HeroCard getCurrentHeroCard()
	{
		HeroCard h = null;
		
		if (hm.cardListModel.size() > 0 && hm.cardList.getSelectedIndex() >= 0)
		{
			h = (HeroCard)hm.cardListModel.get(hm.cardList.getSelectedIndex());
		}
		
		return h;
	}
}
