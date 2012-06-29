package db;

import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import db.util.ISetter;
import db.util.PreparedStatementExecutionItem;
import db.util.ISetter.StringSetter;
import models.CommitPattern;
import models.DiffEntry;
import models.Item;
import models.STPattern;
import models.STPattern.patternTypes;
import models.STPattern.weightLevels;

public class StcaDb extends SocialDb
{
	public StcaDb(){
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
	
	public Set<Item> getItems(int iLIMIT, int iOFFSET)
	{
		Set<Item> items = new HashSet<Item>();
		return items;
	}
	
	public CommitPattern getTechnicalNetworkForCommit(String commitId)
	{
		try{
			Map<String, STPattern> stPatterns = new HashMap<String, STPattern>();
			String sql = "SELECT source, target, weight, is_fuzzy FROM " +
					"networks natural join edges WHERE " +
					"new_commit_id=?";
			
			ISetter[] params = {new StringSetter(1, commitId)};
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, params);
			this.addExecutionItem(ei);
			ei.waitUntilExecuted();
			
			ResultSet rs = ei.getResult();
			while(rs.next())
			{
				String person1 	    = rs.getString("source");
				String person2      = rs.getString("target");
				String isFuzzy		= rs.getString("is_fuzzy");
				float weight 		= rs.getFloat("weight");
				
				// add all the network found from the edges.
				// Key are email1+email2
				String patternKey 		 = person1 + person2;
				String patternKeyReverse = person2 + person1;
				if(stPatterns.containsKey(patternKey))
				{
					if(isFuzzy.equalsIgnoreCase("f"))
						stPatterns.get(patternKey).addWeight(weight);
					else
						 stPatterns.get(patternKey).addFuzzyWeight(weight);
				}
				else
				if(stPatterns.containsKey(patternKeyReverse))
				{
					if(isFuzzy.equalsIgnoreCase("f"))
						stPatterns.get(patternKeyReverse).addWeight(weight);
					else
						 stPatterns.get(patternKeyReverse).addFuzzyWeight(weight);					
				}
				else 
				{
					// add new pattern
					STPattern pattern = new STPattern(person1, person2, patternTypes.TECHNICAL_ONLY, weightLevels.UNKNOWN, 0, 0, 0);
					if(isFuzzy.equalsIgnoreCase("f"))
						pattern.addWeight(weight);
					else
						pattern.addFuzzyWeight(weight);		
					
					stPatterns.put(patternKey, pattern);
				}
			}

			CommitPattern comPatt = new CommitPattern(commitId, stPatterns, false);
			return comPatt;	
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
