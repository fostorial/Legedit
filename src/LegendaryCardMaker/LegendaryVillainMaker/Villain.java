package LegendaryCardMaker.LegendaryVillainMaker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Villain implements Comparator<Villain>, Comparable<Villain>  {

	public String name;
	public List<VillainCard> cards = new ArrayList<VillainCard>();
	public boolean changed = false;
	
	public String generateOutputString()
	{
		String str = "";
		
		str += "VILLAIN;" + name + "\n\n";
		
		for (VillainCard vc : cards)
		{
			str += vc.generateOutputString() + "\n";
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
