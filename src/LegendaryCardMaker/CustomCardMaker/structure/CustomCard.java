package LegendaryCardMaker.CustomCardMaker.structure;

import java.io.File;
import java.util.Comparator;

import javax.swing.ImageIcon;

import LegendaryCardMaker.LegendaryItem;

public class CustomCard extends LegendaryItem implements Comparator<CustomCard>, Comparable<CustomCard> {

	public String templateName;
	public CustomTemplate template;
	
	public ImageIcon imageSummary;
	
	public boolean changed;
	
	public String getCardName(String exportDir)
	{
			int i = 1;
			String filename = templateName.replace(" ", "") + "_" + getName() + "_" + i;
			
			do
			{
				filename = templateName.replace(" ", "") + "_" + getName() + "_" + i;
				i++;
			}
			while (new File(exportDir + File.separator + filename + ".jpg").exists() 
					|| new File(exportDir + File.separator + filename + ".png").exists());
			
			return filename;
	}
	
	public String getName()
	{
		return "";
	}
	
	public String generateOutputString()
	{
		return generateOutputString(false);
	}
	
	public String generateOutputString(boolean fullExport)
	{
		String str = "";
		
		str += "CUSTOMCARD;\n";
		str += "TEMPLATE;" + template.templateName + "\n";
		
		for (CustomElement e : template.elements)
		{
			str += e.generateOutputString();
		}
		
		return str;
	}
	
	public String getTextExportString()
	{
		String str = "";
		
		return str;
	}
	
	@Override
	public int compareTo(CustomCard o) {
		return (this.getCardName()).compareTo(o.getCardName());
	}

	@Override
	public int compare(CustomCard o1, CustomCard o2) {
		return (o1.getCardName()).compareTo(o2.getCardName());
	}
	
	public String getCardName()
	{
		for (CustomElement ce : template.elements)
		{
			if (ce instanceof ElementCardName)
			{
				if (((ElementCardName)ce).getSubnameValue() != null)
				{
					return ((ElementCardName)ce).getValue() + " (" + ((ElementCardName)ce).getSubnameValue() + ") - " + template.templateDisplayName;
				}
				else
				{
					return ((ElementCardName)ce).getValue() + " - " + template.templateDisplayName;
				}
			}
		}
		return template.templateDisplayName;
	}
}
