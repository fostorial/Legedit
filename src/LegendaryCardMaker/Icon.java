package LegendaryCardMaker;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import LegendaryCardMaker.LegendaryHeroMaker.Hero;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCard;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCardType;
import LegendaryCardMaker.LegendaryVillainMaker.Villain;

public final class Icon implements Comparator<Icon>, Comparable<Icon> {
	
	private static List<Icon> icons = null;
	
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
	
	private String tagName;
	private String imagePath;
	private boolean underlayMinimized;
	private ICON_TYPE type;
	
	private static Icon noneIcon = new Icon(null, ICON_TYPE.NONE);
	
	public Icon()
	{
		
	}
	
	private Icon(String image, ICON_TYPE type)
	{
		this.imagePath = image;
		this.type = type;
	}
	
	private Icon(String image, boolean underlayMinimized, ICON_TYPE type)
	{
		this.imagePath = image;
		this.underlayMinimized = underlayMinimized;
		this.type = type;
	}
	
	public Icon(String tagName, String image, boolean underlayMinimized, ICON_TYPE type)
	{
		this.imagePath = image;
		this.underlayMinimized = underlayMinimized;
		this.type = type;
		this.tagName = tagName;
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
		
		if (tagName != null)
		{
			return tagName;
		}
		
		String imageSplit = File.separator;
		if (imageSplit.equals("\\")) { imageSplit = File.separator+File.separator; }
		String name = imagePath;
		String[] split = name.split(imageSplit);
		name = split[split.length - 1];
		name = name.substring(0, name.lastIndexOf("."));
		name = name.toUpperCase().replace(" ", "_").replace("-", "_");
		return name;
	}
	
	public static void loadIcons()
	{
		System.out.println("Loading Icons...");
		
		icons = new ArrayList<Icon>();
		
		System.out.println("Loading Teams...");
		
		File file = new File("legendary" + File.separator + "definitions" + File.separator + "teams.txt");
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
						   boolean underlay = false;
						   if (split[2].equals("true"))
						   {
							   underlay = true;
						   }
						   Icon i = new Icon(split[0], "legendary"+File.separator+"Teams"+File.separator+split[1], underlay, ICON_TYPE.TEAM);
						   icons.add(i);
						   System.out.println("Loaded: " + i.getEnumName()); 
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
				System.err.println("Error loading Team Icons");
				e.printStackTrace();
			}
		}
		
		System.out.println("Loading Powers...");
		
		file = new File("legendary" + File.separator + "Powers" + File.separator);
		if (file.exists())
		{
			File[] files = file.listFiles();
			for (File f : files)
			{
				if (f.getName().toLowerCase().endsWith(".png"))
				{
					
					Icon i = new Icon(f.getAbsolutePath(), ICON_TYPE.POWER);
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
					
					Icon i = new Icon(f.getAbsolutePath(), ICON_TYPE.ATTRIBUTE);
					icons.add(i);
					System.out.println("Loaded: " + i.getEnumName());
				}
			}
		}
		
		icons.add(Icon.noneIcon);
		
		System.out.println("Icons Loaded...");
	}
	
	public static Icon valueOf(String key)
	{
		if (icons == null)
		{
			loadIcons();
		}
		
		if (key != null && key.equals("NONE"))
		{
			return noneIcon;
		}
		
		for (Icon i : icons)
		{
			if (key.toUpperCase().equals(i.getEnumName()))
			{
				return i;
			}
		}
		return null;
	}
	
	public static List<Icon> values()
	{
		if (icons == null)
		{
			loadIcons();
		}
		
		return icons;
	}
	
	public static void saveIconDefinitions()
	{
		try
		{
			String str = "";
			
			for (Icon i : Icon.values())
			{
				if (i.type.equals(Icon.ICON_TYPE.TEAM))
				{
					str += i.getEnumName() + "," + new File(i.getImagePath()).getName() + "," + i.isUnderlayMinimized() + "\n";
				}
			}
			
			File file = new File("legendary" + File.separator + "definitions" + File.separator + "teams.txt");
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(str);
			
			bw.close();
			fw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}	
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public ICON_TYPE getType() {
		return type;
	}

	public void setType(ICON_TYPE type) {
		this.type = type;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setUnderlayMinimized(boolean underlayMinimized) {
		this.underlayMinimized = underlayMinimized;
	}

	@Override
	public int compareTo(Icon i) {
		return (this.getEnumName()).compareTo(i.getEnumName());
	}

	@Override
	public int compare(Icon i1, Icon i2) {
		return (i1.getEnumName()).compareTo(i2.getEnumName());
	}
	
}
