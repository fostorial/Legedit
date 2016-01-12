package LegendaryCardMaker.LegendaryHeroMaker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryItem;

public class Hero extends LegendaryItem implements Comparator<Hero>, Comparable<Hero> {

	public String name;
	public List<HeroCard> cards = new ArrayList<HeroCard>();
	public boolean changed;
	
	public String imagePath;
	public double imageZoom = 1.0d;
	public int imageOffsetX = 0;
	public int imageOffsetY = 0;
	
	public String dividerIconEnum = null;
	
	public ImageIcon imageSummary;
	
	public String generateOutputString()
	{
		return generateOutputString(false);
	}
	
	public String generateOutputString(boolean fullExport)
	{
		String str = "";
		
		str += "HERO;" + name + "\n\n";
		
		if (imagePath != null)
			str += "HFIMAGEPATH;" + imagePath + "\n";
		
		if (imagePath != null)
			str += "HFIMAGEZOOM;" + imageZoom + "\n";
		
		if (imagePath != null)
			str += "HFIMAGEOFFSETX;" + imageOffsetX + "\n";
		
		if (imagePath != null)
			str += "HFIMAGEOFFSETY;" + imageOffsetY + "\n";
		
		str +="\n";
		
		for (HeroCard hc : cards)
		{
			str += hc.generateOutputString(fullExport) + "\n";
		}
		
		str +="\n";
		
		return str;
	}

	@Override
	public int compareTo(Hero o) {
		return (this.name).compareTo(o.name);
	}

	@Override
	public int compare(Hero o1, Hero o2) {
		return (o1.name).compareTo(o2.name);
	}
	
	public String analyseHero()
	{
		String str = "";
		
		int totalNumberOfCards = 0;
		int totalNumberOfUniqueCards = 0;
		int totalNumberOfHeroCards = 0;
		
		HashMap<Object, Integer> cardRarityHash = new HashMap<Object, Integer>();
		HashMap<Object, Integer> teamHash = new HashMap<Object, Integer>();
		HashMap<Object, Integer> powerHash = new HashMap<Object, Integer>();
		HashMap<Object, Integer> triggerHash = new HashMap<Object, Integer>();
		
		int totalBaseAttack = 0;
		int totalAttackCards = 0;
		HashMap<Object, Integer> baseAttackHash = new HashMap<Object, Integer>();
		
		int totalBaseRecruit = 0;
		int totalRecruitCards = 0;
		HashMap<Object, Integer> baseRecruitHash = new HashMap<Object, Integer>();
		
		int totalBaseCost = 0;
		int totalCostCards = 0;
		HashMap<Object, Integer> baseCostHash = new HashMap<Object, Integer>();
		
			for (HeroCard hc : cards)
			{
				totalNumberOfCards += hc.rarity.getCount();
				totalNumberOfUniqueCards++;
				totalNumberOfHeroCards += hc.rarity.getCount();
				
				updateCount(cardRarityHash, hc.rarity, hc.rarity.getCount());
				updateCount(teamHash, hc.cardTeam, hc.rarity.getCount());
				updateCount(powerHash, hc.cardPower, hc.rarity.getCount());
				
				for (String tr : hc.getTriggers())
				{
					updateCount(triggerHash, tr, hc.rarity.getCount());
				}
				
				Integer baseAttack = hc.getBaseAttack();
				if (baseAttack != null)
				{
					if (baseAttack > 0)
					{
						totalAttackCards += hc.rarity.getCount();
					}
					totalBaseAttack += hc.getBaseAttack() * hc.rarity.getCount();
					updateCount(baseAttackHash, hc.getBaseAttack(), hc.rarity.getCount());
				}
				
				Integer baseRecruit = hc.getBaseRecruit();
				if (baseRecruit != null)
				{
					if (baseRecruit > 0)
					{
						totalRecruitCards += hc.rarity.getCount();
					}
					totalBaseRecruit += hc.getBaseRecruit() * hc.rarity.getCount();
					updateCount(baseRecruitHash, hc.getBaseRecruit(), hc.rarity.getCount());
				}
				
				Integer baseCost = hc.getBaseCost();
				if (baseCost != null)
				{
					if (baseCost > 0)
					{
						totalCostCards += hc.rarity.getCount();
					}
					totalBaseCost += hc.getBaseCost() * hc.rarity.getCount();
					updateCount(baseCostHash, hc.getBaseCost(), hc.rarity.getCount());
				}
			}
		
		DecimalFormat f = new DecimalFormat("##.##");
		
		str += "--------------------------\n";
		str += "HERO: " + name + "\n";
		str += "--------------------------\n";
		str += "\n";
		
		str += "Number of Unique Cards: " + totalNumberOfUniqueCards + "\n";
		str += "Number of Cards: " + totalNumberOfCards + "\n";
		str += "\n";
		
		str += "Card Rarity Counts:\n";
		str += printHash(cardRarityHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Team Counts:\n";
		str += printHash(teamHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Power Counts:\n";
		str += printHash(powerHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Trigger Counts:\n";
		str += printHash(triggerHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Total Base Attack: " + totalBaseAttack + "\n";
		str += "Average Base Attack Per Card (Including 0 Attack): " + f.format((double)((double)totalBaseAttack / (double)totalNumberOfCards)) + "\n";
		str += "Average Base Attack Per Card (Not Including 0 Attack): " + f.format((double)((double)totalBaseAttack / (double)totalAttackCards)) + "\n";
		str += "Cards by Base Attack:\n";
		str += printHash(baseAttackHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Total Base Recruit: " + totalBaseRecruit + "\n";
		str += "Average Base Recruit Per Card (Including 0 Recruit): " + f.format((double)((double)totalBaseRecruit / (double)totalNumberOfCards)) + "\n";
		str += "Average Base Recruit Per Card (Not Including 0 Recruit): " + f.format((double)((double)totalBaseRecruit / (double)totalRecruitCards)) + "\n";
		str += "Cards by Base Recruit:\n";
		str += printHash(baseRecruitHash, totalNumberOfHeroCards);
		str += "\n";
		
		str += "Total Base Cost: " + totalBaseCost + "\n";
		str += "Average Base Cost Per Card (Including 0 Cost): " + f.format((double)((double)totalBaseCost / (double)totalNumberOfCards)) + "\n";
		str += "Average Base Cost Per Card (Not Including 0 Cost): " + f.format((double)((double)totalBaseCost / (double)totalCostCards)) + "\n";
		str += "Cards by Base Cost:\n";
		str += printHash(baseCostHash, totalNumberOfHeroCards);
		str += "\n";
		
		return str;
	}
	
	public void updateCount(HashMap<Object, Integer> hash, Object obj, int value)
	{
		Integer i = hash.get(obj);
		if (i == null) 
		{
			i = new Integer(0);
		}
		i = new Integer(i.intValue() + value);
		hash.put(obj, i);
	}
	
	public String printHash(HashMap<Object, Integer> hash, Integer total)
	{
		DecimalFormat f = new DecimalFormat("##.##");
		
		String str = "";
		
		Set<Map.Entry<Object, Integer>> set = new TreeMap<Object, Integer>(hash).entrySet();
		
		for (Map.Entry<Object, Integer> entry : set)
		{
			str += entry.getKey().toString() + ": " + entry.getValue();
			if (total != null)
			{
				 str += " (" + f.format(getPercentageValue(entry.getValue(), total)) + "%)";
			}
			str += "\n";
		}
		
		return str;
	}
	
	public static double getPercentageValue(int value, int max)
	{
		return (double)((double)(((double)value / (double)max) * 100d));
	}
}
