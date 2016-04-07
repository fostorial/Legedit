package LegendaryCardMaker.CustomCardMaker.structure;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingUtilities;

import LegendaryCardMaker.Icon;
import LegendaryCardMaker.WordDefinition;
import LegendaryCardMaker.CustomCardMaker.ALIGNMENT;
import LegendaryCardMaker.CustomCardMaker.CustomCardMaker;
import LegendaryCardMaker.LegendaryHeroMaker.CardRarity;

public class ElementTextArea extends CustomElement {
	
	public String defaultValue;
	public ALIGNMENT alignmentHorizontal = ALIGNMENT.LEFT;
	public ALIGNMENT alignmentVertical = ALIGNMENT.TOP;
	public boolean allowChange;
	public Color colour;
	public int textSize = 27;
	public int textSizeBold = 27;
	public String fontName;
	public String fontNameBold;
	public int fontStyle;
	public String rectXArray;
	public String rectYArray;
	public boolean debug;
	
	public double textGapHeight = 0.6d;
	public double textDefaultGapHeight = 0.2d;
	public int textIconBlurRadius = 5;
	public boolean textIconBlurDouble = true;
	public int expandTextIcon = 0;
	
	private Polygon polygon = null;
	
	public String value;
	
	public String getValue()
	{
		if (value != null)
		{
			return value;
		}
		return defaultValue;
	}
	
