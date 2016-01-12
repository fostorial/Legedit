package LegendaryCardMaker.CustomCardMaker.structure;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import LegendaryCardMaker.Icon;
import LegendaryCardMaker.Icon.ICON_TYPE;
import LegendaryCardMaker.LegendarySchemeMaker.SchemeCard;
import LegendaryCardMaker.LegendaryCardMaker;
import LegendaryCardMaker.CustomCardMaker.ALIGNMENT;
import LegendaryCardMaker.CustomCardMaker.CustomCardMaker;
import LegendaryCardMaker.CustomCardMaker.structure.ElementCardName.HIGHLIGHT;

public class CustomTemplate implements Cloneable {

	public String templateName;
	public String templateDisplayName;
	public String tab = "Custom";
	
	public HashMap<String, CustomElement> elementsHash = new HashMap<String, CustomElement>();
	public List<CustomElement> elements = new ArrayList<CustomElement>();
	
	public void addElement(CustomElement element)
	{
		elementsHash.put(element.name, element);
		elements.add(element);
	}
	
	public static CustomTemplate parseCustomTemplate(String templateFolder, String templateName)
	{
		CustomTemplate t = new CustomTemplate();
		
		try
		{
			File fXmlFile = new File(templateFolder + File.separator + templateName + File.separator + "template.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			doc.getDocumentElement().normalize();
			
			if (doc.hasChildNodes() && doc.getChildNodes().item(0).hasChildNodes()) 
			{
				NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
				for (int count = 0; count < nodeList.getLength(); count++) {
					Node node = nodeList.item(count);
					
					LegendaryCardMaker.debug("Processing: " + node.getNodeName());
					
					if (node.getNodeName().equals("template"))
					{	
						if (node.getAttributes().getNamedItem("name") != null)
						{
							t.templateName = node.getAttributes().getNamedItem("name").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("displayname") != null)
						{
							t.templateDisplayName = node.getAttributes().getNamedItem("displayname").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("tab") != null)
						{
							t.tab = node.getAttributes().getNamedItem("tab").getNodeValue();
						}
					}
					
					if (node.getNodeName().equals("cardsize"))
					{
						if (node.getAttributes().getNamedItem("cardwidth") != null)
						{
							CustomCardMaker.cardWidth = Integer.parseInt(node.getAttributes().getNamedItem("cardwidth").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("cardheight") != null)
						{
							CustomCardMaker.cardHeight = Integer.parseInt(node.getAttributes().getNamedItem("cardheight").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("dpi") != null)
						{
							CustomCardMaker.dpi = Integer.parseInt(node.getAttributes().getNamedItem("dpi").getNodeValue());
						}
					}
					
					if (node.getNodeName().equals("bgimage"))
					{
						ElementBackgroundImage element = new ElementBackgroundImage();
						element.template = t;
						
						if (node.getAttributes().getNamedItem("name") != null)
						{
							element.name = node.getAttributes().getNamedItem("name").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("path") != null)
						{
							element.path = node.getAttributes().getNamedItem("path").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("allowchange") != null)
						{
							element.allowChange = Boolean.parseBoolean(node.getAttributes().getNamedItem("allowchange").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("x") != null)
						{
							element.x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("y") != null)
						{
							element.y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("maxwidth") != null)
						{
							element.maxWidth = Integer.parseInt(node.getAttributes().getNamedItem("maxwidth").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("maxheight") != null)
						{
							element.maxHeight = Integer.parseInt(node.getAttributes().getNamedItem("maxheight").getNodeValue());
						}
						
						
						if (node.getAttributes().getNamedItem("zoomable") != null)
						{
							element.zoomable = Boolean.parseBoolean(node.getAttributes().getNamedItem("zoomable").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("fullsize") != null)
						{
							element.fullSize = Boolean.parseBoolean(node.getAttributes().getNamedItem("fullsize").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("templatefile") != null)
						{
							element.templateFile = Boolean.parseBoolean(node.getAttributes().getNamedItem("templatefile").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("visible") != null)
						{
							element.visible = Boolean.parseBoolean(node.getAttributes().getNamedItem("visible").getNodeValue());
						}
						
						t.addElement(element);
					}
					
					if (node.getNodeName().equals("icon"))
					{
						ElementIcon elementIcon = new ElementIcon();
						elementIcon.template = t;
						
						if (node.getAttributes().getNamedItem("name") != null)
						{
							elementIcon.name = node.getAttributes().getNamedItem("name").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("defaultvalue") != null)
						{
							elementIcon.defaultValue = Icon.valueOf(node.getAttributes().getNamedItem("defaultvalue").getNodeValue().toUpperCase());
						}
						
						if (node.getAttributes().getNamedItem("icontype") != null)
						{
							elementIcon.iconType = ICON_TYPE.valueOf(node.getAttributes().getNamedItem("icontype").getNodeValue().toUpperCase());
						}
						
						if (node.getAttributes().getNamedItem("allowchange") != null)
						{
							elementIcon.allowChange = Boolean.parseBoolean(node.getAttributes().getNamedItem("allowchange").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("optional") != null)
						{
							elementIcon.optional = Boolean.parseBoolean(node.getAttributes().getNamedItem("optional").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("x") != null)
						{
							elementIcon.x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("y") != null)
						{
							elementIcon.y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("maxwidth") != null)
						{
							elementIcon.maxWidth = Integer.parseInt(node.getAttributes().getNamedItem("maxwidth").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("maxheight") != null)
						{
							elementIcon.maxHeight = Integer.parseInt(node.getAttributes().getNamedItem("maxheight").getNodeValue());
						}
						
						
						if (node.getAttributes().getNamedItem("drawunderlay") != null)
						{
							elementIcon.drawUnderlay = Boolean.parseBoolean(node.getAttributes().getNamedItem("drawunderlay").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("blurradius") != null)
						{
							elementIcon.blurRadius = Integer.parseInt(node.getAttributes().getNamedItem("blurradius").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("bluedouble") != null)
						{
							elementIcon.blurDouble = Boolean.parseBoolean(node.getAttributes().getNamedItem("bluedouble").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("blurexpand") != null)
						{
							elementIcon.blurExpand = Integer.parseInt(node.getAttributes().getNamedItem("blurexpand").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("blurcolour") != null)
						{
							elementIcon.blurColour = Color.decode(node.getAttributes().getNamedItem("blurcolour").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("visible") != null)
						{
							elementIcon.visible = Boolean.parseBoolean(node.getAttributes().getNamedItem("visible").getNodeValue());
						}
						
						t.addElement(elementIcon);
					}
					
					if (node.getNodeName().equals("image"))
					{
						ElementImage element = new ElementImage();
						element.template = t;
						
						if (node.getAttributes().getNamedItem("name") != null)
						{
							element.name = node.getAttributes().getNamedItem("name").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("path") != null)
						{
							element.path = node.getAttributes().getNamedItem("path").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("allowchange") != null)
						{
							element.allowChange = Boolean.parseBoolean(node.getAttributes().getNamedItem("allowchange").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("x") != null)
						{
							element.x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("y") != null)
						{
							element.y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("maxwidth") != null)
						{
							element.maxWidth = Integer.parseInt(node.getAttributes().getNamedItem("maxwidth").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("maxheight") != null)
						{
							element.maxHeight = Integer.parseInt(node.getAttributes().getNamedItem("maxheight").getNodeValue());
						}
						
						
						if (node.getAttributes().getNamedItem("zoomable") != null)
						{
							element.zoomable = Boolean.parseBoolean(node.getAttributes().getNamedItem("zoomable").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("fullsize") != null)
						{
							element.fullSize = Boolean.parseBoolean(node.getAttributes().getNamedItem("fullsize").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("templatefile") != null)
						{
							element.templateFile = Boolean.parseBoolean(node.getAttributes().getNamedItem("templatefile").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("visible") != null)
						{
							element.visible = Boolean.parseBoolean(node.getAttributes().getNamedItem("visible").getNodeValue());
						}
						
						t.addElement(element);
					}
				
					if (node.getNodeName().equals("property"))
					{
						ElementProperty element = new ElementProperty();
						element.template = t;
						
						if (node.getAttributes().getNamedItem("name") != null)
						{
							element.name = node.getAttributes().getNamedItem("name").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("defaultvalue") != null)
						{
							element.defaultValue = node.getAttributes().getNamedItem("defaultvalue").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("property") != null)
						{
							element.property = CustomProperties.valueOf(node.getAttributes().getNamedItem("property").getNodeValue().toUpperCase());
						}
						
						if (node.getAttributes().getNamedItem("visible") != null)
						{
							element.visible = Boolean.parseBoolean(node.getAttributes().getNamedItem("visible").getNodeValue());
						}
						
						t.addElement(element);
					}
					
					if (node.getNodeName().equals("text"))
					{
						ElementText element = new ElementText();
						element.template = t;
						
						if (node.getAttributes().getNamedItem("name") != null)
						{
							element.name = node.getAttributes().getNamedItem("name").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("defaultvalue") != null)
						{
							element.defaultValue = node.getAttributes().getNamedItem("defaultvalue").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("linkedelement") != null)
						{
							element.linkedElement = t.elementsHash.get(node.getAttributes().getNamedItem("linkedelement").getNodeValue());
							if (element.linkedElement != null)
							{
								element.linkedElement.childElements.add(element);
							}
						}
						
						if (node.getAttributes().getNamedItem("allowchange") != null)
						{
							element.allowChange = Boolean.parseBoolean(node.getAttributes().getNamedItem("allowchange").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("x") != null)
						{
							element.x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("y") != null)
						{
							element.y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("colour") != null)
						{
							element.colour = Color.decode(node.getAttributes().getNamedItem("colour").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("drawunderlay") != null)
						{
							element.drawUnderlay = Boolean.parseBoolean(node.getAttributes().getNamedItem("drawunderlay").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("blurradius") != null)
						{
							element.blurRadius = Integer.parseInt(node.getAttributes().getNamedItem("blurradius").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("bluedouble") != null)
						{
							element.blurDouble = Boolean.parseBoolean(node.getAttributes().getNamedItem("bluedouble").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("blurexpand") != null)
						{
							element.blurExpand = Integer.parseInt(node.getAttributes().getNamedItem("blurexpand").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("blurcolour") != null)
						{
							element.blurColour = Color.decode(node.getAttributes().getNamedItem("blurcolour").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("textsize") != null)
						{
							element.textSize = Integer.parseInt(node.getAttributes().getNamedItem("textsize").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("uppercase") != null)
						{
							element.uppercase = Boolean.parseBoolean(node.getAttributes().getNamedItem("uppercase").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("fontname") != null)
						{
							element.fontName = node.getAttributes().getNamedItem("fontname").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("textsize") != null)
						{
							element.textSize = Integer.parseInt(node.getAttributes().getNamedItem("textsize").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("alignment") != null)
						{
							element.alignment = ALIGNMENT.valueOf(node.getAttributes().getNamedItem("alignment").getNodeValue().toUpperCase());
						}
						
						if (node.getAttributes().getNamedItem("visible") != null)
						{
							element.visible = Boolean.parseBoolean(node.getAttributes().getNamedItem("visible").getNodeValue());
						}
						
						t.addElement(element);
					}
					
					if (node.getNodeName().equals("cardname"))
					{
						ElementCardName element = new ElementCardName();
						element.template = t;
						
						if (node.getAttributes().getNamedItem("name") != null)
						{
							element.name = node.getAttributes().getNamedItem("name").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("defaultvalue") != null)
						{
							element.defaultValue = node.getAttributes().getNamedItem("defaultvalue").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("includesubname") != null)
						{
							element.includeSubname = Boolean.parseBoolean(node.getAttributes().getNamedItem("includesubname").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("subnametext") != null)
						{
							element.subnameText = node.getAttributes().getNamedItem("subnametext").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("subnameeditable") != null)
						{
							element.subnameEditable = Boolean.parseBoolean(node.getAttributes().getNamedItem("subnameeditable").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("allowchange") != null)
						{
							element.allowChange = Boolean.parseBoolean(node.getAttributes().getNamedItem("allowchange").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("x") != null)
						{
							element.x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("y") != null)
						{
							element.y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("colour") != null)
						{
							element.colour = Color.decode(node.getAttributes().getNamedItem("colour").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("drawunderlay") != null)
						{
							element.drawUnderlay = Boolean.parseBoolean(node.getAttributes().getNamedItem("drawunderlay").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("blurradius") != null)
						{
							element.blurRadius = Integer.parseInt(node.getAttributes().getNamedItem("blurradius").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("bluedouble") != null)
						{
							element.blurDouble = Boolean.parseBoolean(node.getAttributes().getNamedItem("bluedouble").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("blurexpand") != null)
						{
							element.blurExpand = Integer.parseInt(node.getAttributes().getNamedItem("blurexpand").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("highlightcolour") != null)
						{
							element.highlightColour = Color.decode(node.getAttributes().getNamedItem("highlightcolour").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("uppercase") != null)
						{
							element.uppercase = Boolean.parseBoolean(node.getAttributes().getNamedItem("uppercase").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("fontname") != null)
						{
							element.fontName = node.getAttributes().getNamedItem("fontname").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("textsize") != null)
						{
							element.textSize = Integer.parseInt(node.getAttributes().getNamedItem("textsize").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("subnamesize") != null)
						{
							element.subnameSize = Integer.parseInt(node.getAttributes().getNamedItem("subnamesize").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("alignment") != null)
						{
							element.alignment = ALIGNMENT.valueOf(node.getAttributes().getNamedItem("alignment").getNodeValue().toUpperCase());
						}
						
						if (node.getAttributes().getNamedItem("highlight") != null)
						{
							element.highlight = HIGHLIGHT.valueOf(node.getAttributes().getNamedItem("highlight").getNodeValue().toUpperCase());
						}
						
						if (node.getAttributes().getNamedItem("visible") != null)
						{
							element.visible = Boolean.parseBoolean(node.getAttributes().getNamedItem("visible").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("subnamegap") != null)
						{
							element.subnameGap = Integer.parseInt(node.getAttributes().getNamedItem("subnamegap").getNodeValue());
						}
						
						t.addElement(element);
					}
					
					if (node.getNodeName().equals("textarea"))
					{
						ElementTextArea element = new ElementTextArea();
						element.template = t;
						
						if (node.getAttributes().getNamedItem("name") != null)
						{
							element.name = node.getAttributes().getNamedItem("name").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("defaultvalue") != null)
						{
							element.defaultValue = node.getAttributes().getNamedItem("defaultvalue").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("allowchange") != null)
						{
							element.allowChange = Boolean.parseBoolean(node.getAttributes().getNamedItem("allowchange").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("colour") != null)
						{
							element.colour = Color.decode(node.getAttributes().getNamedItem("colour").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("fontname") != null)
						{
							element.fontName = node.getAttributes().getNamedItem("fontname").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("fontnamebold") != null)
						{
							element.fontNameBold = node.getAttributes().getNamedItem("fontnamebold").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("textsize") != null)
						{
							element.textSize = Integer.parseInt(node.getAttributes().getNamedItem("textsize").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("textsizebold") != null)
						{
							element.textSizeBold = Integer.parseInt(node.getAttributes().getNamedItem("textsizebold").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("alignmenthorizontal") != null)
						{
							element.alignmentHorizontal = ALIGNMENT.valueOf(node.getAttributes().getNamedItem("alignmenthorizontal").getNodeValue().toUpperCase());
						}
						
						if (node.getAttributes().getNamedItem("alignmentvertical") != null)
						{
							element.alignmentVertical = ALIGNMENT.valueOf(node.getAttributes().getNamedItem("alignmentvertical").getNodeValue().toUpperCase());
						}
						
						if (node.getAttributes().getNamedItem("rectxarray") != null)
						{
							element.rectXArray = node.getAttributes().getNamedItem("rectxarray").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("rectyarray") != null)
						{
							element.rectYArray = node.getAttributes().getNamedItem("rectyarray").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("debug") != null)
						{
							element.debug = Boolean.parseBoolean(node.getAttributes().getNamedItem("debug").getNodeValue());
						}
						
						if (node.getAttributes().getNamedItem("visible") != null)
						{
							element.visible = Boolean.parseBoolean(node.getAttributes().getNamedItem("visible").getNodeValue());
						}
						
						t.addElement(element);
					}
				}
				
			}
			
			return t;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public CustomTemplate getCopy()
	{
		try {
			CustomTemplate template = (CustomTemplate) clone();
			List<CustomElement> elements = new ArrayList<CustomElement>();
			for (CustomElement e : template.elements)
			{
				elements.add(e.getCopy());
			}
			for (CustomElement e : elements)
			{
				if (e.childElements != null && e.childElements.size() > 0)
				{
					List<CustomElement> newChildElements = new ArrayList<CustomElement>();
					for (CustomElement ce : e.childElements)
					{
						for (CustomElement ne : elements)
						{
							if (ne.name.equals(ce.name))
							{
								newChildElements.add(ne);
							}
						}
					}
					e.childElements = newChildElements;
				}
			}
			template.elements = elements;
			return template;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public CustomElement getElement(String name)
	{
		for (CustomElement e : elements)
		{
			if (e.name.equals(name))
			{
				return e;
			}
		}
		return null;
	}
	
	public ElementProperty getProperty(CustomProperties property)
	{
		for (CustomElement ce : elements)
		{
			if (ce instanceof ElementProperty && ((ElementProperty)ce).property != null && ((ElementProperty)ce).property.equals(property))
			{
				return ((ElementProperty)ce);
			}
		}
		return null;
	}
}
