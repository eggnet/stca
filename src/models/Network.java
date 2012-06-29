package models;

import java.util.Map;
import java.util.Set;

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
	private Set<CommitPattern> networkLink;
	
	public Network() { }

	/**
	 * Only can be called after setting {@link #threadItemMap} and {@link #threadPersonMap}
	 */
	public void buildCommitPatterns() {
		for (Integer threadId : threadPersonMap.keySet())
		{
			// for each thread, construct links between the people involved.
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

	public Set<CommitPattern> getNetworkLink()
	{
		return networkLink;
	}

	public void setNetworkLink(Set<CommitPattern> networkLink)
	{
		this.networkLink = networkLink;
	}
}
