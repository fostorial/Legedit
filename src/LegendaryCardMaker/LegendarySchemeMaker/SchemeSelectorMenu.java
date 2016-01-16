package LegendaryCardMaker.LegendarySchemeMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import LegendaryCardMaker.LegendaryHeroMaker.Hero;
import LegendaryCardMaker.LegendaryHeroMaker.HeroCardSelector;

public class SchemeSelectorMenu extends JMenu implements ActionListener{
	
	JMenuItem newHero = new JMenuItem("New Scheme...");
	JMenuItem edit = new JMenuItem("Edit Scheme...");
	JMenuItem rename = new JMenuItem("Rename Scheme...");
	JMenuItem delete = new JMenuItem("Delete Scheme...");
	JMenuItem copy = new JMenuItem("Copy Scheme...");
	
	JMenuItem random = new JMenuItem("Random Scheme...");
	
	public LegendaryCardMakerFrame lcmf;
	
	static CardMakerToolbar tb = null;
	
	public SchemeSelectorMenu(LegendaryCardMakerFrame lcmf, CardMakerToolbar tb)
	{
		this.tb = tb;
		this.lcmf = lcmf;
		
		this.setText("Edit");
		
		newHero.addActionListener(this);
		add(newHero);
		
		addSeparator();
		
		edit.addActionListener(this);
		add(edit);
		
		//rename.addActionListener(this);
		//add(rename);
		
		delete.addActionListener(this);
		add(delete);
		
		addSeparator();
		
		copy.addActionListener(this);
		add(copy);
		
		addSeparator();
		
		random.addActionListener(this);
		add(random);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(newHero))
		{
			String s = JOptionPane.showInputDialog(lcmf, "Enter the Scheme Name", "");
			if (s == null) { return; }
			if (s != null && s.isEmpty()) { return; }
			
			Scheme h = new Scheme();
			h.name = s;
			h.changed = true;
			
			SchemeCard hc = SchemeMaker.getBlankSchemeCard();
			hc.name = h.name;
			hc.cardType = SchemeCardType.valueOf("SCHEME");
			hc.changed = true;
			h.cards.add(hc);
			
			lcmf.schemeListModel.addElement(hc);
			lcmf.lcm.schemes.add(hc);
		}
		
		if (e.getSource().equals(delete))
		{
			if (getCurrentScheme() == null)
			{
				return;
			}
			
			int outcome = JOptionPane.showOptionDialog(lcmf, "Delete Scheme?", "Delete Scheme?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (outcome == JOptionPane.YES_OPTION)
			{
				lcmf.lcm.schemes.remove(getCurrentScheme());
				getSchemeListModel().removeElement(getCurrentScheme());
			}
		}
		
		if (e.getSource().equals(edit))
		{
			if (getCurrentScheme() == null)
			{
				return;
			}
			new SchemeMakerFrame(getCurrentScheme());
		}
		
		if (e.getSource().equals(copy))
		{
			if (getCurrentScheme() == null)
			{
				return;
			}
			SchemeCard c = getCurrentScheme().getCopy();
			lcmf.schemeListModel.addElement(c);
			lcmf.lcm.schemes.add(c);
		}
		
		if (e.getSource().equals(random))
		{
			if (lcmf.schemeListModel.size() > 0)
			{
				Random rand = new Random(new Date().getTime());
				new SchemeMakerFrame((SchemeCard)lcmf.schemeListModel.get(rand.nextInt(lcmf.schemeListModel.size())));
			}
		}
	}
	
	public JList getSchemeList()
	{
		return lcmf.schemeList;
	}
	
	public DefaultListModel getSchemeListModel()
	{
		return lcmf.schemeListModel;
	}
	
	public SchemeCard getCurrentScheme()
	{
		SchemeCard h = null;
		
		if (lcmf.schemeListModel.size() > 0 && lcmf.schemeList.getSelectedIndex() >= 0)
		{
			h = (SchemeCard)lcmf.schemeListModel.get(lcmf.schemeList.getSelectedIndex());
		}
		
		return h;
	}
}
