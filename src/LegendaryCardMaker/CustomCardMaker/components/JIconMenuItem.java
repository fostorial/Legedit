package LegendaryCardMaker.CustomCardMaker.components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;

import LegendaryCardMaker.Icon;

public class JIconMenuItem extends JCheckBoxMenuItem{

	public Icon icon;
	
	public JIconMenuItem(Icon i)
	{
		icon = i;
	
		super.setText(i.toString());
		
		if (i.getImagePath() != null)
		{
			super.setIcon(new ImageIcon(getIconMaxHeight(i, 20)));
		}
	}
	
	public BufferedImage getIconMaxHeight(Icon icon, int maxHeight)
	{
		ImageIcon ii = new ImageIcon(icon.getImagePath());
		double r = (double)((double)maxHeight / (double)ii.getIconHeight());
		
		return resizeImage(ii, r);
	}
	
	public BufferedImage resizeImage(ImageIcon imageIcon, double scale)
	{
			int w = (int)(imageIcon.getIconWidth() * scale);
	        int h = (int)(imageIcon.getIconHeight() * scale);
	        int type = BufferedImage.TYPE_INT_ARGB;
	        
	        if (w <= 0 || h <= 0)
	        {
	        	return null;
	        }
	        
	        BufferedImage image = new BufferedImage(w, h, type);
	        Graphics g = image.getGraphics();
	        
	        g.drawImage(imageIcon.getImage(), 0, 0, w, h, 
	        		0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
	        
	        g.dispose();
	        
	        return image;
	}
}
