package lu.uni.snt.pcleaks.validator.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import soot.Body;
import soot.BodyTransformer;
import soot.G;
import soot.PackManager;
import soot.PatchingChain;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.Value;
import soot.jimple.Stmt;
import soot.options.Options;

public class ExtraExtractor extends BodyTransformer
{
	//method to extras, when a string is not directly specified in the invoke stmt, <anything> is specified.
	public static Map<String, List<String>> getExtras = new HashMap<String, List<String>>(); 
	public static Map<String, List<String>> putExtras = new HashMap<String, List<String>>();
	public static final String intentCls = "android.content.Intent";
	public static final String bundleCls = "android.os.Bundle";
	
	private String methodSignature = "";
	
	public static List<String> extract(String apkPath, String androidJars, String methodSignature)
	{
		String[] args =
        {
            "-android-jars", androidJars,
            "-process-dir", apkPath,
            "-ire",
            "-pp",
            "-allow-phantom-refs",
        };
		
		G.reset();
		
		Options.v().set_src_prec(Options.src_prec_apk);
		Options.v().set_output_format(Options.output_format_none);
		
		ExtraExtractor extractor = new ExtraExtractor(methodSignature);
		
		PackManager.v().getPack("jtp").add(new Transform("jtp.extractor", extractor));
		
        soot.Main.main(args);
        
        return getExtras.get(methodSignature);
	}
	
	public ExtraExtractor(String methodSignature)
	{
		this.methodSignature = methodSignature;
	}
	
	@Override
	protected void internalTransform(Body b, String phaseName, Map<String, String> options) 
	{
		if (b.getMethod().getSignature().equals(methodSignature))
		{
			parse(b);
		}
	}

	public void parse(Body body) 
	{
		PatchingChain<Unit> units = body.getUnits();
		
		String methodSignature = body.getMethod().getSignature();
		int count = 0;
		List<String> getKeys = new ArrayList<String>();
		List<String> putKeys = new ArrayList<String>();
		
		for (Iterator<Unit> iter = units.snapshotIterator(); iter.hasNext(); )
		{
			Stmt stmt = (Stmt) iter.next();
			
			if (! stmt.containsInvokeExpr())
			{
				continue;
			}
			
			SootMethod sm = stmt.getInvokeExpr().getMethod();
			String methodName = sm.getName();
			int type = 0; //0:other | 1:get | 2:put
			String extraKey = null;
			
			if (methodName.startsWith("get"))
			{
				type = 1;
			}
			else if (methodName.startsWith("put"))
			{
				type = 2;
			}
			
			if (0 == type)
			{
				continue;
			}
			
			if ( (sm.getDeclaringClass().toString().equals(intentCls) && methodName.contains("Extra")) ||
				 (sm.getDeclaringClass().toString().equals(bundleCls)))
			{
				if (stmt.getInvokeExpr().getArgs().size() > 0)
				{
					Value v = stmt.getInvokeExpr().getArgs().get(0);
					if (v.toString().contains("\""))
					{
						extraKey = v.toString();
					}
					else
					{
						extraKey = "<anything>" + (count++);
					}
				}
			}
			
			if (type == 1 && extraKey != null)
			{
				getKeys.add(extraKey);
			}
			else if (type == 2 && extraKey != null)
			{
				putKeys.add(extraKey);
			}
		}
		
		if (getKeys.size() != 0)
		{
			getExtras.put(methodSignature, getKeys);
		}
		if (putKeys.size() != 0)
		{
			putExtras.put(methodSignature, putKeys);
		}
		
		
	}
}
