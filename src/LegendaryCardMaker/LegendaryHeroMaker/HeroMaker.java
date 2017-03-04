package LegendaryCardMaker.LegendaryHeroMaker;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import LegendaryCardMaker.CardMaker;
import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryCardMaker;
import LegendaryCardMaker.LegendaryCardMakerFrame;
import LegendaryCardMaker.WordDefinition;

public class HeroMaker extends CardMaker {
	
	public enum DUAL_CLASS_STYLE { HALF_AND_HALF, SIDE_BY_SIDE, TOP_AND_BOTTOM};
	
	//Template values
	public static int cardNameSizeTemplate = 40;
	public static String cardNameFontNameTemplate = null;
	public static int cardNameFontStyleTemplate = Font.PLAIN;
	public static Color cardNameColorTemplate = new Color (255,186,20);
	public static int heroNameSizeTemplate = 33;
	public static String heroNameFontNameTemplate = null;
	public static int heroNameFontStyleTemplate = Font.PLAIN;
	public static Color heroNameColorTemplate = new Color (255,186,20);
	public static int abilityTextSizeTemplate = 27;
	public static Color abilityTextColorTemplate = Color.BLACK;
	public static String abilityTextFontNameTemplate = null;
	public static int abilityTextFontStyleTemplate = Font.PLAIN;
	public static Color teamPowerUnderlayColorTemplate = Color.BLACK;
	public static int teamPowerBlurRadiusTemplate = 5;
	public static String nameHighlightTemplate = "Blur";
	public static DUAL_CLASS_STYLE dualClassStyle = DUAL_CLASS_STYLE.SIDE_BY_SIDE;
	
	public static int costSizeTemplate = 120;
	public static String costFontNameTemplate = null;
	public static int costFontStyleTemplate = Font.PLAIN;
	public static Color costColorTemplate = Color.WHITE;
	
	public static int attackSizeTemplate = 80;
	public static String attackFontNameTemplate = null;
	public static int attackFontStyleTemplate = Font.PLAIN;
	public static Color attackColorTemplate = Color.WHITE;
	
	public static int recruitSizeTemplate = 80;
	public static String recruitFontNameTemplate = null;
	public static int recruitFontStyleTemplate = Font.PLAIN;
	public static Color recruitColorTemplate = Color.WHITE;
	
	public static void resetTemplateValues()
	{
		cardNameSizeTemplate = 40;
		cardNameFontNameTemplate = null;
		cardNameFontStyleTemplate = Font.PLAIN;
		cardNameColorTemplate = new Color (255,186,20);
		heroNameSizeTemplate = 33;
		heroNameFontNameTemplate = null;
		heroNameFontStyleTemplate = Font.PLAIN;
		heroNameColorTemplate = new Color (255,186,20);
		abilityTextSizeTemplate = 27;
		abilityTextColorTemplate = Color.BLACK;
		abilityTextFontNameTemplate = null;
		abilityTextFontStyleTemplate = Font.PLAIN;
		teamPowerUnderlayColorTemplate = Color.BLACK;
		teamPowerBlurRadiusTemplate = 5;
		nameHighlightTemplate = "Blur";
		
		costSizeTemplate = 120;
		costFontNameTemplate = null;
		costFontStyleTemplate = Font.PLAIN;
		costColorTemplate = Color.WHITE;
		
		attackSizeTemplate = 80;
		attackFontNameTemplate = null;
		attackFontStyleTemplate = Font.PLAIN;
		attackColorTemplate = Color.WHITE;
		
		recruitSizeTemplate = 80;
		recruitFontNameTemplate = null;
		recruitFontStyleTemplate = Font.PLAIN;
		recruitColorTemplate = Color.WHITE;
		
		dualClassStyle = DUAL_CLASS_STYLE.SIDE_BY_SIDE;
	}
	
	public void resetTemplateValuesInstance()
	{
		cardNameSize = 40;
		cardNameFontName = null;
		cardNameFontStyle = Font.PLAIN;
		cardNameColor = new Color (255,186,20);
		heroNameSize = 33;
		heroNameFontName = null;
		heroNameFontStyle = Font.PLAIN;
		heroNameColor = new Color (255,186,20);
		textSize = 27;
		textColor = Color.BLACK;
		textFontName = null;
		textFontStyle = Font.PLAIN;
		teamPowerUnderlayColor = Color.BLACK;
		teamBlurRadius = 5;
		powerBlurRadius = 5;
		nameHighlight = "Blur";
		
		costSize = 120;
		costFontName = null;
		costFontStyle = Font.PLAIN;
		costColor = Color.WHITE;
		
		attackSize = 80;
		attackFontName = null;
		attackFontStyle = Font.PLAIN;
		attackColor = Color.WHITE;
		
		recruitSize = 80;
		recruitFontName = null;
		recruitFontStyle = Font.PLAIN;
		recruitColor = Color.WHITE;
	}
	
