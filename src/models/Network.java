package models;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.STPattern.patternTypes;

/**
 * Holds all the information of the social network based on information from the <a href="https://github.com/eggnet/com2pgsql">com2pgsql</a> project.
 * @author braden
 *
 */
public class Network
{
	private Map<Integer, Set<Item>> threadItemMap;
	private Map<Integer, Set<Person>> threadPersonMap;
	private boolean isPass;
	private CommitPattern networkCommitPattern;
	
	public Network() {
		this.networkCommitPattern = new CommitPattern();
	}

	/**
	 * Only can be called after setting {@link #threadItemMap} and {@link #threadPersonMap}
	 */
	public void buildCommitPatterns() {
		STPattern newSTPattern = null;
		for (Integer threadId : threadPersonMap.keySet())
		{						
			// for each thread, construct links between the people involved.
			List<Person> personList = new LinkedList<Person>(threadPersonMap.get(threadId));
			
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
				this.networkCommitPattern.getStPatterns().put(newSTPattern.getPerson1Id() + newSTPattern.getPerson2Id(), newSTPattern);
			}
		}
	}
	
	public Map<Integer, Set<Item>> getThreadItemMap()
	{
		return threadItemMap;
	}

	public void setThreadItemMap(Map<Integer, Set<Item>> threadItemMap)
	{
		this.threadItemMap = threadItemMap;
	}

	public Map<Integer, Set<Person>> getThreadPersonMap()
	{
		return threadPersonMap;
	}

	public void setThreadPersonMap(Map<Integer, Set<Person>> threadPersonMap)
	{
		this.threadPersonMap = threadPersonMap;
	}

	public boolean isPass()
	{
		return isPass;
	}

	public void setPass(boolean isPass)
	{
		this.isPass = isPass;
	}

	public CommitPattern getNetworkCommitPattern()
	{
		return networkCommitPattern;
	}

	public void setNetworkCommitPattern(CommitPattern networkCommitPattern)
	{
		this.networkCommitPattern = networkCommitPattern;
	}
}
