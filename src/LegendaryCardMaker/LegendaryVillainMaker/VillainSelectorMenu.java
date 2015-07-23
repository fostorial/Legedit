package LegendaryCardMaker.LegendaryVillainMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;

import LegendaryCardMaker.CardMaker;
import LegendaryCardMaker.CardMakerToolbar;
import LegendaryCardMaker.ExportHomeprintProgressBarDialog;
import LegendaryCardMaker.LegendaryCardMakerFrame;
import LegendaryCardMaker.LegendaryDividerMaker.HeroDividerMakerFrame;
import LegendaryCardMaker.LegendaryDividerMaker.VillainDividerMakerFrame;
import LegendaryCardMaker.LegendaryHeroMaker.HeroCard;
import LegendaryCardMaker.LegendaryHeroMaker.HeroMaker;

public class VillainSelectorMenu extends JMenu implements ActionListener{
	
	JMenuItem newHenchmen = new JMenuItem("New Henchmen...");
	JMenuItem newVillain = new JMenuItem("New Villain...");
	JMenuItem newMastermind = new JMenuItem("New Mastermind...");
	JMenuItem edit = new JMenuItem("Edit Villain...");
	JMenuItem rename = new JMenuItem("Rename Villain...");
	JMenuItem delete = new JMenuItem("Delete Villain...");
	JMenuItem editDivider = new JMenuItem("Edit Divider...");
	JMenuItem exportJPEGHomeprint = new JMenuItem("Export to JPEG for Homeprint...");
	
	public LegendaryCardMakerFrame lcmf;
	
	static CardMakerToolbar tb = null;
	
	public VillainSelectorMenu(LegendaryCardMakerFrame lcmf, CardMakerToolbar tb)
	{
		this.tb = tb;
		this.lcmf = lcmf;
		
		this.setText("Edit");
		
		newHenchmen.addActionListener(this);
		add(newHenchmen);
		
		newVillain.addActionListener(this);
		add(newVillain);
		
		newMastermind.addActionListener(this);
		add(newMastermind);
		
		addSeparator();
		
		edit.addActionListener(this);
		add(edit);
		
		rename.addActionListener(this);
		add(rename);
		
		delete.addActionListener(this);
		add(delete);
		
		addSeparator();
		
		editDivider.addActionListener(this);
		add(editDivider);
		
		addSeparator();
		
		exportJPEGHomeprint.addActionListener(this);
		add(exportJPEGHomeprint);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(newVillain))
		{
			String s = JOptionPane.showInputDialog(lcmf, "Enter the Villain Group Name", "");
			if (s == null) { return; }
			if (s != null && s.isEmpty()) { return; }
			
			Villain h = new Villain();
			h.name = s;
			h.changed = true;
			
			VillainCard hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.VILLAIN;
			hc.numberInDeck = VillainCardType.VILLAIN.getCount();
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.VILLAIN;
			hc.numberInDeck = VillainCardType.VILLAIN.getCount();
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.VILLAIN;
			hc.numberInDeck = VillainCardType.VILLAIN.getCount();
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.VILLAIN;
			hc.numberInDeck = VillainCardType.VILLAIN.getCount();
			hc.changed = true;
			h.cards.add(hc);
			
			lcmf.villainListModel.addElement(h);
			lcmf.lcm.villains.add(h);
		}
		
		if (e.getSource().equals(newHenchmen))
		{
			String s = JOptionPane.showInputDialog(lcmf, "Enter the Henchmen Group Name", "");
			if (s == null) { return; }
			if (s != null && s.isEmpty()) { return; }
			
			Villain h = new Villain();
			h.name = s;
			h.changed = true;
			
			VillainCard hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.numberInDeck = VillainCardType.HENCHMEN.getCount();
			hc.cardType = VillainCardType.HENCHMEN;
			hc.changed = true;
			h.cards.add(hc);
			
			lcmf.villainListModel.addElement(h);
			lcmf.lcm.villains.add(h);
		}
		