	public void loadTemplateDefaults()
	{
		cardNameSize = cardNameSizeTemplate;
		cardNameColor = cardNameColorTemplate;
		cardNameFontName = cardNameFontNameTemplate;
		cardNameFontStyle = cardNameFontStyleTemplate;
		heroNameSize = heroNameSizeTemplate;
		heroNameColor = heroNameColorTemplate;
		heroNameFontName = heroNameFontNameTemplate;
		heroNameFontStyle = heroNameFontStyleTemplate;
		textSize = abilityTextSizeTemplate;
		textColor = abilityTextColorTemplate;
		textFontName = abilityTextFontNameTemplate;
		textFontStyle = abilityTextFontStyleTemplate;
		teamPowerUnderlayColor = teamPowerUnderlayColorTemplate;
		teamBlurRadius = teamPowerBlurRadiusTemplate;
		powerBlurRadius = teamPowerBlurRadiusTemplate;
		nameHighlight = nameHighlightTemplate;
		
		costSize = costSizeTemplate;
		costColor = costColorTemplate;
		costFontName = costFontNameTemplate;
		costFontStyle = costFontStyleTemplate;
		
		attackSize = attackSizeTemplate;
		attackColor = attackColorTemplate;
		attackFontName = attackFontNameTemplate;
		attackFontStyle = attackFontStyleTemplate;
		
		recruitSize = recruitSizeTemplate;
		recruitColor = recruitColorTemplate;
		recruitFontName = recruitFontNameTemplate;
		recruitFontStyle = recruitFontStyleTemplate;
	}
	
	public static String generateTemplateOutputString()
	{
		String str = "";
		
		if (cardNameSizeTemplate > 0)
			str += "HCTCARDNAMESIZE;" + cardNameSizeTemplate + "\n";
		
		if (heroNameSizeTemplate > 0)
			str += "HCTHERONAMESIZE;" + heroNameSizeTemplate + "\n";
		
		if (abilityTextSizeTemplate > 0)
			str += "HCTABILITYTEXTSIZE;" + abilityTextSizeTemplate + "\n";
		
		if (cardNameColorTemplate != null)
			str += "HCTCARDNAMECOLOUR;" + cardNameColorTemplate.getRGB() + "\n";
		
		if (cardNameFontNameTemplate != null)
			str += "HCTCARDNAMEFONTNAME;" + cardNameFontNameTemplate + "\n";
		
		str += "HCTCARDNAMEFONTSTYLE;" + cardNameFontStyleTemplate + "\n";
		
		if (heroNameColorTemplate != null)
			str += "HCTHERONAMECOLOUR;" + heroNameColorTemplate.getRGB() + "\n";
		
		if (heroNameFontNameTemplate != null)
			str += "HCTHERONAMEFONTNAME;" + heroNameFontNameTemplate + "\n";
		
		str += "HCTHERONAMEFONTSTYLE;" + heroNameFontStyleTemplate + "\n";
		
		if (abilityTextColorTemplate != null)
			str += "HCTABILITYTEXTCOLOUR;" + abilityTextColorTemplate.getRGB() + "\n";
		
		if (abilityTextFontNameTemplate != null)
			str += "HCTABILITYTEXTFONTNAME;" + abilityTextFontNameTemplate + "\n";
		
		str += "HCTABILITYTEXTFONTSTYLE;" + abilityTextFontStyleTemplate + "\n";
		
		if (teamPowerUnderlayColorTemplate != null)
			str += "HCTTEAMPOWERUNDERLAYCOLOUR;" + teamPowerUnderlayColorTemplate.getRGB() + "\n";
		
		if (teamPowerBlurRadiusTemplate > 0)
			str += "HCTTEAMPOWERBLURRADIUS;" + teamPowerBlurRadiusTemplate + "\n";
		
		if (nameHighlightTemplate != null)
			str += "HCTNAMEHIGHLIGHTTYPE;" + nameHighlightTemplate + "\n";
		
		if (costSizeTemplate > 0)
			str += "HCTCOSTSIZE;" + costSizeTemplate + "\n";
		
		if (costColorTemplate != null)
			str += "HCTCOSTCOLOUR;" + costColorTemplate.getRGB() + "\n";
		
		if (costFontNameTemplate != null)
			str += "HCTCOSTFONTNAME;" + costFontNameTemplate + "\n";
		
		str += "HCTCOSTFONTSTYLE;" + costFontStyleTemplate + "\n";
		
		if (attackSizeTemplate > 0)
			str += "HCTATTACKSIZE;" + attackSizeTemplate + "\n";
		
		if (attackColorTemplate != null)
			str += "HCTATTACKCOLOUR;" + attackColorTemplate.getRGB() + "\n";
		
		if (attackFontNameTemplate != null)
			str += "HCTATTACKFONTNAME;" + attackFontNameTemplate + "\n";
		
		str += "HCTATTACKFONTSTYLE;" + attackFontStyleTemplate + "\n";
		
		if (recruitSizeTemplate > 0)
			str += "HCTRECRUITSIZE;" + recruitSizeTemplate + "\n";
		
		if (recruitColorTemplate != null)
			str += "HCTRECRUITCOLOUR;" + recruitColorTemplate.getRGB() + "\n";
		
		if (recruitFontNameTemplate != null)
			str += "HCTRECRUITFONTNAME;" + recruitFontNameTemplate + "\n";
		
		str += "HCTRECRUITFONTSTYLE;" + recruitFontStyleTemplate + "\n";
		
		str += "HCTDUALCLASSSTYLE;" + dualClassStyle.name();
		
		str +="\n";
		
		return str;
	}
	
