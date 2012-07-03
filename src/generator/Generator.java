package generator;

import java.util.List;

import models.Commit;
import models.Network;
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
	
	public Generator(StcaDb stcaDb, DbConnection db) {
		this.stcaDb = stcaDb;
		this.db = db;
	}
	
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
				// Get all the related items and their threads.
				Network commitNetwork = stcaDb.getNetwork(currentCommit.getCommit_id());
				commitNetwork.buildCommitPatterns();
				
				// get pass/fail from technical db
				commitNetwork.setPass(db.getCommitStatus(currentCommit.getCommit_id()));
				
				// Insert into graph tables.
				// TODO @braden
			}
		}
	}
}
