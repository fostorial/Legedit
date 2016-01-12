package LegendaryCardMaker.CustomCardMaker.gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import LegendaryCardMaker.LegendaryCardMaker;
import LegendaryCardMaker.LegendaryCardMakerFrame;
import LegendaryCardMaker.LegendaryItem;
import LegendaryCardMaker.CustomCardMaker.CustomCardMakerFrame;
import LegendaryCardMaker.CustomCardMaker.structure.CustomCard;
import LegendaryCardMaker.CustomCardMaker.structure.CustomStructure;
import LegendaryCardMaker.CustomCardMaker.structure.CustomTemplate;

public class CustomTemplateList extends JScrollPane {

	public List<CustomCard> cards = new ArrayList<CustomCard>();
	public List<CustomStructure> structures = new ArrayList<CustomStructure>();
	public List<LegendaryItem> items = new ArrayList<LegendaryItem>();
	
	public JList cardList;
	public DefaultListModel cardListModel;
	public List<CustomTemplate> templateList = new ArrayList<CustomTemplate>();
	
	public CustomTemplateEditMenu menu;
	
	public CustomTemplateList(CustomTemplate template, List<CustomCard> cards, List<CustomStructure> structures)
	{
		templateList.add(template);
		
		for (CustomCard cc : cards)
		{
			if (cc.template.tab.equals(template.tab))
			{
				this.cards.add(cc);
			}
		}
		
		for (CustomStructure cc : structures)
		{
			if (cc.tab.equals(template.tab))
			{
				this.structures.add(cc);
			}
		}
		
		LegendaryCardMaker lcm = LegendaryCardMakerFrame.lcmf.lcm;
		
		cardListModel = new DefaultListModel();
		Collections.sort(this.cards, new CustomCard());
		for (CustomCard h : this.cards)
		{
			cardListModel.addElement(h);
		}
		cardList = new JList(cardListModel);
		cardList.setCellRenderer(new CustomCardListRenderer());
		cardList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0)
		            {
		            	CustomCard cc = (CustomCard)list.getSelectedValue();
		            	new CustomCardMakerFrame(cc);
		            }
		        }
		    }
		});
		
		setViewportView(cardList);
		
		menu = new CustomTemplateEditMenu(this);
	}
	
	public void addTemplate(CustomTemplate template, List<CustomCard> cards)
	{
		templateList.add(template);
		
		for (CustomCard cc : cards)
		{
			if (cc.template.tab.equals(template.tab) && !this.cards.contains(cc))
			{
				this.cards.add(cc);
			}
		}
		
		cardListModel.removeAllElements();
		Collections.sort(this.cards, new CustomCard());
		for (CustomCard h : this.cards)
		{
			cardListModel.addElement(h);
		}
		
		menu = new CustomTemplateEditMenu(this);
	}
	
	public void addStructure(CustomStructure structure)
	{
		if (structure.tab.equals(structure.tab) && !this.structures.contains(structure))
		{
			this.structures.add(structure);
		}
		
		cardListModel.removeAllElements();
		Collections.sort(this.cards, new CustomCard());
		for (CustomCard h : this.cards)
		{
			cardListModel.addElement(h);
		}
		
		menu = new CustomTemplateEditMenu(this);
	}
	
	public void loadList(List<CustomCard> cards)
	{
		this.cards.clear();
		this.cardListModel.removeAllElements();
		for (CustomTemplate template : templateList)
		{
			for (CustomCard cc : cards)
			{
				if (cc.template.tab.equals(template.tab) && !this.cards.contains(cc))
				{
					this.cards.add(cc);
					this.cardListModel.addElement(cc);
				}
			}
		}
	}
	
	public void loadListStructures(List<CustomStructure> structures)
	{
		this.cards.clear();
		this.cardListModel.removeAllElements();
		
		for (CustomStructure cc : structures)
		{
			if (!this.structures.contains(cc))
			{
				this.structures.add(cc);
				this.cardListModel.addElement(cc);
			}
		}
	}
	
	public class CustomCardListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
        	
        	CustomCard c = (CustomCard)value;

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setHorizontalTextPosition(JLabel.RIGHT);
            
            String s = c.getCardName();
            if (c.changed) { s += " *"; }
            label.setText(s);
            
            return label;
        }
    }
	
	public CustomCard getSelectedCustomCard()
	{
		return (CustomCard)cardList.getSelectedValue();
	}
	
	public void removeCustomCard(CustomCard cc)
	{
		cards.remove(cc);
		cardListModel.removeElement(cc);
	}
}
