package LegendaryCardMaker.LegendaryVillainMaker;

import java.io.File;

import LegendaryCardMaker.Icon;
import LegendaryCardMaker.LegendaryItem;

public class VillainCard extends LegendaryItem {

	public Villain villain;
	
	public String name;
	public int nameSize;
	public String villainGroup;
	public VillainCardType cardType;
	public Icon cardTeam;
	public Icon cardPower;
	public String attack;
	public String victory;
	public String cost;
	public String abilityText;
	public int abilityTextSize;
	public boolean changed = false;
	public String imagePath;
	public double imageZoom = 1.0d;
	public int imageOffsetX = 0;
	public int imageOffsetY = 0;
	public int numberInDeck = -1;
	
	public String getCardName(String exportDir)
	{
		if (cardType != null && cardType.equals(VillainCardType.BYSTANDER))
		{
			int i = 1;
			String filename = cardType.toString().toLowerCase() + "_" + name + "_" + i;
			
			do
			{
				filename = cardType.toString().toLowerCase() + "_" + name + "_" + i;
				i++;
			}
			while (new File(exportDir + File.separator + filename + ".jpg").exists() 
					|| new File(exportDir + File.separator + filename + ".png").exists());
			
			return filename;
		}
		else if (cardType != null && cardType.equals(VillainCardType.WOUND))
		{
			int i = 1;
			String filename = cardType.toString().toLowerCase() + "_" + name + "_" + i;
			
			do
			{
				filename = cardType.toString().toLowerCase() + "_" + name + "_" + i;
				i++;
			}
			while (new File(exportDir + File.separator + filename + ".jpg").exists() 
					|| new File(exportDir + File.separator + filename + ".png").exists());
			
			return filename;
		}
		else if (cardType != null && cardType.equals(VillainCardType.BINDINGS))
		{
			int i = 1;
			String filename = cardType.toString().toLowerCase() + "_" + name + "_" + i;
			
			do
			{
				filename = cardType.toString().toLowerCase() + "_" + name + "_" + i;
				i++;
			}
			while (new File(exportDir + File.separator + filename + ".jpg").exists() 
					|| new File(exportDir + File.separator + filename + ".png").exists());
			
			return filename;
		}
		else
		{
			int i = 1;
			String filename = cardType.toString().toLowerCase() + "_" + villainGroup.replace(" ", "") + "_" + name + "_" + i;
			
			do
			{
				filename = cardType.toString().toLowerCase() + "_" + villainGroup.replace(" ", "") + "_" + name + "_" + i;
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
		
		str += "VILLAINCARD;\n";
		
		str += "VCNAME;" + name + "\n";
		
		if (name != null)
			str += "VCNAMESIZE;" + nameSize + "\n";
		
		if (cardType != null)
			str += "VCTYPE;" + cardType.toString() + "\n";
		
		if (cardTeam != null)
			str += "VCTEAM;" + cardTeam.toString() + "\n";
		
		if (cardPower != null)
			str += "VCPOWER;" + cardPower.toString() + "\n";
		
		if (attack != null)
			str += "VCATTACK;" + attack + "\n";
		
		if (victory != null)
			str += "VCVICTORY;" + victory + "\n";
		
		if (abilityText != null)
			str += "VCTEXT;" + abilityText + "\n";
		
		if (abilityText != null)
			str += "VCTEXTSIZE;" + abilityTextSize + "\n";
		
		if (imagePath != null)
			str += "VCIMAGEPATH;" + imagePath + "\n";
		
		if (imagePath != null)
			str += "VCIMAGEZOOM;" + imageZoom + "\n";
		
		if (imagePath != null)
			str += "VCIMAGEOFFSETX;" + imageOffsetX + "\n";
		
		if (imagePath != null)
			str += "VCIMAGEOFFSETY;" + imageOffsetY + "\n";
		
		str += "VCNUMBERINDECK;" + numberInDeck + "\n";
		
		str += "VCGENERATE;\n";
		
		return str;
	}
	
	public String getTextExportString()
	{
		String str = "";
		
		str += cardType.toString() + "\n";
		str += name + " x " + numberInDeck + "\n";
		str += cardTeam.toString() + "\n";
		
		if (attack != null || victory != null)
		{
			if (attack != null)
			{
				str += "Attack " + attack;
				if (victory != null)
				{
					str += ", ";
				}
			}
			
			if (victory != null)
			{
				str += "Victory Points " + victory;
			}
			
			str += "\n";
		}
		
		if (abilityText != null)
		str += abilityText.replace("<k>", "").replace("<r>", "").replace(" <g> ", "\n");
		
		str += "\n";
		
		return str;
	}
}
