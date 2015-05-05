package LegendaryCardMaker.LegendarySchemeMaker;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public final class SchemeCardType {
	/*
	SCHEME (new Color(71,152,96)), 
	TRANSFORMATION (new Color(211, 125, 33)), 
	COMMANDER (Color.red),
	RESOURCE (new Color(33, 113, 211)),
	;
	*/
	
	private static List<SchemeCardType> schemeCardTypes = null;
	
	private Color bgColor;
	private String displayString;
	private boolean allowHeadings = false;
	
	private SchemeCardType(Color bgColor)
	{
		this.bgColor = bgColor;
		this.displayString = this.toString();
	}
	
	private SchemeCardType(Color bgColor, String displayString, boolean allowHeadings)
	{
		this.bgColor = bgColor;
		this.displayString = displayString;
		this.allowHeadings = allowHeadings;
	}
	
	public Color getBgColor()
	{
		return bgColor;
	}
	
	public String getDisplayString()
	{
		return displayString;
	}
	
	public boolean doesAllowHeadings()
	{
		return allowHeadings;
	}
	
	public String toString()
	{
		return getEnumName();
	}
	
	public String getEnumName()
	{
		if (displayString == null)
		{
			return "NONE";
		}
		
		String name = displayString;
		name = name.toUpperCase().replace(" ", "_").replace("-", "_");
		return name;
	}
	
	public static void loadSchemeTypes()
	{
		System.out.println("Loading Scheme Card Types...");
		
		schemeCardTypes = new ArrayList<SchemeCardType>();
		
		File file = new File("legendary" + File.separator + "definitions" + File.separator + "scheme_types.txt");
		if (file.exists())
		{	
			try
			{
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				while ((line = br.readLine()) != null) {
				   if (line != null && !line.startsWith("#") && !line.isEmpty())
				   {
					   try
					   {
						   String[] split = line.split(",");
						   boolean allowHeadings = false;
						   if (split[4].equals("true"))
						   {
							   allowHeadings = true;
						   }
						   SchemeCardType s = new SchemeCardType(new Color(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3])), split[0], allowHeadings);
						   schemeCardTypes.add(s);
						   System.out.println("Loaded: " + s.getEnumName()); 
					   }
					   catch (Exception e)
					   {
						   System.err.println("Failed to load: " + line);
					   }					   
				   }
				}
			}
			catch (Exception e)
			{
				System.err.println("Error loading Scheme Types");
				e.printStackTrace();
			}
		}
		
		System.out.println("Scheme Card Types Loaded...");
	}
	
	public static SchemeCardType valueOf(String key)
	{
		if (schemeCardTypes == null)
		{
			loadSchemeTypes();
		}
		
		for (SchemeCardType i : schemeCardTypes)
		{
			if (key.toUpperCase().equals(i.getEnumName()))
			{
				return i;
			}
		}
		return null;
	}
	
	public static List<SchemeCardType> values()
	{
		if (schemeCardTypes == null)
		{
			loadSchemeTypes();
		}
		
		return schemeCardTypes;
	}
}
