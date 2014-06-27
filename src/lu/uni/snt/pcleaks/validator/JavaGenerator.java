package lu.uni.snt.pcleaks.validator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class JavaGenerator 
{
	public static final String DEFAULT_ACTIVITY = "DefaultActivity";
	
	public static void generateForPACL(Component comp) 
	{
		String clsName = comp.name;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("package " + comp.pkg + ";" + "\n");
		sb.append("" + "\n");
		
		sb.append("import android.app.*;" + "\n");
		sb.append("import android.os.*;" + "\n");
		sb.append("import android.content.*;" + "\n");
		sb.append("import android.util.*;" + "\n");
		
		if (comp.compType.equals(Constants.TYPE_SERVICE))
		{
			sb.append("public class " + clsName + " extends Service {" + "\n");
			
			sb.append("@Override" + "\n");
			sb.append("public IBinder onBind(Intent intent) {" + "\n");
			sb = logExtras(sb, false);
			sb.append("    return null;" + "\n");
			sb.append("}" + "\n"); // close for method
			
			
			sb.append("@Override" + "\n");
			sb.append("public int onStartCommand(Intent intent, int flags, int startId) {" + "\n");
			sb = logExtras(sb, false);
			sb.append("    return 1;" + "\n");
			sb.append("}" + "\n"); // close for method
		}
		else if (comp.compType.equals(Constants.TYPE_PROVIDER))
		{
			// provider
		}
		else if (comp.compType.equals(Constants.TYPE_RECEIVER))
		{
			sb.append("public class " + clsName + " extends BroadcastReceiver {" + "\n");
			
			sb.append("@Override" + "\n");
			sb.append("public void onReceive(Context context, Intent intent) {" + "\n");
			sb = logExtras(sb, false);
			sb.append("}" + "\n"); // close for method
		}
		else
		{
			sb.append("public class " + clsName + " extends Activity {" + "\n");
			
			sb.append("@Override" + "\n" + "protected void onCreate(Bundle savedInstanceState) {" + "\n");
			sb.append("    " + "super.onCreate(savedInstanceState);" + "\n");
			sb = logExtras(sb, true);
			sb.append("}" + "\n");  // close for method
		}
		
		
		sb.append("}" + "\n");  // close for class
		
		System.out.println(sb.toString());
		
		generateFile("workspace/src/lu/uni/snt/" + clsName + ".java", sb.toString());
	}
	
	public static void generateDefaultComp()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("package lu.uni.snt;" + "\n");
		sb.append("" + "\n");
		
		sb.append("import android.app.*;" + "\n");
		sb.append("import android.os.*;" + "\n");
		sb.append("import android.content.*;" + "\n");
		sb.append("import android.util.*;" + "\n");
		sb.append("" + "\n");
		
		sb.append("public class " + DEFAULT_ACTIVITY + " extends Activity {" + "\n");
		

		sb.append("@Override" + "\n" + "protected void onCreate(Bundle savedInstanceState) {" + "\n");
		sb.append("    " + "super.onCreate(savedInstanceState);" + "\n");
		sb.append("}" + "\n");  // close for method
		
		sb.append("}" + "\n");  // close for class
		
		System.out.println(sb.toString());
		
		generateFile("workspace/src/lu/uni/snt/" + DEFAULT_ACTIVITY + ".java", sb.toString());
	}
	
	public static void generateForPPCL(Component comp) 
	{
		String clsName = DEFAULT_ACTIVITY;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("package " + comp.pkg + ";" + "\n");
		sb.append("" + "\n");
		
		
		sb.append("import android.app.*;" + "\n");
		sb.append("import android.os.*;" + "\n");
		sb.append("import android.content.*;" + "\n");
		sb.append("import android.util.*;" + "\n");
		sb.append("" + "\n");
		
		sb.append("public class " + clsName + " extends Activity {" + "\n");
		
		sb.append("@Override" + "\n" + "protected void onCreate(Bundle savedInstanceState) {" + "\n");
		sb.append("    " + "super.onCreate(savedInstanceState);" + "\n");
		sb.append("    " + "Intent i = new Intent();" + "\n");
		
		for (String action : comp.actions)
		{
			sb.append("    " + "i.setAction(\"" + action + "\");" + "\n");
			break;
		}
		
		for (String category : comp.categories)
		{
			sb.append("    " + "i.addCategory(\"" + category + "\");" + "\n");
		}
		
		for (String extraName : comp.extraNames)
		{
			sb.append("    " + "i.putExtra(\"" + extraName + "\", \"####----####\");" + "\n");
		}
		
		if (comp.data.containingMimeType())
		{
			String s = comp.data.getMimeType();
			sb.append("    " + "i.setType(\"" + s +"\");" + "\n");
		}
		
		if ("a".equals(comp.compType))
		{
			sb.append("    " + "startActivity(i);" + "\n");
		}
		else if ("s".endsWith(comp.compType))
		{
			sb.append("    " + "startService(i);" + "\n");
			
			//bindService...
		}
		else if ("r".endsWith(comp.compType))
		{
			sb.append("    " + "sendBroadcast(i);" + "\n");
		}
		else
		{
			sb.append("    " + "startActivity(i);" + "\n");
		}
		
		sb.append("}" + "\n");  // close for method
		sb.append("}" + "\n");  // close for class
		
		System.out.println(sb.toString());
		
		generateFile("workspace/src/lu/uni/snt/" + clsName + ".java", sb.toString());
	}
	
	public static void generateFile(String filename, String content)
	{
		try
		{
			File file = new File(filename);
			file.createNewFile();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
			bw.flush();
			bw.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private static StringBuilder logExtras(StringBuilder sb, boolean isActivity)
	{
		if (isActivity)
		{
			sb.append("    " + "Bundle b = getIntent().getExtras();" + "\n");
		}
		else
		{
			sb.append("    " + "Bundle b = intent.getExtras();" + "\n");
		}
		
		sb.append("    " + "for (String key : b.keySet()) {" + "\n");
		sb.append("        " + "Log.e(\"" + Constants.PCLEAKS_VALIDATOR + "\", b.get(key).toString());" + "\n");
		sb.append("    " + "}" + "\n");
		return sb;
	}
}
