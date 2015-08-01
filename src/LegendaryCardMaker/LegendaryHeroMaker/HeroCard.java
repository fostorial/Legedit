package LegendaryCardMaker.LegendaryHeroMaker;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCard;
import LegendaryCardMaker.LegendaryVillainMaker.Villain;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCard;

public class HeroCard {

	public String name;
	public int nameSize;
	public String heroName;
	public int heroNameSize;
	public CardRarity rarity;
	public Icon cardTeam;
	public Icon cardTeam2;
	public Icon cardPower;
	public Icon cardPower2;
	public String attack;
	public String recruit;
	public String cost;
	public String abilityText;
	public int abilityTextSize;
	public boolean changed;
	public String imagePath;
	public double imageZoom = 1.0d;
	public int imageOffsetX = 0;
	public int imageOffsetY = 0;
	public int numberInDeck = -1;
	
	public ImageIcon imageSummary;
	
	public String getCardName(String exportDir)
	{
		if (rarity != null && rarity.equals(CardRarity.COMMON))
		{
			int i = 1;
			String fileName = heroName.replace(" ", "") + "_" + rarity.toString().toLowerCase() + "_" + i;
		
			do
			{
				fileName = heroName.replace(" ", "") + "_" + rarity.toString().toLowerCase() + "_" + i;
				i++;
			}
			while (new File(exportDir + File.separator + fileName + ".jpg").exists() 
					|| new File(exportDir + File.separator + fileName + ".png").exists());
			
			return fileName;
		}
		else if (rarity != null)
		{
			int i = 1;
			String filename = heroName.replace(" ", "") + "_" + rarity.toString().toLowerCase() + "_" + i;
			
			do
			{
				filename = heroName.replace(" ", "") + "_" + rarity.toString().toLowerCase() + "_" + i;
				i++;
			}
			while (new File(exportDir + File.separator + filename + ".jpg").exists() 
					|| new File(exportDir + File.separator + filename + ".png").exists());
			
			return filename;
		}
		else
		{
			int i = 1;
			String filename = heroName.replace(" ", "") + "_" + name + "_" + i;
			
			do
			{
				filename = heroName.replace(" ", "") + "_" + name + "_" + i;
				i++;
			}
			while (new File(exportDir + File.separator + filename + ".jpg").exists() 
					|| new File(exportDir + File.separator + filename + ".png").exists());
			
			return filename;
		}
	}
	
	public String generateOutputString()
	{
		return generateOutputString(false);
	}
	
	public String generateOutputString(boolean fullExport)
	{
		String str = "";
		
		//str += "HCNAMESIZE;" + 40 + "\n";
		
		str += "HEROCARD;\n";
		
		str += "HCNAME;" + name + "\n";
		
		if (name != null)
			str += "HCNAMESIZE;" + nameSize + "\n";
		
		if (name != null)
			str += "HCHERONAMESIZE;" + heroNameSize + "\n";
		
		if (rarity != null)
			str += "HCRARITY;" + rarity.toString() + "\n";
		
		if (cardTeam != null)
			str += "HCTEAM;" + cardTeam.toString() + "\n";
		
		if (cardTeam2 != null)
			str += "HCTEAM2;" + cardTeam2.toString() + "\n";
		
		if (cardPower != null)
			str += "HCPOWER;" + cardPower.toString() + "\n";
		
		if (cardPower2 != null)
			str += "HCPOWER2;" + cardPower2.toString() + "\n";
		
		if (attack != null)
			str += "HCATTACK;" + attack + "\n";
		
		if (recruit != null)
			str += "HCRECRUIT;" + recruit + "\n";
		
		if (cost != null)
			str += "HCCOST;" + cost + "\n";
		
		if (abilityText != null)
			str += "HCTEXT;" + abilityText + "\n";
		
		if (abilityText != null)
			str += "HCTEXTSIZE;" + abilityTextSize + "\n";
		
		if (imagePath != null)
			str += "HCIMAGEPATH;" + imagePath + "\n";
		
		if (imagePath != null)
			str += "HCIMAGEZOOM;" + imageZoom + "\n";
		
		if (imagePath != null)
			str += "HCIMAGEOFFSETX;" + imageOffsetX + "\n";
		
		if (imagePath != null)
			str += "HCIMAGEOFFSETY;" + imageOffsetY + "\n";
		
		str += "HCNUMBERINDECK;" + numberInDeck + "\n";
		
		str += "HCGENERATE;\n";
		
		return str;
	}
	
	public String getTextExportString()
	{
		String str = "";
		
		str += rarity.toString();
		if (numberInDeck > 0)
		{
			str += " (" + numberInDeck + " in deck)";
		}
		str += "\n";
		
		str += name;
		str += "\n";
		
		str += cardTeam.toString();
				
		if (cardTeam2 != null) 
			str += " + " + cardTeam2.toString();
			
		str += ", " + cardPower.toString();
		
		if (cardPower2 != null) 
			str += " + " + cardPower2.toString();
		
		str += "\n";
		
		if (attack != null || recruit != null || cost != null)
		{
			if (attack != null)
			{
				str += "Attack " + attack;
				if (recruit != null || cost != null)
				{
					str += ", ";
				}
			}
			
			if (recruit != null)
			{
				str += "Recruit " + recruit;
				if (cost != null)
				{
					str += ", ";
				}
			}
			
			if (cost != null)
			{
				str += "Cost " + cost;
			}
			
			str += "\n";
		}
		
		if (abilityText != null)
			str += abilityText.replace("<k>", "").replace("<r>", "").replace(" <g> ", "\n");
		
		return str;
	}
	
	public List<String> getTriggers()
	{
		List<String> triggers = new ArrayList<String>();
		
		if (abilityText != null)
		{
			String[] split = abilityText.split(" <g> ");
			for (String str : split)
			{
				String trigger = "";
				String[] words = str.split(" ");
				if (words.length > 0)
				{
					System.out.println(words[0]);
					if (words[0].startsWith("<") && Icon.valueOf(words[0].replace("<", "").replace(">", "")) != null)
					{
						
						trigger += words[0];
						
						if (words.length > 1)
						{
							if (words[1].startsWith("<") && Icon.valueOf(words[1].replace("<", "").replace(">", "")) != null)
							{
								trigger += words[1];
							}
						}
						
						triggers.add(trigger);
					}
				}
			}
		}
		
		return triggers;
	}
	
	public Integer getBaseAttack()
	{
		if (attack == null)
		{
			return 0;
		}
		
		try
		{
			return Integer.parseInt(attack.replace(" ", "").replace("+", ""));
		}
		catch (Exception e)
		{
			return 0;
		}
	}
	
	public Integer getBaseRecruit()
	{
		if (recruit == null)
		{
			return 0;
		}
		
		try
		{
			return Integer.parseInt(recruit.replace(" ", "").replace("+", ""));
		}
		catch (Exception e)
		{
			return 0;
		}
	}
	
	public Integer getBaseCost()
	{
		if (cost == null)
		{
			return 0;
		}
		
		try
		{
			return Integer.parseInt(cost.replace(" ", "").replace("+", ""));
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}
