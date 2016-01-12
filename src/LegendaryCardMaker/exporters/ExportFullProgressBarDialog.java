package LegendaryCardMaker.exporters;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import LegendaryCardMaker.LegendaryCardMaker;
import LegendaryCardMaker.LegendaryCardMakerFrame;
import LegendaryCardMaker.CustomCardMaker.structure.CustomCard;
import LegendaryCardMaker.CustomCardMaker.structure.CustomElement;
import LegendaryCardMaker.CustomCardMaker.structure.ElementBackgroundImage;
import LegendaryCardMaker.CustomCardMaker.structure.ElementImage;
import LegendaryCardMaker.LegendaryHeroMaker.Hero;
import LegendaryCardMaker.LegendaryHeroMaker.HeroCard;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCard;
import LegendaryCardMaker.LegendaryVillainMaker.Villain;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCard;

import java.beans.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Savepoint;
import java.util.Random;
 
public class ExportFullProgressBarDialog extends JPanel
                             implements ActionListener, 
                                        PropertyChangeListener {
 
    private JProgressBar progressBar;
    private Task task;
    
    private int maxValue;
    private int currentValue;
    
    private LegendaryCardMaker lcm;
    private File folder;
    
    private JDialog frame;
 
    class Task extends SwingWorker<Void, Void> {
    	
    	private ExportFullProgressBarDialog bar;
    	
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() throws Exception
        {
        	System.out.println("Full Export Started");
        	
        	try
        	{
        		File tempFolder = new File(folder.getName().replace(".", "") + File.separator);
            	tempFolder.mkdirs();
            	
            	if (LegendaryCardMakerFrame.lcmf.lcm.dbImagePath != null)
    			{
    				File newFile = new File(tempFolder.getAbsolutePath() + File.separator + new File(LegendaryCardMakerFrame.lcmf.lcm.dbImagePath).getName());
        			try {
        				copyFile(new File(LegendaryCardMakerFrame.lcmf.lcm.dbImagePath), newFile);
        				LegendaryCardMakerFrame.lcmf.lcm.dbImagePath = new File(LegendaryCardMakerFrame.lcmf.lcm.dbImagePath).getName();
        			} catch (IOException e1) {
        				LegendaryCardMakerFrame.lcmf.lcm.dbImagePath = null;
        			}
    			}
            	
            	if (LegendaryCardMakerFrame.lcmf.lcm.dfImagePath != null)
    			{
    				File newFile = new File(tempFolder.getAbsolutePath() + File.separator + new File(LegendaryCardMakerFrame.lcmf.lcm.dfImagePath).getName());
        			try {
        				copyFile(new File(LegendaryCardMakerFrame.lcmf.lcm.dfImagePath), newFile);
        				LegendaryCardMakerFrame.lcmf.lcm.dfImagePath = new File(LegendaryCardMakerFrame.lcmf.lcm.dfImagePath).getName();
        			} catch (IOException e1) {
        				LegendaryCardMakerFrame.lcmf.lcm.dfImagePath = null;
        			}
    			}
            	
            	for (Hero h : lcm.heroes)
        		{
        			
            		for (HeroCard hc : h.cards)
            		{
            			if (hc.imagePath != null)
            			{
            				File newFile = new File(tempFolder.getAbsolutePath() + File.separator + new File(hc.imagePath).getName());
                			try {
                				copyFile(new File(hc.imagePath), newFile);
                				hc.imagePath = new File(hc.imagePath).getName();
                			} catch (IOException e1) {
                				hc.imagePath = null;
                			}
            			}
            		}
            		
            		if (h.imagePath != null)
        			{
        				File newFile = new File(tempFolder.getAbsolutePath() + File.separator + new File(h.imagePath).getName());
            			try {
            				copyFile(new File(h.imagePath), newFile);
            				h.imagePath = new File(h.imagePath).getName();
            			} catch (IOException e1) {
            				h.imagePath = null;
            			}
        			}
        			
        			frame.setTitle("Exporting (" + (getCurrentValue()+1) + "/" + getMaxValue() + ")...");
        			setProgress(currentValue++);
        		}
        		
        		for (Villain v : lcm.villains)
        		{
        			for (VillainCard hc : v.cards)
            		{
            			if (hc.imagePath != null)
            			{
            				File newFile = new File(tempFolder.getAbsolutePath() + File.separator + new File(hc.imagePath).getName());
                			try {
                				copyFile(new File(hc.imagePath), newFile);
                				hc.imagePath = new File(hc.imagePath).getName();
                			} catch (IOException e1) {
                				hc.imagePath = null;
                			}
            			}
            		}
        			
        			if (v.imagePath != null)
        			{
        				File newFile = new File(tempFolder.getAbsolutePath() + File.separator + new File(v.imagePath).getName());
            			try {
            				copyFile(new File(v.imagePath), newFile);
            				v.imagePath = new File(v.imagePath).getName();
            			} catch (IOException e1) {
            				v.imagePath = null;
            			}
        			}
        			
        			frame.setTitle("Exporting (" + (getCurrentValue()+1) + "/" + getMaxValue() + ")...");
        			setProgress(currentValue++);
        		}
        		
        		for (SchemeCard s : lcm.schemes)
        		{
        			if (s.imagePath != null)
        			{
        				File newFile = new File(tempFolder.getAbsolutePath() + File.separator + new File(s.imagePath).getName());
            			try {
            				copyFile(new File(s.imagePath), newFile);
            				s.imagePath = new File(s.imagePath).getName();
            			} catch (IOException e1) {
            				s.imagePath = null;
            			}
        			}
        			
        			frame.setTitle("Exporting (" + (getCurrentValue()+1) + "/" + getMaxValue() + ")...");
        			 setProgress(currentValue++);
        		}
        		
        		for (CustomCard s : lcm.customCards)
        		{
        			for (CustomElement el : s.template.elements)
        			{
        				if (el instanceof ElementImage)
        				{
        					ElementImage eli = (ElementImage)el;
        					if (eli.path != null)
        					{
        						File newFile = new File(tempFolder.getAbsolutePath() + File.separator + new File(eli.path).getName());
                    			try {
                    				copyFile(new File(eli.path), newFile);
                    				eli.path = new File(eli.path).getName();
                    			} catch (IOException e1) {
                    				eli.path = null;
                    			}
        					}
        				}
        				
        				if (el instanceof ElementBackgroundImage)
        				{
        					ElementBackgroundImage eli = (ElementBackgroundImage)el;
        					if (eli.path != null)
        					{
        						File newFile = new File(tempFolder.getAbsolutePath() + File.separator + new File(eli.path).getName());
                    			try {
                    				copyFile(new File(eli.path), newFile);
                    				eli.path = new File(eli.path).getName();
                    			} catch (IOException e1) {
                    				eli.path = null;
                    			}
        					}
        				}
        			}
        			
        			frame.setTitle("Exporting (" + (getCurrentValue()+1) + "/" + getMaxValue() + ")...");
        			 setProgress(currentValue++);
        		}
        		
        		lcm.saveExpansion(true, tempFolder.getAbsolutePath() + File.separator + new File(lcm.currentFile).getName());
        		
        		ZipperHelper.createCBZ(tempFolder.getAbsolutePath() + File.separator, folder.getAbsolutePath());
        		
        		for (File f : tempFolder.listFiles())
        		{
        			f.delete();
        		}
        		tempFolder.delete();
        		
        		lcm.processInput(lcm.currentFile);
        	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        	
    		System.out.println("Full Export Finished");
    		
            return null;
        }
 
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            bar.hideGUI();
        }

		public ExportFullProgressBarDialog getBar() {
			return bar;
		}

		public void setBar(ExportFullProgressBarDialog bar) {
			this.bar = bar;
		}
        
        
    }
 
    public ExportFullProgressBarDialog(int maxValue, LegendaryCardMaker lcm, File folder) {
        super(new BorderLayout());
        
        this.lcm = lcm;
        this.folder = folder;
        
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
        frame.setTitle("Exporting (" + (getCurrentValue()+1) + "/" + getMaxValue() + ")...");
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
	
	public void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }
	    else
	    {
	    	return;
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
}
