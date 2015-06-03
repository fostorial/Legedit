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

public class WoundSelectorMenu extends JMenu implements ActionListener{
	
	JMenuItem newWound = new JMenuItem("New Wound...");
	JMenuItem edit = new JMenuItem("Edit Wound...");
	JMenuItem delete = new JMenuItem("Delete Wound...");
	
	public LegendaryCardMakerFrame lcmf;
	
	static CardMakerToolbar tb = null;
	
	public WoundSelectorMenu(LegendaryCardMakerFrame lcmf, CardMakerToolbar tb)
	{
		this.tb = tb;
		this.lcmf = lcmf;
		
		this.setText("Edit");
		
		newWound.addActionListener(this);
		add(newWound);
		
		addSeparator();
		
		edit.addActionListener(this);
		add(edit);
		
		delete.addActionListener(this);
		add(delete);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(newWound))
		{
			String s = JOptionPane.showInputDialog(lcmf, "Enter the Wound Name", "");
			if (s == null) { return; }
			if (s != null && s.isEmpty()) { return; }
			
			Villain h = lcmf.lcm.woundVillain;
			h.changed = true;
			
			VillainCard hc = VillainMaker.getBlankVillainCard();
			hc.name = s;
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.WOUND;
			hc.changed = true;
			hc.abilityText = "<k>Healing: <r>If you dont recruit any Heroes or defeat any Villains on your turn, you may KO all the wounds from your hand.";

			lcmf.woundListModel.addElement(hc);
			lcmf.lcm.woundVillain.cards.add(hc);
		}
		
		if (e.getSource().equals(edit))
		{
			if (getCurrentWound() == null)
			{
				return;
			}
			VillainMakerFrame vmf = new VillainMakerFrame(getCurrentWound());
		}
		
		if (e.getSource().equals(delete))
		{
			if (getCurrentWound() == null)
			{
				return;
			}
			
			int outcome = JOptionPane.showOptionDialog(lcmf, "Delete Wound?", "Delete Wound?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (outcome == JOptionPane.YES_OPTION)
			{
				lcmf.lcm.woundVillain.cards.remove(getCurrentWound());
				getWoundListModel().removeElement(getCurrentWound());
			}
		}
	}
	
	public JList getWoundList()
	{
		return lcmf.woundList;
	}
	
	public DefaultListModel getWoundListModel()
	{
		return lcmf.woundListModel;
	}
	
	public VillainCard getCurrentWound()
	{
		VillainCard h = null;
		
		if (lcmf.woundListModel.size() > 0 && lcmf.woundList.getSelectedIndex() >= 0)
		{
			h = (VillainCard)lcmf.woundListModel.get(lcmf.woundList.getSelectedIndex());
		}
		
		return h;
	}
}
