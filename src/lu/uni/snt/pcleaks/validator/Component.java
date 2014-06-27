package lu.uni.snt.pcleaks.validator;

import java.util.HashSet;
import java.util.Set;

public class Component 
{
	public String name = "MaliciousComponent";
	public String pkg = "lu.uni.snt";
	
	//"a", "s", "r", "p"
	public String compType = "a";
	public String leakType = "pacl";    //or ppcl
	
	public Set<String> actions = new HashSet<String>();
	public Set<String> categories = new HashSet<String>();
	public Set<String> extraNames = new HashSet<String>();
	
	public DataAndType data = new DataAndType();
	
	public Component()
	{
		categories.add("android.intent.category.DEFAULT");
	}
}
