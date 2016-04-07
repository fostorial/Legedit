package LegendaryCardMaker.CustomCardMaker.structure;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.SwingUtilities;

import LegendaryCardMaker.CustomCardMaker.ALIGNMENT;
import LegendaryCardMaker.CustomCardMaker.CustomCardMaker;

public class ElementText extends CustomElement {

	public String defaultValue;
	public CustomElement linkedElement;
	public ALIGNMENT alignment = ALIGNMENT.CENTER;
	public boolean allowChange;
	public int x;
	public int y;
	public Color colour;
	public boolean drawUnderlay;
	public int blurRadius;
	public boolean blurDouble;
	public int blurExpand;
	public Color blurColour;
	public int textSize;
	public String fontName;
	public int fontStyle;
	public boolean uppercase;
	
	public String value;
	
	public void drawElement(Graphics2D g)
	{
		if (getValue() != null && visible == true)
		{
			BufferedImage bi = new BufferedImage(CustomCardMaker.cardWidth, CustomCardMaker.cardHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = getGraphics(bi);
			g2 = setGraphicsHints(g2);
			
			if (colour != null)
			{
				g2.setColor(colour);
			}
			
			Font font = null;
	    	try
	    	{
	    	font = Font.createFont(Font.TRUETYPE_FONT, new File("Sylfaen.ttf"));
	        font = font.deriveFont((float)textSize);
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    		
	    		font = new Font("Sylfaen", Font.PLAIN, textSize);
	    	}
	    	if (fontName != null)
    		{
    			font = new Font(fontName, fontStyle, textSize);
    		}
	        g2.setFont(font);
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, getValueForDraw());
	        
	        g2 = setGraphicsHints(g2);
	        
	        int newx = x;
	        if (alignment.equals(ALIGNMENT.CENTER))
	        {
	        	newx = x - (stringLength / 2);
	        }
	        if (alignment.equals(ALIGNMENT.RIGHT))
	        {
	        	newx = x - stringLength;
	        }
	        
	        LineMetrics lm = metrics.getLineMetrics(getValueForDraw(), g2);
	        int yModified = y + (int)((lm.getAscent() - lm.getDescent()) / 2);
	        
	        g2.drawString(getValueForDraw(), newx, yModified);
	    	if (drawUnderlay)
	    	{
	    		drawUnderlay(bi, g2, BufferedImage.TYPE_INT_ARGB, 0, 0, blurRadius, blurDouble, blurExpand, blurColour);
	    	}
	    	
	    	g2.drawString(getValueForDraw(), newx, yModified);

	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
		}
	}
	
	public String getValue()
	{
		if (value != null)
		{
			return value;
		}
		return defaultValue;
	}
	
	private String getValueForDraw()
	{
		if (uppercase && getValue() != null)
		{
			return getValue().toUpperCase();
		}
		return getValue();
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
