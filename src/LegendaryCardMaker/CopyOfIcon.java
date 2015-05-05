package LegendaryCardMaker;

import java.io.File;

public enum CopyOfIcon {
	
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
	
	public enum ICON_TYPE {NONE, ATTRIBUTE, TEAM, POWER};
	
	private String imagePath;
	private boolean underlayMinimized;
	private ICON_TYPE type;
	
	private CopyOfIcon(String image, ICON_TYPE type)
	{
		this.imagePath = image;
		this.type = type;
	}
	
	private CopyOfIcon(String image, boolean underlayMinimized, ICON_TYPE type)
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
}
