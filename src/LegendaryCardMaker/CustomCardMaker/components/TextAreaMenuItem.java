package LegendaryCardMaker.CustomCardMaker.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import LegendaryCardMaker.CardTextDialog;
import LegendaryCardMaker.CustomCardMaker.CustomCardMakerFrame;
import LegendaryCardMaker.CustomCardMaker.structure.ElementCardName;
import LegendaryCardMaker.CustomCardMaker.structure.ElementText;
import LegendaryCardMaker.CustomCardMaker.structure.ElementTextArea;

public class TextAreaMenuItem extends JMenu implements ActionListener {

	public ElementTextArea property;
	public CustomCardMakerFrame frame;
	
	public TextAreaMenuItem(CustomCardMakerFrame frame, ElementTextArea property)
	{
		this.property = property;
		this.frame = frame;
		
		setText(property.name);
		addActionListener(this);
		
		JMenuItem setCardName = new JMenuItem("Set Card Text...");
		setCardName.setActionCommand("setCardText");
		setCardName.addActionListener(this);
		this.add(setCardName);
		
		JMenuItem setTextSize = new JMenuItem("Set Text Size...");
		setTextSize.setActionCommand("setTextSize");
		setTextSize.addActionListener(this);
		this.add(setTextSize);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("setCardText"))
		{
			String s = new CardTextDialog(property.getValue()).showInputDialog();
			if (s == null) { s = property.getValue(); }
			if (s != null && s.isEmpty()) { s = null; }
			property.value = s;
			frame.reRenderCard();
			frame.hm.card.changed = true;
		}
		
		if (e.getActionCommand().equals("setTextSize"))
		{
			String s = JOptionPane.showInputDialog(frame, "Enter the Card Name Size", property.textSize);
			if (s == null) { s = "" + property.textSize; }
			if (s != null && s.isEmpty()) { s = "" + property.textSize; }
			try
			{
				property.textSize = Integer.parseInt(s);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			frame.reRenderCard();
			frame.hm.card.changed = true;
		}
	}
}
