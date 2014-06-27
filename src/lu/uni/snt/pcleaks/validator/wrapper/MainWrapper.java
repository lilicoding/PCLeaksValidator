package lu.uni.snt.pcleaks.validator.wrapper;

import java.util.List;

import lu.uni.snt.pcleaks.validator.Component;
import lu.uni.snt.pcleaks.validator.Constants;
import lu.uni.snt.pcleaks.validator.DataAndType;

/**
 * 
 * 1. args example:
 * /Users/li.li/Project/github/DroidBench/apk/InterAppCommunication_startActivity1/InterAppCommunication_startActivity1_source.apk /Users/li.li/Project/github/android-platforms <lu.uni.serval.iac_startactivity1_source.OutFlowActivity:*void*onCreate(android.os.Bundle)> 1 12
 * 
 * 
 */


public class MainWrapper {

	/**
	 * Please be sure you want to use this class (need MySQL's support for all the informations of the analyzed apk) instead of lu.uni.snt.pcleaks.validator.Main
	 * For methodSignature, since it contains whitespace that makes parse the args become complicated, so each whitespace in methodSignature should be replace to '*'.
	 * 
	 * args[0]: appPath
	 * args[1]: androidJars
	 * args[2]: methodSignature
	 * args[3]: isIcc
	 * args[4]: jimpleIndex
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		if (args.length != 5)
		{
			printUsage();
			
			throw new RuntimeException("Wrong parameters.");
		}
		
		args[2] = args[2].replace("*", " ");
		
		if (args[3].equals("1"))
		{
			//for PACL
			Component comp = build(args[2], Integer.parseInt(args[4]));
			lu.uni.snt.pcleaks.validator.Main.generate(comp);
		}
		else
		{
			//for PPCL
			Component comp = build(args[2], args[0], args[1]);
			lu.uni.snt.pcleaks.validator.Main.generate(comp);
		}
		
	}

	private static void printUsage()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PCLeaksValidator-MainWrapper Usage: " + "\n");
		sb.append("args[0]: appPath." + "\n");
		sb.append("args[1]: androidJars." + "\n");
		sb.append("args[2]: methodSignature, using '*' to replace whitespace." + "\n");
		sb.append("args[3]: isIcc, 1 for icc and 0 for non-Icc." + "\n");
		sb.append("args[4]: jimpleIndex, the line number of the icc method." + "\n");
		
		System.out.println(sb.toString());
	}
	
	//for PACL, e.g., generate a startActivity
	//It is not necessary to perform string analysis for put extras
	public static Component build(String methodSignature, int jimpleIndex)
	{
		int intentId = DBHelper.getIntentId(methodSignature, jimpleIndex);
		List<String> actions = DBHelper.getIntentActions(intentId);
		List<String> categories = DBHelper.getIntentCategories(intentId);
		String mimeType = DBHelper.getIntentMimeType(intentId);
		String exitType = DBHelper.getExitType(methodSignature, jimpleIndex);
		
		Component comp = new Component();
		comp.leakType = Constants.PACL;
		
		for (String act : actions)
		{
			comp.actions.add(act);
		}
		
		for (String cat : categories)
		{
			comp.categories.add(cat);
		}
		
		if ( ! (mimeType == null || mimeType.equals("")) )
		{
			String[] types = mimeType.split("/");
			if (! (types[0].equals("null") && types[1].equals("null")) )
			{
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
		}
		
		comp.compType = exitType;
		
		return comp;
	}
	
	//perform string analysis for get extras
	public static Component build(String methodSignature, String apkPath, String androidJars)
	{
		int id = DBHelper.getIntentFilterId(methodSignature);
		List<String> actions = DBHelper.getIntentFilterActions(id);
		List<String> categories = DBHelper.getIntentFilterCategories(id);
		String mimeType = DBHelper.getIntentMimeType(id);
		String compType = DBHelper.getComponentType(id);
		List<String> extras = ExtraExtractor.extract(apkPath, androidJars, methodSignature);
		
		Component comp = new Component();
		comp.leakType = Constants.PPCL;
		
		for (String act : actions)
		{
			comp.actions.add(act);
		}
		
		for (String cat : categories)
		{
			comp.categories.add(cat);
		}
		
		if ( ! (mimeType == null || mimeType.equals("")) )
		{
			String[] types = mimeType.split("/");
			if (! (types[0].equals("null") && types[1].equals("null")) )
			{
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
		}
		
		comp.compType = compType;
		
		
		System.out.println("Extras: " + extras);
		for (String ext : extras)
		{
			comp.extraNames.add(ext.replace("\"", ""));
		}
			
		return comp;
	}
}
