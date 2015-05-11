package LegendaryCardMaker;

import java.util.ArrayList;
import java.util.List;

public class WordDefinition {

	public String word;
	public boolean space = true;
	
	public WordDefinition(String word, boolean space) {
		super();
		this.word = word;
		this.space = space;
	}
	
	public static List<WordDefinition> getWordDefinitionList(String text)
	{
		List<WordDefinition> retVal = new ArrayList<WordDefinition>();
		
		String[] words = text.split(" ");
		
		for (String s : words)
		{
			int breakCount = 0;
			for (char c : s.toCharArray())
			{
				if (c == '<')
				{
					breakCount++;
				}
			}
			
			if (breakCount > 1)
			{
				String[] split = s.split("<");
				if (split[0] != null && !split[0].isEmpty())
				{
					if (s.startsWith("<"))
					{
						retVal.add(new WordDefinition("<" + split[0], false));
					}
					else
					{
						retVal.add(new WordDefinition(split[0], false));
					}
				}
				for (int i = 1; i < split.length - 1; i++)
				{
					retVal.add(new WordDefinition("<" + split[i], false));
				}
				retVal.add(new WordDefinition("<" + split[split.length - 1], true));
			}
			else
			{
				retVal.add(new WordDefinition(s, true));
			}
		}
		
		return retVal;
	}
}
