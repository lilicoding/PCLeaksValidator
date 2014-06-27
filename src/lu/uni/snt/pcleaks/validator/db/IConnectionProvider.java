package lu.uni.snt.pcleaks.validator.db;

import java.sql.Connection;

public interface IConnectionProvider {
	public Connection getConnection(String dbName) throws DBException;
	//public Connection getConnection(String dbName, boolean isSlave) throws DBException;
	
}
