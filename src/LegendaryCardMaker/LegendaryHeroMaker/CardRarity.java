package LegendaryCardMaker.LegendaryHeroMaker;

public enum CardRarity {
	
	COMMON (5), 
	UNCOMMON (3), 
	RARE (1),
	OFFICER (30),
	;
	
	private int count;
	
	private CardRarity(int count)
	{
		this.count = count;
	}
	
	public int getCount()
	{
		return count;
	}
}
