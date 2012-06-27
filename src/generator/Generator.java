package generator;

import java.util.List;

import models.Commit;

import db.DbConnection;
import db.Resources;
import db.StcaDb;

/**
 * <code>Generator</code will go through each commit
 * and create relationships
 * @author braden
 *
 */
public class Generator
{
	public StcaDb stcaDb;
	
	public DbConnection db;
	
	public Generator() { }
	
	/**
	 * Iterates over all the {@link models.Commit} in a repo, and builds up a list of 
	 * <code>Patterns</code> 
	 */
	public void generate() { 
		int pagingOffset = 0;
		List<Commit> commits;
		while (pagingOffset != -1)
		{
			commits = db.getCommits(Resources.DB_LIMIT, pagingOffset);
			for (Commit currentCommit : commits)
			{
				
			}
		}
	}
}
