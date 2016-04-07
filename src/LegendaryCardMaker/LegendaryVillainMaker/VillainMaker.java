package LegendaryCardMaker.LegendaryVillainMaker;

import java.awt.Color;
import java.awt.Font;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

import LegendaryCardMaker.CardMaker;
import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryCardMaker;
import LegendaryCardMaker.LegendaryCardMakerFrame;
import LegendaryCardMaker.WordDefinition;

public class VillainMaker extends CardMaker {
	
	public String exportFolder = "cardCreator";
	String templateFolder = "legendary" + File.separator + "templates" + File.separator + LegendaryCardMaker.expansionStyle;
	
	// 2.5 by 3.5 inches - Poker Size
	int cardWidth = 750;
	int cardHeight = 1050;
	int dpi = 300;
	
	boolean exportImage = false;
	public boolean exportToPNG = true;
	
	int teamIconX = 700;
	int teamIconY = 785;
	int teamMaxWidth = 48;
	int teamMaxHeight = 48;
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
	int cardNameY = 55;
	Color cardNameColor = new Color (255,186,20);
	boolean includeBlurredBGName = true;
	int expandCardName = 2;
	int cardNameBlurRadius = 5;
	boolean cardNameBlurDouble = true;
	
	public int villainGroupSize = 33;
	int villainGroupMinSize = 30;
	int villainGroupY = 90;
	Color villainGroupColor = new Color (255,186,20);
	boolean includeBlurredBGvillainGroup = true;
	int expandvillainGroup = 2;
	int villainGroupBlurRadius = 5;
	boolean villainGroupBlurDouble = true;
	
	int costSize = 120;
	int costX = 635;
	int costY = 993;
	Color costColor = Color.WHITE;
	boolean includeBlurredBGCost = true;
	int expandCost = 5;
	int costBlurRadius = 5;
	boolean costBlurDouble = false;
	
	int vpSize = 38;
	int vpX = 638;
	int vpY = 795;
	Color vpColor = Color.WHITE;
	boolean includeBlurredBGVP = true;
	int expandVp = 2;
	int vpBlurRadius = 2;
	boolean vpBlurDouble = false;
	
	int attackSize = 120;
	int attackX = 650;
	int attackY = 990;
	Color attackColor = Color.WHITE;
	boolean includeBlurredBGAttack = true;
	int expandAttack = 5;
	int attackBlurRadius = 5;
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
	public double yOffsetRatio = 0.10d;
	
	public VillainCard card;
	
	public BufferedWriter bwErr = null;

	public VillainMaker()
	{
		costOffsets.put("4", -8);
		costOffsets.put("6", -5);
		
		//populateVillainCard();
		//generateCard();
	}
	
	/*
	public static void main(String[] args)
	{
		new HeroMaker();
	}
	*/
	
	public void setCard(VillainCard c)
	{
		card = c;
		if (card.numberInDeck <= 0 && card.cardType != null)
		{
			card.numberInDeck = card.cardType.getCount();
		}
		
		if (card.nameSize > 0)
			cardNameSize = card.nameSize;
		
		if (card.abilityTextSize > 0)
			textSize = card.abilityTextSize;
	}
	
	public void populateVillainCard()
	{
		card = new VillainCard();
		card.villainGroup = "Swoop";
		card.name = "The Friendly One";
		card.cardType = VillainCardType.VILLAIN;
		card.cardTeam = Icon.valueOf("AUTOBOT");
		card.attack = "2";
		card.victory = "3";
		card.abilityText = "<k>Transform <g> <r>Select a player. That player must play an <AUTOBOT> hero from their hand or discard pile that has a <COST> of 5 or less. You gain all effects of that card as if you played it. That player discards the card after this turn.";
	}
	
	public static VillainCard getBlankVillainCard()
	{
		VillainCard card = new VillainCard();
		card.villainGroup = "";
		card.name = "";
		card.cardType = VillainCardType.VILLAIN;
		card.cardTeam = Icon.valueOf("NONE");
		card.abilityText = "card text";
		return card;
	}
	
