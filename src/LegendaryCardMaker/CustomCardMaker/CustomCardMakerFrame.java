package LegendaryCardMaker.CustomCardMaker;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import LegendaryCardMaker.CustomCardMaker.structure.CustomCard;
import LegendaryCardMaker.CustomCardMaker.structure.CustomElement;
import LegendaryCardMaker.CustomCardMaker.structure.ElementBackgroundImage;

public class CustomCardMakerFrame extends JDialog {
	
	public CustomCardMaker hm;
	JLabel label;
	JLabel backLabel;
	JLayeredPane layers;
	
	//JLabel imageLabel;
	
	CustomCardMakerToolbar hmt;
	
	public CustomCardMakerFrame(CustomCard hc)
	{	
		hm = new CustomCardMaker();
		
		hm.setCard(hc);
		
		setTitle("Card Editor: " + hc.template.templateDisplayName);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.setModal(true);
		
		backLabel = new JLabel();
		backLabel.setOpaque(true);
		
		BufferedImage bi = hm.generateCard();
		ImageIcon icon = new ImageIcon(resizeImage(new ImageIcon(bi), 0.5d));
		
		label = new JLabel(icon);
		label.setOpaque(false);
		
		label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		backLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		
		
		layers = new JLayeredPane();
		layers.setLayout(null);
		layers.add(label, JLayeredPane.PALETTE_LAYER, -1);
		
		layers.add(backLabel, JLayeredPane.DEFAULT_LAYER, -1);
		layers.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		layers.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		
		layers.addMouseListener(new MouseListener() {
			
			int startX = 0;
			int startY = 0;
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				ElementBackgroundImage bgi = null;
				
				for (CustomElement el : hm.card.template.elements)
				{
					if (el instanceof ElementBackgroundImage)
					{
						bgi = (ElementBackgroundImage)el;
					}
				}
				
				if (bgi != null)
				{
					bgi.imageOffsetX += e.getX() - startX;
					bgi.imageOffsetY += e.getY() - startY;
				}
				
				/*
				System.out.println("Released: " + hm.card.imageOffsetX + ":" + hm.card.imageOffsetY);
				*/
				
				reRenderCard();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				startX = e.getX();
				startY = e.getY();
				
				System.out.println("Pressed: " + startX + ":" + startY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(layers);
		this.add(scroll);
		
		int scrollBarSize = ((Integer)UIManager.get("ScrollBar.width")).intValue();
		setSize((int)(hm.cardWidth / 1.8), (int)(hm.cardHeight / 1.8));
		//setSize(icon.getIconWidth(), icon.getIconHeight());
		
		hmt = new CustomCardMakerToolbar(hm, this);
		this.setJMenuBar(hmt);
		
		setVisible(true);
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
	
	public BufferedImage resizeImage(ImageIcon imageIcon, int width, int height)
	{
	        int type = BufferedImage.TYPE_INT_ARGB;
	        
	        BufferedImage image = new BufferedImage(width, height, type);
	        Graphics g = image.getGraphics();
	        
	        g.drawImage(imageIcon.getImage(), 0, 0, width, height, 
	        		0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
	        
	        g.dispose();
	        
	        return image;
	}
	
	public void reRenderCard()
	{
		BufferedImage bi = hm.generateCard();
		ImageIcon icon = new ImageIcon(resizeImage(new ImageIcon(bi), 0.5d));
		//ImageIcon icon = new ImageIcon(new ImageIcon(bi));
		label.setIcon(icon);
	}
}
