package LegendaryCardMaker.LegendaryHeroMaker;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.HashMap;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import org.w3c.dom.Element;

import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryCardMaker;

public class HeroMaker {
	
	public String exportFolder = "cardCreator";
	String templateFolder = "legendary" + File.separator + "templates" + File.separator + LegendaryCardMaker.expansionStyle;
	
	// 2.5 by 3.5 inches - Poker Size
	int cardWidth = 750;
	int cardHeight = 1050;
	int dpi = 300;
	
	boolean exportImage = false;
	boolean exportToPNG = true;
	
	int teamIconX = 54;
	int teamIconY = 60;
	int teamMaxWidth = 54;
	int teamMaxHeight = 54;
	boolean includeBlurredBGTeam = true;
	int expandTeam = 1;
	int teamBlurRadius = 5;
	boolean teamBlurDouble = true;
	
	int powerIconX = 54;
	int powerIconY = 120;
	int powerMaxWidth = 54;
	int powerMaxHeight = 54;
	boolean includeBlurredBGPower = true;
	int expandPower = 1;
	int powerBlurRadius = 5;
	boolean powerBlurDouble = true;
	
	public int cardNameSize = 40;
	int cardNameMinSize = 30;
	int cardNameY = 50;
	Color cardNameColor = new Color (255,186,20);
	boolean includeBlurredBGName = true;
	int expandCardName = 2;
	int cardNameBlurRadius = 5;
	boolean cardNameBlurDouble = true;
	
	public int heroNameSize = 33;
	int heroNameMinSize = 30;
	int heroNameY = 85;
	Color heroNameColor = new Color (255,186,20);
	boolean includeBlurredBGHeroName = true;
	int expandHeroName = 2;
	int heroNameBlurRadius = 5;
	boolean heroNameBlurDouble = true;
	
	int costSize = 120;
	int costX = 635;
	int costY = 993;
	Color costColor = Color.WHITE;
	boolean includeBlurredBGCost = true;
	int expandCost = 5;
	int costBlurRadius = 5;
	boolean costBlurDouble = false;
	
	int recruitSize = 80;
	int recruitX = 98;
	int recruitY = 791;
	Color recruitColor = Color.WHITE;
	boolean includeBlurredBGRecruit = true;
	int expandRecruit = 3;
	int recruitBlurRadius = 3;
	boolean recruitBlurDouble = false;
	
	int attackSize = 80;
	int attackX = 87;
	int attackY = 942;
	Color attackColor = Color.WHITE;
	boolean includeBlurredBGAttack = true;
	int expandAttack = 3;
	int attackBlurRadius = 3;
	boolean attackBlurDouble = false;
	HashMap<String, Integer> costOffsets = new HashMap<String, Integer>();
	
	public int textSize = 27;
	int textX = 154;
	int textY = 805;
	Color textColor = Color.BLACK;
	boolean includeBlurredBGText = false;
	int expandText = 0;
	int textBlurRadius = 0;
	boolean textBlurDouble = false;
	double textIconHeight = 1.2d;
	double textGapHeight = 0.6d;
	double textDefaultGapHeight = 0.2d;
	int expandTextIcon = 0;
	int textIconBlurRadius = 5;
	boolean textIconBlurDouble = true;
	double rarePaddingRatio = 0.06d;
	int rareBlurRadius = 25;
	public int textStartOffset = 0;
	
	static HeroCard card;
	
	public BufferedWriter bwErr = null;

	public HeroMaker()
	{
		//populateHeroCard();
		//generateCard();
		
		costOffsets.put("4", -8);
		costOffsets.put("6", -5);
		costOffsets.put("X", -3);
	}
	
	/*
	public static void main(String[] args)
	{
		new HeroMaker();
	}
	*/
	
	public void setCard(HeroCard c)
	{
		card = c;
	}
	
	public void populateHeroCard()
	{
		card = new HeroCard();
		card.heroName = "Swoop";
		card.name = "The Friendly One";
		card.rarity = CardRarity.COMMON;
		card.cardTeam = Icon.valueOf("AUTOBOT");
		card.cardPower = Icon.valueOf("INSTINCT");
		card.cost = "3";
		card.attack = "2";
		card.recruit = "3";
		card.abilityText = "<k>Transform <g> <r>Select a player. That player must play an <AUTOBOT> hero from their hand or discard pile that has a <COST> of 5 or less. You gain all effects of that card as if you played it. That player discards the card after this turn.";
	}
	
