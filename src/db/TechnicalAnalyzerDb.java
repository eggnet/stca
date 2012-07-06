package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import models.CommitPattern;
import models.STPattern;
import models.STPattern.patternTypes;
import models.STPattern.weightLevels;
import models.UnorderedPair;
import db.util.ISetter;
import db.util.PreparedStatementExecutionItem;
import db.util.ISetter.StringSetter;

public class TechnicalAnalyzerDb extends TechnicalDb
{
	public TechnicalAnalyzerDb(){
		super();
	}
	
	public boolean connect(String dbName)
	{
		return super.connect(dbName);
	}
	
	public CommitPattern getTechnicalNetworkForCommit(String commitId)
	{
		try{
			Map<UnorderedPair<String, String>, STPattern> stPatterns = new HashMap<UnorderedPair<String, String>, STPattern>();
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
				UnorderedPair<String, String> patternKey = new UnorderedPair<String, String>(person1, person2);
				if(stPatterns.containsKey(patternKey))
				{
					if(isFuzzy.equalsIgnoreCase("f"))
						stPatterns.get(patternKey).addWeight(weight);
					else
						 stPatterns.get(patternKey).addFuzzyWeight(weight);
				}
				else 
				{
					// add new pattern
					STPattern pattern = new STPattern(commitId, person1, person2, patternTypes.TECHNICAL_ONLY, weightLevels.UNKNOWN, 0, 0, 0);
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
