package LegendaryCardMaker.CustomCardMaker.structure;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import LegendaryCardMaker.Icon;
import LegendaryCardMaker.Icon.ICON_TYPE;

public class ElementIcon extends CustomElement {
	
	public Icon defaultValue = Icon.valueOf("NONE");
	public boolean allowChange = false;
	public boolean optional = false;
	public int x;
	public int y;
	public int maxWidth = Integer.MAX_VALUE;
	public int maxHeight = Integer.MAX_VALUE;
	public boolean drawUnderlay;
	public int blurRadius;
	public boolean blurDouble;
	public int blurExpand;
	public Color blurColour;
	public ICON_TYPE iconType;
	
	//User values
	public Icon value;
	
	public void drawElement(Graphics2D g)
	{
		if (visible && getIconValue().getImagePath() != null)
		{
			BufferedImage bi = getIcon(getIconValue(), maxWidth, maxHeight);
			int xStart = x - (bi.getWidth() / 2);
	    	int yStart = y - (bi.getHeight() / 2);
	    	
	    	if (drawUnderlay)
	    	{
	    		drawUnderlay(bi, g, BufferedImage.TYPE_INT_ARGB, xStart, yStart, blurRadius, blurDouble, blurExpand, blurColour);
	    	}
	    	
	    	g.drawImage(bi, xStart, yStart, null);
		}
	}
	
	public Icon getIconValue()
	{
		if (value != null)
		{
			return value;
		}
		if (defaultValue != null)
		{
			return defaultValue;
		}
		return Icon.valueOf("NONE");
	}
	
	public String generateOutputString()
	{
		return generateOutputString(false);
	}
	
	public String generateOutputString(boolean fullExport)
	{
		String str = "";
		if (value != null)
		{
			str += "CUSTOMVALUE;" + name + ";value;" + value + "\n";
		}
		
		str += "CUSTOMVALUE;" + name + ";visible;" + visible + "\n";
		
		return str;
	}
}