	public void drawElement(Graphics2D g)
	{
		if (getValue() != null)
		{
			BufferedImage bi = new BufferedImage(CustomCardMaker.cardWidth, CustomCardMaker.cardHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = getGraphics(bi);
			g2 = setGraphicsHints(g2);
			
			if (debug)
			{
				g2.setColor(Color.LIGHT_GRAY);
				g2.fillPolygon(getPolygon());
			}
			
	    	g2.setColor(colour);
	    	try
	    	{
	    		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Swiss 721 Light Condensed.ttf"));
	    		font = font.deriveFont((float)textSize);
	    		if (fontName != null)
	    		{
	    			font = new Font(fontName, fontStyle, textSize);
	    		}
	    		g2.setFont(font);
	    		g2 = setGraphicsHints(g2);
	    		
	    		Font fontBold = Font.createFont(Font.TRUETYPE_FONT, new File("Swiss 721 Black Condensed.ttf"));
	    		fontBold = fontBold.deriveFont((float)textSizeBold);
	    		if (fontName != null && fontNameBold == null)
	    		{
	    			fontBold = new Font(fontName, Font.BOLD, textSizeBold);
	    		}
	    		if (fontNameBold != null)
	    		{
	    			fontBold = new Font(fontNameBold, Font.BOLD, textSizeBold);
	    		}
	    		
	    		FontMetrics metrics = g2.getFontMetrics(font);
	    		
	    		int x = -1;
	    		int y = -1;
	    		
	    		//TODO other alignments
	    		
	    		Point startPoint = getStartPosition();
	    		x = startPoint.x;
	    		y = startPoint.y + metrics.getAscent();
	    		
	            
	            List<WordDefinition> words = WordDefinition.getWordDefinitionList(getValue());

	    		for (WordDefinition wd : words)
	    		{
	    			String s = wd.word;
					String spaceChar = "";
					if (wd.space)
					{
						spaceChar = " ";
					}
					
	    			if (s.startsWith("<k>"))
	    			{
	    				g2.setFont(fontBold);
	    				metrics = g2.getFontMetrics(fontBold);
	    				s = s.replace("<k>", "");
	    				g2 = setGraphicsHints(g2);
	    				continue;
	    			}
	    			
	    			if (s.startsWith("<r>"))
	    			{
	    				g2.setFont(font);
	    				metrics = g2.getFontMetrics(font);
	    				s = s.replace("<r>", "");
	    				g2 = setGraphicsHints(g2);
	    				continue;
	    			}
	    			
	    			boolean gap = false;
	    			if (s.equals("<g>"))
	    			{
	    				gap = true;
	    			}
	    			
	    			Icon icon = isIcon(s);
	    			if (gap == true)
	    			{
	    				y += g2.getFontMetrics(font).getHeight() + getPercentage(g2.getFontMetrics(font).getHeight(), textGapHeight);
	    				x = getXStart(y);
	    			}
	    			else if (icon == null)
	    			{
	    				int stringLength = SwingUtilities.computeStringWidth(metrics, s);

	    				if (!getPolygon().contains(x+stringLength, y))
	    				{
	    					//TODO Restore for rare backing?
	    					/*
	    					if (x > xEnd)
	    					{
	    						xEnd = x;
	    					}
	    					*/
	    					y += g2.getFontMetrics(font).getHeight() + getPercentage(g2.getFontMetrics(font).getHeight(), textDefaultGapHeight);
	    					x = getXStart(y);
	    				}
	    				g2.drawString(s + " ", x, y);
	    				x += stringLength + SwingUtilities.computeStringWidth(metrics, spaceChar);
	    			}
	    			else if (icon != null)
	    			{
	    				BufferedImage i = getIconMaxHeight(icon, getPercentage(metrics.getHeight(), 1.2d));

	    				if (!getPolygon().contains(x + i.getWidth(), y))
	    				{
	    					//TODO Restore for rare backing?
	    					/*
	    					if (x > xEnd)
	    					{
	    						xEnd = x;
	    					}
	    					*/
	    					y += g2.getFontMetrics(font).getHeight() + getPercentage(g2.getFontMetrics(font).getHeight(), textDefaultGapHeight);
	    					x = getXStart(y);
	    				}
	    				
	    				int modifiedY = (int)(y - i.getHeight() + metrics.getDescent());
	    				
	    				//System.out.println(offsetRatio + " " + offset + " " + modifiedY + " " + i.getHeight() + " " + metrics.getHeight());
	    				
	    				if (icon.isUnderlayMinimized())
	    				{
	    					drawUnderlay(i, g2, BufferedImage.TYPE_INT_ARGB, x, modifiedY, textIconBlurRadius, textIconBlurDouble, expandTextIcon, Color.black);
	    				}
	    				g2.drawImage(i, x, modifiedY, null);
	    				x += i.getWidth() + SwingUtilities.computeStringWidth(metrics, spaceChar);
	    			}
	    		}
	    		
	    		//TODO Provide blur background option
	    		/*
	    		if (card.rarity.equals(CardRarity.RARE))
		    	{
	    			int padding = getPercentage(xEnd - xOrigin, rarePaddingRatio);
	    			yOrigin = yOrigin + (metrics.getHeight() / 3);
		    		BufferedImage blurBG = createRareBacking(xOrigin - padding, yOrigin - padding, xEnd + padding, y + padding);
		    		blurBG = makeTransparent(blurBG, 0.85d);
		    		blurBG = blurImage(blurBG, g2, rareBlurRadius);
		    		
		    		g.drawImage(blurBG, 0, 0, null);
		    	}
		    	*/
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    	}

	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
		}
	}
	
	public Polygon getPolygon()
	{
		if (polygon == null)
		{
			String[] xsplit = rectXArray.split(",");
			int[] xpoints = new int[xsplit.length];
			for (int i = 0; i< xsplit.length; i++)
			{
				xpoints[i] = Integer.parseInt(xsplit[i].trim());
			}
			
			String[] ysplit = rectYArray.split(",");
			int[] ypoints = new int[ysplit.length];
			for (int i = 0; i< ysplit.length; i++)
			{
				ypoints[i] = Integer.parseInt(ysplit[i].trim());
			}
			
			polygon = new Polygon(xpoints, ypoints, xpoints.length);
		}
		
		return polygon;
	}
	
	public int getXStart(int y)
	{
		if (alignmentHorizontal.equals(ALIGNMENT.LEFT))
		{
			for (int i = 0; i < CustomCardMaker.cardWidth; i++)
			{
				if (getPolygon().contains(i, y))
				{
					return i;
				}
			}
		}
		
		//TODO other alignments
		
		return -1;
	}
	
	public int getYStart(int x)
	{
		if (alignmentVertical.equals(ALIGNMENT.TOP))
		{
			for (int i = 0; i < CustomCardMaker.cardHeight; i++)
			{
				if (getPolygon().contains(x, i))
				{
					return i;
				}
			}
		}
		
		//TODO other alignments
		
		return -1;
	}
	
	public Point getStartPosition()
	{
		if (alignmentHorizontal.equals(ALIGNMENT.LEFT))
		{
			for (int i = 0; i < CustomCardMaker.cardWidth; i++)
			{
				int ypos = getYStart(i);
				if (ypos > -1)
				{
					return new Point(i, ypos);
				}
			}
		}
		
		//TODO other alignments
		return null;
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
		
		str += "CUSTOMVALUE;" + name + ";textSize;" + textSize + "\n";
		
		str += "CUSTOMVALUE;" + name + ";visible;" + visible + "\n";
		
		return str;
	}
}
