package LegendaryCardMaker;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;

import LegendaryCardMaker.LegendaryCardMakerFrame.TeamListRenderer;
import LegendaryCardMaker.LegendaryVillainMaker.Villain;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCard;

public class CardTextDialog extends JDialog implements ActionListener {
	
	private JTextArea textArea;
	private JScrollPane scroll;
	
	private JToolBar optionsBar = new JToolBar();
	private JButton keywordButton = new JButton("Keyword");
	private JButton regularButton = new JButton("Regular");
	private JButton headingButton = new JButton("Heading");
	private JButton focusButton = new JButton("Focus");
	private JButton headerIconButton = new JButton("Header Icon");
	private JComboBox iconCombo;
	private ComboBoxModel iconListModel;
	private JButton addButton = new JButton("Add");
	
	private JToolBar buttonBar = new JToolBar();
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	
	private String originalText;
	private String returnString;

	public CardTextDialog(String text)
	{
		
		this.setModal(true);
		this.setSize(500, 300);
		
		originalText = text;
		
		keywordButton.addActionListener(this);
		regularButton.addActionListener(this);
		focusButton.addActionListener(this);
		headingButton.addActionListener(this);
		headerIconButton.addActionListener(this);
		addButton.addActionListener(this);
		optionsBar.setFloatable(false);
		optionsBar.add(keywordButton);
		optionsBar.add(regularButton);
		optionsBar.add(focusButton);
		
		List<Icon> icons = Icon.values();
		Collections.sort(icons, new Icon());
		iconCombo = new JComboBox(icons.toArray());
		iconCombo.setRenderer(new IconListRenderer());
		optionsBar.add(iconCombo);
		
		optionsBar.add(addButton);
		this.add(optionsBar, BorderLayout.PAGE_START);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		if (text != null)
		{
			textArea.setText(text.replace(" <g> ", "\n"));
		}
		scroll = new JScrollPane();
		scroll.setViewportView(textArea);
		this.add(scroll, BorderLayout.CENTER);
		
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		buttonBar.setFloatable(false);
		buttonBar.add(okButton);
		buttonBar.add(cancelButton);
		this.add(buttonBar, BorderLayout.PAGE_END);
	}
	
	public void addHeadingButton()
	{
		optionsBar.add(headingButton, 0);
	}
	
	public void addHeaderIconButton()
	{
		optionsBar.add(headerIconButton, 0);
	}
	
	public String showInputDialog()
	{
		this.setVisible(true);
		
		return returnString;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton))
		{
			returnString = textArea.getText().replace("\n", " <g> ");
			this.setVisible(false);
		}
		
		if (e.getSource().equals(cancelButton))
		{
			returnString = originalText;
			this.setVisible(false);
		}
		
		if (e.getSource().equals(keywordButton))
		{
			textArea.insert("<k>", textArea.getCaretPosition());
		}
		
		if (e.getSource().equals(regularButton))
		{
			textArea.insert("<r>", textArea.getCaretPosition());
		}
		
		if (e.getSource().equals(focusButton))
		{
			textArea.insert("⟶", textArea.getCaretPosition());
		}
		
		if (e.getSource().equals(headingButton))
		{
			textArea.insert("<h></h>", textArea.getCaretPosition());
		}
		
		if (e.getSource().equals(headerIconButton))
		{
			textArea.insert("<hi icon= value= />", textArea.getCaretPosition());
		}
		
		if (e.getSource().equals(addButton))
		{
			textArea.insert("<" + ((Icon)iconCombo.getSelectedItem()).getEnumName() + ">", textArea.getCaretPosition());
		}
	}
	
	private class IconListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
        	
        	Icon villain = (Icon)value;

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(new ImageIcon(getImageSummary(villain)));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            
            String s = villain.getEnumName();
            label.setText(s);
            
            return label;
        }
        
        private BufferedImage getImageSummary(Icon v)
        {
        	int maxWidth = 24;
        	int maxHeight = 24;
        	
        	BufferedImage bi = new BufferedImage(maxWidth * (2), maxHeight, BufferedImage.TYPE_INT_ARGB);
        	Graphics g2 = bi.getGraphics();
        	
        	int offset = 0;
        	BufferedImage icon = getCardIcon(v, maxWidth, maxHeight);
        	g2.drawImage(icon, offset + ((maxWidth / 2) - (icon.getWidth() / 2)), ((maxHeight / 2) - (icon.getHeight() / 2)), null);
        	
        	g2.dispose();
        
        	return bi;
        }
    }
	
	public BufferedImage getCardIcon(Icon icon, int maxWidth, int maxHeight)
	{
		if (icon == null || icon.getImagePath() == null)
		{
	        int type = BufferedImage.TYPE_INT_ARGB;
	        BufferedImage image = new BufferedImage(maxWidth, maxHeight, type);
	        return image;
		}
		
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
}
