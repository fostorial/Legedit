package LegendaryCardMaker.LegendaryVillainMaker;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import LegendaryCardMaker.LegendaryDividerMaker.HeroDividerMakerFrame;
import LegendaryCardMaker.LegendaryDividerMaker.VillainDividerMakerFrame;

public class VillainCardSelectorToolbar extends JMenuBar implements ActionListener{

	VillainCardSelector hm;
	
	JMenu file = new JMenu("File");
	
	JMenuItem exportJPG = new JMenuItem("Export to JPEG...");
	JMenuItem exportPNG = new JMenuItem("Export to PNG...");
	JMenuItem exportPrinterStudioPNG = new JMenuItem("Export to Printer Studio PNG...");
	JMenuItem close = new JMenuItem("Close");
	
	JMenu edit = new JMenu("Edit");
	JMenuItem newCard = new JMenuItem("New Card");
	JMenuItem editCard = new JMenuItem("Edit Card...");
	JMenuItem delete = new JMenuItem("Delete Card...");
	JMenuItem editDivider = new JMenuItem("Edit Divider...");
	
	static VillainCardSelectorToolbar tb = null;
	
	public VillainCardSelectorToolbar(VillainCardSelector hm)
	{
		this.tb = this;
		
		this.hm = hm;
		
		//exportJPG.addActionListener(this);
		//file.add(exportJPG);
		exportPNG.addActionListener(this);
		file.add(exportPNG);
		
		//exportPrinterStudioPNG.addActionListener(this);
		//file.add(exportPrinterStudioPNG);
		
		file.addSeparator();
		
		close.addActionListener(this);
		file.add(close);
		
		add(file);
		
		newCard.addActionListener(this);
		edit.add(newCard);
		
		edit.addSeparator();
		
		editCard.addActionListener(this);
		edit.add(editCard);
		
		delete.addActionListener(this);
		edit.add(delete);
		
		edit.addSeparator();
		
		editDivider.addActionListener(this);
		edit.add(editDivider);
		
		add(edit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(exportPNG))
		{
			JFileChooser chooser = new JFileChooser(hm.lcmf.lcm.exportFolder);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int outcome = chooser.showSaveDialog(this);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				File f = chooser.getSelectedFile();
				hm.lcmf.lcm.exportFolder = f.getAbsolutePath();
				
				f.mkdirs();
				
				try
				{
					hm.lcmf.lcm.exportVillainToPng(hm.h, f);
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(hm.lcmf, "Error! " + ex.getMessage());
				}
			}
		}
		
		if (e.getSource().equals(close))
		{
			hm.setVisible(false);
		}
		
		if (e.getSource().equals(editCard))
		{
			if (getCurrentVillainCard() == null)
			{
				return;
			}
			
			VillainMakerFrame hmf = new VillainMakerFrame((VillainCard)this.hm.cardList.getSelectedValue());
		}
		
		if (e.getSource().equals(newCard))
		{
			VillainCard hc = VillainMaker.getBlankVillainCard();
			hc.villain = hm.h;
			hc.villainGroup = hm.h.name;
			hc.changed = true;
			hm.h.cards.add(hc);
			hm.h.changed = true;
			
			hm.cardListModel.addElement(hc);
		}
		
		if (e.getSource().equals(delete))
		{
			if (getCurrentVillainCard() == null)
			{
				return;
			}
			
			int outcome = JOptionPane.showOptionDialog(hm.lcmf, "Delete Card?", "Delete Card?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (outcome == JOptionPane.YES_OPTION)
			{
				hm.h.cards.remove(getCurrentVillainCard());
				getVillainCardListModel().removeElement(getCurrentVillainCard());
			}
		}
		
		if (e.getSource().equals(editDivider))
		{			
			VillainDividerMakerFrame dmf = new VillainDividerMakerFrame(hm.h, this.hm.lcmf.lcm.dividerHorizontal);
		}
	}
	
	public JList getVillainCardList()
	{
		return hm.cardList;
	}
	
	public DefaultListModel getVillainCardListModel()
	{
		return hm.cardListModel;
	}
	
	public VillainCard getCurrentVillainCard()
	{
		VillainCard h = null;
		
		if (hm.cardListModel.size() > 0 && hm.cardList.getSelectedIndex() >= 0)
		{
			h = (VillainCard)hm.cardListModel.get(hm.cardList.getSelectedIndex());
		}
		
		return h;
	}
}