	public String exportFolder = "cardCreator";
	String templateFolder = "legendary" + File.separator + "templates" + File.separator + LegendaryCardMaker.expansionStyle;
	
	// 2.5 by 3.5 inches - Poker Size
	int cardWidth = 750;
	int cardHeight = 1050;
	int dpi = 300;
	
	boolean exportImage = false;
	public boolean exportToPNG = true;
	
	Color teamPowerUnderlayColor = Color.BLACK;
	
	public int teamIconX = 54;
	public int teamIconY = 60;
	public int teamMaxWidth = 54;
	public int teamMaxHeight = 54;
	public boolean includeBlurredBGTeam = true;
	public int expandTeam = 1;
	public int teamBlurRadius = 5;
	public boolean teamBlurDouble = true;
	
	public int powerIconX = 54;
	public int powerIconY = 120;
	public int powerMaxWidth = 54;
	public int powerMaxHeight = 54;
	public boolean includeBlurredBGPower = true;
	public int expandPower = 1;
	public int powerBlurRadius = 5;
	public boolean powerBlurDouble = true;
	
	public String nameHighlight = "Blur";
	
	public String cardNameFontName = null;
	public int cardNameFontStyle = Font.PLAIN;
	public int cardNameSize = 40;
	public int cardNameMinSize = 30;
	public int cardNameY = 20;
	public Color cardNameColor = new Color (255,186,20);
	public boolean includeBlurredBGName = true;
	public int expandCardName = 2;
	public int cardNameBlurRadius = 5;
	public boolean cardNameBlurDouble = true;
	
	public String heroNameFontName = null;
	public int heroNameFontStyle = Font.PLAIN;
	public int heroNameSize = 33;
	public int heroNameMinSize = 30;
	public int heroNameY = 60;
	public Color heroNameColor = new Color (255,186,20);
	public boolean includeBlurredBGHeroName = true;
	public int expandHeroName = 2;
	public int heroNameBlurRadius = 5;
	public boolean heroNameBlurDouble = true;
	
	public String costFontName = null;
	public int costFontStyle = Font.PLAIN;
	public int costSize = 120;
	public int costX = 635;
	public int costY = 950;
	public Color costColor = Color.WHITE;
	public boolean includeBlurredBGCost = true;
	public int expandCost = 5;
	public int costBlurRadius = 5;
	public boolean costBlurDouble = false;
	
	public String recruitFontName = null;
	public int recruitFontStyle = Font.PLAIN;
	public int recruitSize = 80;
	public int recruitX = 98;
	public int recruitY = 760;
	public Color recruitColor = Color.WHITE;
	public boolean includeBlurredBGRecruit = true;
	public int expandRecruit = 3;
	public int recruitBlurRadius = 3;
	public boolean recruitBlurDouble = false;
	
	public String attackFontName = null;
	public int attackFontStyle = Font.PLAIN;
	public int attackSize = 80;
	public int attackX = 87;
	public int attackY = 917;
	public Color attackColor = Color.WHITE;
	public boolean includeBlurredBGAttack = true;
	public int expandAttack = 3;
	public int attackBlurRadius = 3;
	public boolean attackBlurDouble = false;
	public HashMap<String, Integer> costOffsets = new HashMap<String, Integer>();
	