	public void populateBlankHeroCard()
	{
		card = new HeroCard();
		card.heroName = "Hero";
		card.name = "Card Name";
		card.rarity = CardRarity.COMMON;
		card.cardTeam = Icon.valueOf("NONE");
		card.cardPower = Icon.valueOf("NONE");
		card.cost = "X";
		card.attack = null;
		card.recruit = null;
		card.abilityText = "Card text";
	}
	
	public static HeroCard getBlankHeroCard()
	{
		HeroCard card = new HeroCard();
		card.heroName = "Hero";
		card.name = "Card Name";
		card.rarity = CardRarity.COMMON;
		card.cardTeam = Icon.valueOf("NONE");
		card.cardPower = Icon.valueOf("NONE");
		card.cost = "X";
		card.attack = null;
		card.recruit = null;
		card.abilityText = "Card text";
		return card;
	}
	
	public BufferedImage generateCard()
	{
		int type = BufferedImage.TYPE_INT_RGB;
		if (exportToPNG)
		{
			type = BufferedImage.TYPE_INT_ARGB;	
		}
	    BufferedImage image = new BufferedImage(cardWidth, cardHeight, type);
	    Graphics2D g = (Graphics2D)image.getGraphics();
	    
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				
		//g.setRenderingHint(
		 //       RenderingHints.KEY_TEXT_ANTIALIASING,
		  //      RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
	    
	    if (card.imagePath != null)
	    {
	    	BufferedImage bi = resizeImage(new ImageIcon(card.imagePath), card.imageZoom);
	    	g.drawImage(bi, card.imageOffsetX, card.imageOffsetY, null);
	    }

	    // Template back
	    if (card.rarity != null && card.rarity.equals(CardRarity.COMMON))
	    {
	    	ImageIcon ii = null;
	    	if (card.cardPower == null || (card.cardPower != null && card.cardPower.equals(Icon.valueOf("NONE"))))
	    	{
	    		ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "back_grey.png");
		    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	}
	    	else
	    	{
	    		ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "back_" + card.cardPower.toString() + ".png");
		    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	}
	    	
	    	ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "back_text.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    }
	    if (card.rarity != null && card.rarity.equals(CardRarity.UNCOMMON))
	    {
	    	ImageIcon ii = null;
	    	if (card.cardPower != null && card.cardPower.equals(Icon.valueOf("NONE")))
	    	{
	    		ii = new ImageIcon(templateFolder + File.separator + "hero_uncommon" + File.separator + "back_grey.png");
		    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	}
	    	else
	    	{
	    		ii = new ImageIcon(templateFolder + File.separator + "hero_uncommon" + File.separator + "back_" + card.cardPower.toString() + ".png");
		    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	}
	    	
	    	ii = new ImageIcon(templateFolder + File.separator + "hero_uncommon" + File.separator + "back_text.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    }
	    if (card.rarity != null && card.rarity.equals(CardRarity.RARE))
	    {	
	    	
	    }
	    
	    // Card Team
	    if (card.cardTeam != null && card.cardTeam.getImagePath() != null)
	    {
	    	BufferedImage bi = getIcon(card.cardTeam, teamMaxWidth, teamMaxHeight);
	    	int x = teamIconX - (bi.getWidth() / 2);
	    	int y = teamIconY - (bi.getWidth() / 2);
	    	
	    	if (includeBlurredBGTeam)
	    	{
	    		drawUnderlay(bi, g, type, x, y, teamBlurRadius, teamBlurDouble, expandTeam);
	    	}
	    	
	    	g.drawImage(bi, x, y, null);
	    }
	    
	    // Card Power
	    if (card.cardPower != null && card.cardPower.getImagePath() != null)
	    {
	    	BufferedImage bi = getIcon(card.cardPower, powerMaxWidth, powerMaxHeight);
	    	int x = powerIconX - (bi.getWidth() / 2);
	    	int y = powerIconY - (bi.getWidth() / 2);
	    	
	    	if (includeBlurredBGPower)
	    	{
	    		drawUnderlay(bi, g, type, x, y, powerBlurRadius, powerBlurDouble, expandPower);
	    	}

	    	g.drawImage(bi, x, y, null);
	    }
	    
	    // Card Name
	    if (card.name != null)
	    {
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics g2 = bi.getGraphics();
	        
	    	g2.setColor(cardNameColor);
	        //Font font = new Font("Percolator", Font.PLAIN, cardNameSize);
	    	Font font = null;
	    	try
	    	{
	    	font = Font.createFont(Font.TRUETYPE_FONT, new File("Percolator.otf"));
	        font = font.deriveFont((float)cardNameSize);
	        g2.setFont(font);
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    		
	    		font = new Font("Percolator", Font.PLAIN, cardNameSize);
	    		g2.setFont(font);
	    	}
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.name.toUpperCase());
	        int x = (cardWidth / 2) - (stringLength / 2);
	        
	        g2.drawString(card.name.toUpperCase(), x, cardNameY);
	    	if (includeBlurredBGName)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, cardNameBlurRadius, cardNameBlurDouble, expandCardName);
	    	}
	    	
