package LegendaryCardMaker.CustomCardMaker.structure;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import LegendaryCardMaker.LegendaryCardMaker;
import LegendaryCardMaker.LegendaryItem;

public class CustomStructure extends LegendaryItem {

	public String name;
	public String displayName;
	public String tab;
	
	public List<CustomStructureTemplate> templates = new ArrayList<CustomStructureTemplate>();
	
	public static CustomStructure parseCustomStructure(File structureFile)
	{
		CustomStructure t = new CustomStructure();
		
		try
		{
			File fXmlFile = structureFile;
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
					
					if (node.getNodeName().equals("structure"))
					{	
						if (node.getAttributes().getNamedItem("name") != null)
						{
							t.name = node.getAttributes().getNamedItem("name").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("displayname") != null)
						{
							t.displayName = node.getAttributes().getNamedItem("displayname").getNodeValue();
						}
						
						if (node.getAttributes().getNamedItem("tab") != null)
						{
							t.tab = node.getAttributes().getNamedItem("tab").getNodeValue();
						}
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
	
	public class CustomStructureTemplate
	{
		public CustomStructure structure;
		public CustomTemplate template;
		public int defaultNumber = 0;
		
		public CustomStructureTemplate(CustomStructure structure,
				CustomTemplate template, int defaultNumber) {
			super();
			this.structure = structure;
			this.template = template;
			this.defaultNumber = defaultNumber;
		}
	}
}
