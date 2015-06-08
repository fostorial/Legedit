package LegendaryCardMaker.LegendaryHeroMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import LegendaryCardMaker.CardMakerToolbar;
import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryCardMakerFrame;
import LegendaryCardMaker.LegendaryDividerMaker.HeroDividerMakerFrame;
import LegendaryCardMaker.LegendaryHeroMaker.Hero;

public class HeroSelectorMenu extends JMenu implements ActionListener{
	
	JMenuItem newHero = new JMenuItem("New Hero...");
	JMenuItem edit = new JMenuItem("Edit Hero...");
	JMenuItem rename = new JMenuItem("Rename Hero...");
	JMenuItem delete = new JMenuItem("Delete Hero...");
	JMenuItem editDivider = new JMenuItem("Edit Divider...");
	
	JMenu team = new JMenu("Set Team");
	List<JMenuItem> teamItems = new ArrayList<JMenuItem>();
	
	public LegendaryCardMakerFrame lcmf;
	
	static CardMakerToolbar tb = null;
	
	public HeroSelectorMenu(LegendaryCardMakerFrame lcmf, CardMakerToolbar tb)
	{
		this.tb = tb;
		this.lcmf = lcmf;
		
		this.setText("Edit");
		
		newHero.addActionListener(this);
		add(newHero);
		
		addSeparator();
		
		edit.addActionListener(this);
		add(edit);
		
		rename.addActionListener(this);
		add(rename);
		
		delete.addActionListener(this);
		add(delete);
		
		addSeparator();
		
		for (Icon icon : Icon.values())
		{
			if (icon.getIconType().equals(Icon.ICON_TYPE.TEAM) || icon.getIconType().equals(Icon.ICON_TYPE.NONE))
			{
				String s = icon.toString().substring(0, 1).toUpperCase() + icon.toString().substring(1).toLowerCase();
				s = s.replace("_", " ");
				JMenuItem m = new JMenuItem(s);
				m.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if (getCurrentHero() == null)
						{
							return;
						}
						
						String enumValue = ((JMenuItem)e.getSource()).getText().replace(" ", "_").toUpperCase();
						Icon icon = Icon.valueOf(enumValue);
						for (HeroCard hc : getCurrentHero().cards)
						{
							hc.cardTeam = icon;
							hc.changed = true;
						}
						
						getCurrentHero().changed = true;
					}
				});
				
				teamItems.add(m);
				team.add(m);
			}
		}
		add(team);
		
		addSeparator();
		
		editDivider.addActionListener(this);
		add(editDivider);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(newHero))
		{
			String s = JOptionPane.showInputDialog(lcmf, "Enter the Hero Name", "");
			if (s == null) { return; }
			if (s != null && s.isEmpty()) { return; }
			
			Hero h = new Hero();
			h.name = s;
			h.changed = true;
			
			HeroCard hc = HeroMaker.getBlankHeroCard();
			hc.heroName = h.name;
			hc.rarity = CardRarity.COMMON;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = HeroMaker.getBlankHeroCard();
			hc.heroName = h.name;
			hc.rarity = CardRarity.COMMON;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = HeroMaker.getBlankHeroCard();
			hc.heroName = h.name;
			hc.rarity = CardRarity.UNCOMMON;
			hc.changed = true;
			h.cards.add(hc);
			
			hc = HeroMaker.getBlankHeroCard();
			hc.heroName = h.name;
			hc.rarity = CardRarity.RARE;
			hc.changed = true;
			h.cards.add(hc);
			
			lcmf.heroListModel.addElement(h);
			lcmf.lcm.heroes.add(h);
		}
		
		if (e.getSource().equals(rename))
		{
			if (getCurrentHero() == null)
			{
				return;
			}
			
			String s = JOptionPane.showInputDialog(lcmf, "Enter the Hero Name", getCurrentHero().name);
			if (s == null) { return; }
			if (s != null && s.isEmpty()) { return; }
			
			Hero h = getCurrentHero();
			h.name = s;
			h.changed = true;
			
			for (HeroCard hc : h.cards)
			{
				hc.heroName = s;
				hc.changed = true;
			}
		}
		
		if (e.getSource().equals(edit))
		{
			if (getCurrentHero() == null)
			{
				return;
			}
			new HeroCardSelector(getCurrentHero(), lcmf);
		}
		
		if (e.getSource().equals(delete))
		{
			if (getCurrentHero() == null)
			{
				return;
			}
			
			int outcome = JOptionPane.showOptionDialog(lcmf, "Delete Hero?", "Delete Hero?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (outcome == JOptionPane.YES_OPTION)
			{
				lcmf.lcm.heroes.remove(getCurrentHero());
				getHeroListModel().removeElement(getCurrentHero());
			}
		}
		
		if (e.getSource().equals(editDivider))
		{
			if (getCurrentHero() == null)
			{
				return;
			}
			
			HeroDividerMakerFrame dmf = new HeroDividerMakerFrame(getCurrentHero(), lcmf.lcm.dividerHorizontal);
		}
	}
	
	public JList getHeroList()
	{
		return lcmf.heroList;
	}
	
	public DefaultListModel getHeroListModel()
	{
		return lcmf.heroListModel;
	}
	
	public Hero getCurrentHero()
	{
		Hero h = null;
		
		if (lcmf.heroListModel.size() > 0 && lcmf.heroList.getSelectedIndex() >= 0)
		{
			h = (Hero)lcmf.heroListModel.get(lcmf.heroList.getSelectedIndex());
		}
		
		return h;
	}
}
