package LegendaryCardMaker.CustomCardMaker.components;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import LegendaryCardMaker.CustomCardMaker.CustomCardMakerFrame;
import LegendaryCardMaker.CustomCardMaker.structure.ElementBackgroundImage;

public class BackgroundImageMenuItem extends JMenu implements ActionListener {

	public ElementBackgroundImage property;
	public CustomCardMakerFrame frame;
	
	public BackgroundImageMenuItem(CustomCardMakerFrame frame, ElementBackgroundImage property)
	{
		this.property = property;
		this.frame = frame;
		
		setText(property.name);
		addActionListener(this);
		
		JMenuItem setImagePath = new JMenuItem("Set Image Path...");
		setImagePath.setActionCommand("setImagePath");
		setImagePath.addActionListener(this);
		this.add(setImagePath);
		
		JMenuItem setImageZoom = new JMenuItem("Set Image Zoom...");
		setImageZoom.setActionCommand("setImageZoom");
		setImageZoom.addActionListener(this);
		this.add(setImageZoom);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("setImagePath"))
		{
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Image files", "png", "jpeg", "jpg", "bmp");
		    chooser.addChoosableFileFilter(filter1);
		    chooser.setFileFilter(filter1);
			int outcome = chooser.showOpenDialog(frame);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					//ImageIcon ii = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
					//hmf.imageLabel.setIcon(ii);
					property.path = chooser.getSelectedFile().getAbsolutePath();
					property.zoom = 1.0d;
					property.imageOffsetX = 0;
					property.imageOffsetY = 0;
					
					frame.reRenderCard();
					frame.hm.card.changed = true;
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		
		if (e.getActionCommand().equals("setImageZoom"))
		{
			String s = JOptionPane.showInputDialog(frame, "Enter the Image Zoom", property.zoom);
			if (s == null) { s = "" + property.zoom; }
			if (s != null && s.isEmpty()) { s = "" + property.zoom; }
			try
			{
				property.zoom = Double.parseDouble(s);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			frame.setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			frame.reRenderCard();
			frame.hm.card.changed = true;
			frame.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
}