	public String textFontName = null;
	public int textFontStyle = Font.PLAIN;
	public int textSize = 27;
	public int textX = 154;
	public int textY = 805;
	public Color textColor = Color.BLACK;
	public boolean includeBlurredBGText = false;
	public int expandText = 0;
	public int textBlurRadius = 0;
	public boolean textBlurDouble = false;
	public double textIconHeight = 1.2d;
	public double textGapHeight = 0.6d;
	public double textDefaultGapHeight = 0.2d;
	public int expandTextIcon = 0;
	public int textIconBlurRadius = 5;
	public boolean textIconBlurDouble = true;
	public double rarePaddingRatio = 0.06d;
	public int rareBlurRadius = 25;
	public int textStartOffset = 0;
	
	public HeroCard card;
	
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
		
		if (card.nameSize > 0)
			cardNameSize = card.nameSize;
		
		if (card.heroNameSize > 0)
			heroNameSize = card.heroNameSize;
		
		if (card.abilityTextSize > 0)
			textSize = card.abilityTextSize;
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
		this.nameHighlight = nameHighlightTemplate;
		int type = BufferedImage.TYPE_INT_ARGB;
		if (exportToPNG)
		{
			type = BufferedImage.TYPE_INT_ARGB;	
		}
	    BufferedImage image = new BufferedImage(cardWidth, cardHeight, type);
	    Graphics2D g = (Graphics2D)image.getGraphics();
	    
	    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	    
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				
		g.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		
	    
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