	    	g2.drawString(card.name.toUpperCase(), x, cardNameY);
	    	
	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
	    }
	    
	    // Hero Name
	    if (card.heroName != null)
	    {
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics g2 = bi.getGraphics();
	        
	    	g2.setColor(heroNameColor);
	        //Font font = new Font("Percolator", Font.PLAIN, heroNameSize);
	    	Font font = null;
	    	try
	    	{
	    	font = Font.createFont(Font.TRUETYPE_FONT, new File("Percolator.otf"));
	        font = font.deriveFont((float)heroNameSize);
	        g2.setFont(font);
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    		
	    		font = new Font("Percolator", Font.PLAIN, heroNameSize);
	    		g2.setFont(font);
	    	}
	        g2.setFont(font);
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.heroName.toUpperCase());
	        int x = (cardWidth / 2) - (stringLength / 2);
	        
	        g2.drawString(card.heroName.toUpperCase(), x, heroNameY);
	    	if (includeBlurredBGHeroName)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, heroNameBlurRadius, heroNameBlurDouble, expandHeroName);
	    	}
	    	
	    	g2.drawString(card.heroName.toUpperCase(), x, heroNameY);
	    	
	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
	    }
	    
	    // Recruit
	    if (card.recruit != null)
	    {
	    	ImageIcon ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "attr_recruit.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics g2 = bi.getGraphics();
	        
	    	g2.setColor(recruitColor);
	        //Font font = new Font("Sylfaen", Font.PLAIN, recruitSize);
	    	Font font = null;
	    	try
	    	{
	    	font = Font.createFont(Font.TRUETYPE_FONT, new File("Sylfaen.ttf"));
	        font = font.deriveFont((float)recruitSize);
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    		
	    		font = new Font("Sylfaen", Font.PLAIN, recruitSize);
	    	}
	        g2.setFont(font);
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.recruit.toUpperCase());
	        int x = recruitX - (stringLength / 2);
	        
	        g2.drawString(card.recruit.toUpperCase(), x, recruitY);
	    	if (includeBlurredBGRecruit)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, recruitBlurRadius, recruitBlurDouble, expandRecruit);
	    	}
	    	
	    	g2.drawString(card.recruit.toUpperCase(), x, recruitY);
	    	
	    	//bi = blurImage(bi, g2, 2);
	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
	    }
	    
	    // Attack
	    if (card.attack != null)
	    {
	    	ImageIcon ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "attr_attack.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics g2 = bi.getGraphics();
	        
	    	g2.setColor(attackColor);
	        //Font font = new Font("Sylfaen", Font.PLAIN, attackSize);
	    	Font font = null;
	    	try
	    	{
	    	font = Font.createFont(Font.TRUETYPE_FONT, new File("Sylfaen.ttf"));
	        font = font.deriveFont((float)attackSize);
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    		
	    		font = new Font("Sylfaen", Font.PLAIN, attackSize);
	    	}
	        g2.setFont(font);
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.attack.toUpperCase());
	        int x = attackX - (stringLength / 2);
	        
	        g2.drawString(card.attack.toUpperCase(), x, attackY);
	    	if (includeBlurredBGAttack)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, attackBlurRadius, attackBlurDouble, expandAttack);
	    	}
	    	
	    	g2.drawString(card.attack.toUpperCase(), x, attackY);
	    	
	    	//bi = blurImage(bi, g2, 2);
	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
	    }
	    
	    // Card Text
	    if (card.abilityText != null)
	    {
	    	ImageIcon ii = null;
	    	BufferedImage overlay = null;
	    	
	    	if (card.rarity != null && card.rarity.equals(CardRarity.COMMON))
		    {
	    		ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "back_text_overlay.png");
		    	overlay = resizeImage(ii, cardWidth, cardHeight);
		    	//g.drawImage(overlay, 0, 0, null);
		    }
	    	else if (card.rarity != null && card.rarity.equals(CardRarity.UNCOMMON))
		    {
	    		ii = new ImageIcon(templateFolder + File.separator + "hero_uncommon" + File.separator + "back_text_overlay.png");
		    	overlay = resizeImage(ii, cardWidth, cardHeight);
		    	//g.drawImage(overlay, 0, 0, null);
		    }
	    	else if (card.rarity != null && card.rarity.equals(CardRarity.RARE))
		    {
	    		ii = new ImageIcon(templateFolder + File.separator + "hero_rare" + File.separator + "back_text_overlay.png");
		    	overlay = resizeImage(ii, cardWidth, cardHeight);
		    	//g.drawImage(overlay, 0, 0, null);
		    }
	    	
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics g2 = bi.getGraphics();
	        
	    	g2.setColor(textColor);
	    	try
	    	{
	    		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Swiss 721 Light Condensed.ttf"));
	    		font = font.deriveFont((float)textSize);
	    		g2.setFont(font);
	    		
	    		Font fontBold = Font.createFont(Font.TRUETYPE_FONT, new File("Swiss 721 Black Condensed.ttf"));
	    		fontBold = fontBold.deriveFont((float)textSize);
	    		
	    		FontMetrics metrics = g2.getFontMetrics(font);
	    		
	    		int x = textX;
	    		int y = textY;
	    		int xOrigin = textX;
	    		int xEnd = textX;
	    		int yOrigin = textY;
	    		
	    		//find first overlay pixel
	    		int width = overlay.getWidth();
	            int height = overlay.getHeight();
	            boolean done = false;
	            
	            for (int xx = 0; xx < width; xx++) {
	                for (int yy = 0; yy < height; yy++) {
	                    Color originalColor = new Color(overlay.getRGB(xx, yy), true);
	                    if (originalColor.getAlpha() > 0 && done == false) {
	                        x = xx;
	                        y = yy + metrics.getHeight();
	                        xOrigin = xx;
	                        yOrigin = yy;
	                        done = true;
	                    }
	                }
	            }
	            
	            //apply modifier
	            yOrigin = yOrigin + textStartOffset;
	            y = y + textStartOffset;
	            
	            //x end works from right to left but top down to 
	            done = false;
	            for (int xx = width - 1; xx >= 0; xx--) {
	                for (int yy = 0; yy < height; yy++) {
	                    Color originalColor = new Color(overlay.getRGB(xx, yy), true);
	                    if (originalColor.getAlpha() > 0 && done == false) {
	                        xEnd = xx;
	                        done = true;
	                    }
	                }
	            }
	    		
	    		String[] words = card.abilityText.split(" ");
	    		for (String s : words)
	    		{
	    			if (s.startsWith("<k>"))
	    			{
	    				g2.setFont(fontBold);
	    				metrics = g2.getFontMetrics(fontBold);
	    				s = s.replace("<k>", "");
	    			}
	    			
	    			if (s.startsWith("<r>"))
	    			{
	    				g2.setFont(font);
	    				metrics = g2.getFontMetrics(font);
	    				s = s.replace("<r>", "");
	    			}
	    			
	    			boolean gap = false;
	    			if (s.equals("<g>"))
	    			{
	    				gap = true;
	    			}
	    			
	    			Icon icon = isIcon(s);
	    			if (gap == true)
	    			{
	    				x = xOrigin;
	    				y += g2.getFontMetrics(font).getHeight() + getPercentage(g2.getFontMetrics(font).getHeight(), textGapHeight);
	    			}
	    			else if (icon == null)
	    			{
	    				int stringLength = SwingUtilities.computeStringWidth(metrics, s);
	    				Color color = null;
	    				try
	    				{
	    					color = new Color(overlay.getRGB(x + stringLength, y), true);
	    				}
	    				catch (ArrayIndexOutOfBoundsException e)
	    				{
	    					color = new Color(overlay.getRGB(0, 0), true);
	    				}
	    				if (color.getAlpha() == 0)
	    				{
	    					if (x > xEnd)
	    					{
	    						xEnd = x;
	    					}
	    					x = xOrigin;
	    					y += g2.getFontMetrics(font).getHeight() + getPercentage(g2.getFontMetrics(font).getHeight(), textDefaultGapHeight);
	    				}
	    				g2.drawString(s + " ", x, y);
	    				x += stringLength + SwingUtilities.computeStringWidth(metrics, " ");
	    			}
	    			else if (icon != null)
	    			{
	    				BufferedImage i = getIconMaxHeight(icon, getPercentage(metrics.getHeight(), textIconHeight));
	    				
	    				Color color = new Color(overlay.getRGB(x + i.getWidth(), y + i.getHeight()), true);
	    				if (color.getAlpha() == 0)
	    				{
	    					if (x > xEnd)
	    					{
	    						xEnd = x;
	    					}
	    					x = xOrigin;
	    					y += g2.getFontMetrics(font).getHeight() + getPercentage(g2.getFontMetrics(font).getHeight(), textDefaultGapHeight);
	    				}
	    				
	    				double offsetRatio = ((textIconHeight - 1d));
	    				int offset = getPercentage(i.getHeight(), offsetRatio);
	    				int modifiedY = (int)(y - i.getHeight() + offset);
	    				
	    				//System.out.println(offsetRatio + " " + offset + " " + modifiedY + " " + i.getHeight() + " " + metrics.getHeight());
	    				
	    				if (icon.isUnderlayMinimized())
	    				{
	    					drawUnderlay(i, g2, BufferedImage.TYPE_INT_ARGB, x, modifiedY, textIconBlurRadius, textIconBlurDouble, expandTextIcon);
	    				}
	    				g2.drawImage(i, x, modifiedY, null);
	    				x += i.getWidth() + SwingUtilities.computeStringWidth(metrics, " ");
	    			}
	    		}
	    		
	    		if (card.rarity.equals(CardRarity.RARE))
		    	{
	    			int padding = getPercentage(xEnd - xOrigin, rarePaddingRatio);
	    			yOrigin = yOrigin + (metrics.getHeight() / 3);
		    		BufferedImage blurBG = createRareBacking(xOrigin - padding, yOrigin - padding, xEnd + padding, y + padding);
		    		blurBG = makeTransparent(blurBG, 0.85d);
		    		blurBG = blurImage(blurBG, g2, rareBlurRadius);
		    		
		    		g.drawImage(blurBG, 0, 0, null);
		    	}
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    		
	    		if (bwErr != null)
	    		{
	    			try
	    			{
	    				bwErr.write(e.getMessage());
					   for (StackTraceElement s : e.getStackTrace())
					   {
						   bwErr.write(s.toString());
					   }
	    			}
	    			catch (Exception ex)
	    			{
	    				ex.printStackTrace();
	    			}
	    		}
	    	}
	        
	        g.drawImage(bi, 0, 0, null);
	    }
	    
	    // Cost
	    if (card.cost != null)
	    {
	    	ImageIcon ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "back_cost.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics g2 = bi.getGraphics();
	        
	    	g2.setColor(costColor);
	        //Font font = new Font("Sylfaen", Font.PLAIN, costSize);
	    	Font font = null;
	    	try
	    	{
	    	font = Font.createFont(Font.TRUETYPE_FONT, new File("Sylfaen.ttf"));
	        font = font.deriveFont((float)costSize);
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    		
	    		font = new Font("Sylfaen", Font.PLAIN, costSize);
	    	}
	        g2.setFont(font);
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.cost.toUpperCase());
	        
	        Integer offset = costOffsets.get("");
	        int primitiveOffset = 0; 
	        for (char c : card.cost.toCharArray())
	        {
	        	offset = costOffsets.get("" + c);
	        	if (offset != null) { primitiveOffset += offset.intValue(); }
	        }
	        int x = costX - (stringLength / 2) + primitiveOffset;
	        //System.out.println("cost x =" + x);
	        
	        g2.drawString(card.cost.toUpperCase(), x, costY);
	    	if (includeBlurredBGCost)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, costBlurRadius, costBlurDouble, expandCost);
	    	}
	    	
	    	g2.drawString(card.cost.toUpperCase(), x, costY);
	    	
	    	bi = blurImage(bi, g2, 2);
	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
	    }
	    
	    if (exportImage)
	    {
	    	exportImage(image);
	    }
	    
		
		g.dispose();
		
		return image;
	}
	
	public void exportImage(BufferedImage image)
	{
		try
		{
			//ImageIO.write(image, "jpg", newFile);
			if (exportToPNG)
			{
				File newFile = new File(exportFolder + File.separator + card.getCardName(exportFolder) + ".png");
				exportToPNG(image, newFile);
			}
			else
			{
				File newFile = new File(exportFolder + File.separator + card.getCardName(exportFolder) + ".jpg");
				exportToJPEG(image, newFile);
			}
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public void exportToJPEG(BufferedImage image, File newFile) throws Exception
	{
		System.out.println("Exporting: " + newFile.getName());
		
		File dir = new File(exportFolder);
		dir.mkdirs();
		
		FileOutputStream fos = new FileOutputStream(newFile);
		ImageWriter imageWriter = ImageIO.getImageWritersBySuffix("jpeg").next();
		//JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpeg").next();
	    ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
	    imageWriter.setOutput(ios);
	 
	    //and metadata
	    IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image), null);
	    
	    /*
	  //new metadata
        Element tree = (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
        Element jfif = (Element)tree.getElementsByTagName("app0JFIF").item(0);
        jfif.setAttribute("Xdensity", Integer.toString(dpi));
		jfif.setAttribute("Ydensity", Integer.toString(dpi));
		jfif.setAttribute("resUnits", "1"); // density is dots per inch	
        imageMetaData.setFromTree("javax_imageio_jpeg_image_1.0", tree);
        */
        
        JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
        jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(1f);
        
      //new Write and clean up
        imageWriter.write(null, new IIOImage(image, null, imageMetaData), jpegParams);
        ios.close();
        imageWriter.dispose();
	}
	
	public static void exportToPNG(BufferedImage image, File newFile) throws Exception
	{
		System.out.println("Exporting: " + newFile.getName());
		ImageIO.write(image, "png", newFile);
	}
	
	public BufferedImage getIcon(Icon icon, int maxWidth, int maxHeight)
	{
		ImageIcon ii = new ImageIcon(icon.getImagePath());
		double r = 1d;
		double rX = (double)((double)maxWidth / (double)ii.getIconWidth());
		double rY = (double)((double)maxHeight / (double)ii.getIconHeight());
		if (rY < rX)
		{
			r = rY;
		}
		else
		{
			r = rX;
		}
		
		return resizeImage(ii, r);
	}
	
	public BufferedImage getIconMaxHeight(Icon icon, int maxHeight)
	{
		ImageIcon ii = new ImageIcon(icon.getImagePath());
		double r = (double)((double)maxHeight / (double)ii.getIconHeight());
		
		return resizeImage(ii, r);
	}
	
	public int getPercentageValue(int value, int max)
	{
		return (int)Math.round((double)(((double)value / (double)max) * 100d));
	}
	
	public int getPercentage(int size, double scale)
	{
		return (int)(((double)size * (double)scale));
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
	
	public static ConvolveOp getGaussianBlurFilter(int radius,
            boolean horizontal) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }
        
        int size = radius * 2 + 1;
        float[] data = new float[size];
        
        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;
        
        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }
        
        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }        
        
        Kernel kernel = null;
        if (horizontal) {
            kernel = new Kernel(size, 1, data);
        } else {
            kernel = new Kernel(1, size, data);
        }
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }
	
	private BufferedImage blackoutImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                Color originalColor = new Color(image.getRGB(xx, yy), true);
                //System.out.println(xx + "|" + yy + " color: " + originalColor.toString() + "alpha: " + originalColor.getAlpha());
                if (originalColor.getAlpha() > 0) {
                    image.setRGB(xx, yy, Color.BLACK.getRGB());
                }
            }
        }
        return image;
    }
	
	private void drawUnderlay(BufferedImage bi, Graphics g, int type, int x, int y, int blurRadius, boolean doubleBlur, int expandBlackout)
	{
		BufferedImage blackout = new BufferedImage(cardWidth, cardHeight, type);
    	blackout.getGraphics().drawImage(bi, x, y, null);
    	
    	blackout = blackoutImage(blackout);
    	
    	if (expandBlackout > 0)
    	{
    		blackout = expandBlackout(blackout, expandBlackout);
    	}
    	
    	if (blurRadius > 0)
    	{
    		BufferedImageOp op = new GaussianFilter( blurRadius );
        	BufferedImage bi2 = op.filter(blackout, null);
        	g.drawImage(bi2, 0, 0, null);
        	
        	if (doubleBlur)
        	{
        		BufferedImage bi3 = op.filter(bi2, null);
        		g.drawImage(bi3, 0, 0, null);
        	}
    	}
    	else
    	{
    		g.drawImage(blackout, 0, 0, null);
    	}
	}
	
	private BufferedImage blurImage(BufferedImage bi, Graphics g, int blurRadius)
	{
		if (blurRadius > 0)
    	{
    		BufferedImageOp op = new GaussianFilter( blurRadius );
        	BufferedImage bi2 = op.filter(bi, null);
        	return bi2;
    	}
		return bi;
	}
	
	private BufferedImage expandBlackout(BufferedImage image, int expandBlackout)
	{
		BufferedImage expand = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
		
		int width = image.getWidth();
        int height = image.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                Color originalColor = new Color(image.getRGB(xx, yy), true);
                
                if (originalColor.getAlpha() > 0) {
                	//Quick and Dirty - Just ignore out of bounds
                	for (int i = expandBlackout; i > 0; i--)
                	{
                		try { expand.setRGB(xx, yy - i, Color.BLACK.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx, yy + i, Color.BLACK.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx - i, yy, Color.BLACK.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx + i, yy, Color.BLACK.getRGB()); } catch (Exception e) {}
                    	
                    	if (i == 1)
                    	{
                    	try { expand.setRGB(xx - i, yy - i, Color.BLACK.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx - i, yy + i, Color.BLACK.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx + i, yy - i, Color.BLACK.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx + i, yy + i, Color.BLACK.getRGB()); } catch (Exception e) {}
                    	}
                	}
                }
            }
        }
        return expand;
	}
	
	private void listAllFonts()
	{
		Font[] fonts = 
      	      GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

      	    for ( int i = 0; i < fonts.length; i++ )
      	    {
      	      System.out.println(fonts[i].getFontName());
      	    System.out.println(fonts[i].getName());
      	      System.out.println(fonts[i].getFamily());
      	      System.out.println(fonts[i].getAttributes().toString());
      	      System.out.println(fonts[i].getAvailableAttributes().toString());
      	    }
	}
	
	private Icon isIcon(String str)
	{
		try
		{
			if (str != null && !str.startsWith("<") && !str.endsWith(">"))
			{
				return null;
			}
			
			Icon i = Icon.valueOf(str.replace("<", "").replace(">", ""));
			return i;
		}
		catch (IllegalArgumentException e)
		{
			return null;
		}
	}
	
	private BufferedImage createRareBacking(int x, int y, int x2, int y2)
	{
		BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = bi.getGraphics();
        
        //System.out.println(x +":"+y+":"+x2+":"+y2+":"+(x2-x)+":"+(y2-y));
        
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, x2 - x, y2 - y);
        
		return bi;
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
	
	public BufferedImage resizeImagePS(BufferedImage bi)
	{
		//Resize for printer studio
		double scale = 2.0;
		double xPadding = 0.043;
		double yPadding = 0.08;
		String exportType = "jpg"; //png or jpg
		
		ImageIcon imageIcon = new ImageIcon(bi);
		
		int w = (int)(imageIcon.getIconWidth() * scale);
		int xPad = (int)((imageIcon.getIconWidth() * scale) * xPadding);
		int fullW = w + xPad + xPad;
        int h = (int)(imageIcon.getIconHeight() * scale);
        int yPad = (int)((imageIcon.getIconHeight() * scale) * yPadding);
        int fullH = h + yPad + yPad;
        int type = BufferedImage.TYPE_INT_ARGB;
        if (exportType.equals("jpg"))
        {
        	type = BufferedImage.TYPE_INT_RGB;
        }
        BufferedImage image = new BufferedImage(fullW, fullH, type);
        Graphics g = image.getGraphics();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, fullW, fullH);
        
        g.drawImage(imageIcon.getImage(), xPad, yPad, w + xPad, h + yPad, 
        		0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
        
        g.dispose();
        
        return image;
	}
}
