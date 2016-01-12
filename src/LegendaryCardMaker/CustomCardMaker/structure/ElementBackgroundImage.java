package LegendaryCardMaker.CustomCardMaker.structure;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;

import LegendaryCardMaker.CustomCardMaker.CustomCardMaker;

public class ElementBackgroundImage extends CustomElement {
	
	public String path;
	public boolean allowChange = false;
	public int x;
	public int y;
	public int maxWidth = Integer.MAX_VALUE;
	public int maxHeight = Integer.MAX_VALUE;
	public boolean zoomable;
	public boolean fullSize;
	public boolean templateFile;
	
	public double zoom = 1.0d;
	public int imageOffsetX = 0;
	public int imageOffsetY = 0;
	
	public void drawElement(Graphics2D g)
	{
		String file = path;
		if (templateFile)
		{
			file = CustomCardMaker.templateFolder + File.separator 
					+ template.templateName 
					+ File.separator + path; 
		}
		else
		{
			file = path;
		}
		
		
		if (file != null)
		{
			File fileValue = new File(file);
			if (fileValue.exists())
			{
				BufferedImage bi = null;
				if (fullSize)
				{
					bi = resizeImage(new ImageIcon(file), CustomCardMaker.cardWidth, CustomCardMaker.cardHeight);				
				}
				else
				{
					ImageIcon ii = new ImageIcon(file);
					bi = resizeImage(new ImageIcon(file), (int)(ii.getIconWidth() * zoom), (int)(ii.getIconHeight() * zoom));
				}
				g.drawImage(bi, x + imageOffsetX, y + imageOffsetY, null);
			}
		}
	}
	
	public String generateOutputString()
	{
		return generateOutputString(false);
	}
	
	public String generateOutputString(boolean fullExport)
	{
		String str = "";
		if (allowChange)
		{
			str += "CUSTOMVALUE;" + name + ";path;" + path + "\n";
		}
		
		str += "CUSTOMVALUE;" + name + ";visible;" + visible + "\n";
		
		str += "CUSTOMVALUE;" + name + ";zoom;" + zoom + "\n";
		str += "CUSTOMVALUE;" + name + ";imageOffsetX;" + imageOffsetX + "\n";
		str += "CUSTOMVALUE;" + name + ";imageOffsetY;" + imageOffsetY + "\n";
		
		return str;
	}
}
