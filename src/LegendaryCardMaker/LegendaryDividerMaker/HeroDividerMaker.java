package LegendaryCardMaker.LegendaryDividerMaker;

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
import java.util.List;
import java.util.Map.Entry;

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
import LegendaryCardMaker.DividerMaker;
import LegendaryCardMaker.HSLColor;
import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryCardMaker;
import LegendaryCardMaker.LegendaryCardMakerFrame;
import LegendaryCardMaker.WordDefinition;
import LegendaryCardMaker.LegendaryHeroMaker.Hero;
import LegendaryCardMaker.LegendaryHeroMaker.HeroCard;

public class HeroDividerMaker extends DividerMaker {
	
	public String exportFolder = "cardCreator";
	
	int verticalWidth = 787;
	int verticalHeight = 1087;
	int horizontalWidth = 1100;
	int horizontalHeight = 800;
	int width = horizontalWidth;
	int height = horizontalHeight;
	int dpi = 300;
	
	boolean exportToPNG = true;
	
	int teamIconX = 44;
	int teamIconY = 39;
	int teamMaxWidth = 54;
	int teamMaxHeight = 54;
	boolean includeBlurredBGTeam = true;
	int expandTeam = 1;
	int teamBlurRadius = 5;
	boolean teamBlurDouble = true;
	
	int powerIconX = 15;
	int powerIconY = 39;
	int powerMaxWidth = 54;
	int powerMaxHeight = 54;
	boolean includeBlurredBGPower = true;
	int expandPower = 1;
	int powerBlurRadius = 5;
	boolean powerBlurDouble = true;
	
	public int heroNameSize = 55;
	int heroNameMinSize = 30;
	int heroNameX = 130;
	int heroNameY = 55;
	Color heroNameColor = Color.white; //new Color (255,186,20);
	boolean includeBlurredBGHeroName = true;
	int expandHeroName = 3;
	int heroNameBlurRadius = 5;
	boolean heroNameBlurDouble = true;
	
	int titleBarHeight = 76;

	public Hero hero;
	
	public BufferedWriter bwErr = null;
	
	public boolean horizontal;

	public HeroDividerMaker(Hero h, boolean horizontal)
	{
		this.hero = h;
		this.horizontal = horizontal;
	}
	
