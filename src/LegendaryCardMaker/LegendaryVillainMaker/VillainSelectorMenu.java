package LegendaryCardMaker.LegendaryVillainMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;

import LegendaryCardMaker.CardMakerToolbar;
import LegendaryCardMaker.LegendaryCardMakerFrame;

public class VillainSelectorMenu extends JMenu implements ActionListener{
	
	JMenuItem newHenchmen = new JMenuItem("New Henchmen...");
	JMenuItem newVillain = new JMenuItem("New Villain...");
	JMenuItem newMastermind = new JMenuItem("New Mastermind...");
	JMenuItem edit = new JMenuItem("Edit Villain...");
	JMenuItem rename = new JMenuItem("Rename Villain...");
	JMenuItem delete = new JMenuItem("Delete Villain...");
	
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
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.VILLAIN;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.VILLAIN;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.VILLAIN;
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
			hc.cardType = VillainCardType.MASTERMIND;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.MASTERMIND_TACTIC;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.MASTERMIND_TACTIC;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.MASTERMIND_TACTIC;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
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
