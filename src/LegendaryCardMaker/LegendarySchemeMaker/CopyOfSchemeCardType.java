package LegendaryCardMaker.LegendarySchemeMaker;

import java.awt.Color;

public enum CopyOfSchemeCardType {
	
	SCHEME (new Color(71,152,96)), 
	TRANSFORMATION (new Color(211, 125, 33)), 
	COMMANDER (Color.red),
	RESOURCE (new Color(33, 113, 211)),
	;
	
	private Color bgColor;
	private String displayString;
	
	private CopyOfSchemeCardType(Color bgColor)
	{
		this.bgColor = bgColor;
		this.displayString = this.toString();
	}
	
	private CopyOfSchemeCardType(Color bgColor, String displayString)
	{
		this.bgColor = bgColor;
		this.displayString = displayString;
	}
	
	public Color getBgColor()
	{
		return bgColor;
	}
	
	public String getDisplayString()
	{
		return displayString;
	}
}
