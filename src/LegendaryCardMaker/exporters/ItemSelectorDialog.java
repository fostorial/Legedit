package LegendaryCardMaker.exporters;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import LegendaryCardMaker.LegendaryCardMakerFrame;
import LegendaryCardMaker.LegendaryItem;
import LegendaryCardMaker.LegendaryHeroMaker.Hero;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCard;
import LegendaryCardMaker.LegendaryVillainMaker.Villain;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCard;

public class ItemSelectorDialog extends JDialog {

	private JCheckList<LegendaryItem> checklist;
	
	public ItemSelectorDialog(LegendaryCardMakerFrame parent)
	{
		super(parent);
		
		setSize(400, 500);
		
		setTitle("Select Items");
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		checklist = new JCheckList<LegendaryItem>();
		
		for (Hero h : LegendaryCardMakerFrame.lcmf.lcm.heroes)
		{
			checklist.addCheckListItem("Hero - " + h.name, h);
		}
		
		for (Villain h : LegendaryCardMakerFrame.lcmf.lcm.villains)
		{
			if (h.name.equals("system_bystander_villain"))
			{
				for (VillainCard vc : h.cards)
				{
					checklist.addCheckListItem("Bystander - " + h.name, h);
				}
			}
			else if (h.name.equals("system_wound_villain"))
			{
				for (VillainCard vc : h.cards)
				{
					checklist.addCheckListItem("Wound - " + h.name, h);
				}
			}
			else if (h.name.equals("system_bindings_villain"))
			{
				for (VillainCard vc : h.cards)
				{
					checklist.addCheckListItem("Bindings - " + h.name, h);
				}
			}
			else
			{
				checklist.addCheckListItem("Villain - " + h.name, h);
			}
		}
		
		for (SchemeCard h : LegendaryCardMakerFrame.lcmf.lcm.schemes)
		{
			checklist.addCheckListItem("Scheme - " + h.name + " (" + h.cardType.getDisplayString() + ")", h);
		}
		
		this.add(checklist);
		
		this.setModal(true);
		
		this.setVisible(true);
	}
	
	public List<LegendaryItem> getLegendaryItems()
	{
		return checklist.getSelectedItems();
	}

}
