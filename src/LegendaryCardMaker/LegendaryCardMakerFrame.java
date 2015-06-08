package LegendaryCardMaker;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import LegendaryCardMaker.LegendaryHeroMaker.Hero;
import LegendaryCardMaker.LegendaryHeroMaker.HeroCard;
import LegendaryCardMaker.LegendaryHeroMaker.HeroCardSelector;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCard;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeMaker;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeMakerFrame;
import LegendaryCardMaker.LegendaryVillainMaker.Villain;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCard;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCardSelector;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCardType;
import LegendaryCardMaker.LegendaryVillainMaker.VillainMakerFrame;

public class LegendaryCardMakerFrame extends JFrame {

	public LegendaryCardMaker lcm;
	
	public JTabbedPane tabs;
	
	public JList heroList;
	public DefaultListModel heroListModel;
	JScrollPane heroScroll = new JScrollPane();
	
	public JList villainList;
	public DefaultListModel villainListModel;
	JScrollPane villainScroll = new JScrollPane();
	
	public JList bystanderList;
	public DefaultListModel bystanderListModel;
	JScrollPane bystanderScroll = new JScrollPane();
	
	public JList woundList;
	public DefaultListModel woundListModel;
	JScrollPane woundScroll = new JScrollPane();
	
	public JList schemeList;
	public DefaultListModel schemeListModel;
	JScrollPane schemeScroll = new JScrollPane();
	
	public JList teamList;
	public DefaultListModel teamListModel;
	JScrollPane teamScroll = new JScrollPane();
	
	public Properties applicationProps = new Properties();
	
	CardMakerToolbar toolbar;
	
	public static LegendaryCardMakerFrame lcmf;
	
