package LegendaryCardMaker.CustomCardMaker.components;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import LegendaryCardMaker.Icon;
import LegendaryCardMaker.Icon.ICON_TYPE;
import LegendaryCardMaker.CustomCardMaker.CustomCardMakerFrame;
import LegendaryCardMaker.CustomCardMaker.structure.CustomElement;
import LegendaryCardMaker.CustomCardMaker.structure.ElementIcon;
import LegendaryCardMaker.CustomCardMaker.structure.ElementText;

public class CustomIconMenu extends JMenu implements ActionListener {
	
	public CustomCardMakerFrame frame = null;
	public ElementIcon icon = null;
	
	private List<JIconMenuItem> iconsMenuItems = new ArrayList<JIconMenuItem>();

	public CustomIconMenu(CustomCardMakerFrame frame, ElementIcon icon)
	{
		super(icon.name);
		
		this.frame = frame;
		this.icon = icon;
		
		if (icon.childElements != null && icon.childElements.size() > 0)
		{
			for (CustomElement e : icon.childElements)
			{
				if (e instanceof ElementText)
				{
					add(new TextMenuItem(frame, (ElementText)e));
				}
			}
		}
		
		if (icon.allowChange)
		{
			JMenu icons = new JMenu("Icon");
			JMenu teams = new JMenu("Teams");
			JMenu attributes = new JMenu("Attributes");
			JMenu powers = new JMenu("Powers");
			for (Icon i : Icon.values())
			{
				JIconMenuItem mi = new JIconMenuItem(i);
				if (i.equals(icon.getIconValue()))
				{
					mi.setSelected(true);
				}
				
				mi.setActionCommand("icon_" + i.getEnumName());
				mi.addActionListener(this);
				
				if (i.getIconType().equals(ICON_TYPE.TEAM))
				{
					teams.add(mi);
				}
				if (i.getIconType().equals(ICON_TYPE.POWER))
				{
					powers.add(mi);
				}
				if (i.getIconType().equals(ICON_TYPE.ATTRIBUTE))
				{
					attributes.add(mi);
				}
				iconsMenuItems.add(mi);
			}
			
			if (icon.iconType == null)
			{
				icons.add(attributes);
				icons.add(powers);
				icons.add(teams);
				this.add(icons);
			}
			else if (icon.iconType.equals(ICON_TYPE.TEAM))
			{
				this.add(teams);
			}
			else if (icon.iconType.equals(ICON_TYPE.ATTRIBUTE))
			{
				this.add(attributes);
			}
			else if (icon.iconType.equals(ICON_TYPE.POWER))
			{
				this.add(powers);
			}
			
		}
		
		if (icon.optional)
		{
			JCheckBoxMenuItem mi = new JCheckBoxMenuItem("Visible");
			mi.setSelected(true);
			mi.setActionCommand("visible");
			mi.addActionListener(this);
			add(mi);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("visible"))
		{
			if (((JCheckBoxMenuItem)e.getSource()).isSelected())
			{
				icon.visible = true;
			}
			else
			{
				icon.visible = false;
			}
			
			for (CustomElement el: icon.childElements)
			{
				el.visible = icon.visible;
			}
			
			frame.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			frame.reRenderCard();
			frame.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getActionCommand().startsWith("icon_"))
		{
			for (JIconMenuItem i : iconsMenuItems)
			{
				if (e.getActionCommand().equals("icon_" + i.icon.getEnumName()))
				{
					i.setSelected(true);
					icon.value = i.icon;
				}
				else
				{
					i.setSelected(false);
				}
			}
			
			frame.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			frame.reRenderCard();
			frame.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
}
