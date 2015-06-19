package LegendaryCardMaker;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import LegendaryCardMaker.LegendarySchemeMaker.SchemeCardType;

public class SchemeTypeSelectorMenu extends JMenu implements ActionListener{
	
	JMenuItem newSchemeType = new JMenuItem("New Scheme Type...");
	JMenuItem rename = new JMenuItem("Rename Scheme Type...");
	JMenuItem headingColour = new JMenuItem("Change Heading Colour...");
	JMenuItem customHeadings = new JMenuItem("Change Custom Headings...");
	JMenuItem delete = new JMenuItem("Delete Scheme Type...");
	
	public LegendaryCardMakerFrame lcmf;
	
	static CardMakerToolbar tb = null;
	
	public SchemeTypeSelectorMenu(LegendaryCardMakerFrame lcmf, CardMakerToolbar tb)
	{
		this.tb = tb;
		this.lcmf = lcmf;
		
		this.setText("Edit");
		
		newSchemeType.addActionListener(this);
		add(newSchemeType);
		
		addSeparator();
		
		rename.addActionListener(this);
		add(rename);
		
		headingColour.addActionListener(this);
		add(headingColour);
		
		customHeadings.addActionListener(this);
		add(customHeadings);
		
		delete.addActionListener(this);
		add(delete);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(newSchemeType))
		{
			String name = JOptionPane.showInputDialog(LegendaryCardMakerFrame.lcmf, "Enter the Scheme Type name", null);
			if (name == null) { return; }
			if (name != null && name.isEmpty()) { return; }
			
			JColorChooser chooser = new JColorChooser();
			Color bgColor = chooser.showDialog(LegendaryCardMakerFrame.lcmf, "Select heading colour...", null);
			if (bgColor == null)
			{
				return;
			}
			
			boolean headings = false;
			int outcome = JOptionPane.showConfirmDialog(LegendaryCardMakerFrame.lcmf, "Custom Headings?", "Custom Headings?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (outcome == JOptionPane.YES_OPTION)
			{
				headings = true;
			}
			else
			{
				headings = false;
			}
			
			SchemeCardType type = new SchemeCardType(bgColor, name.toUpperCase(), headings);
			SchemeCardType.values().add(type);
			
			getSchemeTypeListModel().addElement(type);
			
			SchemeCardType.saveSchemeTypeDefinitions();
		}
		
		if (e.getSource().equals(rename))
		{
			if (getCurrentSchemeType() == null)
			{
				return;
			}
			
			String name = JOptionPane.showInputDialog(lcmf, "Enter the Scheme Type name", getCurrentSchemeType().getEnumName());
			if (name == null) { return; }
			if (name != null && name.isEmpty()) { return; }
			
			getCurrentSchemeType().setDisplayString(name);
			
			SchemeCardType.saveSchemeTypeDefinitions();
		}
		
		if (e.getSource().equals(headingColour))
		{
			if (getCurrentSchemeType() == null)
			{
				return;
			}
			
			JColorChooser chooser = new JColorChooser();
			Color outcome = chooser.showDialog(lcmf, "Select heading colour...", getCurrentSchemeType().getBgColor());
			if (outcome != null)
			{
				getCurrentSchemeType().setBgColor(outcome);
			}
			
			SchemeCardType.saveSchemeTypeDefinitions();
			
		}
		
		if (e.getSource().equals(customHeadings))
		{
			if (getCurrentSchemeType() == null)
			{
				return;
			}
			
			boolean headings = false;
			int outcome = JOptionPane.showConfirmDialog(this, "Custom Headings?", "Custom Headings?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (outcome == JOptionPane.YES_OPTION)
			{
				headings = true;
			}
			else
			{
				headings = false;
			}
			
			getCurrentSchemeType().setAllowHeadings(headings);
			
			SchemeCardType.saveSchemeTypeDefinitions();
		}
		
		if (e.getSource().equals(delete))
		{
			if (getCurrentSchemeType() == null)
			{
				return;
			}
			
			int outcome = JOptionPane.showOptionDialog(lcmf, "Delete Scheme Type?", "Delete Scheme Type?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (outcome == JOptionPane.YES_OPTION)
			{
				SchemeCardType.values().remove(getCurrentSchemeType());
				getSchemeTypeListModel().removeElement(getCurrentSchemeType());
				
				SchemeCardType.saveSchemeTypeDefinitions();
			}
		}
	}
	
	public JList getSchemeTypeList()
	{
		return lcmf.schemeTypeList;
	}
	
	public DefaultListModel getSchemeTypeListModel()
	{
		return lcmf.schemeTypeListModel;
	}
	
	public SchemeCardType getCurrentSchemeType()
	{
		SchemeCardType i = null;
		
		if (lcmf.schemeTypeListModel.size() > 0 && lcmf.schemeTypeList.getSelectedIndex() >= 0)
		{
			i = (SchemeCardType)lcmf.schemeTypeListModel.get(lcmf.schemeTypeList.getSelectedIndex());
		}
		
		return i;
	}
}
