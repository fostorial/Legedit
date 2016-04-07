package LegendaryCardMaker.CustomCardMaker.structure;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;

import javax.swing.SwingUtilities;

import LegendaryCardMaker.CustomCardMaker.ALIGNMENT;
import LegendaryCardMaker.CustomCardMaker.CustomCardMaker;
import LegendaryCardMaker.LegendaryHeroMaker.MotionBlurOp;

public class ElementCardName extends CustomElement implements Cloneable {

	public enum HIGHLIGHT {BANNER, BLUR, NONE}
	
	public String defaultValue;
	public boolean includeSubname;
	public String subnameText;
	public boolean subnameEditable;
	public ALIGNMENT alignment = ALIGNMENT.CENTER;
	public boolean allowChange;
	public int x;
	public int y;
	public Color colour;
	public boolean drawUnderlay;
	public int blurRadius;
	public boolean blurDouble;
	public int blurExpand;
	public Color highlightColour;
	public int textSize;
	public int subnameSize;
	public String fontName;
	public int fontStyle;
	public boolean uppercase;
	public HIGHLIGHT highlight = HIGHLIGHT.NONE;
	public int subnameGap = -1;
	
	public String value;
	public String subnameValue;
	
	public void drawElement(Graphics2D g)
	{
		if (getValue() != null)
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
	    	font = Font.createFont(Font.TRUETYPE_FONT, new File("Percolator.otf"));
	        font = font.deriveFont((float)textSize);
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    		
	    		font = new Font("Percolator", Font.PLAIN, textSize);
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
	        int yModified = y + (int)((lm.getAscent() - lm.getDescent()));
	        
	        int bannerStart = (yModified - (int)lm.getAscent() + (int)lm.getDescent()) - getPercentage(CustomCardMaker.cardHeight, 0.01d);
	        int bannerEnd = yModified + getPercentage(CustomCardMaker.cardHeight, 0.015d);
	        
	        /*
	        g.setColor(Color.GREEN);
	        g.drawLine(0, yModified - (int)lm.getAscent() + (int)lm.getDescent(), 750, yModified - (int)lm.getAscent() + (int)lm.getDescent());
	        
	        g.setColor(Color.RED);
	        g.drawLine(0, yModified, 750, yModified);
	        
	        g.setColor(Color.BLUE);
	        g.drawLine(0, yModified + (int)lm.getDescent(), 750, yModified + (int)lm.getDescent());
	        */
	        
	        int subnameStartY = yModified + (int)lm.getDescent() + (int)(lm.getDescent() / 2);
	        if (subnameGap >= 0)
	        {
	        	subnameStartY = yModified + subnameGap;
	        }
	        int newxSubname = x;
	        int yModifiedSubname = subnameStartY;
	        Font fontSubname = null;
	        if (includeSubname)
	        {
	        	fontSubname = font.deriveFont((float)subnameSize);
		        
		        g2.setFont(fontSubname);
		        metrics = g2.getFontMetrics(fontSubname);
		        stringLength = SwingUtilities.computeStringWidth(metrics, getSubnameValueForDraw());
		        
		        if (alignment.equals(ALIGNMENT.CENTER))
		        {
		        	newxSubname = x - (stringLength / 2);
		        }
		        if (alignment.equals(ALIGNMENT.RIGHT))
		        {
		        	newxSubname = x - stringLength;
		        }
		        
		        lm = metrics.getLineMetrics(getValueForDraw(), g2);
		        yModifiedSubname = subnameStartY + (int)((lm.getAscent() - lm.getDescent()));
		        
		        g2.setFont(font);
		        metrics = g2.getFontMetrics(font);
		        
		        bannerEnd = yModifiedSubname + getPercentage(CustomCardMaker.cardHeight, 0.015d);
		        
	        }
	        
	        
	        if (highlight.equals(HIGHLIGHT.BLUR))
	        {
	        	g2 = setGraphicsHints(g2);
	        	
	        	g2.drawString(getValueForDraw(), newx, yModified);
	        	
	        	if (includeSubname)
	        	{
	        		g2.setFont(fontSubname);
			        metrics = g2.getFontMetrics(fontSubname);
			        
			        g2 = setGraphicsHints(g2);
			        
	        		g2.drawString(getSubnameValueForDraw(), newxSubname, yModifiedSubname);
	        		
	        		g2.setFont(font);
			        metrics = g2.getFontMetrics(font);
			        
			        g2 = setGraphicsHints(g2);
	        	}
	        	
		    	drawUnderlay(bi, g2, BufferedImage.TYPE_INT_ARGB, 0, 0, blurRadius, blurDouble, blurExpand, highlightColour);
	        }
	        
	        if (highlight.equals(HIGHLIGHT.BANNER))
	        {
	        	BufferedImage bi2 = new BufferedImage(CustomCardMaker.cardWidth, CustomCardMaker.cardHeight, BufferedImage.TYPE_INT_ARGB);
		        Graphics g3 = bi2.getGraphics();
		        
	        	int bannerHeight = bannerEnd - bannerStart;
				g3.setColor(highlightColour);
				g3.fillRect((CustomCardMaker.cardWidth / 2), bannerStart - getPercentage(CustomCardMaker.cardHeight, 0.005d), getPercentage(CustomCardMaker.cardWidth, 0.15d), bannerHeight);
		    	
				MotionBlurOp op = new MotionBlurOp();
				op.setDistance(200f);
	        	bi2 = op.filter(bi2, null);
	        	
	        	makeTransparent(bi2, 0.8d);
				
				g2.drawImage(bi2, 0, 0, null);
				
				//Flip and re-draw image
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-bi2.getWidth(null), 0);
				AffineTransformOp aop = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				bi2 = aop.filter(bi2, null);
				
				g2.drawImage(bi2, 0, 0, null);
	        }
	    	
	        g2 = setGraphicsHints(g2);
	        
	    	g2.drawString(getValueForDraw(), newx, yModified);
	    	
	    	if (includeSubname)
        	{
	    		g2.setFont(fontSubname);
		        metrics = g2.getFontMetrics(fontSubname);
		        
		        g2 = setGraphicsHints(g2);
		        
        		g2.drawString(getSubnameValueForDraw(), newxSubname, yModifiedSubname);
        		
        		g2.setFont(font);
		        metrics = g2.getFontMetrics(font);
		        
		        g2 = setGraphicsHints(g2);
        	}

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
	
	public String getSubnameValue()
	{
		if (subnameValue != null)
		{
			return subnameValue;
		}
		return subnameText;
	}
	
	private String getSubnameValueForDraw()
	{
		if (uppercase && getSubnameValue() != null)
		{
			return getSubnameValue().toUpperCase();
		}
		return getSubnameValue();
	}
	
	private BufferedImage makeTransparent(BufferedImage bi, double percent)
	{
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                Color originalColor = new Color(bi.getRGB(xx, yy), true);
                if (originalColor.getAlpha() > 0) {
                    int col = (getPercentage(originalColor.getAlpha(), percent) << 24) | (originalColor.getRed() << 16) | (originalColor.getGreen() << 8) | originalColor.getBlue();
                    bi.setRGB(xx, yy, col);
                }
            }
        }
		
		return bi;
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
		if (subnameValue != null)
		{
			str += "CUSTOMVALUE;" + name + ";subnameValue;" + subnameValue + "\n";
		}
		str += "CUSTOMVALUE;" + name + ";visible;" + visible + "\n";
		return str;
	}
}
