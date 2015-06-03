package LegendaryCardMaker;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import LegendaryCardMaker.LegendaryHeroMaker.CardRarity;
import LegendaryCardMaker.LegendaryHeroMaker.Hero;
import LegendaryCardMaker.LegendaryHeroMaker.HeroCard;
import LegendaryCardMaker.LegendaryHeroMaker.HeroMaker;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCard;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCardType;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeMaker;
import LegendaryCardMaker.LegendaryVillainMaker.Villain;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCard;
import LegendaryCardMaker.LegendaryVillainMaker.VillainCardType;
import LegendaryCardMaker.LegendaryVillainMaker.VillainMaker;

public class LegendaryCardMaker {
	
	public static String version = "0.5";

	public String inputFile = null;
	
	public boolean exportBasicText = false;
	public String textOutputFile = null;
	public String textErrorFile = "error.txt";
	public String exportFolder = null;
	
	public List<Hero> heroes = new ArrayList<Hero>();
	public List<Villain> villains = new ArrayList<Villain>();
	public List<SchemeCard> schemes = new ArrayList<SchemeCard>();
	public Villain bystanderVillain = new Villain();
	public Villain woundVillain = new Villain();
	
	public boolean ignoreGenerate = true;
	
	public String textDividerFile = "cardCreator/dividers.txt";
	public boolean generateDividerFile = false;
	
	public String currentFile = null;
	
	public String lastSaved = null;
	public String lastOpened = null;
	
	public static String expansionStyle = "default";
	
	public static void main(String[] args)
	{
		/*
		HeroMaker hm = new HeroMaker();
		hm.populateHeroCard();
		hm.generateCard();
		*/
		
		LegendaryCardMaker lcm = new LegendaryCardMaker();
		new LegendaryCardMakerFrame(lcm);
		
		//new LegendaryCardMakerBasicFrame(new LegendaryCardMaker());
	}
	
	public LegendaryCardMaker()
	{
		//Icon.loadIcons();
		
		//processInput(inputFile);
		
		//new VillainMaker();
		
		//new SchemeMaker();
		
		bystanderVillain.name = "system_bystander_villain";
		villains.add(bystanderVillain);
		
		woundVillain.name = "system_wound_villain";
		villains.add(woundVillain);
	}
	
