package LegendaryCardMaker.CustomCardMaker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import LegendaryCardMaker.CardMaker;
import LegendaryCardMaker.LegendaryCardMaker;
import LegendaryCardMaker.LegendaryCardMakerFrame;
import LegendaryCardMaker.CustomCardMaker.CustomCardMaker;
import LegendaryCardMaker.CustomCardMaker.CustomCardMakerFrame;
import LegendaryCardMaker.CustomCardMaker.structure.CustomCard;
import LegendaryCardMaker.CustomCardMaker.structure.CustomProperties;
import LegendaryCardMaker.CustomCardMaker.structure.CustomStructure;
import LegendaryCardMaker.CustomCardMaker.structure.CustomTemplate;
import LegendaryCardMaker.CustomCardMaker.structure.ElementProperty;
import LegendaryCardMaker.LegendaryHeroMaker.HeroCard;
import LegendaryCardMaker.LegendaryHeroMaker.HeroMaker;
import LegendaryCardMaker.exporters.ExportHomeprintProgressBarDialog;

public class CustomTemplateEditMenu extends JMenu implements ActionListener {

	private CustomTemplateList list = null;
	private JMenu newMenu = new JMenu("New");
	
	JMenuItem exportJPEGHomeprint = new JMenuItem("Export to JPEG for Homeprint...");
	
	public CustomTemplateEditMenu(CustomTemplateList list)
	{
		super("Edit");
		
		this.list = list;
		
		if (list.templateList.size() > 1)
		{
			for (CustomTemplate t : list.templateList)
			{
				JMenuItem newItem = new JMenuItem(t.templateDisplayName);
				newItem.addActionListener(this);
				newItem.setActionCommand("new_"+t.templateName);
				newMenu.add(newItem);
			}
			for (CustomStructure t : list.structures)
			{
				JMenuItem newItem = new JMenuItem(t.displayName);
				newItem.addActionListener(this);
				newItem.setActionCommand("new_struct_"+t.name);
				newMenu.add(newItem);
			}
			add(newMenu);
			addSeparator();
		}
		else if (list.templateList.size() == 1 && list.structures.size() == 0)
		{
			JMenuItem newItem = new JMenuItem("New " + list.templateList.get(0).templateDisplayName);
			newItem.addActionListener(this);
			newItem.setActionCommand("new_"+list.templateList.get(0).templateName);
			add(newItem);
			addSeparator();
		}
		else if (list.templateList.size() == 0 && list.structures.size() == 1)
		{
			JMenuItem newItem = new JMenuItem("New " + list.structures.get(0).displayName);
			newItem.addActionListener(this);
			newItem.setActionCommand("new_struct_"+list.structures.get(0).name);
			add(newItem);
			addSeparator();
		}
		
		JMenuItem editItem = new JMenuItem("Edit Card");
		editItem.addActionListener(this);
		editItem.setActionCommand("edit_card");
		add(editItem);
		
		JMenuItem deleteItem = new JMenuItem("Delete Card");
		deleteItem.addActionListener(this);
		deleteItem.setActionCommand("delete_card");
		add(deleteItem);
		
		addSeparator();
		
		exportJPEGHomeprint.addActionListener(this);
		add(exportJPEGHomeprint);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().startsWith("new_"))
		{
			for (CustomTemplate t : list.templateList)
			{
				if (t.templateName.equals(e.getActionCommand().replace("new_", "")))
				{
					CustomCard cc = new CustomCard();
					cc.template = t.getCopy();
					cc.templateName = t.templateName;
					list.cardListModel.addElement(cc);
					list.cards.add(cc);
					LegendaryCardMakerFrame.lcmf.lcm.customCards.add(cc);
				}
			}
		}
		
		if (e.getActionCommand().startsWith("edit_card"))
		{
            	CustomCard cc = list.getSelectedCustomCard();
            	if (cc != null)
            	{
            		new CustomCardMakerFrame(cc);
            	}
		}
		
		if (e.getActionCommand().startsWith("delete_card"))
		{
			CustomCard cc = list.getSelectedCustomCard();
        	if (cc != null)
        	{
        		int outcome = JOptionPane.showOptionDialog(LegendaryCardMakerFrame.lcmf, "Delete Card?", "Delete Hero?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    			if (outcome == JOptionPane.YES_OPTION)
    			{
    				LegendaryCardMakerFrame.lcmf.lcm.customCards.remove(cc);
    				
    				Set<Entry<String, CustomTemplateList>> entrySet = LegendaryCardMakerFrame.lcmf.customTemplateListSet.entrySet();
    				for (Entry<String, CustomTemplateList> entry : entrySet)
    				{
    					entry.getValue().removeCustomCard(cc);
    				}
    			}
        	}
		}
		
		if (e.getSource().equals(exportJPEGHomeprint))
		{
			CustomCard cc = list.getSelectedCustomCard();
			if (cc == null)
			{
				return;
			}
			
			JFileChooser chooser = new JFileChooser();
			if (LegendaryCardMakerFrame.lcmf.lcm.exportFolder != null)
			{
				File tf = new File(LegendaryCardMakerFrame.lcmf.lcm.exportFolder);
				chooser = new JFileChooser(tf.getParent());
			}
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				File f = chooser.getSelectedFile();
				LegendaryCardMakerFrame.lcmf.lcm.exportFolder = f.getAbsolutePath();
				
				f.mkdirs();
				
				try
				{
					List<CardMaker> cardMakers = new ArrayList<CardMaker>();
					for (CustomCard hc : list.cards)
					{
						CustomCardMaker hm = new CustomCardMaker();
						hm.setCard(hc);
						
						int numberInDeck = 1;
	        			ElementProperty numberInDeckProp = cc.template.getProperty(CustomProperties.NUMBERINDECK);
	        			if (numberInDeckProp != null)
	        			{
	        				try
	        				{
	        					numberInDeck = Integer.parseInt(numberInDeckProp.getValue());
	        				}
	        				catch (Exception ex)
	        				{
	        					numberInDeck = 1;
	        				}
	        			}
            			for (int i = 0; i < numberInDeck; i++)
            			{
            				cardMakers.add(hm);
            			}
					}
					
					LegendaryCardMakerFrame.lcmf.applicationProps.put("lastExportDirectory", chooser.getSelectedFile().getAbsolutePath());
					
					ExportHomeprintProgressBarDialog exporter = new ExportHomeprintProgressBarDialog(LegendaryCardMakerFrame.lcmf.lcm.getCardCount(), LegendaryCardMakerFrame.lcmf.lcm, f, cardMakers);
					exporter.createAndShowGUI();
					
					//lcmf.lcm.exportToPng(f);
					
					LegendaryCardMakerFrame.lcmf.saveProperties();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(LegendaryCardMakerFrame.lcmf, "Error! " + ex.getMessage());
				}
			}
		}
	}
	
}
