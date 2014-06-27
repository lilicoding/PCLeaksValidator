package lu.uni.snt.pcleaks.validator;

public class Main 
{
	/**
	 * args[0]: 1 / 0 for the leakType
	 * args[1]: actions -> action:action
	 * args[2]: categories -> category:category
	 * args[3]: type:subtype
	 * args[4]: extra:extra
	 * args[5]: compType
	 * 
	 */
	public static void main(String[] args) 
	{
		Component comp = parseArgs(args);
		generate(comp);
	}
	
	private static void printUsage()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PCLeaksValidator-Main Usage: " + "\n");
		sb.append("args[0]: 1 / 0 for the leakType." + "\n");
		sb.append("args[1]: action:action." + "\n");
		sb.append("args[2]: category:category." + "\n");
		sb.append("args[3]: type:subtype." + "\n");
		sb.append("args[4]: extra:extra." + "\n");
		sb.append("args[5]: compType." + "\n");
		
		System.out.println(sb.toString());
	}
	
	public static void generate(Component comp)
	{
		ApkGenerator.generateApk(comp);
	}
	
	private static Component parseArgs(String[] args)
	{
		if (args.length != 6)
		{
			printUsage();
			
			throw new RuntimeException("Wrong parameters.");
		}
		
		Component comp = new Component();
		
		if (Integer.parseInt(args[0]) == 1)
		{
			comp.leakType = "pacl";
		}
		else
		{
			comp.leakType = "ppcl";
		}
		
		if (args[1].contains(":"))
		{
			String[] actions = args[1].split(":");
			for (String action : actions)
			{
				comp.actions.add(action);
			}
		}
		else
		{
			comp.actions.add(args[1]);
		}
		
		if (args[2].contains(":"))
		{
			String[] categories = args[2].split(":");
			for (String category : categories)
			{
				comp.categories.add(category);
			}
		}
		else
		{
			comp.categories.add(args[2]);
		}
		
		if (! args[3].contains(":"))
		{
			throw new RuntimeException("Wrong parameters.");
		}
		else
		{
			String[] types = args[3].split(":");
			DataAndType data = new DataAndType();
			if (! types[0].equals("null"))
			{
				data.setType(types[0]);
			}
			
			if (! types[1].equals("null"))
			{
				data.setSubtype(types[1]);
			}
			
			comp.data = data;
		}
		
		if (args[4].contains(":"))
		{
			String[] extras = args[4].split(":");
			for (String extra : extras)
			{
				comp.extraNames.add(extra);
			}
		}
		else
		{
			comp.extraNames.add(args[4]);
		}
		
		comp.compType = args[5];
		
		return comp;
	}
	
	
}