	public void setHero(Hero h)
	{
		hero = h;
	}
	
	
	public BufferedImage generateDivider()
	{	
		if (horizontal)
		{
			width = horizontalWidth;
			height = horizontalHeight;
		}
		else
		{
			width = verticalWidth;
			height = verticalHeight;
		}
		
		int type = BufferedImage.TYPE_INT_RGB;
		if (exportToPNG)
		{
			type = BufferedImage.TYPE_INT_ARGB;	
		}
	    
		BufferedImage image = new BufferedImage(width, height, type);
	    Graphics2D g = (Graphics2D)image.getGraphics();
	    
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    
	    g.setColor(Color.white);
	    g.fillRect(0, 0, width, height);
	    g.setColor(Color.black);
	    
	    String cardStyle = LegendaryCardMakerFrame.lcmf.lcm.dividerCardStyle;
	    if (cardStyle != null) {cardStyle.replace(" ", ""); }
	    
	    String bodyStyle = LegendaryCardMakerFrame.lcmf.lcm.dividerBodyStyle;
	    if (cardStyle != null) {cardStyle.replace(" ", ""); }
	    
	    // Background Images
	    if (bodyStyle.equals("Images"))
	    {
	    	if (LegendaryCardMakerFrame.lcmf != null && LegendaryCardMakerFrame.lcmf.lcm != null
		    		&& LegendaryCardMakerFrame.lcmf.lcm.dbImagePath != null)
		    {
	    		String imagePath = LegendaryCardMakerFrame.lcmf.lcm.dbImagePath;
		    	if (!imagePath.contains(File.separator) && LegendaryCardMakerFrame.lcmf.lcm.currentFile != null)
		    	{
		    		imagePath = new File(LegendaryCardMakerFrame.lcmf.lcm.currentFile).getParent() + File.separator + LegendaryCardMakerFrame.lcmf.lcm.dbImagePath;
		    	}
		    	BufferedImage bi = resizeImage(new ImageIcon(imagePath), LegendaryCardMakerFrame.lcmf.lcm.dbImageZoom);
		    	g.drawImage(bi, LegendaryCardMakerFrame.lcmf.lcm.dbImageOffsetX, LegendaryCardMakerFrame.lcmf.lcm.dbImageOffsetY, null);
		    }
		    
		    if (LegendaryCardMakerFrame.lcmf != null && LegendaryCardMakerFrame.lcmf.lcm != null
		    		&& LegendaryCardMakerFrame.lcmf.lcm.dfImagePath != null)
		    {
		    	String imagePath = LegendaryCardMakerFrame.lcmf.lcm.dfImagePath;
		    	if (!imagePath.contains(File.separator) && LegendaryCardMakerFrame.lcmf.lcm.currentFile != null)
		    	{
		    		imagePath = new File(LegendaryCardMakerFrame.lcmf.lcm.currentFile).getParent() + File.separator + LegendaryCardMakerFrame.lcmf.lcm.dfImagePath;
		    	}
		    	BufferedImage bi = resizeImage(new ImageIcon(imagePath), LegendaryCardMakerFrame.lcmf.lcm.dfImageZoom);
		    	g.drawImage(bi, LegendaryCardMakerFrame.lcmf.lcm.dfImageOffsetX, LegendaryCardMakerFrame.lcmf.lcm.dfImageOffsetY, null);
		    }
		    
		    if (hero.imagePath != null)
		    {
		    	String imagePath = hero.imagePath;
		    	if (!imagePath.contains(File.separator) && LegendaryCardMakerFrame.lcmf.lcm.currentFile != null)
		    	{
		    		imagePath = new File(LegendaryCardMakerFrame.lcmf.lcm.currentFile).getParent() + File.separator + hero.imagePath;
		    	}
		    	BufferedImage bi = resizeImage(new ImageIcon(imagePath), hero.imageZoom);
		    	g.drawImage(bi, hero.imageOffsetX, hero.imageOffsetY, null);
		    }
	    }
	    if (bodyStyle.equals("TeamLogo"))
	    {
	    	BufferedImage bi = getIcon(getTeamIcon(), getPercentage(width, 0.7d), getPercentage(height, 0.7d));
	    	
	    	HashMap<Color, Integer> colorMap = new HashMap<Color, Integer>();
	    	for (int xx = 0; xx < bi.getWidth(); xx++) {
	            for (int yy = 0; yy < bi.getHeight(); yy++) {
	                Color originalColor = new Color(bi.getRGB(xx, yy), true);
	                if (originalColor.getAlpha() > 0 
	                		&& !(originalColor.getRed() < 10 && originalColor.getGreen() < 10 && originalColor.getBlue() < 10)
	                		&& !(originalColor.getRed() > 245 && originalColor.getGreen() > 245 && originalColor.getBlue() > 245)) {
	                	if (colorMap.containsKey(originalColor))
	    	    		{
	    	    			Integer i = colorMap.get(originalColor);
	    	    			i = new Integer(i.intValue() + 1);
	    	    			colorMap.put(originalColor, i);
	    	    		}
	    	    		else
	    	    		{
	    	    			colorMap.put(originalColor, new Integer(1));
	    	    		}
	                }
	            }
	        }
	    	
	    	int count = 0;
	    	Color mainColor = Color.WHITE;
    		for (Entry<Color, Integer> en : colorMap.entrySet())
    		{
    			System.out.println(en.getKey().toString() + ":" + en.getValue());
    			if (en.getValue().intValue() > count)
    			{
    				count = en.getValue().intValue();
    				mainColor = en.getKey();
    			}
    		}
	    	
    		HSLColor hsl = new HSLColor(mainColor);
    		g.setColor(hsl.getComplementary());
    		g.fillRect(0, titleBarHeight, width, height);
    		
	    	int modifiedHeight = height - titleBarHeight;
	    	int iconX = (width / 2) - (bi.getWidth() / 2);
	    	int iconY = titleBarHeight + (modifiedHeight / 2) - (bi.getHeight() / 2);
	    	g.drawImage(bi, iconX, iconY, null);
	    }
	    
	    
	    // Title Bar
	    if  (LegendaryCardMakerFrame.lcmf.lcm.dividerTitleBarVisible)
	    {
	    	g.setColor(LegendaryCardMakerFrame.lcmf.lcm.dividerTitleBarColour);
	    	g.fillRect(0, 0, width, titleBarHeight);
	    	g.setColor(Color.BLACK);
	    }
	    
	    
	    // Card Team
	    int heroNameStart = heroNameX;
	    if (hero.cards != null && hero.cards.size() > 0)
	    {
	    	Icon teamIcon = getTeamIcon();
        	
        	if (teamIcon != null && teamIcon.getImagePath() != null)
        	{
        		heroNameStart = heroNameX;
        		
        		BufferedImage bi = getIcon(hero.cards.get(0).cardTeam, teamMaxWidth, teamMaxHeight);
    	    	int x = teamIconX - (bi.getWidth() / 2);
    	    	int y = teamIconY - (bi.getWidth() / 2);
    	    	
    	    	if (includeBlurredBGTeam)
    	    	{
    	    		drawUnderlay(bi, g, type, x, y, teamBlurRadius, teamBlurDouble, expandTeam);
    	    	}
    	    	
    	    	g.drawImage(bi, x, y, null);
        	}
	    }
	    
	    
	    // Card Power
	    int heroNameEnd = width;
	    if (cardStyle.equals("PowerIcons"))
	    {
	    	heroNameEnd = width - teamIconX;
		    if (hero.cards != null && hero.cards.size() > 0)
		    {
		    	if (horizontal)
		    	{
		    		int tempX = width - (hero.cards.size() * (powerMaxWidth + 5)) - powerIconX;
			    	heroNameEnd = tempX - 15;
			    	for (HeroCard hc : hero.cards)
			    	{
			    		if (hc.cardPower != null && !hc.cardPower.equals(Icon.valueOf("NONE")))
			    		{
			    			BufferedImage bi = getIcon(hc.cardPower, powerMaxWidth, powerMaxHeight);
					    	int y = teamIconY - (bi.getWidth() / 2);
					    	
					    	if (includeBlurredBGTeam)
					    	{
					    		drawUnderlay(bi, g, type, tempX, y, teamBlurRadius, teamBlurDouble, expandTeam);
					    	}
					    	
					    	g.drawImage(bi, tempX, y, null);
			    		}
			    		tempX += powerMaxWidth + 5;
			    	}
		    	}
		    	else
		    	{
		    		int count = 0;
		    		int tempX = width - powerMaxWidth - 15;
		    		int tempY = powerIconY / 3;
		    		heroNameEnd = tempX - 15;
			    	for (HeroCard hc : hero.cards)
			    	{
			    		if (hc.cardPower != null && !hc.cardPower.equals(Icon.valueOf("NONE")))
			    		{
			    			BufferedImage bi = getIcon(hc.cardPower, powerMaxWidth / 2, powerMaxHeight / 2);
					    	
					    	if (includeBlurredBGTeam)
					    	{
					    		drawUnderlay(bi, g, type, tempX, tempY, teamBlurRadius, teamBlurDouble, expandTeam);
					    	}
					    	
					    	g.drawImage(bi, tempX, tempY, null);
			    		}
			    		tempX = tempX + (powerMaxWidth / 2);
			    		
			    		count++;
			    		if (count == 2)
			    		{
			    			count = 0;
			    			tempX = width - powerMaxWidth - 15;
			    			tempY += (powerMaxHeight/2) + (5/2);
			    		}
			    	}
		    	}
		    	
		    }
	    }
	    
	    
	    
	    // Hero Name
	    if (hero.name != null)
	    {
	    	BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
	        int stringLength = SwingUtilities.computeStringWidth(metrics, hero.name.toUpperCase());
	        int heroLength = width - heroNameStart - (width - heroNameEnd);
	        int x = heroNameStart + ((heroLength / 2) - (stringLength / 2));
	        
	        g2.drawString(hero.name.toUpperCase(), x, heroNameY);
	    	if (includeBlurredBGHeroName)
	    	{
	    		drawUnderlay(bi, g2, type, 0, 0, heroNameBlurRadius, heroNameBlurDouble, expandHeroName);
	    	}
	    	
	    	g2.drawString(hero.name.toUpperCase(), x, heroNameY);
	    	
	    	g.drawImage(bi, 0, 0, null);
	    	
	    	g2.dispose();
	    }
	    

		g.dispose();
		
		return image;
	}
	
