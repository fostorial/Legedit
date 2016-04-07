package LegendaryCardMaker.LegendarySchemeMaker;

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
import java.util.ArrayList;
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

public class SchemeMaker extends CardMaker {
	
	public String exportFolder = "cardCreator";
	String templateFolder = "legendary" + File.separator + "templates" + File.separator + LegendaryCardMaker.expansionStyle;
	
	// 2.5 by 3.5 inches - Poker Size
	int cardWidth = 750;
	int cardHeight = 1050;
	int dpi = 300;
	
	public boolean exportToPNG = true;
	
	public int cardNameSize = 40;
	int cardNameMinSize = 30;
	int cardNameY = 50;
	Color cardNameColor = Color.white;
	boolean includeBlurredBGName = true;
	int expandCardName = 2;
	int cardNameBlurRadius = 5;
	boolean cardNameBlurDouble = true;
	
	public int subCategorySize = 33;
	int subCategoryMinSize = 30;
	int subCategoryY = 85;
	Color subCategoryColor = Color.WHITE;
	boolean includeBlurredBGsubCategory = true;
	int expandSubCategory = 2;
	int subCategoryBlurRadius = 5;
	boolean subCategoryBlurDouble = true;
	
	public int textSize = 27;
	public int textHeaderSize = 45;
	int textY = 100;
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
	
	public SchemeCard card;
	
	public BufferedWriter bwErr = null;

	public SchemeMaker()
	{
		
		//populateSchemeCard();
		//generateCard();
	}
	
	/*
	public static void main(String[] args)
	{
		new HeroMaker();
	}
	*/
	
	public void setCard(SchemeCard c)
	{
		card = c;
		
		if (card.cardNameSize > 0)
			cardNameSize = card.cardNameSize;
		
		if (card.subCategorySize > 0)
			subCategorySize = card.subCategorySize;
		
		if (card.cardTextSize > 0)
			textSize = card.cardTextSize;
	}
	
	public void populateSchemeCard()
	{
		card = new SchemeCard();
		card.name = "Desertion of the Dinobots";
		card.subCategory = "";
		card.cardType = SchemeCardType.valueOf("SCHEME");
		card.cardText = "<k>Setup: <r>8 Twists. Hero deck must include at least 3 <DINOBOT> Heroes. <g> " + 
				"<k>Special Rules: <r>Heroes in City or from the Villain Deck counts as Villains with <ATTACK> equal to the Hero's <COST> +2. If you defeat the Hero, you gain it. At the start of each players turn, that player must spend <RECRUIT> equal to the number of <DINOBOT> Heroes in the HQ OR choose a <DINOBOT> to enter the City. <g> " +
				"<k>Twist: <r>Each player chooses a <DINOBOT> from their hand or Discard pile and places it in the Villain Deck. Any player who cannot gains a Wound. Shuffle the Villain Deck. <g> " +
				"<k>Evil Wins: <r>If 5 <DINOBOT> Heroes escape.";
	}
	
	public static SchemeCard getBlankSchemeCard()
	{
		SchemeCard card = new SchemeCard();
		card.name = "Scheme Name";
		card.subCategory = "";
		card.cardType = SchemeCardType.valueOf("SCHEME");
		card.cardText = "<k>Setup: <r> <g> <k>Twist: <r> <g> <k>Evil Wins: <r>";
		return card;
	}
	
	public void populateBlankSchemeCard()
	{
		card = new SchemeCard();
		card.name = "Scheme Name";
		card.subCategory = "";
		card.cardType = SchemeCardType.valueOf("SCHEME");
		card.cardText = "<k>Always Leads: <r>Villain Group <g> <k>Master Strike: <r>Some effect.";
	}
	
