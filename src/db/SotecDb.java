package db;

import java.io.InputStreamReader;
import java.sql.PreparedStatement;

import db.util.PreparedStatementExecutionItem;

public class SotecDb extends DbConnection
{
	public SotecDb(){
		super();
	}
	
	public boolean connect(String dbName)
	{
		return super.connect(dbName);
	}
	/**
	 * Creates a db on the current connection.
	 * @param dbName
	 * @return true for success
	 */
	public boolean createDb(String dbName) {
		PreparedStatement s;
		try {
			
			// Drop the DB if it already exists
			String query = "DROP DATABASE IF EXISTS " + dbName + ";";
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, null);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
			
			// First create the DB.
			query = "CREATE DATABASE " + dbName + ";";
			ei = new PreparedStatementExecutionItem(query, null);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
			
			// Reconnect to our new database.
			connect(dbName.toLowerCase());
			
			// Now load our default schema in.
			runScript(new InputStreamReader(this.getClass().getResourceAsStream("createdb.sql")));
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	

}
