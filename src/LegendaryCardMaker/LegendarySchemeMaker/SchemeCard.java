package LegendaryCardMaker.LegendarySchemeMaker;

import java.io.File;
import java.util.Comparator;

import LegendaryCardMaker.Icon;

public class SchemeCard implements Comparator<SchemeCard>, Comparable<SchemeCard> {

	public String name;
	public int cardNameSize;
	public String subCategory = "";
	public int subCategorySize;
	public SchemeCardType cardType;
	public String cardText;
	public int cardTextSize;
	public boolean changed = false;
	public String imagePath;
	public double imageZoom = 1.0d;
	public int imageOffsetX = 0;
	public int imageOffsetY = 0;
	
	public String getCardName(String exportDir)
	{
		{
			int i = 1;
			String filename = cardType.toString().toLowerCase() + "_" + name.replace(" ", "") + "_" + subCategory.replace(" ", "") + "_" + i;
			
			do
			{
				filename = cardType.toString().toLowerCase() + "_" + name.replace(" ", "") + "_" + subCategory.replace(" ", "") + "_" + i;
				i++;
			}
			while (new File(exportDir + File.separator + filename + ".jpg").exists() 
					|| new File(exportDir + File.separator + filename + ".png").exists());
			
			return filename;
		}
	}
	
	public String generateOutputString()
	{
		String str = "";
		
		//str += "HCNAMESIZE;" + 40 + "\n";
		
		str += "SCHEME;" + name + "\n";
		
		if (name != null)
			str += "SCNAMESIZE;" + cardNameSize + "\n";
		
		if (subCategory != null)
			str += "SCSUBNAME;" + subCategory.toString() + "\n";
		
		if (name != null)
			str += "SCSUBCATSIZE;" + subCategorySize + "\n";
		
		if (cardType != null)
			str += "SCTYPE;" + cardType.toString() + "\n";
		
		if (cardText != null)
			str += "SCTEXT;" + cardText + "\n";
		
		if (cardText != null)
			str += "SCTEXTSIZE;" + cardTextSize + "\n";
		
		if (imagePath != null)
			str += "SCIMAGEPATH;" + imagePath + "\n";
		
		if (imagePath != null)
			str += "SCIMAGEZOOM;" + imageZoom + "\n";
		
		if (imagePath != null)
			str += "SCIMAGEOFFSETX;" + imageOffsetX + "\n";
		
		if (imagePath != null)
			str += "SCIMAGEOFFSETY;" + imageOffsetY + "\n";
		
		str += "SCGENERATE;\n";
		
		return str;
	}

	@Override
	public int compareTo(SchemeCard o) {
		return (this.name).compareTo(o.name);
	}

	@Override
	public int compare(SchemeCard o1, SchemeCard o2) {
		return (o1.name).compareTo(o2.name);
	}
	
	public String getTextExportString()
	{
		String str = "";
		
		str += cardType.toString() + "\n";
		str += name + "\n";
		
		if (subCategory != null)
		{
			str += subCategory + "\n";
		}
		
		if (cardText != null)
			cardText.replace("<k>", "").replace("<r>", "").replace(" <g> ", "\n").replace("</h>", "\n").replace("<h>", "\n");
		
		str += "\n";
		
		return str;
	}
}
