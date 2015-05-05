package LegendaryCardMaker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Copy_2_of_Icon {
	
	private static List<Copy_2_of_Icon> icons = new ArrayList<Copy_2_of_Icon>();
	
	/*
	//Attributes
	ATTACK ("legendary/icons/attack.png", ICON_TYPE.ATTRIBUTE),
	RECRUIT ("legendary/icons/recruit.png", ICON_TYPE.ATTRIBUTE),
	VICTORY ("legendary/icons/victory.png", ICON_TYPE.ATTRIBUTE),
	COST ("legendary/icons/cost.png", ICON_TYPE.ATTRIBUTE),
		
	//Teams
	AUTOBOT ("Teams/autobot.png", ICON_TYPE.TEAM), 
	DECEPTICON ("Teams/decepticon.png", ICON_TYPE.TEAM), 
	DINOBOT ("Teams/dinobot.png", ICON_TYPE.TEAM), 
	US_ARMY ("Teams/us_army.png", ICON_TYPE.TEAM), 
	PREDACON ("Teams/predacons.png", true, ICON_TYPE.TEAM), 
	QUINTESSON ("Teams/Quintesson.png", ICON_TYPE.TEAM), 
	AVENGERS ("Teams/avengers.png", ICON_TYPE.TEAM), 
	FANTASTIC_FOUR ("Teams/fantastic-four.png", ICON_TYPE.TEAM), 
	GUARDIANS ("Teams/guardians.png", ICON_TYPE.TEAM), 
	MARVEL_KNIGHTS ("Teams/marvel-knights.png", ICON_TYPE.TEAM), 
	SHIELD ("Teams/shield.png", ICON_TYPE.TEAM), 
	SPIDER_FRIENDS ("Teams/spider-friends.png", ICON_TYPE.TEAM), 
	X_FORCE ("Teams/x-force.png", ICON_TYPE.TEAM), 
	X_MEN ("Teams/x-men.png", ICON_TYPE.TEAM), 
	NO_TEAM (null, ICON_TYPE.TEAM), 
	
	//POWERS
	COVERT ("Powers" + File.separator + "covert.png", ICON_TYPE.POWER),
	INSTINCT ("Powers" + File.separator + "instinct.png", ICON_TYPE.POWER),
	RANGE ("Powers" + File.separator + "range.png", ICON_TYPE.POWER), 
	STRENGTH ("Powers" + File.separator + "strength.png", ICON_TYPE.POWER), 
	TECH ("Powers" + File.separator + "tech.png", ICON_TYPE.POWER),  
	NONE (null, ICON_TYPE.POWER),
	;
	*/
	
	public enum ICON_TYPE {NONE, ATTRIBUTE, TEAM, POWER};
	
	private String imagePath;
	private boolean underlayMinimized;
	private ICON_TYPE type;
	
	private Copy_2_of_Icon(String image, ICON_TYPE type)
	{
		this.imagePath = image;
		this.type = type;
	}
	
	private Copy_2_of_Icon(String image, boolean underlayMinimized, ICON_TYPE type)
	{
		this.imagePath = image;
		this.underlayMinimized = underlayMinimized;
		this.type = type;
	}
	
	public String getImagePath()
	{
		return imagePath;
	}
	
	public boolean isUnderlayMinimized()
	{
		return underlayMinimized;
	}
	
	public ICON_TYPE getIconType()
	{
		return type;
	}
	
	public String toString()
	{
		return getEnumName();
	}
	
	public String getEnumName()
	{
		if (imagePath == null)
		{
			return "NONE";
		}
		
		String name = imagePath;
		String[] split = name.split(File.separator);
		name = split[split.length - 1];
		name = name.substring(0, name.lastIndexOf("."));
		name = name.toUpperCase().replace(" ", "_").replace("-", "_");
		return name;
	}
	
	public static void loadIcons()
	{
		System.out.println("Loading Icons...");
		
		icons = new ArrayList<Copy_2_of_Icon>();
		
		System.out.println("Loading Teams...");
		
		File file = new File("Teams" + File.separator);
		if (file.exists())
		{
			File[] files = file.listFiles();
			for (File f : files)
			{
				if (f.getName().toLowerCase().endsWith(".png"))
				{
					
					Copy_2_of_Icon i = new Copy_2_of_Icon(f.getAbsolutePath(), ICON_TYPE.TEAM);
					icons.add(i);
					System.out.println("Loaded: " + i.getEnumName());
				}
			}
		}
		
		System.out.println("Loading Powers...");
		
		file = new File("Powers" + File.separator);
		if (file.exists())
		{
			File[] files = file.listFiles();
			for (File f : files)
			{
				if (f.getName().toLowerCase().endsWith(".png"))
				{
					
					Copy_2_of_Icon i = new Copy_2_of_Icon(f.getAbsolutePath(), ICON_TYPE.POWER);
					icons.add(i);
					System.out.println("Loaded: " + i.getEnumName());
				}
			}
		}
		
		System.out.println("Loading Attributes...");
		
		file = new File("legendary" + File.separator + "icons" + File.separator);
		if (file.exists())
		{
			File[] files = file.listFiles();
			for (File f : files)
			{
				if (f.getName().toLowerCase().endsWith(".png"))
				{
					
					Copy_2_of_Icon i = new Copy_2_of_Icon(f.getAbsolutePath(), ICON_TYPE.ATTRIBUTE);
					icons.add(i);
					System.out.println("Loaded: " + i.getEnumName());
				}
			}
		}
		
		System.out.println("Icons Loaded...");
	}
	
	public static Copy_2_of_Icon valueOf(String key)
	{
		if (key != null && key.equals("NONE"))
		{
			return new Copy_2_of_Icon(null, ICON_TYPE.NONE);
		}
		
		for (Copy_2_of_Icon i : icons)
		{
			if (key.toUpperCase().equals(i.getEnumName()))
			{
				return i;
			}
		}
		return null;
	}
	
	public static List<Copy_2_of_Icon> values()
	{
		return icons;
	}
}