	    // Template back
	    if (card.rarity != null && card.rarity.equals(CardRarity.COMMON))
	    {
	    	ImageIcon ii = null;
	    	
	    	ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "back_text.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	
	    	if (card.cardPower == null || (card.cardPower != null && card.cardPower.equals(Icon.valueOf("NONE"))))
	    	{
	    		ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "back_grey.png");
		    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	}
	    	else
	    	{
	    		
		    	
		    	if (card.cardPower2 != null && card.cardPower != null && !card.cardPower2.equals(Icon.valueOf("NONE")))
		    	{
		    		ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "back_" + card.cardPower2.toString() + ".png");
		    		g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
		    		
		    		ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "back_" + card.cardPower.toString() + ".png");
		    		BufferedImage bi = getFadedBackground(ii);
		    		g.drawImage(resizeImage(new ImageIcon(bi), cardWidth, cardHeight), 0, 0, null);
		    	}
		    	else
		    	{
		    		ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "back_" + card.cardPower.toString() + ".png");
			    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
		    	}
	    	}
	    }
	    if (card.rarity != null && card.rarity.equals(CardRarity.UNCOMMON))
	    {
	    	ImageIcon ii = null;
	    	
	    	ii = new ImageIcon(templateFolder + File.separator + "hero_uncommon" + File.separator + "back_text.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	
	    	if (card.cardPower != null && card.cardPower.equals(Icon.valueOf("NONE")))
	    	{
	    		ii = new ImageIcon(templateFolder + File.separator + "hero_uncommon" + File.separator + "back_grey.png");
		    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	}
	    	else
	    	{
	    		if (card.cardPower2 != null && card.cardPower != null && !card.cardPower2.equals(Icon.valueOf("NONE")))
		    	{
	    			ii = new ImageIcon(templateFolder + File.separator + "hero_uncommon" + File.separator + "back_" + card.cardPower2.toString() + ".png");
			    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
			    	
		    		ii = new ImageIcon(templateFolder + File.separator + "hero_uncommon" + File.separator + "back_" + card.cardPower.toString() + ".png");
			    	BufferedImage bi = getFadedBackground(ii);
			    	g.drawImage(resizeImage(new ImageIcon(bi), cardWidth, cardHeight), 0, 0, null);
		    	}
		    	else
		    	{
		    		ii = new ImageIcon(templateFolder + File.separator + "hero_uncommon" + File.separator + "back_" + card.cardPower.toString() + ".png");
			    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
		    	}
	    	}
	    	
	    }
	    if (card.rarity != null && card.rarity.equals(CardRarity.RARE))
	    {	
	    	ImageIcon ii = null;
	    	
	    	if (new File(templateFolder + File.separator + "hero_rare" + File.separator + "back_text.png").exists())
	    	{
	    		ii = new ImageIcon(templateFolder + File.separator + "hero_rare" + File.separator + "back_text.png");
	    		g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	}
	    }
	    
	    
	    //Draw Name Banner before power and team
	    if (nameHighlight != null && nameHighlight.equals("Banner"))
	    {
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics g2 = bi.getGraphics();
	        
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
	    	int nameAscent = (int)metrics.getLineMetrics(card.name.toUpperCase(), g2).getAscent();
	    	int nameDescent = (int)metrics.getLineMetrics(card.name.toUpperCase(), g2).getDescent();
	        
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
	        metrics = g2.getFontMetrics(font);
	        int heroDescent = (int)metrics.getLineMetrics(card.name.toUpperCase(), g2).getDescent();
	        
	        int bannerHeight = (heroNameY + heroDescent) - (cardNameY - nameAscent);
	        bannerHeight = bannerHeight + getPercentage(cardHeight, 0.01d);
			g2.setColor(Color.black);
			g2.fillRect((cardWidth / 2), cardNameY - getPercentage(cardHeight, 0.005d), getPercentage(cardWidth, 0.15d), bannerHeight);
	    	
			MotionBlurOp op = new MotionBlurOp();
			op.setDistance(200f);
        	bi = op.filter(bi, null);
        	
        	makeTransparent(bi, 0.8d);
			
			g.drawImage(bi, 0, 0, null);
			
			//Flip and re-draw image
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-bi.getWidth(null), 0);
			AffineTransformOp aop = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			bi = aop.filter(bi, null);
			
			g.drawImage(bi, 0, 0, null);
			
	    	g2.dispose();
	    }
	    
	    // Card Name
	    int cardNameEndPos = 0;
	    if (card.name != null)
	    {
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2 = getGraphics(bi);
	        
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
	    	if (cardNameFontName != null)
    		{
    			font = new Font(cardNameFontName, cardNameFontStyle, cardNameSize);
    			g2.setFont(font);
    		}
	        FontMetrics metrics = g2.getFontMetrics(font);
	        
	        g2 = setGraphicsHints(g2);
	        
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.name.toUpperCase());
	        int x = (cardWidth / 2) - (stringLength / 2);
	        
	        LineMetrics lm = metrics.getLineMetrics(card.name.toUpperCase(), g2);       
	        
	        g2.drawString(card.name.toUpperCase(), x, cardNameY + (int)lm.getAscent());
	    	if (includeBlurredBGName && nameHighlight != null && nameHighlight.equals("Blur"))
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, cardNameBlurRadius, cardNameBlurDouble, expandCardName, Color.black);
	    	}
	    	
	    	g2.drawString(card.name.toUpperCase(), x, cardNameY + (int)lm.getAscent());
	    	
	    	cardNameEndPos = cardNameY + (int)lm.getHeight();
	    			
	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
	    }
	    
	    // Hero Name
	    if (card.heroName != null)
	    {
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2 = getGraphics(bi);
	        
	    	g2.setColor(heroNameColor);
	    	
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
	        if (heroNameFontName != null)
    		{
    			font = new Font(heroNameFontName, heroNameFontStyle, heroNameSize);
    		}
	        g2.setFont(font);
	        FontMetrics metrics = g2.getFontMetrics(font);
	        LineMetrics lm = metrics.getLineMetrics(card.heroName.toUpperCase(), g2);
	        
	        g2 = setGraphicsHints(g2);
	        
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.heroName.toUpperCase());
	        int x = (cardWidth / 2) - (stringLength / 2);
	        
	        int modifiedY = heroNameY;
	        if (cardNameEndPos > heroNameY)
	        {
	        	modifiedY = cardNameEndPos;
	        }
	        	
	        g2.drawString(card.heroName.toUpperCase(), x, modifiedY + (int)lm.getAscent());
	    	if (includeBlurredBGHeroName && nameHighlight != null && nameHighlight.equals("Blur"))
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, heroNameBlurRadius, heroNameBlurDouble, expandHeroName, Color.black);
	    	}
	    	
	    	g2.drawString(card.heroName.toUpperCase(), x, modifiedY + (int)lm.getAscent());
	    	
	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
	    }
	    
	    // Card Team
	    if (card.cardTeam != null && card.cardTeam.getImagePath() != null)
	    {
	    	if (card.cardTeam2 != null && card.cardTeam != null && !card.cardTeam2.equals(Icon.valueOf("NONE")))
	    	{
	    		BufferedImage bi1 = getIcon(card.cardTeam, teamMaxWidth, teamMaxHeight);
	    		BufferedImage bi2 = getIcon(card.cardTeam2, teamMaxWidth, teamMaxHeight);
		    	int x = teamIconX - (bi1.getWidth() / 2);
		    	int y = teamIconY - (bi1.getWidth() / 2);
		    	
		    	if (includeBlurredBGTeam)
		    	{
		    		drawUnderlay(bi1, g, type, x, y, teamBlurRadius, teamBlurDouble, expandTeam, teamPowerUnderlayColor);
		    		drawUnderlay(bi2, g, type, x + bi1.getWidth(), y, teamBlurRadius, teamBlurDouble, expandTeam, teamPowerUnderlayColor);
		    	}
		    	
		    	g.drawImage(bi1, x, y, null);
		    	g.drawImage(bi2, x + bi1.getWidth(), y, null);
	    	}
	    	else
	    	{
	    		BufferedImage bi = getIcon(card.cardTeam, teamMaxWidth, teamMaxHeight);
		    	int x = teamIconX - (bi.getWidth() / 2);
		    	int y = teamIconY - (bi.getWidth() / 2);
		    	
		    	if (includeBlurredBGTeam)
		    	{
		    		drawUnderlay(bi, g, type, x, y, teamBlurRadius, teamBlurDouble, expandTeam, teamPowerUnderlayColor);
		    	}
		    	
		    	g.drawImage(bi, x, y, null);
	    	}
	    }
	    
	    // Card Power
	    if (card.cardPower != null && card.cardPower.getImagePath() != null)
	    {
	    	if (card.cardPower2 != null && card.cardPower != null && !card.cardPower2.equals(Icon.valueOf("NONE")))
	    	{
	    		BufferedImage bi1 = getIcon(card.cardPower, powerMaxWidth, powerMaxHeight);
	    		BufferedImage bi2 = getIcon(card.cardPower2, powerMaxWidth, powerMaxHeight);
		    	int x = powerIconX - (bi1.getWidth() / 2);
		    	int y = powerIconY - (bi1.getWidth() / 2);
		    	
		    	int offset = 0;
		    	int yoffset = 0;
		    	if (dualClassStyle.equals(DUAL_CLASS_STYLE.SIDE_BY_SIDE))
		    	{
		    		offset = bi1.getWidth();
		    	}
		    	if (dualClassStyle.equals(DUAL_CLASS_STYLE.HALF_AND_HALF))
		    	{
		    		bi1 = clearHalfImage(bi1, true);
		    		bi2 = clearHalfImage(bi2, false);
		    	}
		    	if (dualClassStyle.equals(DUAL_CLASS_STYLE.TOP_AND_BOTTOM))
		    	{
		    		yoffset = bi1.getHeight();
		    	}
		    	
		    	if (includeBlurredBGTeam)
		    	{
		    		drawUnderlay(bi1, g, type, x, y, powerBlurRadius, powerBlurDouble, expandPower, teamPowerUnderlayColor);
		    		drawUnderlay(bi2, g, type, x + offset, y+yoffset, powerBlurRadius, powerBlurDouble, expandPower, teamPowerUnderlayColor);
		    	}
		    	
		    	g.drawImage(bi1, x, y, null);
		    	g.drawImage(bi2, x + offset, y+yoffset, null);
	    	}
	    	else
	    	{
	    		BufferedImage bi = getIcon(card.cardPower, powerMaxWidth, powerMaxHeight);
		    	int x = powerIconX - (bi.getWidth() / 2);
		    	int y = powerIconY - (bi.getWidth() / 2);
		    	
		    	if (includeBlurredBGPower)
		    	{
		    		drawUnderlay(bi, g, type, x, y, powerBlurRadius, powerBlurDouble, expandPower, teamPowerUnderlayColor);
		    	}

		    	g.drawImage(bi, x, y, null);
	    	}
	    }
	    
	    // Recruit
	    if (card.recruit != null)
	    {
	    	ImageIcon ii = new ImageIcon(templateFolder + File.separator + "hero_common" + File.separator + "attr_recruit.png");
	    	g.drawImage(resizeImage(ii, cardWidth, cardHeight), 0, 0, null);
	    	
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2 = getGraphics(bi);
	        
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
	    	if (recruitFontName != null)
    		{
    			font = new Font(recruitFontName, recruitFontStyle, recruitSize);
    		}
	        g2.setFont(font);
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.recruit.toUpperCase());
	        int x = recruitX - (stringLength / 2);
	        
	        LineMetrics lm = metrics.getLineMetrics(card.recruit, g2);
	        int recruitYModified = recruitY + (int)((lm.getAscent() - lm.getDescent()) / 2); 
	   
	        g2 = setGraphicsHints(g2);
	        
	        g2.drawString(card.recruit.toUpperCase(), x, recruitYModified);
	    	if (includeBlurredBGRecruit)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, recruitBlurRadius, recruitBlurDouble, expandRecruit, Color.black);
	    	}
	    	
	    	g2.drawString(card.recruit.toUpperCase(), x, recruitYModified);
	    	
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
	        Graphics2D g2 = getGraphics(bi);
	        
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
	    	if (attackFontName != null)
    		{
    			font = new Font(attackFontName, attackFontStyle, attackSize);
    		}
	        g2.setFont(font);
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.attack.toUpperCase());
	        int x = attackX - (stringLength / 2);
	        
	        g2 = setGraphicsHints(g2);
	        
	        LineMetrics lm = metrics.getLineMetrics(card.attack, g2);
	        int attackYModified = attackY + (int)((lm.getAscent() - lm.getDescent()) / 2);
	        
	        g2.drawString(card.attack.toUpperCase(), x, attackYModified);
	    	if (includeBlurredBGAttack)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, attackBlurRadius, attackBlurDouble, expandAttack, Color.black);
	    	}
	    	
	    	g2.drawString(card.attack.toUpperCase(), x, attackYModified);
	    	
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
	        Graphics2D g2 = getGraphics(bi);
	        
	    	g2.setColor(textColor);
	    	try
	    	{
	    		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Swiss 721 Light Condensed.ttf"));
	    		font = font.deriveFont((float)textSize);
	    		if (textFontName != null)
	    		{
	    			font = new Font(textFontName, textFontStyle, textSize);
	    		}
	    		g2.setFont(font);
	    		
	    		Font fontBold = Font.createFont(Font.TRUETYPE_FONT, new File("Swiss 721 Black Condensed.ttf"));
	    		fontBold = fontBold.deriveFont((float)textSize);
	    		
	    		FontMetrics metrics = g2.getFontMetrics(font);
	    		
	    		g2 = setGraphicsHints(g2);
	    		
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
	            
	            int yOffset = 0;
	            if (card.rarity.equals(CardRarity.RARE))
	            {
	            	yOffset = getYOffset(g2, font, fontBold, x, xOrigin, xEnd, y, yOrigin, metrics, overlay);
	            }
	            
	            //apply modifier
	            yOrigin = yOrigin + textStartOffset + yOffset;
	            y = y + textStartOffset + yOffset;
	            
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
	            
	            List<WordDefinition> words = WordDefinition.getWordDefinitionList(card.abilityText);

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
	    				x += stringLength + SwingUtilities.computeStringWidth(metrics, spaceChar);
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
	    					drawUnderlay(i, g2, BufferedImage.TYPE_INT_ARGB, x, modifiedY, textIconBlurRadius, textIconBlurDouble, expandTextIcon, Color.black);
	    				}
	    				g2.drawImage(i, x, modifiedY, null);
	    				x += i.getWidth() + SwingUtilities.computeStringWidth(metrics, spaceChar);
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
	        Graphics2D g2 = getGraphics(bi);
	        
	    	g2.setColor(costColor);
	        //Font font = new Font("Sylfaen", Font.PLAIN, costSize);
	    	Font font = null;
	    	try
	    	{
	    	font = Font.createFont(Font.TRUETYPE_FONT, new File("Percolator.otf"));
	        font = font.deriveFont((float)120);
	        g2.setFont(font);
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    		
	    		font = new Font("Percolator", Font.PLAIN, 120);
	    		g2.setFont(font);
	    	}
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int originalHeight = metrics.getHeight();
	        
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
	    	if (costFontName != null)
    		{
    			font = new Font(costFontName, costFontStyle, costSize);
    		}
	        g2.setFont(font);
	        metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.cost.toUpperCase());
	        int newHeight = metrics.getHeight();
	        
	        g2 = setGraphicsHints(g2);
	        
	        Integer offset = costOffsets.get("");
	        int primitiveOffset = 0; 
	        for (char c : card.cost.toCharArray())
	        {
	        	offset = costOffsets.get("" + c);
	        	if (offset != null) 
	        	{
	        		double percentage = (double)newHeight / (double)originalHeight;
	        		
	        		primitiveOffset += getPercentage(offset.intValue(), percentage); 
	        	}
	        }
	        int x = costX - (stringLength / 2) + primitiveOffset;
	        //System.out.println("cost x =" + x);
	        
	        LineMetrics lm = metrics.getLineMetrics(card.cost, g2);
	        int costYModified = costY + (int)((lm.getAscent() - lm.getDescent()) / 2);
	        
	        g2.drawString(card.cost.toUpperCase(), x, costYModified);
	    	if (includeBlurredBGCost)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, costBlurRadius, costBlurDouble, expandCost, Color.black);
	    	}
	    	
	    	g2.drawString(card.cost.toUpperCase(), x, costYModified);
	    	
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
		
		BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		g.dispose();
		
		File dir = new File(exportFolder);
		dir.mkdirs();
		
		ImageIO.write(bi, "jpeg", newFile);
	}
	
	public void exportToPNG(BufferedImage image, File newFile) throws Exception
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
	        Graphics2D g = getGraphics(image);
	        
	        g.drawImage(imageIcon.getImage(), 0, 0, w, h, 
	        		0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
	        
	        g.dispose();
	        
	        return image;
	}
	
	public BufferedImage resizeImage(ImageIcon imageIcon, int width, int height)
	{
	        int type = BufferedImage.TYPE_INT_ARGB;
	        
	        BufferedImage image = new BufferedImage(width, height, type);
	        Graphics2D g = getGraphics(image);
	        
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
	
	private BufferedImage blackoutImage(BufferedImage image, Color blackoutColor) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                Color originalColor = new Color(image.getRGB(xx, yy), true);
                //System.out.println(xx + "|" + yy + " color: " + originalColor.toString() + "alpha: " + originalColor.getAlpha());
                if (originalColor.getAlpha() > 0) {
                    image.setRGB(xx, yy, blackoutColor.getRGB());
                }
            }
        }
        return image;
    }
	
	private void drawUnderlay(BufferedImage bi, Graphics2D g, int type, int x, int y, int blurRadius, boolean doubleBlur, int expandBlackout, Color underlayColour)
	{
		BufferedImage blackout = new BufferedImage(cardWidth, cardHeight, type);
    	getGraphics(blackout).drawImage(bi, x, y, null);
    	
    	blackout = blackoutImage(blackout, teamPowerUnderlayColor);
    	
    	if (expandBlackout > 0)
    	{
    		blackout = expandBlackout(blackout, expandBlackout, underlayColour);
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
	
	private BufferedImage expandBlackout(BufferedImage image, int expandBlackout, Color blackoutColor)
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
                		try { expand.setRGB(xx, yy - i, blackoutColor.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx, yy + i, blackoutColor.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx - i, yy, blackoutColor.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx + i, yy, blackoutColor.getRGB()); } catch (Exception e) {}
                    	
                    	if (i == 1)
                    	{
                    	try { expand.setRGB(xx - i, yy - i, blackoutColor.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx - i, yy + i, blackoutColor.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx + i, yy - i, blackoutColor.getRGB()); } catch (Exception e) {}
                    	try { expand.setRGB(xx + i, yy + i, blackoutColor.getRGB()); } catch (Exception e) {}
                    	}
                	}
                }
            }
        }
        return expand;
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
        Graphics2D g = getGraphics(image);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, fullW, fullH);
        
        g.drawImage(imageIcon.getImage(), xPad, yPad, w + xPad, h + yPad, 
        		0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
        
        g.dispose();
        
        return image;
	}
	
	public int getYOffset(Graphics g2, Font font, Font fontBold, int x, int xOrigin, int xEnd, int y, int yOrigin, FontMetrics metrics, BufferedImage overlay)
	{
		int yOffset = 0;
		boolean finished = false;
		do
		{
			
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
            yOrigin = yOrigin + textStartOffset + yOffset;
            y = y + textStartOffset + yOffset;
            
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
            
            List<WordDefinition> words = WordDefinition.getWordDefinitionList(card.abilityText);

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
    				x += stringLength + SwingUtilities.computeStringWidth(metrics, spaceChar);
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
    				x += i.getWidth() + SwingUtilities.computeStringWidth(metrics, spaceChar);
    			}
    		}
    		
    		if (y >= getPercentage(cardHeight, 0.89d))
    		{
    			finished = true;
    		}
    		else
    		{
    			yOffset += metrics.getHeight();
    		}
    		
		} while (finished == false);
		
		return yOffset;
	}
	
	private BufferedImage getFadedBackground(ImageIcon ii)
	{
		BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(ii.getImage(), 0, 0, cardWidth, cardHeight, null);
		
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		int fadeHeight = getPercentage(cardHeight, 0.14d);
		double increment = 255d / (double)fadeHeight;
		
		int alpha = 0;
		for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                Color originalColor = new Color(bi.getRGB(xx, yy), true);
                if (originalColor.getAlpha() > 0) {
                	
                	if (yy <= ((cardHeight / 2) + (fadeHeight / 2)))
                    {
                		
                    	alpha = (int)((((cardHeight / 2) + (fadeHeight / 2)) - yy) * increment);
                    	if (alpha > 255)
                    	{
                    		alpha = 255;
                    	}
              
                    }
                	
                    else
                    {
                    	alpha = 0;
                    }
                	
                    int col = (alpha << 24) | (originalColor.getRed() << 16) | (originalColor.getGreen() << 8) | originalColor.getBlue();
                    bi.setRGB(xx, yy, col);
                }
            }
        }
		
		g.dispose();
		
		return bi;
	}
	
	private BufferedImage clearHalfImage(BufferedImage bi, boolean leftHalf)
	{
		Graphics2D g2D = (Graphics2D)bi.getGraphics();
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		Rectangle2D.Double rect = null;
		if (!leftHalf)
		{
			rect = new Rectangle2D.Double(0,0,bi.getWidth() / 2, bi.getHeight());
		}
		else
		{
			rect = new Rectangle2D.Double(bi.getWidth() / 2,0,bi.getWidth(), bi.getHeight());
		}
		g2D.fill(rect);
		g2D.dispose();
		return bi;
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