	public LegendaryCardMakerFrame(LegendaryCardMaker lcm)
	{
		this.setTitle("Legendedit");
		
		this.lcmf = this;
		
		loadProperties();
		if (applicationProps.get("lastExpansion") != null)
		{
			lcm.ignoreGenerate = true;
			File file = new File((String)applicationProps.get("lastExpansion"));
			if (file.exists())
			{
				lcm.processInput((String)applicationProps.get("lastExpansion"));
			}
		}
		
		if (applicationProps.get("lastExportDirectory") != null)
		{
			File file = new File((String)applicationProps.get("lastExportDirectory"));
			if (file.exists())
			{
				lcm.exportFolder = (String)applicationProps.get("lastExportDirectory");
			}
		}
		
		if (applicationProps.get("lastOpenDirectory") != null)
		{
			File file = new File((String)applicationProps.get("lastOpenDirectory"));
			if (file.exists())
			{
				lcm.lastOpened = (String)applicationProps.get("lastOpenDirectory");
			}
		}
		
		if (applicationProps.get("lastSaveDirectory") != null)
		{
			File file = new File((String)applicationProps.get("lastSaveDirectory"));
			if (file.exists())
			{
				lcm.lastSaved = (String)applicationProps.get("lastSaveDirectory");
			}
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.lcm = lcm;
		
		tabs = new JTabbedPane();
		
		tabs.addChangeListener(new ChangeListener() 
		{
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    //System.out.println("Selected paneNo : " + pane.getSelectedIndex());
                    
                    if (toolbar != null)
                    {
                    	toolbar.setEditMenu();
                    }
                }
            }
        });
		
		
		heroListModel = new DefaultListModel();
		Collections.sort(lcm.heroes, new Hero());
		for (Hero h : lcm.heroes)
		{
			heroListModel.addElement(h);
		}
		heroList = new JList(heroListModel);
		heroList.setCellRenderer(new HeroListRenderer());
		heroList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0)
		            {
		            	new HeroCardSelector((Hero)list.getSelectedValue(), lcmf);
		            }
		        }
		    }
		});
		
		heroScroll.setViewportView(heroList);
		this.add(heroScroll);
		tabs.add("Heroes", heroScroll);
		
		
		villainListModel = new DefaultListModel();
		Collections.sort(lcm.villains, new Villain());
		for (Villain v : lcm.villains)
		{
			if (!v.name.equals("system_bystander_villain") && !v.name.equals("system_wound_villain"))
			{
				villainListModel.addElement(v);
			}
		}
		villainList = new JList(villainListModel);
		villainList.setCellRenderer(new VillainListRenderer());
		villainList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0)
		            {
		            	new VillainCardSelector((Villain)list.getSelectedValue(), lcmf);
		            }
		        }
		    }
		});
		
		villainScroll.setViewportView(villainList);
		this.add(villainScroll);
		tabs.add("Villains", villainScroll);
		
		
		bystanderListModel = new DefaultListModel();
		Collections.sort(lcm.villains, new Villain());
		for (Villain v : lcm.villains)
		{
			for (VillainCard vc : v.cards)
			{
				if (vc.cardType != null && vc.cardType.equals(VillainCardType.BYSTANDER))
				{
					bystanderListModel.addElement(vc);
				}
			}
		}
		bystanderList = new JList(bystanderListModel);
		bystanderList.setCellRenderer(new BystanderListRenderer());
		bystanderList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0)
		            {
		            	VillainMakerFrame vmf = new VillainMakerFrame((VillainCard)list.getSelectedValue());
		            	//new BystanderCardSelector((Bystander)list.getSelectedValue(), lcmf);
		            }
		        }
		    }
		});
		
		bystanderScroll.setViewportView(bystanderList);
		this.add(bystanderScroll);
		tabs.add("Bystanders", bystanderScroll);
		
		
		woundListModel = new DefaultListModel();
		Collections.sort(lcm.villains, new Villain());
		for (Villain v : lcm.villains)
		{
			for (VillainCard vc : v.cards)
			{
				if (vc.cardType != null && vc.cardType.equals(VillainCardType.WOUND))
				{
					woundListModel.addElement(vc);
				}
			}
		}
		woundList = new JList(woundListModel);
		woundList.setCellRenderer(new WoundListRenderer());
		woundList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0)
		            {
		            	VillainMakerFrame vmf = new VillainMakerFrame((VillainCard)list.getSelectedValue());
		            	//new WoundCardSelector((Wound)list.getSelectedValue(), lcmf);
		            }
		        }
		    }
		});
		
		woundScroll.setViewportView(woundList);
		this.add(woundScroll);
		tabs.add("Wounds", woundScroll);
		
		
		schemeListModel = new DefaultListModel();
		Collections.sort(lcm.schemes, new SchemeCard());
		for (SchemeCard v : lcm.schemes)
		{
			schemeListModel.addElement(v);
		}
		schemeList = new JList(schemeListModel);
		schemeList.setCellRenderer(new SchemeListRenderer());
		schemeList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0)
		            {
		            	//new HeroCardSelector((Hero)list.getSelectedValue(), lcmf);
		            	SchemeMakerFrame smf = new SchemeMakerFrame((SchemeCard)list.getSelectedValue());
		            }
		        }
		    }
		});
		
		schemeScroll.setViewportView(schemeList);
		this.add(schemeScroll);
		tabs.add("Schemes", schemeScroll);
		
		
		teamListModel = new DefaultListModel();
		List<Icon> icons = Icon.values();
		Collections.sort(icons, new Icon());
		for (Icon v : icons)
		{
			if (v.getIconType().equals(Icon.ICON_TYPE.TEAM))
			teamListModel.addElement(v);
		}
		teamList = new JList(teamListModel);
		teamList.setCellRenderer(new TeamListRenderer());
		teamList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0)
		            {
		            	//new HeroCardSelector((Hero)list.getSelectedValue(), lcmf);
		            }
		        }
		    }
		});
		
		teamScroll.setViewportView(teamList);
		this.add(teamScroll);
		tabs.add("Teams", teamScroll);
		
		
		this.add(tabs);
		
		toolbar = new CardMakerToolbar(this);
		this.setJMenuBar(toolbar);
		
		this.setSize(400, 500);
		this.setVisible(true);
	}
	
	public class HeroListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
        	
        	Hero hero = (Hero)value;

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(new ImageIcon(getImageSummary(hero)));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            
            String s = hero.name;
            boolean changed = false;
            for (HeroCard hc : hero.cards)
            {
            	if (hc.changed)
            	{
            		changed = true;
            	}
            }
            if (hero.changed || changed) { s += " *"; }
            label.setText(s);
            
            return label;
        }
        
        private BufferedImage getImageSummary(Hero h)
        {
        	int maxWidth = 24;
        	int maxHeight = 24;
        	
        	BufferedImage bi = new BufferedImage(maxWidth * (h.cards.size() + 1 + 1), maxHeight, BufferedImage.TYPE_INT_ARGB);
        	Graphics g2 = bi.getGraphics();
        	
        	Icon teamIcon = Icon.valueOf("");
        	HashMap<Icon, Integer> teamMap = new HashMap<Icon, Integer>();
        	for (HeroCard hc : h.cards)
        	{
        		if (teamMap.containsKey(hc.cardTeam))
        		{
        			Integer i = teamMap.get(hc.cardTeam);
        			i = new Integer(i.intValue() + 1);
        			teamMap.put(hc.cardTeam, i);
        		}
        		else
        		{
        			teamMap.put(hc.cardTeam, new Integer(1));
        		}
        		
        		int count = 0;
        		for (Entry<Icon, Integer> en : teamMap.entrySet())
        		{
        			if (en.getValue().intValue() > count)
        			{
        				count = en.getValue().intValue();
        				teamIcon = en.getKey();
        			}
        		}
        	}
        	
        	int offset = 0;
        	BufferedImage icon = getCardIcon(teamIcon, maxWidth, maxHeight);
        	g2.drawImage(icon, offset + ((maxWidth / 2) - (icon.getWidth() / 2)), ((maxHeight / 2) - (icon.getHeight() / 2)), null);
        	
        	offset += maxWidth;
        	for (HeroCard hc : h.cards)
        	{
        		icon = getCardIcon(hc.cardPower, maxWidth, maxHeight);
            	g2.drawImage(icon, offset + ((maxWidth / 2) - (icon.getWidth() / 2)), ((maxHeight / 2) - (icon.getHeight() / 2)), null);
            	offset += maxWidth;
        	}
        	
        	g2.dispose();
        
        	return bi;
        }
    }
	
	public class VillainListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
        	
        	Villain villain = (Villain)value;

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(new ImageIcon(getImageSummary(villain)));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            
            String s = villain.name;
            boolean changed = false;
            for (VillainCard vc : villain.cards)
            {
            	if (vc.changed)
            	{
            		changed = true;
            	}
            }
            if (villain.changed || changed) { s += " *"; }
            label.setText(s);
            
            return label;
        }
        
        private BufferedImage getImageSummary(Villain v)
        {
        	int maxWidth = 24;
        	int maxHeight = 24;
        	
        	BufferedImage bi = new BufferedImage(maxWidth * (2), maxHeight, BufferedImage.TYPE_INT_ARGB);
        	Graphics g2 = bi.getGraphics();
        	
        	Icon teamIcon = Icon.valueOf("");
        	HashMap<Icon, Integer> teamMap = new HashMap<Icon, Integer>();
        	for (VillainCard hc : v.cards)
        	{
        		if (teamMap.containsKey(hc.cardTeam))
        		{
        			Integer i = teamMap.get(hc.cardTeam);
        			i = new Integer(i.intValue() + 1);
        			teamMap.put(hc.cardTeam, i);
        		}
        		else
        		{
        			teamMap.put(hc.cardTeam, new Integer(1));
        		}
        		
        		int count = 0;
        		for (Entry<Icon, Integer> en : teamMap.entrySet())
        		{
        			if (en.getValue().intValue() > count)
        			{
        				count = en.getValue().intValue();
        				teamIcon = en.getKey();
        			}
        		}
        	}
        	
        	int offset = 0;
        	BufferedImage icon = getCardIcon(teamIcon, maxWidth, maxHeight);
        	g2.drawImage(icon, offset + ((maxWidth / 2) - (icon.getWidth() / 2)), ((maxHeight / 2) - (icon.getHeight() / 2)), null);
        	
        	g2.dispose();
        
        	return bi;
        }
    }
	
	public class SchemeListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
        	
        	SchemeCard villain = (SchemeCard)value;

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            //label.setIcon(new ImageIcon(getImageSummary(villain)));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            
            String s = villain.name + " (" +  villain.cardType.toString() + ")";
            if (villain.changed) { s += " *"; }
            label.setText(s);
            
            return label;
        }
    }
	
	public class BystanderListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
        	
        	VillainCard villain = (VillainCard)value;

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            //label.setIcon(new ImageIcon(getImageSummary(villain)));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            
            String s = villain.name;
            if (villain.changed) { s += " *"; }
            label.setText(s);
            
            return label;
        }
    }
	
	public class WoundListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
        	
        	VillainCard villain = (VillainCard)value;

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            //label.setIcon(new ImageIcon(getImageSummary(villain)));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            
            String s = villain.name;
            if (villain.changed) { s += " *"; }
            label.setText(s);
            
            return label;
        }
    }
	
	public class TeamListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
        	
        	Icon villain = (Icon)value;

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(new ImageIcon(getImageSummary(villain)));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            
            String s = villain.getEnumName();
            label.setText(s);
            
            return label;
        }
        
        private BufferedImage getImageSummary(Icon v)
        {
        	int maxWidth = 24;
        	int maxHeight = 24;
        	
        	BufferedImage bi = new BufferedImage(maxWidth * (2), maxHeight, BufferedImage.TYPE_INT_ARGB);
        	Graphics g2 = bi.getGraphics();
        	
        	int offset = 0;
        	BufferedImage icon = getCardIcon(v, maxWidth, maxHeight);
        	g2.drawImage(icon, offset + ((maxWidth / 2) - (icon.getWidth() / 2)), ((maxHeight / 2) - (icon.getHeight() / 2)), null);
        	
        	g2.dispose();
        
        	return bi;
        }
    }
	
	public BufferedImage getCardIcon(Icon icon, int maxWidth, int maxHeight)
	{
		if (icon == null || icon.getImagePath() == null)
		{
	        int type = BufferedImage.TYPE_INT_ARGB;
	        BufferedImage image = new BufferedImage(maxWidth, maxHeight, type);
	        return image;
		}
		
		ImageIcon ii = new ImageIcon(icon.getImagePath());
		double r = 1d;
		double rX = (double)((double)maxWidth / (double)ii.getIconWidth());
		double rY = (double)((double)maxHeight / (double)ii.getIconHeight());
		if (rY < rX)
		{
			r = rY;
		}
		else
		{
			r = rX;
		}
		
		return resizeImage(ii, r);
	}
	
	public BufferedImage resizeImage(ImageIcon imageIcon, double scale)
	{
			int w = (int)(imageIcon.getIconWidth() * scale);
	        int h = (int)(imageIcon.getIconHeight() * scale);
	        int type = BufferedImage.TYPE_INT_ARGB;
	        
	        BufferedImage image = new BufferedImage(w, h, type);
	        Graphics g = image.getGraphics();
	        
	        g.drawImage(imageIcon.getImage(), 0, 0, w, h, 
	        		0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
	        
	        g.dispose();
	        
	        return image;
	}
	
	public void loadProperties()
	{
		try {
			FileInputStream in = new FileInputStream("appProperties");
			applicationProps.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveProperties()
	{
		try {
			FileOutputStream out = new FileOutputStream("appProperties");
			applicationProps.store(out, "---No Comment---");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createNewExpansion()
	{
		lcm.heroes = new ArrayList<Hero>();
		lcm.villains = new ArrayList<Villain>();
		lcm.schemes = new ArrayList<SchemeCard>();
		
		lcm.dividerHorizontal = true;
		lcm.dbImageOffsetX = 0;
		lcm.dbImageOffsetY = 0;
		lcm.dbImagePath = null;
		lcm.dbImageZoom = 1.0d;
		
		lcm.inputFile = null;
		lcm.textOutputFile = null;
		lcm.textErrorFile = null;
		lcm.exportFolder = null;
		lcm.textDividerFile = null;
		lcm.currentFile = null;
		
		heroListModel.clear();
		villainListModel.clear();
		schemeListModel.clear();
		bystanderListModel.clear();
		woundListModel.clear();
	}
}