	public void processInput(String inputFile)
	{	
		heroes = new ArrayList<Hero>();
		villains = new ArrayList<Villain>();
		schemes = new ArrayList<SchemeCard>();
		
		bystanderVillain = new Villain();
		bystanderVillain.name = "system_bystander_villain";
		villains.add(bystanderVillain);
		
		woundVillain = new Villain();
		woundVillain.name = "system_wound_villain";
		villains.add(woundVillain);
		
		Hero h = new Hero();
		HeroCard hc = new HeroCard();
		HeroMaker hm = new HeroMaker();
		
		Villain v = new Villain();
		VillainCard vc = new VillainCard();
		VillainMaker vm = new VillainMaker();
		
		SchemeCard sc = new SchemeCard();
		SchemeMaker sm = new SchemeMaker();
		
		try
		{
			FileWriter fw = null;
			BufferedWriter bw = null;
			
			if (exportBasicText)
			{
				fw = new FileWriter(new File(textOutputFile));
				bw = new BufferedWriter(fw);
			}
			
			FileWriter fwDiv = null;
			BufferedWriter bwDiv = null;
			
			if (generateDividerFile)
			{
				fwDiv = new FileWriter(new File(textDividerFile));
				bwDiv = new BufferedWriter(fwDiv);
			}
			
			FileWriter fwErr = null;
			BufferedWriter bwErr = null;
			fwErr = new FileWriter(new File(textErrorFile));
			bwErr = new BufferedWriter(fwErr);
			
			BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
			String line;
			while ((line = br.readLine()) != null) {
			   if (line != null && !line.startsWith("#") && !line.isEmpty())
			   {
				   if (line.startsWith("EXPANSIONSTYLE;"))
				   {
					   expansionStyle = line.replace("EXPANSIONSTYLE;", "");
				   }
				   
				   if (line.startsWith("HERO;"))
				   {
					   h = new Hero();
					   h.name = line.replace("HERO;", "");
					   heroes.add(h);
					   
					   if (exportBasicText)
						{
							outputHero(bw, h);
						}
				   }
				   
				   if (line.startsWith("HEROCARD;"))
				   {
					   hm = new HeroMaker();
					   hm.bwErr = bwErr;
					   hm.exportFolder = exportFolder;
					   hc = new HeroCard();
					   hc.heroName = h.name;
					   h.cards.add(hc);
				   }
				   
				   if (line.startsWith("HCNAME;"))
				   {
					   hc.name = line.replace("HCNAME;", "");
				   }
				   
				   if (line.startsWith("HCNAMESIZE;"))
				   {
					   hm.cardNameSize = Integer.parseInt(line.replace("HCNAMESIZE;", ""));
					   hc.nameSize = Integer.parseInt(line.replace("HCNAMESIZE;", ""));
				   }
				   
				   if (line.startsWith("HCRARITY;"))
				   {
					   hc.rarity = CardRarity.valueOf(line.replace("HCRARITY;", ""));
				   }
				   
				   if (line.startsWith("HCTEAM;"))
				   {
					   hc.cardTeam = Icon.valueOf(line.replace("HCTEAM;", ""));
				   }
				   
				   if (line.startsWith("HCPOWER;"))
				   {
					   hc.cardPower = Icon.valueOf(line.replace("HCPOWER;", ""));
				   }
				   
				   if (line.startsWith("HCATTACK;"))
				   {
					   hc.attack = line.replace("HCATTACK;", "");
				   }
				   
				   if (line.startsWith("HCRECRUIT;"))
				   {
					   hc.recruit = line.replace("HCRECRUIT;", "");
				   }
				   
				   if (line.startsWith("HCCOST;"))
				   {
					   hc.cost = line.replace("HCCOST;", "");
				   }
				   
				   if (line.startsWith("HCATTACK;"))
				   {
					   hc.attack = line.replace("HCATTACK;", "");
				   }
				   
				   if (line.startsWith("HCTEXT;"))
				   {
					   hc.abilityText = line.replace("HCTEXT;", "");
				   }
				   
				   if (line.startsWith("HCTEXTOFFSET;"))
				   {
					   hm.textStartOffset = Integer.parseInt(line.replace("HCTEXTOFFSET;", ""));
				   }
				   
				   if (line.startsWith("HCTEXTSIZE;"))
				   {
					   hm.textSize = Integer.parseInt(line.replace("HCTEXTSIZE;", ""));
					   hc.abilityTextSize = Integer.parseInt(line.replace("HCTEXTSIZE;", ""));
				   }
				   
				   if (line.startsWith("HCIMAGEZOOM;"))
				   {
					   hc.imageZoom = Double.parseDouble(line.replace("HCIMAGEZOOM;", ""));
				   }
				   
				   if (line.startsWith("HCIMAGEPATH;"))
				   {
					   hc.imagePath = line.replace("HCIMAGEPATH;", "");
				   }
				   
				   if (line.startsWith("HCIMAGEOFFSETX;"))
				   {
					   hc.imageOffsetX = Integer.parseInt(line.replace("HCIMAGEOFFSETX;", ""));
				   }
				   
				   if (line.startsWith("HCIMAGEOFFSETY;"))
				   {
					   hc.imageOffsetY = Integer.parseInt(line.replace("HCIMAGEOFFSETY;", ""));
				   }
				   
				   if (!ignoreGenerate && line.startsWith("HCGENERATE;"))
				   {
					   
					   try
					   {
						   bwErr.write("Generating: " + hc.getCardName(exportFolder) + "\n");
						   
						   hm.setCard(hc);
						   BufferedImage image = hm.generateCard();
						   hm.exportImage(image);
						   
						   if (exportBasicText)
							{
								outputHeroCard(bw, hc);
							}
					   }
					   catch (Exception ex)
					   {
						   bwErr.write(ex.getMessage());
						   for (StackTraceElement s : ex.getStackTrace())
						   {
							   bwErr.write(s.toString());
						   }
					   }
					   
					   
				   }
				   
				   if (line.startsWith("VILLAIN;"))
				   {
					   v = new Villain();
					   if (line.replace("VILLAIN;", "").equals("system_bystander_villain"))
					   {
						   v = bystanderVillain;
					   }
					   else if (line.replace("VILLAIN;", "").equals("system_wound_villain"))
					   {
						   v = woundVillain;
					   }
					   else
					   {
						   v.name = line.replace("VILLAIN;", "");
						   villains.add(v);
					   }
					   
					   if (exportBasicText)
						{
							outputVillain(bw, v);
						}
				   }
				   
				   if (line.startsWith("VILLAINCARD;"))
				   {
					   vm = new VillainMaker();
					   vm.bwErr = bwErr;
					   vm.exportFolder = exportFolder;
					   vc = new VillainCard();
					   vc.villainGroup = v.name;
					   vc.villain = v;
					   v.cards.add(vc);
				   }
				   
				   if (line.startsWith("VCNAME;"))
				   {
					   vc.name = line.replace("VCNAME;", "");
				   }
				   
				   if (line.startsWith("VCNAMESIZE;"))
				   {
					   vm.cardNameSize = Integer.parseInt(line.replace("VCNAMESIZE;", ""));
					   vc.nameSize = Integer.parseInt(line.replace("VCNAMESIZE;", ""));
				   }
				   
				   if (line.startsWith("VCTYPE;"))
				   {
					   vc.cardType = VillainCardType.valueOf(line.replace("VCTYPE;", ""));
					   if (vc.cardType.equals(VillainCardType.BYSTANDER))
					   {
						   bystanderVillain.cards.add(vc);
						   v.cards.remove(vc);
					   }
				   }
				   
				   if (line.startsWith("VCTEAM;"))
				   {
					   vc.cardTeam = Icon.valueOf(line.replace("VCTEAM;", ""));
				   }
				   
				   if (line.startsWith("VCPOWER;"))
				   {
					   vc.cardPower = Icon.valueOf(line.replace("VCPOWER;", ""));
				   }
				   
				   if (line.startsWith("VCATTACK;"))
				   {
					   vc.attack = line.replace("VCATTACK;", "");
				   }
				   
				   if (line.startsWith("VCVICTORY;"))
				   {
					   vc.victory = line.replace("VCVICTORY;", "");
				   }
				   
				   if (line.startsWith("VCTEXT;"))
				   {
					   vc.abilityText = line.replace("VCTEXT;", "");
				   }
				   
				   if (line.startsWith("VCTEXTOFFSET;"))
				   {
					   vm.textStartOffset = Integer.parseInt(line.replace("VCTEXTOFFSET;", ""));
				   }
				   
				   if (line.startsWith("VCTEXTSIZE;"))
				   {
					   vm.textSize = Integer.parseInt(line.replace("VCTEXTSIZE;", ""));
					   vc.abilityTextSize = Integer.parseInt(line.replace("VCTEXTSIZE;", ""));
				   }
				   
				   if (line.startsWith("VCIMAGEZOOM;"))
				   {
					   vc.imageZoom = Double.parseDouble(line.replace("VCIMAGEZOOM;", ""));
				   }
				   
				   if (line.startsWith("VCIMAGEPATH;"))
				   {
					   vc.imagePath = line.replace("VCIMAGEPATH;", "");
				   }
				   
				   if (line.startsWith("VCIMAGEOFFSETX;"))
				   {
					   vc.imageOffsetX = Integer.parseInt(line.replace("VCIMAGEOFFSETX;", ""));
				   }
				   
				   if (line.startsWith("VCIMAGEOFFSETY;"))
				   {
					   vc.imageOffsetY = Integer.parseInt(line.replace("VCIMAGEOFFSETY;", ""));
				   }
				   
				   if (!ignoreGenerate && line.startsWith("VCGENERATE;"))
				   {
					   
					   try
					   {
						   bwErr.write("Generating: " + vc.getCardName(exportFolder) + "\n");
						   
						   vm.setCard(vc);
						   BufferedImage image = vm.generateCard();
						   vm.exportImage(image);
						   
						   if (exportBasicText)
							{
								outputVillainCard(bw, vc);
							}
					   }
					   catch (Exception ex)
					   {
						   bwErr.write(ex.getMessage());
						   for (StackTraceElement s : ex.getStackTrace())
						   {
							   bwErr.write(s.toString());
						   }
					   }
					   
					   
				   }
				   
				   if (line.startsWith("SCHEME;"))
				   {
					   sm = new SchemeMaker();
					   sm.bwErr = bwErr;
					   sm.exportFolder = exportFolder;
					   sc = new SchemeCard();
					   sc.name = line.replace("SCHEME;", "");
					   schemes.add(sc);
				   }
				   
				   if (line.startsWith("SCNAMESIZE;"))
				   {
					   sm.cardNameSize = Integer.parseInt(line.replace("SCNAMESIZE;", ""));
					   sc.cardNameSize = Integer.parseInt(line.replace("SCNAMESIZE;", ""));
				   }
				   
				   if (line.startsWith("SCSUBNAME;"))
				   {
					   sc.subCategory = line.replace("SCSUBNAME;", "").toUpperCase();
				   }
				   
				   if (line.startsWith("SCSUBCATSIZE;"))
				   {
					   sm.subCategorySize = Integer.parseInt(line.replace("SCSUBCATSIZE;", ""));
					   sc.subCategorySize = Integer.parseInt(line.replace("SCSUBCATSIZE;", ""));
				   }
				   
				   if (line.startsWith("SCTYPE;"))
				   {
					   sc.cardType = SchemeCardType.valueOf(line.replace("SCTYPE;", ""));
				   }
				   
				   if (line.startsWith("SCTEXT;"))
				   {
					   sc.cardText = line.replace("SCTEXT;", "");
				   }
				   
				   if (line.startsWith("SCTEXTSIZE;"))
				   {
					   sm.textSize = Integer.parseInt(line.replace("SCTEXTSIZE;", ""));
					   sc.cardTextSize = Integer.parseInt(line.replace("SCTEXTSIZE;", ""));
				   }
				   
				   if (!ignoreGenerate && line.startsWith("SCGENERATE;"))
				   {
					   try
					   {
						   bwErr.write("Generating: " + sc.getCardName(exportFolder) + "\n");
						   
						   sm.setCard(sc);
						   BufferedImage image = sm.generateCard();
						   sm.exportImage(image);
						   
						   if (exportBasicText)
							{
								outputSchemeCard(bw, sc);
							}
					   }
					   catch (Exception ex)
					   {
						   bwErr.write(ex.getMessage());
						   for (StackTraceElement s : ex.getStackTrace())
						   {
							   bwErr.write(s.toString());
						   }
					   }
				   }
			   }
			}
			
			br.close();
			
			if (exportBasicText)
			{
				bw.close();
			}
			
			if (generateDividerFile)
			{
				generateDividerFile(bwDiv, heroes, villains);
				bwDiv.close();
			}
			
			bwErr.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			currentFile = null;
		}
		
		currentFile = inputFile;
	}
	
