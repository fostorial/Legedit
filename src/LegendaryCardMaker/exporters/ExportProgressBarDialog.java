package LegendaryCardMaker.exporters;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import LegendaryCardMaker.LegendaryCardMaker;
import LegendaryCardMaker.CustomCardMaker.CustomCardMaker;
import LegendaryCardMaker.CustomCardMaker.structure.CustomCard;
import LegendaryCardMaker.CustomCardMaker.structure.CustomProperties;
import LegendaryCardMaker.CustomCardMaker.structure.ElementProperty;
import LegendaryCardMaker.LegendaryHeroMaker.Hero;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCard;
import LegendaryCardMaker.LegendaryVillainMaker.Villain;

import java.beans.*;
import java.io.File;
import java.util.Random;
 
public class ExportProgressBarDialog extends JPanel
                             implements ActionListener, 
                                        PropertyChangeListener {
 
    private JProgressBar progressBar;
    private Task task;
    
    private int maxValue;
    private int currentValue;
    
    private LegendaryCardMaker lcm;
    private File folder;
    
    private JDialog frame;
    
    private boolean jpegMode = false;
 
    class Task extends SwingWorker<Void, Void> {
    	
    	private ExportProgressBarDialog bar;
    	
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() throws Exception
        {
        	
        	for (Hero h : lcm.heroes)
    		{
        		if (jpegMode)
    				lcm.exportHeroToJpeg(h, folder);
    			else
    				lcm.exportHeroToPng(h, folder);
    			
    			frame.setTitle("Exporting (" + (getCurrentValue()+1) + "/" + getMaxValue() + ")...");
    			setProgress(currentValue++);
    		}
    		
    		for (Villain v : lcm.villains)
    		{
    			if (jpegMode)
    				lcm.exportVillainToJpeg(v, folder);
    			else
    				lcm.exportVillainToPng(v, folder);
    			
    			frame.setTitle("Exporting (" + (getCurrentValue()+1) + "/" + getMaxValue() + ")...");
    			setProgress(currentValue++);
    		}
    		
    		for (SchemeCard s : lcm.schemes)
    		{
    			if (jpegMode)
    				lcm.exportSchemeToJpeg(s, folder);
    			else
    				lcm.exportSchemeToPng(s, folder);
    			
    			frame.setTitle("Exporting (" + (getCurrentValue()+1) + "/" + getMaxValue() + ")...");
    			 setProgress(currentValue++);
    		}
    		
    		for (CustomCard s : lcm.customCards)
    		{
    			if (jpegMode)
    				lcm.exportCustomCardToJpeg(s, folder);
    			else
    				lcm.exportCustomCardToPng(s, folder);
    			
    			frame.setTitle("Exporting (" + (getCurrentValue()+1) + "/" + getMaxValue() + ")...");
    			 setProgress(currentValue++);
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

		public ExportProgressBarDialog getBar() {
			return bar;
		}

		public void setBar(ExportProgressBarDialog bar) {
			this.bar = bar;
		}
        
        
    }
 
    public ExportProgressBarDialog(int maxValue, LegendaryCardMaker lcm, File folder) {
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

	public boolean isJpegMode() {
		return jpegMode;
	}

	public void setJpegMode(boolean jpegMode) {
		this.jpegMode = jpegMode;
	}
}
