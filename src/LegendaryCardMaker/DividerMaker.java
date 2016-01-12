package LegendaryCardMaker;

import java.awt.image.BufferedImage;

public class DividerMaker {
	
	static int verticalWidth = 787;
	static int verticalHeight = 1087;
	static int horizontalWidth = 1100;
	static int horizontalHeight = 800;
	
	public BufferedImage generateDivider()
	{
		return null;
	}
	
	public static int getWidth()
	{
		int width = 0;
		if (LegendaryCardMakerFrame.lcmf.lcm.dividerHorizontal)
		{
			width = horizontalWidth;
		}
		else
		{
			width = verticalWidth;
		}
		
		return width;
	}
	
	public static int getHeight()
	{
		int height = 0;
		if (LegendaryCardMakerFrame.lcmf.lcm.dividerHorizontal)
		{
			height = horizontalHeight;
		}
		else
		{
			height = verticalHeight;
		}
		
		return height;
	}
	
	public static int getDividersPerRow()
	{
		int num = 0;
		if (LegendaryCardMakerFrame.lcmf.lcm.dividerHorizontal)
		{
			num = 2;
		}
		else
		{
			num = 3;
		}
		
		return num;
	}
	
	public static int getDividerRows()
	{
		int num = 0;
		if (LegendaryCardMakerFrame.lcmf.lcm.dividerHorizontal)
		{
			num = 4;
		}
		else
		{
			num = 3;
		}
		
		return num;
	}
}