	public void outputHero(BufferedWriter br, Hero h) throws Exception
	{
		br.newLine();
		br.write(h.name);
		br.newLine();
		br.newLine();
	}
	
	public void outputHeroCard(BufferedWriter br, HeroCard hc) throws Exception
	{
		br.write(hc.getTextExportString());
		br.newLine();
		br.newLine();
	}
	
	public void outputVillain(BufferedWriter br, Villain v) throws Exception
	{
		br.newLine();
		br.write(v.name);
		br.newLine();
		br.newLine();
	}
	
	public void outputVillainCard(BufferedWriter br, VillainCard vc) throws Exception
	{
		br.write(vc.cardType.toString()); br.newLine();
		br.write(vc.name); br.newLine();
		br.write(vc.cardTeam.toString()); br.newLine();
		
		if (vc.attack != null || vc.victory != null)
		{
			if (vc.attack != null)
			{
				br.write("Attack " + vc.attack);
				if (vc.victory != null)
				{
					br.write(", ");
				}
			}
			
			if (vc.victory != null)
			{
				br.write("Victory Points " + vc.victory);
			}
			
			br.newLine();
		}
		
		br.write(vc.abilityText.replace("<k>", "").replace("<r>", "").replace(" <g> ", "\n"));
		br.newLine();
		br.newLine();
	}
	
