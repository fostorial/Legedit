package LegendaryCardMaker.LegendarySchemeMaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class SchemeMakerFrame extends JDialog {
	
	SchemeMaker hm;
	JLabel label;
	JLabel backLabel;
	JLayeredPane layers;
	
	//JLabel imageLabel;
	
	SchemeMakerToolbar hmt;
	
	public SchemeMakerFrame(SchemeCard hc)
	{
		hm = new SchemeMaker();
		
		if (hc.cardNameSize > 0)
			hm.cardNameSize = hc.cardNameSize;
		
		if (hc.subCategorySize > 0)
			hm.subCategorySize = hc.subCategorySize;
		
		if (hc.cardTextSize > 0)
			hm.textSize = hc.cardTextSize;
		
		hm.setCard(hc);
		if (hc == null)
		{
			hm.populateBlankSchemeCard();
		}
		
		setTitle("Card Editor: " + hc.name);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.setModal(true);
		
		backLabel = new JLabel();
		backLabel.setOpaque(true);
		//backLabel.setBackground(Color.BLACK);
		
		/*
		if (hc.imagePath != null)
		{
			ImageIcon ii = new ImageIcon(hc.imagePath);
			imageLabel = new JLabel(ii);
		}
		else
		{
			imageLabel = new JLabel();
		}
		imageLabel.setOpaque(true);
		*/
		
		//hm.populateSchemeCard();
		BufferedImage bi = hm.generateCard();
		ImageIcon icon = new ImageIcon(resizeImage(new ImageIcon(bi), 0.5d));
		//ImageIcon icon = new ImageIcon(new ImageIcon(bi));
		label = new JLabel(icon);
		label.setOpaque(false);
		
		label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		backLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		//imageLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		
		layers = new JLayeredPane();
		layers.setLayout(null);
		layers.add(label, JLayeredPane.PALETTE_LAYER, -1);
		//layers.add(imageLabel, new Integer(50), -1);
		layers.add(backLabel, JLayeredPane.DEFAULT_LAYER, -1);
		layers.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		layers.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		
		layers.addMouseListener(new MouseListener() {
			
			int startX = 0;
			int startY = 0;
			
			@Override
			public void mouseReleased(MouseEvent e) {
				hm.card.imageOffsetX += e.getX() - startX;
				hm.card.imageOffsetY += e.getY() - startY;
				
				System.out.println("Released: " + hm.card.imageOffsetX + ":" + hm.card.imageOffsetY);
				
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
		
		hmt = new SchemeMakerToolbar(hm, this);
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
