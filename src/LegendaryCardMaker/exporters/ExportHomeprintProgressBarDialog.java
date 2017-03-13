package LegendaryCardMaker.exporters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.w3c.dom.Element;

import LegendaryCardMaker.CardMaker;
import LegendaryCardMaker.LegendaryCardMaker;
import LegendaryCardMaker.CustomCardMaker.CustomCardMaker;
import LegendaryCardMaker.CustomCardMaker.structure.CustomCard;
import LegendaryCardMaker.CustomCardMaker.structure.CustomElement;
import LegendaryCardMaker.CustomCardMaker.structure.CustomProperties;
import LegendaryCardMaker.CustomCardMaker.structure.ElementProperty;
import LegendaryCardMaker.LegendaryHeroMaker.Hero;
import LegendaryCardMaker.LegendaryHeroMaker.HeroCard;
import LegendaryCardMaker.LegendaryHeroMaker.HeroMaker;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCard;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeMaker;
import LegendaryCardMaker.LegendaryVillainMaker.Villain;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCard;
import LegendaryCardMaker.LegendaryVillainMaker.VillainMaker;
 
public class ExportHomeprintProgressBarDialog extends JPanel
                             implements ActionListener, 
                                        PropertyChangeListener {
 
    private JProgressBar progressBar;
    private Task task;
    
    private int maxValue;
    private int currentValue;
    
    private LegendaryCardMaker lcm;
    private File folder;
    
    private JDialog frame;
    
    private String exportFileName = "export_";
    
    private List<CardMaker> cardMakers;
 
    class Task extends SwingWorker<Void, Void> {
    	
    	private ExportHomeprintProgressBarDialog bar;
    	
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() throws Exception
        {
        	int max = 0;
        	if (cardMakers == null)
        	{
        		cardMakers = new ArrayList<CardMaker>();
        		
        		for (Hero h : lcm.heroes)
        		{
            		for (HeroCard hc : h.cards)
            		{
            			HeroMaker hm = new HeroMaker();
            			hm.setCard(hc);
            			
            			int count = hc.rarity.getCount();
            			if (hc.numberInDeck > 0)
        				{
        					count = hc.numberInDeck;
        				}
            			for (int i = 0; i < count; i++)
            			{
            				cardMakers.add(hm);
            				max++; 
            			}
            		}
        		}
        		
        		for (Villain v : lcm.villains)
        		{
        			for (VillainCard vc : v.cards)
        			{
        				VillainMaker vm = new VillainMaker();
        				vm.setCard(vc);
        				
        				int count = vc.cardType.getCount();
        				if (vc.numberInDeck > 0)
        				{
        					count = vc.numberInDeck;
        				}
        				for (int i = 0; i < count; i++)
            			{
        					cardMakers.add(vm);
        					max++;
            			}
        			}
        		}
        		
        		for (SchemeCard s : lcm.schemes)
        		{
        			SchemeMaker sm = new SchemeMaker();
        			sm.setCard(s);
        			
        			for (int i = 0; i < s.numberInDeck; i++)
        			{
    					cardMakers.add(sm);
    					max++;
        			}
        		}
        		
        		for (CustomCard s : lcm.customCards)
        		{
        			CustomCardMaker sm = new CustomCardMaker();
        			sm.setCard(s);
        			
        			int numberInDeck = 1;
        			ElementProperty numberInDeckProp = s.template.getProperty(CustomProperties.NUMBERINDECK);
        			if (numberInDeckProp != null)
        			{
        				try
        				{
        					numberInDeck = Integer.parseInt(numberInDeckProp.getValue());
        				}
        				catch (Exception e)
        				{
        					numberInDeck = 1;
        				}
        			}
        			for (int i = 0; i < numberInDeck; i++)
        			{
    					cardMakers.add(sm);
    					max++;
        			}
        		}
        	}
        	else
        	{
        		max = cardMakers.size();
        	}
    		
    		setMaxValue(max);
    		double progressDouble = 0d;
    		double progressIncrement = (100d / (double)max);
    		progressBar.setMaximum(100);
    		
    		int cardWidth = 750;
    		int cardHeight = 1050;
    		int dpi = 300;
    		
    		double xPadding = 0.02;
    		double yPadding = 0.02;
    		int w = cardWidth;
			int xPad = (int)((cardWidth) * xPadding);
			int fullW = w + xPad + xPad;
	        int h = cardHeight;
	        int yPad = (int)((cardHeight) * yPadding);
	        int fullH = h + yPad + yPad;
    		
    		int i = 0;
    		int j = 1;
    		
    		int type = BufferedImage.TYPE_INT_RGB;
            BufferedImage image = new BufferedImage(fullW * 3, fullH * 3, type);
            Graphics g = image.getGraphics();
            
            g.setColor(Color.white);
            g.fillRect(0, 0, image.getWidth(), image.getHeight());
            
            int x = xPad; int y = yPad;
    		for (CardMaker cm : cardMakers)
    		{
    			
    			BufferedImage bi = cm.generateCard();
    			
    			g.drawImage(bi, x, y, null);
    			
    			x += bi.getWidth() + xPad + xPad;
    			if (x >= (xPad + xPad + bi.getWidth()) * 3)
    			{
    				y += bi.getHeight() + yPad + yPad;
    				x = xPad;
    			}
    				
    			i++;
    			if (i == 9)
    			{
    				i=0;
    				x = xPad; y = yPad;
    				try
    				{
    					File newFile = new File(lcm.exportFolder + File.separator + exportFileName + j + ".jpg");
    					//ImageIO.write(image, "jpg", newFile);
    					exportToJPEG(image, newFile);
    				}
    				catch (Exception e) { e.printStackTrace(); }
    				
    				g.dispose();
    				image = new BufferedImage(fullW * 3, fullH * 3, type);
    		        g = image.getGraphics();
    		        g.setColor(Color.white);
    		        g.fillRect(0, 0, image.getWidth(), image.getHeight());
    		        j++;
    			}
    			
    			frame.setTitle("Exporting (" + (getCurrentValue()+1) + "/" + getMaxValue() + ")...");
    			progressDouble = progressDouble + progressIncrement;
    			currentValue++;
    			setProgress((int)progressDouble);
    		
    		}
    		
    		if (i < 9)
    		{
    			i=0;
    			try
    			{
    				File newFile = new File(lcm.exportFolder + File.separator + exportFileName + j + ".jpg");
    				//ImageIO.write(image, "jpg", newFile);
    				exportToJPEG(image, newFile);
    			}
    			catch (Exception e) { e.printStackTrace(); }
    	        j++;
    		}
    		
    		
            return null;
        }
 
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            bar.hideGUI();
        }

		public ExportHomeprintProgressBarDialog getBar() {
			return bar;
		}

		public void setBar(ExportHomeprintProgressBarDialog bar) {
			this.bar = bar;
		}
        
        
    }
 
    public ExportHomeprintProgressBarDialog(int maxValue, LegendaryCardMaker lcm, File folder, List<CardMaker> cardMakers) {
        super(new BorderLayout());
        
        this.lcm = lcm;
        this.folder = folder;
        this.cardMakers = cardMakers;
        
        this.maxValue = maxValue;
 
        progressBar = new JProgressBar(0, maxValue);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
 
        JPanel panel = new JPanel();
        panel.add(progressBar);
 
        add(panel, BorderLayout.PAGE_START);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
 
    }
 
    /**
     * Invoked when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
       
    }
 
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        } 
    }
 
 
    /**
     * Create the GUI and show it. As with all GUI code, this must run
     * on the event-dispatching thread.
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        frame = new JDialog();
        frame.setTitle("Exporting (" + (getCurrentValue()+1) + "/" + "..." + ")...");
        frame.setModal(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        //Create and set up the content pane.
        //JComponent newContentPane = new ExportProgressBarDialog(maxValue, lcm, folder);
        this.setOpaque(true); //content panes must be opaque
        frame.setContentPane(this);
 
        task = new Task();
        task.setBar(this);
        task.addPropertyChangeListener(this);
        task.execute();
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public void hideGUI()
    {
    	frame.setVisible(false);
    }

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}
	
	public static void exportToJPEG(BufferedImage image, File newFile) throws Exception
	{
		int dpi = 300;
		
		FileOutputStream fos = new FileOutputStream(newFile);
		ImageWriter imageWriter = ImageIO.getImageWritersBySuffix("jpeg").next();
		//JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpeg").next();
	    ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
	    imageWriter.setOutput(ios);
	 
	    //and metadata
	    IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image), null);
	    
	  //new metadata
        Element tree = (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
        Element jfif = (Element)tree.getElementsByTagName("app0JFIF").item(0);
        jfif.setAttribute("Xdensity", Integer.toString(dpi));
		jfif.setAttribute("Ydensity", Integer.toString(dpi));
		jfif.setAttribute("resUnits", "1"); // density is dots per inch	
        imageMetaData.setFromTree("javax_imageio_jpeg_image_1.0", tree);
        
        
        JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
        jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(1f);
        
      //new Write and clean up
        imageWriter.write(null, new IIOImage(image, null, imageMetaData), jpegParams);
        ios.flush();
        ios.close();
        fos.flush();
        fos.close();
        imageWriter.dispose();
	}
}