	public void outputSchemeCard(BufferedWriter br, SchemeCard sc) throws Exception
	{
		br.newLine();
		br.write(sc.cardType.toString()); br.newLine();
		br.write(sc.name); br.newLine();
		if (sc.subCategory != null)
		
		{
		br.write(sc.subCategory); br.newLine();
		}
		
		br.write(sc.cardText.replace("<k>", "").replace("<r>", "").replace(" <g> ", "\n").replace("</h>", "\n").replace("<h>", "\n"));
		br.newLine();
		br.newLine();
	}
	
	public void generateDividerFile(BufferedWriter bwDiv, List<Hero> heroes, List<Villain> villains) throws Exception
	{
		bwDiv.write("EXPANSION;" + textOutputFile);
		bwDiv.newLine();
		bwDiv.newLine();
		
		for (Hero h : heroes)
		{
			bwDiv.write("HERO;"+h.name+";"+h.cards.get(h.cards.size() - 1).cardTeam.getEnumName());
			bwDiv.newLine();
			
			for (HeroCard hc : h.cards)
			{
				bwDiv.write("HEROCARD;"+hc.rarity.toString()+";"+hc.cardTeam+";"+hc.cost);
				bwDiv.newLine();
			}
			
			bwDiv.newLine();
		}
		
		for (Villain v : villains)
		{
			if (v.cards.get(v.cards.size() - 1).cardType.equals(VillainCardType.HENCHMEN))
			{
				bwDiv.write("HENCHMEN;"+v.name+";"+v.cards.get(v.cards.size() - 1).cardTeam.getEnumName());
				bwDiv.newLine();
			}
			
			if (v.cards.get(v.cards.size() - 1).cardType.equals(VillainCardType.VILLAIN))
			{
				bwDiv.write("VILLAIN;"+v.name+";"+v.cards.get(v.cards.size() - 1).cardTeam.getEnumName());
				bwDiv.newLine();
			}
			
			if (v.cards.get(v.cards.size() - 1).cardType.equals(VillainCardType.MASTERMIND_TACTIC) || v.cards.get(v.cards.size() - 1).cardType.equals(VillainCardType.MASTERMIND))
			{
				bwDiv.write("MASTERMIND;"+v.name+";"+v.cards.get(v.cards.size() - 1).cardTeam.getEnumName());
				bwDiv.newLine();
			}
			
			bwDiv.newLine();
		}
	}
	