	public void exportHeroImage(BufferedImage image)
	{
		try
		{
			//ImageIO.write(image, "jpg", newFile);
			if (exportToPNG)
			{
				File newFile = new File(exportFolder + File.separator + "divider_" + hero.name + ".png");
				exportToPNG(image, newFile);
			}
			else
			{
				File newFile = new File(exportFolder + File.separator + "divider_" + hero.name + ".jpg");
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
		BufferedImage blackout = new BufferedImage(width, height, type);
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
		BufferedImage expand = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
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
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
	
	private Icon getTeamIcon()
	{
		Icon teamIcon = Icon.valueOf("NONE");
    	HashMap<Icon, Integer> teamMap = new HashMap<Icon, Integer>();
    	for (HeroCard hc : hero.cards)
    	{
    		if (teamMap.containsKey(hc.cardTeam))
    		{
    			Integer i = teamMap.get(hc.cardTeam);
    			i = new Integer(i.intValue() + 1);
    			teamMap.put(hc.cardTeam, i);
    		}
    		else
    		{
    			teamMap.put(hc.cardTeam, new Integer(1));
    		}
    		
    		int count = 0;
    		for (Entry<Icon, Integer> en : teamMap.entrySet())
    		{
    			if (en.getValue().intValue() > count)
    			{
    				count = en.getValue().intValue();
    				teamIcon = en.getKey();
    			}
    		}
    	}
    	
    	return teamIcon;
	}

}
