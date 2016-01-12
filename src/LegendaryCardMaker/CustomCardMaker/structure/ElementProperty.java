package LegendaryCardMaker.CustomCardMaker.structure;

public class ElementProperty extends CustomElement {
	
	public CustomProperties property;
	public String defaultValue;
	
	public String value;

	public String getValue()
	{
		if (value != null)
		{
			return value;
		}
		return defaultValue;
	}
	
	public String generateOutputString()
	{
		return generateOutputString(false);
	}
	
	public String generateOutputString(boolean fullExport)
	{
		String str = "";
		if (value != null)
		{
			str += "CUSTOMVALUE;" + name + ";value;" + value + "\n";
		}
		
		str += "CUSTOMVALUE;" + name + ";visible;" + visible + "\n";
		
		return str;
	}
}