	public BufferedImage generateCard()
	{
		int timeCount = 0;
		//System.out.println("TIMING " + timeCount++ + ": " + new Date().getTime());
		
		int type = BufferedImage.TYPE_INT_ARGB;
		if (exportToPNG)
		{
			type = BufferedImage.TYPE_INT_ARGB;	
		}
	    BufferedImage image = new BufferedImage(cardWidth, cardHeight, type);
	    Graphics2D g = (Graphics2D)image.getGraphics();
	    
	    g = setGraphicsHints(g);
	    
	    if (card.imagePath != null)
	    {
	    	String imagePath = card.imagePath;
	    	if (!imagePath.contains(File.separator) && LegendaryCardMakerFrame.lcmf.lcm.currentFile != null)
	    	{
	    		imagePath = new File(LegendaryCardMakerFrame.lcmf.lcm.currentFile).getParent() + File.separator + card.imagePath;
	    	}
	    	BufferedImage bi = resizeImage(new ImageIcon(imagePath), card.imageZoom);
	    	g.drawImage(bi, card.imageOffsetX, card.imageOffsetY, null);
	    }
	    
	    //System.out.println("TIMING " + timeCount++ + ": " + new Date().getTime());

	    // Template back
	    if (card.cardType != null && card.cardType.equals(VillainCardType.VILLAIN))
	    {
	    	ImageIcon ii = new ImageIcon(templateFolder + File.separator + "villain_normal" + File.separator + "back_underlay.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    }
	    if (card.cardType != null && card.cardType.equals(VillainCardType.HENCHMEN))
	    {
	    	ImageIcon ii = new ImageIcon(templateFolder + File.separator + "villain_henchmen" + File.separator + "back_underlay.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    }
	    if (card.cardType != null && card.cardType.equals(VillainCardType.MASTERMIND))
	    {	
	    	
	    }
	    if (card.cardType != null && card.cardType.equals(VillainCardType.BYSTANDER))
	    {
	    	ImageIcon ii = new ImageIcon(templateFolder + File.separator + "bystander" + File.separator + "back_underlay.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    }
	    if (card.cardType != null && card.cardType.equals(VillainCardType.WOUND))
	    {
	    	ImageIcon ii = new ImageIcon(templateFolder + File.separator + "wound" + File.separator + "back_underlay.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    }
	    if (card.cardType != null && card.cardType.equals(VillainCardType.BINDINGS))
	    {
	    	ImageIcon ii = new ImageIcon(templateFolder + File.separator + "bindings" + File.separator + "back_underlay.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    }
	    
	    //System.out.println("TIMING " + timeCount++ + ": " + new Date().getTime());
	    
	    // Card Team
	    if (card.cardTeam != null && card.cardTeam.getImagePath() != null)
	    {
	    	teamIconX = 700;
	    	teamIconY = 785;
	    	
	    	if (card.cardType != null && card.cardType.equals(VillainCardType.HENCHMEN))
		    {
	    		teamIconX = teamIconX + 17;
	    		teamIconY = teamIconY - 90;
		    }
	    	else if (card.cardType != null && card.cardType.equals(VillainCardType.BYSTANDER))
		    {
	    		teamIconX = teamIconX + 17;
	    		teamIconY = teamIconY - 25;
		    }
	    	
	    	BufferedImage bi = getIcon(card.cardTeam, teamMaxWidth, teamMaxHeight);
	    	int x = teamIconX - (bi.getWidth() / 2);
	    	int y = teamIconY - (bi.getWidth() / 2);
	    	
	    	if (includeBlurredBGTeam)
	    	{
	    		drawUnderlay(bi, g, type, x, y, teamBlurRadius, teamBlurDouble, expandTeam);
	    	}
	    	
	    	g.drawImage(bi, x, y, null);
	    }
	    
	    //System.out.println("TIMING " + timeCount++ + ": " + new Date().getTime());
	    
	    // Card Name
	    if (card.name != null)
	    {
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	    	Graphics2D g2 = getGraphics(bi);
	        
	    	g2.setColor(cardNameColor);
	    	
	    	if (card.cardType != null && card.cardType.equals(VillainCardType.BYSTANDER))
		    {
		    	g2.setColor(Color.WHITE);
		    }
	    	if (card.cardType != null && card.cardType.equals(VillainCardType.WOUND))
		    {
		    	g2.setColor(Color.WHITE);
		    }
	    	if (card.cardType != null && card.cardType.equals(VillainCardType.BINDINGS))
		    {
		    	g2.setColor(Color.WHITE);
		    }
	    	
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
	        g2.setFont(font);
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.name.toUpperCase());
	        int x = (cardWidth / 2) - (stringLength / 2);
	        
	        g2 = setGraphicsHints(g2);
	        
	        g2.drawString(card.name.toUpperCase(), x, cardNameY);
	    	if (includeBlurredBGName)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, cardNameBlurRadius, cardNameBlurDouble, expandCardName);
	    	}
	    	
	    	g2.drawString(card.name.toUpperCase(), x, cardNameY);
	    	
	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
	    }
	    
	    //System.out.println("TIMING " + timeCount++ + ": " + new Date().getTime());
	    
	    // Hero Name
	    if (card.villainGroup != null)
	    {
	    	String villainGroup = card.villainGroup;
	    	if (card.cardType != null && card.cardType.equals(VillainCardType.VILLAIN))
		    {
		    	villainGroup = "Villain - " + villainGroup;
		    	villainGroup = villainGroup.toUpperCase();
		    }
		    if (card.cardType != null && card.cardType.equals(VillainCardType.HENCHMEN))
		    {
		    	villainGroup = "Henchman Villain";
		    	villainGroup = villainGroup.toUpperCase();
		    }
		    if (card.cardType != null && card.cardType.equals(VillainCardType.MASTERMIND))
		    {	
		    	villainGroup = "Mastermind";
		    	villainGroup = villainGroup.toUpperCase();
		    }
		    if (card.cardType != null && card.cardType.equals(VillainCardType.MASTERMIND_TACTIC))
		    {	
		    	villainGroup = "Mastermind Tactic - " + card.villain.name;
		    	villainGroup = villainGroup.toUpperCase();
		    }
		    if (card.cardType != null && card.cardType.equals(VillainCardType.BYSTANDER))
		    {
		    	villainGroup = null;
		    }
		    if (card.cardType != null && card.cardType.equals(VillainCardType.WOUND))
		    {
		    	villainGroup = null;
		    }
		    if (card.cardType != null && card.cardType.equals(VillainCardType.BINDINGS))
		    {
		    	villainGroup = null;
		    }
		    
		    if (villainGroup != null)
		    {
		    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
		    	Graphics2D g2 = getGraphics(bi);
		        
		    	g2.setColor(villainGroupColor);
		        //Font font = new Font("Percolator", Font.PLAIN, villainGroupSize);
		    	Font font = null;
		    	try
		    	{
		    	font = Font.createFont(Font.TRUETYPE_FONT, new File("Percolator.otf"));
		        font = font.deriveFont((float)villainGroupSize);
		        g2.setFont(font);
		    	}
		    	catch (Exception e)
		    	{
		    		e.printStackTrace();
		    		
		    		font = new Font("Percolator", Font.PLAIN, villainGroupSize);
		    		g2.setFont(font);
		    	}
		        g2.setFont(font);
		        FontMetrics metrics = g2.getFontMetrics(font);
		        int stringLength = SwingUtilities.computeStringWidth(metrics, villainGroup);
		        int x = (cardWidth / 2) - (stringLength / 2);
		        
		        g2 = setGraphicsHints(g2);
		        
		        g2.drawString(villainGroup, x, villainGroupY);
		    	if (includeBlurredBGvillainGroup)
		    	{
		    		drawUnderlay(bi, g2, type, 0, 0, villainGroupBlurRadius, villainGroupBlurDouble, expandvillainGroup);
		    	}
		    	
		    	g2.drawString(villainGroup, x, villainGroupY);
		    	
		    	g.drawImage(bi, 0, 0, null);
		    	
		    	g2.dispose();
		    }
	    }
	    
	    //System.out.println("TIMING " + timeCount++ + ": " + new Date().getTime());
	    
	    // Recruit
	    if (card.victory != null)
	    {
	    	vpX = 638;
	    	vpY = 795;
	    	
	    	if (card.cardType != null && card.cardType.equals(VillainCardType.HENCHMEN))
		    {
	    		vpX = vpX + 23;
	    		vpY = vpY - 87;
		    }
	    	else if (card.cardType != null && card.cardType.equals(VillainCardType.BYSTANDER))
		    {
	    		vpX = vpX + 25;
	    		vpY = vpY - 21;
		    }
	    	else
	    	{
	    		ImageIcon ii = new ImageIcon(templateFolder + File.separator + "villain_normal" + File.separator + "attr_vp.png");
		    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	}
	    	
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	    	Graphics2D g2 = getGraphics(bi);
	        
	    	g2.setColor(vpColor);
	        //Font font = new Font("Sylfaen", Font.PLAIN, vpSize);
	    	Font font = null;
	    	try
	    	{
	    	font = Font.createFont(Font.TRUETYPE_FONT, new File("Sylfaen.ttf"));
	        font = font.deriveFont((float)vpSize);
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    		
	    		font = new Font("Sylfaen", Font.PLAIN, vpSize);
	    	}
	        g2.setFont(font);
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.victory.toUpperCase());
	        int x = vpX - (stringLength / 2);
	        
	        g2 = setGraphicsHints(g2);
	        
	        g2.drawString(card.victory.toUpperCase(), x, vpY);
	    	if (includeBlurredBGVP)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, vpBlurRadius, vpBlurDouble, expandVp);
	    	}
	    	
	    	g2.drawString(card.victory.toUpperCase(), x, vpY);
	    	
	    	//bi = blurImage(bi, g2, 2);
	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
	    }
	    
	    //System.out.println("TIMING " + timeCount++ + ": " + new Date().getTime());
	    
	    // Card Text
	    if (card.abilityText != null)
	    {
	    	ImageIcon ii = null;
	    	BufferedImage overlay = null;
	    	
	    	if (card.cardType != null && card.cardType.equals(VillainCardType.VILLAIN))
		    {
	    		ii = new ImageIcon(templateFolder + File.separator + "villain_normal" + File.separator + "back_text_overlay.png");
		    	overlay = resizeImage(ii, cardWidth, cardHeight);
		    	//g.drawImage(overlay, 0, 0, null);
		    }
	    	else if (card.cardType != null && card.cardType.equals(VillainCardType.HENCHMEN))
		    {
	    		ii = new ImageIcon(templateFolder + File.separator + "villain_henchmen" + File.separator + "back_text_overlay.png");
		    	overlay = resizeImage(ii, cardWidth, cardHeight);
		    	//g.drawImage(overlay, 0, 0, null);
		    }
	    	else if (card.cardType != null && (card.cardType.equals(VillainCardType.MASTERMIND_TACTIC) || card.cardType.equals(VillainCardType.MASTERMIND)))
		    {
	    		ii = new ImageIcon(templateFolder + File.separator + "villain_mastermind" + File.separator + "back_text_overlay.png");
		    	overlay = resizeImage(ii, cardWidth, cardHeight);
		    	//g.drawImage(overlay, 0, 0, null);
		    }
	    	else if (card.cardType != null && card.cardType.equals(VillainCardType.BYSTANDER))
		    {
	    		ii = new ImageIcon(templateFolder + File.separator + "bystander" + File.separator + "back_text_overlay.png");
		    	overlay = resizeImage(ii, cardWidth, cardHeight);
		    	//g.drawImage(overlay, 0, 0, null);
		    }
	    	else if (card.cardType != null && card.cardType.equals(VillainCardType.WOUND))
		    {
	    		ii = new ImageIcon(templateFolder + File.separator + "wound" + File.separator + "back_text_overlay.png");
		    	overlay = resizeImage(ii, cardWidth, cardHeight);
		    	//g.drawImage(overlay, 0, 0, null);
		    }
	    	else if (card.cardType != null && card.cardType.equals(VillainCardType.BINDINGS))
		    {
	    		ii = new ImageIcon(templateFolder + File.separator + "bindings" + File.separator + "back_text_overlay.png");
		    	overlay = resizeImage(ii, cardWidth, cardHeight);
		    	//g.drawImage(overlay, 0, 0, null);
		    }
	    	
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	    	Graphics2D g2 = getGraphics(bi);
	        
	    	g2.setColor(textColor);
	    	
	    	int y = 0;
	    	try
	    	{
	    		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Swiss 721 Light Condensed.ttf"));
	    		font = font.deriveFont((float)textSize);
	    		g2.setFont(font);
	    		
	    		Font fontBold = Font.createFont(Font.TRUETYPE_FONT, new File("Swiss 721 Black Condensed.ttf"));
	    		fontBold = fontBold.deriveFont((float)textSize);
	    		
	    		FontMetrics metrics = g2.getFontMetrics(font);
	    		
	    		g2 = setGraphicsHints(g2);
	    		
	    		Integer x = textX;
	    		y = textY;
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
	            
	            //x end works from right to left but top down
	            done = false;
	            int xFullEnd = xEnd;
	            for (int xx = width - 1; xx >= 0; xx--) {
	                for (int yy = 0; yy < height; yy++) {
	                    Color originalColor = new Color(overlay.getRGB(xx, yy), true);
	                    if (originalColor.getAlpha() > 0 && done == false) {
	                        xFullEnd = xx;
	                        done = true;
	                    }
	                }
	            }
	            
	    		List<WordDefinition> words = WordDefinition.getWordDefinitionList(card.abilityText);
	    		
	    		for (WordDefinition wd : words)
	    		{
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
	    					g2 = setGraphicsHints(g2);
	    					s = s.replace("<k>", "");
	    					continue;
	    				}
	    				
	    				if (s.startsWith("<r>"))
	    				{
	    					g2.setFont(font);
	    					metrics = g2.getFontMetrics(font);
	    					g2 = setGraphicsHints(g2);
	    					s = s.replace("<r>", "");
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
	    					x = xOrigin;
	    					for (int i = 0; i < overlay.getWidth(); i++)
	    					{
	    						Color c = new Color(overlay.getRGB(i, y), true);
	    						if (c.getAlpha() > 0)
	    						{
	    							x = i;
	    							i = overlay.getWidth();
	    						}
	    					}
	    					y += g2.getFontMetrics(font).getHeight() + getPercentage(g2.getFontMetrics(font).getHeight(), textGapHeight);
	    					continue;
	    				}
	    				else if (icon == null)
	    				{
	    					int stringLength = SwingUtilities.computeStringWidth(metrics, s);
	    					Color color = null;
	    					try
	    					{
	    						
	    						color = new Color(overlay.getRGB(x + 50, y), true);
	    					}
	    					catch (ArrayIndexOutOfBoundsException e)
	    					{
	    						color = new Color(overlay.getRGB(0, 0), true);
	    					}
	    					if (color.getAlpha() == 0)
	    					{
	    						//System.out.println("String: " + s + ", " + x + ":" + xEnd + ":" + xFullEnd);
	    						if ((x > xEnd && x <= xFullEnd) || (x > xFullEnd))
	    						{
	    							xEnd = x;
	    						}
	    						x = xOrigin;
	    						for (int i = 0; i < overlay.getWidth(); i++)
	    						{
	    							Color c = new Color(overlay.getRGB(i, y), true);
	    							if (c.getAlpha() > 0)
	    							{
	    								x = i;
	    								i = overlay.getWidth();
	    							}
	    						}
	    						y += g2.getFontMetrics(font).getHeight() + getPercentage(g2.getFontMetrics(font).getHeight(), textDefaultGapHeight);
	    					}
	    					g2.drawString(s + " ", x, y);
	    					x += stringLength + SwingUtilities.computeStringWidth(metrics, spaceChar);
	    					continue;
	    				}
	    				else if (icon != null)
	    				{
	    					BufferedImage i = getIconMaxHeight(icon, getPercentage(metrics.getHeight(), textIconHeight));
	    					
	    					Color color = new Color(overlay.getRGB(x + i.getWidth(), y + i.getHeight()), true);
	    					if (color.getAlpha() == 0)
	    					{
	    						//System.out.println("Icon: " + icon.toString() + ", " + x + ":" + xEnd + ":" + xFullEnd);
	    						if ((x > xEnd && x <= xFullEnd) || (x > xFullEnd))
	    						{
	    							xEnd = x;
	    						}
	    						x = xOrigin;
	    						for (int j = 0; j < overlay.getWidth(); j++)
	    						{
	    							Color c = new Color(overlay.getRGB(j, y), true);
	    							if (c.getAlpha() > 0)
	    							{
	    								x = j;
	    								j = overlay.getWidth();
	    							}
	    						}
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
	    					x += i.getWidth() + SwingUtilities.computeStringWidth(metrics, spaceChar);
	    					continue;
	    				}
	    			}
	    		}
	    		
	    		{
	    			if (card.cardType.equals(VillainCardType.MASTERMIND) || card.cardType.equals(VillainCardType.MASTERMIND_TACTIC))
	    	    	{
	        			if (xEnd < getPercentage((xFullEnd - xOrigin), 0.65d))
	        			{
	        				xEnd = xFullEnd;
	        			}
	        			
	        			//System.out.println("BG Blur:, " + x + ":" + xEnd + ":" + xFullEnd + ":" + xOrigin);
	        			
	        			int padding = getPercentage(xEnd - xOrigin, rarePaddingRatio);
	        			
	        			yOrigin = yOrigin + (metrics.getHeight() / 3);
	    	    		BufferedImage blurBG = createRareBacking(xOrigin - padding, yOrigin - padding, xEnd + padding, y + padding);
	    	    		blurBG = makeTransparent(blurBG, 0.85d);
	    	    		blurBG = blurImage(blurBG, g2, rareBlurRadius);
	    	    		
	    	    		int yOffsetValue = 0;
	    		    	if (y < cardHeight - getPercentage(cardHeight, yOffsetRatio))
	    		    	{
	    		    		yOffsetValue = (cardHeight - getPercentage(cardHeight, yOffsetRatio)) - y;
	    		    	}
	    		    	
	    	    		g.drawImage(blurBG, 0, yOffsetValue, null);
	    	    	}
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
	        
	    	int yOffsetValue = 0;
	    	if (card.cardType != null && (card.cardType.equals(VillainCardType.MASTERMIND_TACTIC) || card.cardType.equals(VillainCardType.MASTERMIND)))
	    	{
	    		if (y < cardHeight - getPercentage(cardHeight, yOffsetRatio))
		    	{
		    		yOffsetValue = (cardHeight - getPercentage(cardHeight, yOffsetRatio)) - y;
		    	}
	    	}
	    	
	        g.drawImage(bi, 0, yOffsetValue, null);
	    }
	    
	    //System.out.println("TIMING " + timeCount++ + ": " + new Date().getTime());
	    
	    // Attack
	    if (card.attack != null)
	    {
	    	attackSize = 120;
	    	attackX = 650;
	    	expandAttack = 5;
	    	
	    	if (card.cardType.equals(VillainCardType.HENCHMEN))
	    	{
	    		ImageIcon ii = new ImageIcon(templateFolder + File.separator + "villain_henchmen" + File.separator + "back_attack.png");
		    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
		    	
		    	attackSize = attackSize - 34;
		    	attackX = attackX + 16;
		    	expandAttack = expandAttack - 2;
	    	}
	    	else
	    	{
	    		ImageIcon ii = new ImageIcon(templateFolder + File.separator + "villain_normal" + File.separator + "attr_attack.png");
		    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	}
	    	
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	    	Graphics2D g2 = getGraphics(bi);
	        
	    	g2.setColor(attackColor);
	       // Font font = new Font("Sylfaen", Font.PLAIN, attackSize);
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
	        
	        g2 = setGraphicsHints(g2);
	        
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
	    
	    //System.out.println("TIMING " + timeCount++ + ": " + new Date().getTime());
	    
	    // Cost
	    if (card.cost != null)
	    {
	    	ImageIcon ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "back_cost.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	    	Graphics2D g2 = getGraphics(bi);
	        
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
	        
	        g2 = setGraphicsHints(g2);
	        
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
	    
	    //System.out.println("TIMING " + timeCount++ + ": " + new Date().getTime());
		
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
		
		BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		g.dispose();
		
		File dir = new File(exportFolder);
		dir.mkdirs();
		
		ImageIO.write(bi, "jpeg", newFile);
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
	
	private void drawUnderlay(BufferedImage bi, Graphics2D g, int type, int x, int y, int blurRadius, boolean doubleBlur, int expandBlackout)
	{
		BufferedImage blackout = new BufferedImage(cardWidth, cardHeight, type);
		getGraphics(blackout).drawImage(bi, x, y, null);
    	
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
		Graphics2D g2 = getGraphics(bi);
        
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
        Graphics2D g = getGraphics(image);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, fullW, fullH);
        
        g.drawImage(imageIcon.getImage(), xPad, yPad, w + xPad, h + yPad, 
        		0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
        
        g.dispose();
        
        return image;
	}
	
	private Graphics2D getGraphics(BufferedImage bi)
	{
		Graphics2D g2 = (Graphics2D)bi.getGraphics();
        
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				
		g2.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		return g2;
	}
	
	private Graphics2D setGraphicsHints(Graphics2D g2)
	{
		g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				
		g2.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		return g2;
	}
}
