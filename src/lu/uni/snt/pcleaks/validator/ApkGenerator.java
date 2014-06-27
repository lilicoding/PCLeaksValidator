package lu.uni.snt.pcleaks.validator;

public class ApkGenerator 
{
	public static void generateApk(Component comp)
	{
		if (comp.leakType.equals("pacl"))
		{
			JavaGenerator.generateForPACL(comp);
			JavaGenerator.generateDefaultComp();
			XmlGenerator.generate(comp);
		}
		else
		{
			JavaGenerator.generateForPPCL(comp);
		}
	}
}
