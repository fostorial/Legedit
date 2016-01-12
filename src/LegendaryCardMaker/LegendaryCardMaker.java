package LegendaryCardMaker;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import LegendaryCardMaker.CustomCardMaker.CustomCardMaker;
import LegendaryCardMaker.CustomCardMaker.structure.CustomCard;
import LegendaryCardMaker.CustomCardMaker.structure.CustomElement;
import LegendaryCardMaker.CustomCardMaker.structure.CustomStructure;
import LegendaryCardMaker.CustomCardMaker.structure.CustomTemplate;
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
	
	public static String version = "0.11";
	public static boolean debug = true;

	public String inputFile = null;
	
	public boolean exportBasicText = false;
	public String textOutputFile = null;
	public String textErrorFile = "error.txt";
	public String exportFolder = null;
	
	public List<Hero> heroes = new ArrayList<Hero>();
	public List<Villain> villains = new ArrayList<Villain>();
	public List<SchemeCard> schemes = new ArrayList<SchemeCard>();
	public List<CustomCard> customCards = new ArrayList<CustomCard>();
	public Villain bystanderVillain = new Villain();
	public Villain woundVillain = new Villain();
	public List<CustomStructure> customStructures = new ArrayList<CustomStructure>();
	
	public List<CustomTemplate> allTemplates = new ArrayList<CustomTemplate>();
	public List<CustomStructure> allStructures = new ArrayList<CustomStructure>();
	
	public boolean ignoreGenerate = true;
	
	public String textDividerFile = "cardCreator/dividers.txt";
	public boolean generateDividerFile = false;
	
	public String currentFile = null;
	
	public String lastSaved = null;
	public String lastOpened = null;
	
	public static String expansionStyle = "default";
	
	public boolean dividerHorizontal = true;
	public String dfImagePath;
	public double dfImageZoom = 1.0d;
	public int dfImageOffsetX = 0;
	public int dfImageOffsetY = 0;
	public String dbImagePath;
	public double dbImageZoom = 1.0d;
	public int dbImageOffsetX = 0;
	public int dbImageOffsetY = 0;
	public boolean dividerTitleBarVisible = true;
	public Color dividerTitleBarColour = Color.WHITE;
	public String dividerCardStyle = "PowerIcons";
	public String dividerBodyStyle = "Images";
	
	public String expansionName = "";
	public String rules = "";
	public String keywords = "";
	
	public static void main(String[] args)
	{
		LegendaryCardMaker lcm = new LegendaryCardMaker();
		new LegendaryCardMakerFrame(lcm);
	}
	
	public LegendaryCardMaker()
	{
		bystanderVillain.name = "system_bystander_villain";
		villains.add(bystanderVillain);
		
		woundVillain.name = "system_wound_villain";
		villains.add(woundVillain);
		
		loadTemplates();
		loadStructures();
	}
	
	public void processInput(String inputFile)
	{	
		heroes = new ArrayList<Hero>();
		villains = new ArrayList<Villain>();
		schemes = new ArrayList<SchemeCard>();
		customCards = new ArrayList<CustomCard>();
		
		bystanderVillain = new Villain();
		bystanderVillain.name = "system_bystander_villain";
		villains.add(bystanderVillain);
		
		woundVillain = new Villain();
		woundVillain.name = "system_wound_villain";
		villains.add(woundVillain);
		
		expansionName = "";
		keywords = "";
		rules = "";
		
		Hero h = new Hero();
		HeroCard hc = new HeroCard();
		HeroMaker hm = new HeroMaker();
		
		Villain v = new Villain();
		VillainCard vc = new VillainCard();
		VillainMaker vm = new VillainMaker();
		
		SchemeCard sc = new SchemeCard();
		SchemeMaker sm = new SchemeMaker();
		
		CustomCard cc = new CustomCard();
		
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
				   
				   if (line.startsWith("EXPANSIONNAME;"))
				   {
					   expansionName = line.replace("EXPANSIONNAME;", "");
				   }
				   
				   if (line.startsWith("RULES;"))
				   {
					   rules = line.replace("RULES;", "");
				   }
				   
				   if (line.startsWith("KEYWORDS;"))
				   {
					   keywords = line.replace("KEYWORDS;", "");
				   }
				   
				   if (line.startsWith("DIVIDERORIENTATION;"))
				   {
					   String orientation = line.replace("DIVIDERORIENTATION;", "");
					   if (orientation != null && orientation.equals("VERTICAL"))
					   {
						   dividerHorizontal = false;
					   }
					   else
					   {
						   dividerHorizontal = true;
					   }
				   }
				   
				   if (line.startsWith("DIVIDERCARDSTYLE;"))
				   {
					   dividerCardStyle = line.replace("DIVIDERCARDSTYLE;", "");
				   }
				   
				   if (line.startsWith("DIVIDERBODYSTYLE;"))
				   {
					   dividerBodyStyle = line.replace("DIVIDERBODYSTYLE;", "");
				   }
				   
				   if (line.startsWith("DFIMAGEZOOM;"))
				   {
					   dfImageZoom = Double.parseDouble(line.replace("DFIMAGEZOOM;", ""));
				   }
				   
				   if (line.startsWith("DFIMAGEPATH;"))
				   {
					   dfImagePath = line.replace("DFIMAGEPATH;", "");
				   }
				   
				   if (line.startsWith("DFIMAGEOFFSETX;"))
				   {
					   dfImageOffsetX = Integer.parseInt(line.replace("DFIMAGEOFFSETX;", ""));
				   }
				   
				   if (line.startsWith("DFIMAGEOFFSETY;"))
				   {
					   dfImageOffsetY = Integer.parseInt(line.replace("DFIMAGEOFFSETY;", ""));
				   }
				   
				   if (line.startsWith("DBIMAGEZOOM;"))
				   {
					   dbImageZoom = Double.parseDouble(line.replace("DBIMAGEZOOM;", ""));
				   }
				   
				   if (line.startsWith("DBIMAGEPATH;"))
				   {
					   dbImagePath = line.replace("DBIMAGEPATH;", "");
				   }
				   
				   if (line.startsWith("DBIMAGEOFFSETX;"))
				   {
					   dbImageOffsetX = Integer.parseInt(line.replace("DBIMAGEOFFSETX;", ""));
				   }
				   
				   if (line.startsWith("DBIMAGEOFFSETY;"))
				   {
					   dbImageOffsetY = Integer.parseInt(line.replace("DBIMAGEOFFSETY;", ""));
				   }
				   
				   if (line.startsWith("DTITLEBARVISIBLE;"))
				   {
					   dividerTitleBarVisible = Boolean.parseBoolean(line.replace("DTITLEBARVISIBLE;", ""));
				   }
				   
				   if (line.startsWith("DTITLEBARCOLOUR;"))
				   {
					   Integer rgb = Integer.parseInt(line.replace("DTITLEBARCOLOUR;", ""));
					   Color c = new Color(rgb);
					   if (c != null)
					   {
						   dividerTitleBarColour = c;
					   }
				   }
				   
				   
				   // Load Hero Template Information
				   if (line.startsWith("HCTCARDNAMESIZE;"))
				   {
					   HeroMaker.cardNameSizeTemplate = Integer.parseInt(line.replace("HCTCARDNAMESIZE;", ""));
				   }
				   
				   if (line.startsWith("HCTCARDNAMEFONTNAME;"))
				   {
					   HeroMaker.cardNameFontNameTemplate = line.replace("HCTCARDNAMEFONTNAME;", "");
				   }
				   
				   if (line.startsWith("HCTCARDNAMEFONTSTYLE;"))
				   {
					   HeroMaker.cardNameFontStyleTemplate = Integer.parseInt(line.replace("HCTCARDNAMEFONTSTYLE;", ""));
				   }
				   
				   if (line.startsWith("HCTHERONAMESIZE;"))
				   {
					   HeroMaker.heroNameSizeTemplate = Integer.parseInt(line.replace("HCTHERONAMESIZE;", ""));
				   }
				   
				   if (line.startsWith("HCTHERONAMEFONTNAME;"))
				   {
					   HeroMaker.heroNameFontNameTemplate = line.replace("HCTHERONAMEFONTNAME;", "");
				   }
				   
				   if (line.startsWith("HCTHERONAMEFONTSTYLE;"))
				   {
					   HeroMaker.heroNameFontStyleTemplate = Integer.parseInt(line.replace("HCTHERONAMEFONTSTYLE;", ""));
				   }
				   
				   if (line.startsWith("HCTABILITYTEXTSIZE;"))
				   {
					   HeroMaker.abilityTextSizeTemplate = Integer.parseInt(line.replace("HCTABILITYTEXTSIZE;", ""));
				   }
				   
				   if (line.startsWith("HCTABILITYTEXTFONTNAME;"))
				   {
					   HeroMaker.abilityTextFontNameTemplate = line.replace("HCTABILITYTEXTFONTNAME;", "");
				   }
				   
				   if (line.startsWith("HCTABILITYTEXTFONTSTYLE;"))
				   {
					   HeroMaker.abilityTextFontStyleTemplate = Integer.parseInt(line.replace("HCTABILITYTEXTFONTSTYLE;", ""));
				   }
				   
				   if (line.startsWith("HCTCARDNAMECOLOUR;"))
				   {
					   HeroMaker.cardNameColorTemplate = new Color(Integer.parseInt(line.replace("HCTCARDNAMECOLOUR;", "")));
				   }
				   
				   if (line.startsWith("HCTHERONAMECOLOUR;"))
				   {
					   HeroMaker.heroNameColorTemplate = new Color(Integer.parseInt(line.replace("HCTHERONAMECOLOUR;", "")));
				   }
				   
				   if (line.startsWith("HCTABILITYTEXTCOLOUR;"))
				   {
					   HeroMaker.abilityTextColorTemplate = new Color(Integer.parseInt(line.replace("HCTABILITYTEXTCOLOUR;", "")));
				   }
				   
				   if (line.startsWith("HCTTEAMPOWERUNDERLAYCOLOUR;"))
				   {
					   HeroMaker.teamPowerUnderlayColorTemplate = new Color(Integer.parseInt(line.replace("HCTTEAMPOWERUNDERLAYCOLOUR;", "")));
				   }
				   
				   if (line.startsWith("HCTTEAMPOWERBLURRADIUS;"))
				   {
					   HeroMaker.teamPowerBlurRadiusTemplate = Integer.parseInt(line.replace("HCTTEAMPOWERBLURRADIUS;", ""));
				   }
				   
				   if (line.startsWith("HCTNAMEHIGHLIGHTTYPE;"))
				   {
					   HeroMaker.nameHighlightTemplate = line.replace("HCTNAMEHIGHLIGHTTYPE;", "");
				   }
				   
				   if (line.startsWith("HCTCOSTSIZE;"))
				   {
					   HeroMaker.costSizeTemplate = Integer.parseInt(line.replace("HCTCOSTSIZE;", ""));
				   }
				   
				   if (line.startsWith("HCTCOSTFONTNAME;"))
				   {
					   HeroMaker.costFontNameTemplate = line.replace("HCTCOSTFONTNAME;", "");
				   }
				   
				   if (line.startsWith("HCTCOSTFONTSTYLE;"))
				   {
					   HeroMaker.costFontStyleTemplate = Integer.parseInt(line.replace("HCTCOSTFONTSTYLE;", ""));
				   }
				   
				   if (line.startsWith("HCTCOSTCOLOUR;"))
				   {
					   HeroMaker.costColorTemplate = new Color(Integer.parseInt(line.replace("HCTCOSTCOLOUR;", "")));
				   }
				   
				   if (line.startsWith("HCTATTACKSIZE;"))
				   {
					   HeroMaker.attackSizeTemplate = Integer.parseInt(line.replace("HCTATTACKSIZE;", ""));
				   }
				   
				   if (line.startsWith("HCTATTACKFONTNAME;"))
				   {
					   HeroMaker.attackFontNameTemplate = line.replace("HCTATTACKFONTNAME;", "");
				   }
				   
				   if (line.startsWith("HCTATTACKFONTSTYLE;"))
				   {
					   HeroMaker.attackFontStyleTemplate = Integer.parseInt(line.replace("HCTATTACKFONTSTYLE;", ""));
				   }
				   
				   if (line.startsWith("HCTATTACKCOLOUR;"))
				   {
					   HeroMaker.attackColorTemplate = new Color(Integer.parseInt(line.replace("HCTATTACKCOLOUR;", "")));
				   }
				   
				   if (line.startsWith("HCTRECRUITSIZE;"))
				   {
					   HeroMaker.recruitSizeTemplate = Integer.parseInt(line.replace("HCTRECRUITSIZE;", ""));
				   }
				   
				   if (line.startsWith("HCTRECRUITFONTNAME;"))
				   {
					   HeroMaker.recruitFontNameTemplate = line.replace("HCTRECRUITFONTNAME;", "");
				   }
				   
				   if (line.startsWith("HCTRECRUITFONTSTYLE;"))
				   {
					   HeroMaker.recruitFontStyleTemplate = Integer.parseInt(line.replace("HCTRECRUITFONTSTYLE;", ""));
				   }
				   
				   if (line.startsWith("HCTRECRUITCOLOUR;"))
				   {
					   HeroMaker.recruitColorTemplate = new Color(Integer.parseInt(line.replace("HCTRECRUITCOLOUR;", "")));
				   }
				   
				   if (line.startsWith("HCTDUALCLASSSTYLE;"))
				   {
					   HeroMaker.dualClassStyle = HeroMaker.DUAL_CLASS_STYLE.valueOf(line.replace("HCTDUALCLASSSTYLE;", ""));
				   }
				   
				   
				   // Load Hero Information
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
				   
				   if (line.startsWith("HFIMAGEZOOM;"))
				   {
					   h.imageZoom = Double.parseDouble(line.replace("HFIMAGEZOOM;", ""));
				   }
				   
				   if (line.startsWith("HFIMAGEPATH;"))
				   {
					   h.imagePath = line.replace("HFIMAGEPATH;", "");
				   }
				   
				   if (line.startsWith("HFIMAGEOFFSETX;"))
				   {
					   h.imageOffsetX = Integer.parseInt(line.replace("HFIMAGEOFFSETX;", ""));
				   }
				   
				   if (line.startsWith("HFIMAGEOFFSETY;"))
				   {
					   h.imageOffsetY = Integer.parseInt(line.replace("HFIMAGEOFFSETY;", ""));
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
				   
				   if (line.startsWith("HCHERONAMESIZE;"))
				   {
					   hm.heroNameSize = Integer.parseInt(line.replace("HCHERONAMESIZE;", ""));
					   hc.heroNameSize = Integer.parseInt(line.replace("HCHERONAMESIZE;", ""));
				   }
				   
				   if (line.startsWith("HCRARITY;"))
				   {
					   hc.rarity = CardRarity.valueOf(line.replace("HCRARITY;", ""));
				   }
				   
				   if (line.startsWith("HCTEAM;"))
				   {
					   hc.cardTeam = Icon.valueOf(line.replace("HCTEAM;", ""));
				   }
				   
				   if (line.startsWith("HCTEAM2;"))
				   {
					   hc.cardTeam2 = Icon.valueOf(line.replace("HCTEAM2;", ""));
				   }
				   
				   if (line.startsWith("HCPOWER;"))
				   {
					   hc.cardPower = Icon.valueOf(line.replace("HCPOWER;", ""));
				   }
				   
				   if (line.startsWith("HCPOWER2;"))
				   {
					   hc.cardPower2 = Icon.valueOf(line.replace("HCPOWER2;", ""));
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
				   
				   if (line.startsWith("HCNUMBERINDECK;"))
				   {
					   hc.numberInDeck = Integer.parseInt(line.replace("HCNUMBERINDECK;", ""));
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
				   
				   if (line.startsWith("VFIMAGEZOOM;"))
				   {
					   v.imageZoom = Double.parseDouble(line.replace("VFIMAGEZOOM;", ""));
				   }
				   
				   if (line.startsWith("VFIMAGEPATH;"))
				   {
					   v.imagePath = line.replace("VFIMAGEPATH;", "");
				   }
				   
				   if (line.startsWith("VFIMAGEOFFSETX;"))
				   {
					   v.imageOffsetX = Integer.parseInt(line.replace("VFIMAGEOFFSETX;", ""));
				   }
				   
				   if (line.startsWith("VFIMAGEOFFSETY;"))
				   {
					   v.imageOffsetY = Integer.parseInt(line.replace("VFIMAGEOFFSETY;", ""));
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
				   
				   if (line.startsWith("VCNUMBERINDECK;"))
				   {
					   vc.numberInDeck = Integer.parseInt(line.replace("VCNUMBERINDECK;", ""));
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
				   
				   if (line.startsWith("SCIMAGEZOOM;"))
				   {
					   sc.imageZoom = Double.parseDouble(line.replace("SCIMAGEZOOM;", ""));
				   }
				   
				   if (line.startsWith("SCIMAGEPATH;"))
				   {
					   sc.imagePath = line.replace("SCIMAGEPATH;", "");
				   }
				   
				   if (line.startsWith("SCIMAGEOFFSETX;"))
				   {
					   sc.imageOffsetX = Integer.parseInt(line.replace("SCIMAGEOFFSETX;", ""));
				   }
				   
				   if (line.startsWith("SCIMAGEOFFSETY;"))
				   {
					   sc.imageOffsetY = Integer.parseInt(line.replace("SCIMAGEOFFSETY;", ""));
				   }
				   
				   if (line.startsWith("SCNUMBERINDECK;"))
				   {
					   sc.numberInDeck = Integer.parseInt(line.replace("SCNUMBERINDECK;", ""));
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
				   
				   
				   // Load Custom Card Information
				   if (line.startsWith("CUSTOMCARD;"))
				   {
					   cc = new CustomCard();
					   customCards.add(cc);
				   }
				   
				   if (line.startsWith("TEMPLATE;"))
				   {
					   cc.templateName = line.replace("TEMPLATE;", "");
					   for (CustomTemplate t : allTemplates)
					   {
						   if (t.templateName.equals(cc.templateName))
						   {
							   cc.template = t.getCopy();
							   break;
						   }
					   }
				   }
				   
				   if (line.startsWith("CUSTOMVALUE;"))
				   {
					   String[] split = line.split(";");
					   
					   CustomElement element = null;
					   element = cc.template.getElement(split[1]);
					   
					   if (element != null)
					   {
						   Field field = element.getClass().getField(split[2]);
						   if (field != null)
						   {
							   Object value = null;
							   String splitVal = "";
							   try
							   {
								   value = split[3];
								   splitVal = split[3];
							   }
							   catch (Exception e)
							   {
								   value = null;
								   splitVal = "";
							   }
							   if (field.getType().getSimpleName().equals("Icon"))
							   {
								   value = Icon.valueOf(splitVal);
							   }
							   if (field.getType().getSimpleName().equals("boolean"))
							   {
								   value = Boolean.valueOf(splitVal);
							   }
							   
							   if (field.getType().getSimpleName().equals("int"))
							   {
								   value = Integer.valueOf(splitVal);
							   }
							   
							   if (field.getType().getSimpleName().equals("double"))
							   {
								   value = Double.valueOf(splitVal);
							   }
							   field.set(element, value);
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
		saveExpansion(false, currentFile);
	}
	
	public void saveExpansion(boolean fullExport, String saveFile) throws Exception
	{	
		String str = "";
		
		str += "EXPANSIONSTYLE;" + expansionStyle;
		str += "\n\n";
		
		str += "EXPANSIONNAME;" + expansionName;
		str += "\n\n";
		str += "RULES;" + rules;
		str += "\n\n";
		str += "KEYWORDS;" + keywords;
		str += "\n\n";
		
		str += "DIVIDERORIENTATION;" + (dividerHorizontal ? "HORIZONTAL" : "VERTICAL");
		str += "\n";
		
		str += "DIVIDERCARDSTYLE;" + dividerCardStyle + "\n";
		
		str += "DIVIDERBODYSTYLE;" + dividerBodyStyle + "\n";
		
		str += "DTITLEBARCOLOUR;" + (dividerTitleBarColour == null ? Color.white.getRGB() : dividerTitleBarColour.getRGB()) + "\n";
		
		str += "DTITLEBARVISIBLE;" + dividerTitleBarVisible + "\n";
		str += "\n";
		
		str += HeroMaker.generateTemplateOutputString();
		
		if (dfImagePath != null)
			str += "DFIMAGEPATH;" + dfImagePath + "\n";
		
		if (dfImagePath != null)
			str += "DFIMAGEZOOM;" + dfImageZoom + "\n";
		
		if (dfImagePath != null)
			str += "DFIMAGEOFFSETX;" + dfImageOffsetX + "\n";
		
		if (dfImagePath != null)
			str += "DFIMAGEOFFSETY;" + dfImageOffsetY + "\n";
		
		if (dbImagePath != null)
			str += "DBIMAGEPATH;" + dbImagePath + "\n";
		
		if (dbImagePath != null)
			str += "DBIMAGEZOOM;" + dbImageZoom + "\n";
		
		if (dbImagePath != null)
			str += "DBIMAGEOFFSETX;" + dbImageOffsetX + "\n";
		
		if (dbImagePath != null)
			str += "DBIMAGEOFFSETY;" + dbImageOffsetY + "\n";
		
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
		
		for (CustomCard s : customCards)
		{
			str += s.generateOutputString();
			str += "\n\n";
		}
		
		FileWriter fw = new FileWriter(new File(saveFile));
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
		
		for (CustomCard s : customCards)
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
		
		for (CustomCard cc : customCards)
		{
			if (cc.changed)
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
		
		for (CustomCard cc : customCards)
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
	
	public void exportHeroToJpeg(Hero h, File folder) throws Exception
	{
		for (HeroCard hc : h.cards)
		{
			HeroMaker hm = new HeroMaker();
			hm.exportToPNG = false;
			hm.exportFolder = folder.getAbsolutePath();
			hm.setCard(hc);
			BufferedImage image = hm.generateCard();
			hm.exportImage(image);
		}
	}
	
	public void exportVillainToJpeg(Villain v, File folder) throws Exception
	{
		for (VillainCard vc : v.cards)
		{
			VillainMaker vm = new VillainMaker();
			vm.exportToPNG = false;
			vm.exportFolder = folder.getAbsolutePath();
			vm.setCard(vc);
			BufferedImage image = vm.generateCard();
			vm.exportImage(image);
		}
	}
	
	public void exportSchemeToJpeg(SchemeCard s, File folder) throws Exception
	{
		SchemeMaker sm = new SchemeMaker();
		sm.exportToPNG = false;
		sm.exportFolder = folder.getAbsolutePath();
		sm.setCard(s);
		BufferedImage image = sm.generateCard();
		sm.exportImage(image);
	}
	
	public void exportCustomCardToJpeg(CustomCard s, File folder) throws Exception
	{
		CustomCardMaker sm = new CustomCardMaker();
		sm.exportToPNG = false;
		sm.exportFolder = folder.getAbsolutePath();
		sm.setCard(s);
		BufferedImage image = sm.generateCard();
		sm.exportImage(image);
	}
	
	public void exportCustomCardToPng(CustomCard s, File folder) throws Exception
	{
		CustomCardMaker sm = new CustomCardMaker();
		sm.exportFolder = folder.getAbsolutePath();
		sm.setCard(s);
		BufferedImage image = sm.generateCard();
		sm.exportImage(image);
	}
	
	public static void debug(String message)
	{
		if (debug)
		{
			System.out.println(message);
		}
	}
	
	public void loadTemplates()
	{
		System.out.println("Loading Custom Templates");
		
		allTemplates = new ArrayList<CustomTemplate>();
		
		String templateFolder = "legendary" + File.separator + "templates" + File.separator + "custom";
		File dir = new File(templateFolder);
		for (File f : dir.listFiles())
		{
			if (f.isDirectory())
			{
				File templateFile = getTemplateFile(f);
				if (templateFile != null)
				{
					CustomTemplate template = CustomTemplate.parseCustomTemplate(templateFolder, f.getName());
					allTemplates.add(template);
					System.out.println("Loaded: " + template.templateName);
				}
			}
		}
	}
	
	public void loadStructures()
	{
		System.out.println("Loading Custom Templates");
		
		allStructures = new ArrayList<CustomStructure>();
		
		String templateFolder = "legendary" + File.separator + "structures";
		File dir = new File(templateFolder);
		for (File f : dir.listFiles())
		{
			if (f.getName().endsWith(".xml"))
			{
				CustomStructure template = CustomStructure.parseCustomStructure(f);
				allStructures.add(template);
				System.out.println("Loaded: " + template.name);
			}
		}
	}
	
	private File getTemplateFile(File f)
	{
		for (File file : f.listFiles())
		{
			if (file.getName().toLowerCase().equals("template.xml"))
			{
				return file;
			}
		}
		return null;
	}
}