	public void saveExpansion() throws Exception
	{
		String str = "";
		
		str += "EXPANSIONSTYLE;" + expansionStyle;
		str += "\n\n";
		
		for (Hero h : heroes)
		{
			str += h.generateOutputString();
			str += "\n\n";
		}
		
		for (Villain v : villains)
		{
			str += v.generateOutputString();
			str += "\n\n";
		}
		
		for (SchemeCard s : schemes)
		{
			str += s.generateOutputString();
			str += "\n\n";
		}
		
		FileWriter fw = new FileWriter(new File(currentFile));
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(str);
		
		bw.close();
		fw.close();
		
		markAsChanged(false);
	}
	
	public void markAsChanged(boolean flag) throws Exception
	{
		for (Hero h : heroes)
		{
			h.changed = flag;
			for (HeroCard hc : h.cards)
			{
				hc.changed = flag;
			}
		}
		
		for (Villain v : villains)
		{
			v.changed = flag;
			for (VillainCard vc : v.cards)
			{
				vc.changed = flag;
			}
		}
		
		for (SchemeCard s : schemes)
		{
			s.changed = flag;
		}
	}
	
	public boolean doChangesExist()
	{
		for (Hero h : heroes)
		{
			if (h.changed)
			{
				return true;
			}
			
			for (HeroCard hc : h.cards)
			{
				if (hc.changed)
				{
					return true;
				}
			}
		}
		
		for (Villain v : villains)
		{
			if (v.changed)
			{
				return true;
			}
			
			for (VillainCard hc : v.cards)
			{
				if (hc.changed)
				{
					return true;
				}
			}
		}
		
		for (SchemeCard s : schemes)
		{
				if (s.changed)
				{
					return true;
				}
		}
		
		return false;
	}
	
	public void exportToPng(File folder) throws Exception
	{
		for (Hero h : heroes)
		{
			exportHeroToPng(h, folder);
		}
		
		for (Villain v : villains)
		{
			exportVillainToPng(v, folder);
		}
		
		for (SchemeCard s : schemes)
		{
			exportSchemeToPng(s, folder);
		}
	}
	
	public int getCardCount() throws Exception
	{
		int count = 0;
		for (Hero h : heroes)
		{
			count++;
		}
		
		for (Villain v : villains)
		{
			count++;
		}
		
		for (SchemeCard s : schemes)
		{
			count++;
		}
		return count;
	}
	
	public void exportHeroToPng(Hero h, File folder) throws Exception
	{
		for (HeroCard hc : h.cards)
		{
			HeroMaker hm = new HeroMaker();
			hm.exportFolder = folder.getAbsolutePath();
			hm.setCard(hc);
			BufferedImage image = hm.generateCard();
			hm.exportImage(image);
		}
	}
	
	public void exportVillainToPng(Villain v, File folder) throws Exception
	{
		for (VillainCard vc : v.cards)
		{
			VillainMaker vm = new VillainMaker();
			vm.exportFolder = folder.getAbsolutePath();
			vm.setCard(vc);
			BufferedImage image = vm.generateCard();
			vm.exportImage(image);
		}
	}
	
	public void exportSchemeToPng(SchemeCard s, File folder) throws Exception
	{
		SchemeMaker sm = new SchemeMaker();
		sm.exportFolder = folder.getAbsolutePath();
		sm.setCard(s);
		BufferedImage image = sm.generateCard();
		sm.exportImage(image);
	}
}
