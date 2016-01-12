package LegendaryCardMaker.LegendaryVillainMaker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import LegendaryCardMaker.LegendaryItem;

public class Villain extends LegendaryItem implements Comparator<Villain>, Comparable<Villain>  {

	public String name;
	public List<VillainCard> cards = new ArrayList<VillainCard>();
	public boolean changed = false;
	
	public String imagePath;
	public double imageZoom = 1.0d;
	public int imageOffsetX = 0;
	public int imageOffsetY = 0;
	
	public String dividerIconEnum = null;
	
	public String generateOutputString()
	{
		return generateOutputString(false);
	}
	public String generateOutputString(boolean fullExport)
	{
		String str = "";
		
		str += "VILLAIN;" + name + "\n\n";
		
		if (imagePath != null)
			str += "VFIMAGEPATH;" + imagePath + "\n";
		
		if (imagePath != null)
			str += "VFIMAGEZOOM;" + imageZoom + "\n";
		
		if (imagePath != null)
			str += "VFIMAGEOFFSETX;" + imageOffsetX + "\n";
		
		if (imagePath != null)
			str += "VFIMAGEOFFSETY;" + imageOffsetY + "\n";
		
		str +="\n";
		
		for (VillainCard vc : cards)
		{
			str += vc.generateOutputString(fullExport) + "\n";
		}
		
		str +="\n";
		
		return str;
	}

	@Override
	public int compareTo(Villain o) {
		return (this.name).compareTo(o.name);
	}

	@Override
	public int compare(Villain o1, Villain o2) {
		return (o1.name).compareTo(o2.name);
	}
}