	public BufferedImage generateCard()
	{
		
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
		
	 // Card Text
	    if (card.cardText != null)
	    {
	    	String cardString = "";
	    	if (card.cardType.doesAllowHeadings())
	    	{
	    		cardString = card.cardText;
	    	}
	    	else
	    	{
	    		cardString = "<h>" + card.cardType.getDisplayString() + "</h>" + card.cardText;
	    	}
	    	
	    	ImageIcon ii = null;

	    	ii = new ImageIcon(templateFolder + File.separator + "scheme" + File.separator + "scheme_text_underlay.png");
	    	double scale = (double)((double)cardWidth / (double)ii.getIconWidth());
	    	
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	    	Graphics2D g2 = getGraphics(bi);
	        g2.drawImage(ii.getImage(), 0, 0, cardWidth, cardHeight, null);
	        
	        int y = textY;
	    	g2.setColor(textColor);
	    	try
	    	{
	    		
	    		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Swiss 721 Light Condensed.ttf"));
	    		font = font.deriveFont((float)textSize);
	    		g2.setFont(font);
	    		
	    		g2 = setGraphicsHints(g2);
	    		
	    		Font fontBold = Font.createFont(Font.TRUETYPE_FONT, new File("Swiss 721 Black Condensed.ttf"));
	    		fontBold = fontBold.deriveFont((float)textSize);
	    		
	    		//Font fontHeader = new Font("Percolator", Font.PLAIN, textHeaderSize);
	    		Font fontHeader = Font.createFont(Font.TRUETYPE_FONT, new File("Percolator.otf"));
	    		fontHeader = fontHeader.deriveFont((float)textHeaderSize);
	    		
	    		FontMetrics metrics = g2.getFontMetrics(font);
	    		
	    		int x = getPercentage(cardWidth, 0.05);
	    		int xOrigin = x;
	    		y = textY;
	    		int xEnd = cardWidth - getPercentage(cardWidth, 0.05);
	    		int yOrigin = textY;
	    		
	    		System.out.println(cardString);
	    		
	    		String[] sections = cardString.split("<h>");
	    		boolean firstSection = true;
	    		boolean lastWordWasBreak = false;
	    		int lastWordBreakHeight = 0;
	    		for (String sectionString : sections)
	    		{
	    			System.out.println("Section: " + sectionString);
	    			
	    			if (!sectionString.isEmpty())
	    			{
	    				String headerStr = card.cardType.getDisplayString();
	    				if (card.cardType.doesAllowHeadings())
		    			{
		    				headerStr = "";
		    			}
	    				
	    				HeaderIcon headerIcon = null;
		    			String cardStr = sectionString;
		    			if (cardStr.contains("</h>"))
		    			{
		    				String[] headerSplit = sectionString.split("</h>");
		    				headerStr = headerSplit[0];
		    				
		    				//Calculate Header Icons
		    				if (headerStr.toLowerCase().contains("<hi") && headerStr.toLowerCase().contains("/>"))
		    				{
		    					String[] spl = headerStr.split("<hi");
		    					headerStr = spl[0];
		    					String[] spl2 = spl[1].split("/>");
		    					String headerIconText = spl2[0];
		    					
		    					String[] iconPairs = headerIconText.split(" ");
		    					for (String i : iconPairs)
		    					{
		    						if (i.toUpperCase().startsWith("ICON="))
		    						{
		    							if (headerIcon == null)
		    							{
		    								headerIcon = new HeaderIcon();
		    							}
		    							headerIcon.icon = isIcon(i.toUpperCase().trim().replace("ICON=", ""));
		    						}
		    						
		    						if (i.toUpperCase().startsWith("VALUE="))
		    						{
		    							if (headerIcon == null)
		    							{
		    								headerIcon = new HeaderIcon();
		    							}
		    							headerIcon.value = i.toUpperCase().trim().replace("VALUE=", "");
		    						}
		    					}
		    				}
		    				
		    				if (headerSplit.length > 1)
		    				{
		    					cardStr = headerSplit[1];
		    				}
		    				else
		    				{
		    					cardStr = "";
		    				}
		    			}
		    			
		    			System.out.println("header: " + headerStr + ", card: " + cardStr);
		    			
		    			if (headerStr != null && !headerStr.isEmpty())
		    			{
		    				int headerHeight = (int)((double)g.getFontMetrics(fontHeader).getHeight() * 1.2d);
		    				if (lastWordWasBreak)
		    				{
		    					y -= lastWordBreakHeight;
		    				}
			    			drawHeader(g2, headerStr.toUpperCase(), fontHeader, card.cardType.getBgColor(), y, headerHeight, getPercentage(cardWidth, 0.2d), headerIcon);
				    		y += headerHeight + metrics.getHeight() + getPercentage(metrics.getHeight(), 0.5d);
		    			}
		    			else
		    			{
		    				if (firstSection)
		    				{
		    					y += 35;
		    				}
		    			}
		    			
			    		List<WordDefinition> words = WordDefinition.getWordDefinitionList(cardStr);
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
			    				lastWordWasBreak = true;
			    				x = xOrigin;
			    				y += g2.getFontMetrics(font).getHeight() + getPercentage(g2.getFontMetrics(font).getHeight(), textGapHeight);
			    				lastWordBreakHeight = g2.getFontMetrics(font).getHeight() + getPercentage(g2.getFontMetrics(font).getHeight(), textGapHeight);
			    			}
			    			else if (icon == null)
			    			{
			    				lastWordWasBreak = false;
			    				int stringLength = SwingUtilities.computeStringWidth(metrics, s);
			    				
			    					if (x + stringLength > xEnd)
			    					{
			    						x = xOrigin;
			    						y += g2.getFontMetrics(font).getHeight() + getPercentage(g2.getFontMetrics(font).getHeight(), textDefaultGapHeight);
			    					}
			    					
			    				g2.drawString(s + " ", x, y);
			    				x += stringLength + SwingUtilities.computeStringWidth(metrics, spaceChar);
			    			}
			    			else if (icon != null)
			    			{
			    				lastWordWasBreak = false;
			    				BufferedImage i = getIconMaxHeight(icon, getPercentage(metrics.getHeight(), textIconHeight));
			    				
			    				if ((x + i.getWidth() > xEnd))
			   					{
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
			    				x += i.getWidth() + SwingUtilities.computeStringWidth(metrics, spaceChar);
			    			}
			    		}
			    		
			    		y += (int)((double)metrics.getHeight() * 1.0d);
	    			}
	    			
	    			firstSection = false;
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
	    	
	    	g.drawImage(bi, 0, cardHeight - y, null);
    		
	    }
	    
	    // Card Name
	    if (card.name != null)
	    {
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	    	Graphics2D g2 = getGraphics(bi);
	        
	    	g2.setColor(cardNameColor);
	    	Font font = null;
	    	try
	    	{
	        	font = Font.createFont(Font.TRUETYPE_FONT, new File("Percolator.otf"));
	        	font = font.deriveFont((float)cardNameSize);
	    	}
	    	catch (Exception e)
	    	{
    			e.printStackTrace();
    		
    			font = new Font("Sylfaen", Font.PLAIN, cardNameSize);
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
	    
	    // Hero Name
	    if (card.subCategory != null)
	    {
		    
	    	BufferedImage bi = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
	    	Graphics2D g2 = getGraphics(bi);
	        
	    	g2.setColor(subCategoryColor);
	    	Font font = null;
	    	try
	    	{
	    		font = Font.createFont(Font.TRUETYPE_FONT, new File("Percolator.otf"));
		        font = font.deriveFont((float)subCategorySize);
	    	}
	    	catch (Exception e)
	    	{
    			e.printStackTrace();
    		
    			font = new Font("Sylfaen", Font.PLAIN, cardNameSize);
    		}
	        g2.setFont(font);
	        FontMetrics metrics = g2.getFontMetrics(font);
	        int stringLength = SwingUtilities.computeStringWidth(metrics, card.subCategory.toUpperCase());
	        int x = (cardWidth / 2) - (stringLength / 2);
	        
	        g2 = setGraphicsHints(g2);
	        
	        g2.drawString(card.subCategory.toUpperCase(), x, subCategoryY);
	    	if (includeBlurredBGsubCategory)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, subCategoryBlurRadius, subCategoryBlurDouble, expandSubCategory);
	    	}
	    	
	    	g2.drawString(card.subCategory.toUpperCase(), x, subCategoryY);
	    	
	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
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
	
	private void drawHeader(Graphics2D g, String header, Font font, Color color, int y, int height, int blurRadius, HeaderIcon headerIcon)
	{
		BufferedImage bi1 = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = getGraphics(bi1);
		
		g2.setColor(color);
		g2.fillRect(0, y, getPercentage(cardWidth, 0.8d), height);
    	
    	if (blurRadius > 0)
    	{
    		BoxBlurFilter op = new BoxBlurFilter();
    		op.setHRadius(blurRadius);
    		op.setVRadius(0);
        	bi1 = op.filter(bi1, null);
        	makeTransparent(bi1, 0.7d);
    	}
    	
    	g2 = getGraphics(bi1);
    	
    	if (headerIcon != null && headerIcon.icon != null)
	    {
	    	BufferedImage bi = getIcon(headerIcon.icon, getPercentage(height, 1.9d), getPercentage(height, 1.9d));
	    	int iconx = cardWidth - getPercentage(cardWidth, 0.09d) - bi.getWidth() + (bi.getWidth() / 2);
	    	int icony = y + (height / 2) - (bi.getWidth() / 2);
	    	
	    	g2.drawImage(bi, iconx, icony, null);
	    }
    	
    	BufferedImage bi2 = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g3 = getGraphics(bi2);	
		
    	g3.setColor(Color.WHITE);
    	g3.setFont(font);
    	
    	g3 = setGraphicsHints(g3);
    	
		g3.drawString(header, getPercentage(cardWidth, 0.04d), y + g.getFontMetrics(font).getHeight() - (g.getFontMetrics(font).getHeight() / 6));
		
		if (headerIcon != null && headerIcon.value != null)
		{
			Font originalFont = font;
			
			font = font.deriveFont((float)(g.getFontMetrics(originalFont).getHeight() * 1.6f));
			g3.setFont(font);
			
			g3 = setGraphicsHints(g3);
			
			int stringLength = SwingUtilities.computeStringWidth(g.getFontMetrics(font), headerIcon.value.toUpperCase());
			g3.drawString(headerIcon.value, cardWidth - getPercentage(cardWidth, 0.09d) - stringLength + (stringLength / 2), y + g.getFontMetrics(font).getHeight() - (int)(g.getFontMetrics(font).getHeight() / 2.6d));
			
			font = originalFont;
			g3.setFont(originalFont);
			
			g3 = setGraphicsHints(g3);
		}
		
		drawUnderlay(bi2, g3, BufferedImage.TYPE_INT_ARGB, 0, 0, 5, true, 3);
		
		g3.drawString(header, getPercentage(cardWidth, 0.04d), y + g.getFontMetrics(font).getHeight() - (g.getFontMetrics(font).getHeight() / 6));
		
		if (headerIcon != null && headerIcon.value != null)
		{
			Font originalFont = font;
			
			font = font.deriveFont((float)(g.getFontMetrics(originalFont).getHeight() * 1.6f));
			g3.setFont(font);
			g3 = setGraphicsHints(g3);
			int stringLength = SwingUtilities.computeStringWidth(g.getFontMetrics(font), headerIcon.value.toUpperCase());
			g3.drawString(headerIcon.value, cardWidth - getPercentage(cardWidth, 0.09d) - stringLength + (stringLength / 2), y + g.getFontMetrics(font).getHeight() - (int)(g.getFontMetrics(font).getHeight() / 2.6d));
			
			font = originalFont;
			g3.setFont(originalFont);
			g3 = setGraphicsHints(g3);
		}
		
		g.drawImage(bi1, 0, 0, null);
		g.drawImage(bi2, 0, 0, null);
		
		g2.dispose();
		g3.dispose();
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
