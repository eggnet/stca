package generator;

import java.util.LinkedList;
import java.util.List;

import models.Commit;
import models.Network;
import models.Person;
import models.STPattern;
import models.STPattern.patternTypes;
import db.DbConnection;
import db.Resources;
import db.SocialAnalyzerDb;
import db.TechnicalAnalyzerDb;
import db.TechnicalDb;

/**
 * <code>Generator</code will go through each commit
 * and create relationships
 * @author braden
 *
 */
public class Generator
{
	public SocialAnalyzerDb stcaDb;
	public TechnicalAnalyzerDb techDb;
	
	public Generator(SocialAnalyzerDb stcaDb, TechnicalAnalyzerDb db) {
		this.stcaDb = stcaDb;
		this.techDb = db;
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
			commits = techDb.getCommits(Resources.DB_LIMIT, pagingOffset);
			for (Commit currentCommit : commits)
			{
				// Get all the related items and their threads.
				Network commitNetwork = stcaDb.getNetwork(currentCommit.getCommit_id());
				buildCommitPatterns(commitNetwork);
				
				// get pass/fail from technical db
				commitNetwork.setPass(techDb.getCommitStatus(currentCommit.getCommit_id()));
				
				// Insert into graph tables.
				stcaDb.insertNetwork(commitNetwork);
			}
		}
	}
	
	public void buildCommitPatterns(Network network)
	{
		STPattern newSTPattern = null;
		for (Integer threadId : network.getThreadPersonMap().keySet())
		{						
			// for each thread, construct links between the people involved.
			List<Person> personList = new LinkedList<Person>(network.getThreadPersonMap().get(threadId));
			
			for (int currentPersonPos = 0;currentPersonPos < personList.size() - 1;currentPersonPos++)
			{
				// create the connected set.
				Person currentPerson = personList.get(currentPersonPos);
				personList.remove(currentPersonPos);
				for (Person p : personList)
				{
					newSTPattern = new STPattern();
					newSTPattern.setPatternType(patternTypes.SOCIAL_ONLY);
					newSTPattern.setPerson1Id(currentPerson.getEmail());
					newSTPattern.setPerson2Id(p.getEmail());
				}
				network.getSocialNetworkCommitPattern().getStPatterns().put(newSTPattern.getPerson1Id() + newSTPattern.getPerson2Id(), newSTPattern);
			}
		}
		network.setTechnicalNetworkCommitPattern(techDb.getTechnicalNetworkForCommit(network.getCommitId()));
	}
}
