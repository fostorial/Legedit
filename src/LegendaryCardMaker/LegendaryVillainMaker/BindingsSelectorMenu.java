package LegendaryCardMaker.LegendaryVillainMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import LegendaryCardMaker.CardMakerToolbar;
import LegendaryCardMaker.LegendaryCardMakerFrame;

public class BindingsSelectorMenu extends JMenu implements ActionListener{
	
	JMenuItem newBindings = new JMenuItem("New Bindings...");
	JMenuItem edit = new JMenuItem("Edit Bindings...");
	JMenuItem delete = new JMenuItem("Delete Bindings...");
	
	public LegendaryCardMakerFrame lcmf;
	
	static CardMakerToolbar tb = null;
	
	public BindingsSelectorMenu(LegendaryCardMakerFrame lcmf, CardMakerToolbar tb)
	{
		this.tb = tb;
		this.lcmf = lcmf;
		
		this.setText("Edit");
		
		newBindings.addActionListener(this);
		add(newBindings);
		
		addSeparator();
		
		edit.addActionListener(this);
		add(edit);
		
		delete.addActionListener(this);
		add(delete);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(newBindings))
		{
			String s = JOptionPane.showInputDialog(lcmf, "Enter the Bindings Name", "");
			if (s == null) { return; }
			if (s != null && s.isEmpty()) { return; }
			
			Villain h = lcmf.lcm.bindingsVillain;
			h.changed = true;
			
			VillainCard hc = VillainMaker.getBlankVillainCard();
			hc.name = s;
			hc.villain = h;
			hc.villainGroup = h.name;
			hc.cardType = VillainCardType.BINDINGS;
			hc.changed = true;
			hc.abilityText = "<k>Betrayal: <r>If you dont recruit any Allies or defeat any Adversaries or Commanders on your turn, you may KO a Bindings from your hand. If you do, the player to your right gains all the other Bindings from your hand.";

			lcmf.bindingsListModel.addElement(hc);
			lcmf.lcm.bindingsVillain.cards.add(hc);
		}
		
		if (e.getSource().equals(edit))
		{
			if (getCurrentBindings() == null)
			{
				return;
			}
			VillainMakerFrame vmf = new VillainMakerFrame(getCurrentBindings());
		}
		
		if (e.getSource().equals(delete))
		{
			if (getCurrentBindings() == null)
			{
				return;
			}
			
			int outcome = JOptionPane.showOptionDialog(lcmf, "Delete Bindings?", "Delete Bindings?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (outcome == JOptionPane.YES_OPTION)
			{
				lcmf.lcm.bindingsVillain.cards.remove(getCurrentBindings());
				getBindingsListModel().removeElement(getCurrentBindings());
			}
		}
	}
	
	public JList getBindingsList()
	{
		return lcmf.bindingsList;
	}
	
	public DefaultListModel getBindingsListModel()
	{
		return lcmf.bindingsListModel;
	}
	
	public VillainCard getCurrentBindings()
	{
		VillainCard h = null;
		
		if (lcmf.bindingsListModel.size() > 0 && lcmf.bindingsList.getSelectedIndex() >= 0)
		{
			h = (VillainCard)lcmf.bindingsListModel.get(lcmf.bindingsList.getSelectedIndex());
		}
		
		return h;
	}
}
