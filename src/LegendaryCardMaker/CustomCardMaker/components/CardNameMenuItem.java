package LegendaryCardMaker.CustomCardMaker.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import LegendaryCardMaker.CustomCardMaker.CustomCardMakerFrame;
import LegendaryCardMaker.CustomCardMaker.structure.ElementCardName;
import LegendaryCardMaker.CustomCardMaker.structure.ElementText;

public class CardNameMenuItem extends JMenu implements ActionListener {

	public ElementCardName property;
	public CustomCardMakerFrame frame;
	
	public CardNameMenuItem(CustomCardMakerFrame frame, ElementCardName property)
	{
		this.property = property;
		this.frame = frame;
		
		setText(property.name);
		addActionListener(this);
		
		JMenuItem setCardName = new JMenuItem("Set Card Name...");
		setCardName.setActionCommand("setCardName");
		setCardName.addActionListener(this);
		this.add(setCardName);
		
		if (property.subnameEditable)
		{
			JMenuItem setSubCardName = new JMenuItem("Set Card Type...");
			setSubCardName.setActionCommand("setSubCardName");
			setSubCardName.addActionListener(this);
			this.add(setSubCardName);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("setCardName"))
		{
			String s = JOptionPane.showInputDialog(frame, "Set Card Name...", property.getValue());
			if (s == null) { s = property.value; }
			if (s != null && s.isEmpty()) { s = property.defaultValue; }
			property.value = s;
			frame.reRenderCard();
			frame.hm.card.changed = true;
		}
		
		if (e.getActionCommand().equals("setSubCardName"))
		{
			String s = JOptionPane.showInputDialog(frame, "Set Card Type...", property.getSubnameValue());
			if (s == null) { s = property.subnameValue; }
			if (s != null && s.isEmpty()) { s = property.subnameValue; }
			property.subnameValue = s;
			frame.reRenderCard();
			frame.hm.card.changed = true;
		}
	}
}