		if (e.getSource().equals(newMastermind))
		{
			String s = JOptionPane.showInputDialog(lcmf, "Enter the Mastermind Name", "");
			if (s == null) { return; }
			if (s != null && s.isEmpty()) { return; }
			
			Villain h = new Villain();
			h.name = s;
			h.changed = true;
			
			VillainCard hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.numberInDeck = VillainCardType.MASTERMIND.getCount();
			hc.cardType = VillainCardType.MASTERMIND;
			hc.abilityText = "<k>Always Leads: <r> <g> <k>Master Strike: <r>";
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.numberInDeck = VillainCardType.MASTERMIND_TACTIC.getCount();
			hc.cardType = VillainCardType.MASTERMIND_TACTIC;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.numberInDeck = VillainCardType.MASTERMIND_TACTIC.getCount();
			hc.cardType = VillainCardType.MASTERMIND_TACTIC;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.numberInDeck = VillainCardType.MASTERMIND_TACTIC.getCount();
			hc.cardType = VillainCardType.MASTERMIND_TACTIC;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.numberInDeck = VillainCardType.MASTERMIND_TACTIC.getCount();
			hc.cardType = VillainCardType.MASTERMIND_TACTIC;
			hc.changed = true;
			h.cards.add(hc);
			
			lcmf.villainListModel.addElement(h);
			lcmf.lcm.villains.add(h);
		}
		
		if (e.getSource().equals(edit))
		{
			if (getCurrentVillain() == null)
			{
				return;
			}
			new VillainCardSelector(getCurrentVillain(), lcmf);
		}
		
		if (e.getSource().equals(rename))
		{
			if (getCurrentVillain() == null)
			{
				return;
			}
			
			String s = JOptionPane.showInputDialog(lcmf, "Enter the Villain Group Name", getCurrentVillain().name);
			if (s == null) { return; }
			if (s != null && s.isEmpty()) { return; }
			
			Villain h = getCurrentVillain();
			h.name = s;
			h.changed = true;
			
			for (VillainCard hc : h.cards)
			{
				hc.villainGroup = s;
				hc.changed = true;
			}
		}
		
		if (e.getSource().equals(delete))
		{
			if (getCurrentVillain() == null)
			{
				return;
			}
			
			int outcome = JOptionPane.showOptionDialog(lcmf, "Delete Villain?", "Delete Villain?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (outcome == JOptionPane.YES_OPTION)
			{
				lcmf.lcm.villains.remove(getCurrentVillain());
				getVillainListModel().removeElement(getCurrentVillain());
			}
		}
		
		if (e.getSource().equals(editDivider))
		{
			if (getCurrentVillain() == null)
			{
				return;
			}
			
			VillainDividerMakerFrame dmf = new VillainDividerMakerFrame(getCurrentVillain(), lcmf.lcm.dividerHorizontal);
		}
		
		if (e.getSource().equals(exportJPEGHomeprint))
		{
			if (getCurrentVillain() == null)
			{
				return;
			}
			
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
					List<CardMaker> cardMakers = new ArrayList<CardMaker>();
					for (VillainCard hc : getCurrentVillain().cards)
					{
						VillainMaker hm = new VillainMaker();
						hm.setCard(hc);
						
						for (int i = 0; i < hc.numberInDeck; i++)
						cardMakers.add(hm);
					}
					
					lcmf.applicationProps.put("lastExportDirectory", chooser.getSelectedFile().getAbsolutePath());
					
					ExportHomeprintProgressBarDialog exporter = new ExportHomeprintProgressBarDialog(lcmf.lcm.getCardCount(), lcmf.lcm, f, cardMakers);
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
	
	public JList getVillainList()
	{
		return lcmf.villainList;
	}
	
	public DefaultListModel getVillainListModel()
	{
		return lcmf.villainListModel;
	}
	
	public Villain getCurrentVillain()
	{
		Villain h = null;
		
		if (lcmf.villainListModel.size() > 0 && lcmf.villainList.getSelectedIndex() >= 0)
		{
			h = (Villain)lcmf.villainListModel.get(lcmf.villainList.getSelectedIndex());
		}
		
		return h;
	}
}
