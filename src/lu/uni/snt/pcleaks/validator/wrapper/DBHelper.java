package lu.uni.snt.pcleaks.validator.wrapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lu.uni.snt.pcleaks.validator.Constants;
import lu.uni.snt.pcleaks.validator.db.DB;
import lu.uni.snt.pcleaks.validator.db.DBAdapter;

@SuppressWarnings("unchecked")
public class DBHelper 
{
	static
	{
		DB.setJdbcPath("scripts/jdbc.xml");
	}
	
	public static int getIntentId(String methodSignature, int instruction)
	{
		int rtVal = -1;
		
		try
		{
			String sql = "select a.id from Intents a, ExitPoints b where a.exit_id = b.id and b.method = ? and b.instruction = ?";
			
			DBAdapter adapter = new DBAdapter()
			{

				@Override
				protected Object processResultSet(ResultSet rs) 
				{
					int id = -1;
					try {
						if (rs.next())
						{
							id = rs.getInt(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return id;
				}
				
			};
			
			rtVal = (int) adapter.executeQuery(sql, new Object[] {methodSignature, instruction}, Constants.DB_NAME);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rtVal;
	}
	
	public static int getIntentFilterId(String methodSignature)
	{
		int rtVal = -1;
		
		String clsName = methodSignature.split(":")[0].substring(1);
		
		try
		{
			String sql = "select a.id from Components a, Classes b where a.class_id = b.id and b.class=?";
			
			DBAdapter adapter = new DBAdapter()
			{

				@Override
				protected Object processResultSet(ResultSet rs) 
				{
					int id = -1;
					try {
						if (rs.next())
						{
							id = rs.getInt(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return id;
				}
				
			};
			
			rtVal = (int) adapter.executeQuery(sql, new Object[] {clsName}, Constants.DB_NAME);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return rtVal;
	}
	
	public static String getExitType(String methodSignature, int instruction)
	{
		String rtVal = "";
		
		try
		{
			String sql = "select exit_kind from ExitPoints where method = ? and instruction = ?";
			
			DBAdapter adapter = new DBAdapter()
			{

				@Override
				protected Object processResultSet(ResultSet rs) 
				{
					String type = "a";
					try {
						if (rs.next())
						{
							type = rs.getString(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return type;
				}
				
			};
			
			rtVal = (String) adapter.executeQuery(sql, new Object[] {methodSignature, instruction}, Constants.DB_NAME);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rtVal;
	}
	
	public static List<String> getIntentActions(int intentId)
	{
		List<String> rtVal = null;
		
		try
		{
			String sql = "select a.st from ActionStrings a, IActions b, Intents c where a.id = b.action and b.intent_id = c.id and c.id = ?";
			
			DBAdapter adapter = new DBAdapter()
			{

				@Override
				protected Object processResultSet(ResultSet rs) 
				{
					List<String> strs = new ArrayList<String>();
					
					try {
						while (rs.next())
						{
							strs.add(rs.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return strs;
				}
				
			};
			
			rtVal = (List<String>) adapter.executeQuery(sql, new Object[] {intentId}, Constants.DB_NAME);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rtVal;
	}
	
	public static List<String> getIntentCategories(int intentId)
	{
		List<String> rtVal = null;
		
		try
		{
			String sql = "select a.st from CategoryStrings a, ICategories b, Intents c where a.id = b.category and b.intent_id = c.id and c.id = ?";
			
			DBAdapter adapter = new DBAdapter()
			{

				@Override
				protected Object processResultSet(ResultSet rs) 
				{
					List<String> strs = new ArrayList<String>();
					
					try {
						while (rs.next())
						{
							strs.add(rs.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return strs;
				}
				
			};
			
			rtVal = (List<String>) adapter.executeQuery(sql, new Object[] {intentId}, Constants.DB_NAME);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rtVal;
	}
	
	public static String getIntentMimeType(int intentId)
	{
		String rtVal = null;
		
		try
		{
			String sql = "select a.type, a.subtype from IMimeTypes a, Intents b where a.intent_id = b.id and b.id = ?";
			
			DBAdapter adapter = new DBAdapter()
			{

				@Override
				protected Object processResultSet(ResultSet rs) 
				{
					String mimeType = "";
					
					try {
						if (rs.next())
						{
							String type = rs.getString(1);
							if (null == type || type.isEmpty())
							{
								mimeType = "null";
							}
							else
							{
								mimeType = type;
							}

							mimeType = mimeType + "/";
							
							type = rs.getString(2);
							if (null == type || type.isEmpty())
							{
								mimeType = mimeType + "null";
							}
							else
							{
								mimeType = mimeType + type;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return mimeType;
				}
				
			};
			
			rtVal = (String) adapter.executeQuery(sql, new Object[] {intentId}, Constants.DB_NAME);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rtVal;
	}
	
	public static List<String> getIntentFilterActions(int id)
	{
		List<String> rtVal = null;
		
		try
		{
			String sql = "select a.st from ActionStrings a, IFActions b, IntentFilters c where a.id = b.action and b.filter_id = c.id and c.id = ?";
			
			DBAdapter adapter = new DBAdapter()
			{

				@Override
				protected Object processResultSet(ResultSet rs) 
				{
					List<String> strs = new ArrayList<String>();
					
					try {
						while (rs.next())
						{
							strs.add(rs.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return strs;
				}
				
			};
			
			rtVal = (List<String>) adapter.executeQuery(sql, new Object[] {id}, Constants.DB_NAME);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rtVal;
	}
	
	public static List<String> getIntentFilterCategories(int id)
	{
		List<String> rtVal = null;
		
		try
		{
			String sql = "select a.st from CategoryStrings a, IFCategories b, IntentFilters c where a.id = b.category and b.filter_id = c.id and c.id = ?";
			
			DBAdapter adapter = new DBAdapter()
			{

				@Override
				protected Object processResultSet(ResultSet rs) 
				{
					List<String> strs = new ArrayList<String>();
					
					try {
						while (rs.next())
						{
							strs.add(rs.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return strs;
				}
				
			};
			
			rtVal = (List<String>) adapter.executeQuery(sql, new Object[] {id}, Constants.DB_NAME);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rtVal;
	}
	
	public static String getIntentFilterMimeType(int id)
	{
		String rtVal = null;
		
		try
		{
			String sql = "select a.type, a.subtype from IFData a, IntentFilters b where a.filter_id = b.id and b.id = ?";
			
			DBAdapter adapter = new DBAdapter()
			{

				@Override
				protected Object processResultSet(ResultSet rs) 
				{
					String mimeType = "";
					
					try {
						if (rs.next())
						{
							String type = rs.getString(1);
							if (null == type || type.isEmpty())
							{
								mimeType = "null";
							}
							else
							{
								mimeType = type;
							}

							mimeType = mimeType + "/";
							
							type = rs.getString(2);
							if (null == type || type.isEmpty())
							{
								mimeType = mimeType + "null";
							}
							else
							{
								mimeType = mimeType + type;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return mimeType;
				}
				
			};
			
			rtVal = (String) adapter.executeQuery(sql, new Object[] {id}, Constants.DB_NAME);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rtVal;
	}
	
	public static String getComponentType(int id)
	{
		String rtVal = null;
		
		try
		{
			String sql = "select kind from Components where id = ?";
			
			DBAdapter adapter = new DBAdapter()
			{

				@Override
				protected Object processResultSet(ResultSet rs) 
				{
					String type = "";
					
					try {
						if (rs.next())
						{
							type = rs.getString(1);

						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return type;
				}
				
			};
			
			rtVal = (String) adapter.executeQuery(sql, new Object[] {id}, Constants.DB_NAME);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rtVal;
	}
}
