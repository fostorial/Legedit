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

public class BystanderSelectorMenu extends JMenu implements ActionListener{
	
	JMenuItem newBystander = new JMenuItem("New Bystander...");
	JMenuItem edit = new JMenuItem("Edit Bystander...");
	JMenuItem delete = new JMenuItem("Delete Bystander...");
	
	public LegendaryCardMakerFrame lcmf;
	
	static CardMakerToolbar tb = null;
	
	public BystanderSelectorMenu(LegendaryCardMakerFrame lcmf, CardMakerToolbar tb)
	{
		this.tb = tb;
		this.lcmf = lcmf;
		
		this.setText("Edit");
		
		newBystander.addActionListener(this);
		add(newBystander);
		
		addSeparator();
		
		edit.addActionListener(this);
		add(edit);
		
		delete.addActionListener(this);
		add(delete);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(newBystander))
		{
			String s = JOptionPane.showInputDialog(lcmf, "Enter the Bystander Name", "");
			if (s == null) { return; }
			if (s != null && s.isEmpty()) { return; }
			
			Villain h = lcmf.lcm.bystanderVillain;
			h.name = s;
			h.changed = true;
			
			VillainCard hc = VillainMaker.getBlankVillainCard();
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.BYSTANDER;
			hc.changed = true;
			h.cards.add(hc);
			
			lcmf.bystanderListModel.addElement(hc);
			lcmf.lcm.bystanderVillain.cards.add(hc);
		}
		
		if (e.getSource().equals(edit))
		{
			if (getCurrentBystander() == null)
			{
				return;
			}
			VillainMakerFrame vmf = new VillainMakerFrame(getCurrentBystander());
		}
		
		if (e.getSource().equals(delete))
		{
			if (getCurrentBystander() == null)
			{
				return;
			}
			
			int outcome = JOptionPane.showOptionDialog(lcmf, "Delete Bystander?", "Delete Bystander?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (outcome == JOptionPane.YES_OPTION)
			{
				lcmf.lcm.bystanderVillain.cards.remove(getCurrentBystander());
				getBystanderListModel().removeElement(getCurrentBystander());
			}
		}
	}
	
	public JList getBystanderList()
	{
		return lcmf.bystanderList;
	}
	
	public DefaultListModel getBystanderListModel()
	{
		return lcmf.bystanderListModel;
	}
	
	public VillainCard getCurrentBystander()
	{
		VillainCard h = null;
		
		if (lcmf.bystanderListModel.size() > 0 && lcmf.bystanderList.getSelectedIndex() >= 0)
		{
			h = (VillainCard)lcmf.bystanderListModel.get(lcmf.bystanderList.getSelectedIndex());
		}
		
		return h;
	}
}
