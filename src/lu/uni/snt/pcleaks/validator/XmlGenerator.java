package lu.uni.snt.pcleaks.validator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XmlGenerator {

	public static void main(String[] args) 
	{
		generate(null);
	}
	
	private static final String TEMPLATE_XML = "template/AndroidManifest.xml";
	
	public static void generate(Component comp) 
	{
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(TEMPLATE_XML);
		
		try
		{
			Document doc = (Document) builder.build(xmlFile);
			Element root = doc.getRootElement();
			
			Element app = root.getChild(Constants.APPLICATION);
			
			List<Attribute> attrs = app.getAttributes();
			
			Namespace ns = null;
			
			for (Attribute attr : attrs)
			{
				ns = attr.getNamespace();
				break;
			}
			
			String type = Constants.ACTIVITY;
			
			if (comp.compType.equals(Constants.TYPE_SERVICE))
			{
				type = Constants.SERVICE;
			}
			else if (comp.compType.equals(Constants.TYPE_RECEIVER))
			{
				type = Constants.RECEIVER;
			}
			else if (comp.compType.equals(Constants.TYPE_PROVIDER))
			{
				type = Constants.PROVIDER;
			}
			else
			{
				type = Constants.ACTIVITY;
			}
			
			Element c = new Element(type);
			c.setAttribute(Constants.NAME, comp.pkg + "." + comp.name, ns);
			
			Element intentFilter = new Element(Constants.INTENT_FILTER);
			
			for (String action : comp.actions)
			{
				Element act = new Element(Constants.ACTION);
				act.setAttribute(Constants.NAME, action, ns);
				intentFilter.addContent(act);
			}
			
			for (String category : comp.categories)
			{
				Element cat = new Element(Constants.CATEGORY);
				cat.setAttribute(Constants.NAME, category, ns);
				intentFilter.addContent(cat);
			}
			
			if (comp.data.containingMimeType() && comp.leakType.equals(Constants.PACL))
			{
				Element dat = new Element(Constants.DATA);
				dat.setAttribute(Constants.MIMETYPE, "*/*", ns);
				intentFilter.addContent(dat);
			}
			
			c.addContent(intentFilter);
			
			app.addContent(c);
			
			XMLOutputter outputter = new XMLOutputter();
			outputter.setFormat(Format.getPrettyFormat());
			outputter.output(doc, new FileOutputStream("workspace/AndroidManifest.xml"));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

}
