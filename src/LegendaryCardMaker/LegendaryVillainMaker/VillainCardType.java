package LegendaryCardMaker.LegendaryVillainMaker;

public enum VillainCardType {
	
	VILLAIN (2), 
	HENCHMEN (10), 
	MASTERMIND (1),
	MASTERMIND_TACTIC (1),
	BYSTANDER (1),
	;
	
	private int count;
	
	private VillainCardType(int count)
	{
		this.count = count;
	}
	
	public int getCount()
	{
		return count;
	}
}
