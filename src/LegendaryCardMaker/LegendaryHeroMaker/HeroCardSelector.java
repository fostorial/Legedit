package LegendaryCardMaker.LegendaryHeroMaker;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryCardMakerFrame;

public class HeroCardSelector extends JDialog {

	Hero h;
	
	public JList cardList;
	public DefaultListModel cardListModel;
	JScrollPane cardScroll = new JScrollPane();
	
	HeroCardSelectorToolbar tb;
	
	public LegendaryCardMakerFrame lcmf;
	
	public HeroCardSelector(Hero h, LegendaryCardMakerFrame lcmf)
	{
		this.lcmf = lcmf;
		
		tb = new HeroCardSelectorToolbar(this);
		this.setJMenuBar(tb);
		
		this.setTitle("Card Selector: " + h.name);
		
		this.h = h;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		cardListModel = new DefaultListModel();
		for (HeroCard hc : h.cards)
		{
			cardListModel.addElement(hc);
		}
		
		cardList = new JList(cardListModel);
		cardList.setCellRenderer(new CardListRenderer());
		cardList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            if (index >= 0)
		            {
		            	HeroMakerFrame hm = new HeroMakerFrame((HeroCard)list.getSelectedValue());
		            }
		        }
		    }
		});
		
		cardScroll.setViewportView(cardList);
		this.add(cardScroll);
		
		setModal(true);
		setSize(400, 300);
		setVisible(true);
	}
	
	public class CardListRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
        	
        	HeroCard card = (HeroCard)value;

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            if (card.imageSummary != null)
            {
            	label.setIcon(card.imageSummary);
            }
            else
            {
            	card.imageSummary = new ImageIcon(getImageSummary(card));
            	label.setIcon(card.imageSummary);
            }
            label.setHorizontalTextPosition(JLabel.RIGHT);
            String s = card.name + " (" + card.rarity.toString() + ")";
            if (card.changed) { s += " *"; }
            label.setText(s);
            
            return label;
        }
        
        private BufferedImage getImageSummary(HeroCard h)
        {
        	int maxWidth = 24;
        	int maxHeight = 24;
        	
        	BufferedImage bi = new BufferedImage(maxWidth * (3), maxHeight, BufferedImage.TYPE_INT_ARGB);
        	Graphics g2 = bi.getGraphics();
        	
        	int offset = 0;
        	BufferedImage icon = getCardIcon(h.cardTeam, maxWidth, maxHeight);
        	g2.drawImage(icon, offset + ((maxWidth / 2) - (icon.getWidth() / 2)), ((maxHeight / 2) - (icon.getHeight() / 2)), null);
        	
        	offset += maxWidth;
        	icon = getCardIcon(h.cardPower, maxWidth, maxHeight);
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
