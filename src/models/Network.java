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
	private Map<Integer, Map<Person, Integer>> threadPersonMap;
	
	private boolean isPass;
	private CommitPattern networkCommitPattern;
	private String commitId;
	
	public Network() {
		this.networkCommitPattern = new CommitPattern();
	}

	/**
	 * Only can be called after setting {@link #threadItemMap} and {@link #threadPersonMap}
	 */
	public Map<Integer, Set<Item>> getThreadItemMap()
	{
		return threadItemMap;
	}

	public void setThreadItemMap(Map<Integer, Set<Item>> threadItemMap)
	{
		this.threadItemMap = threadItemMap;
	}

	public Map<Integer, Map<Person, Integer>> getThreadPersonMap()
	{
		return threadPersonMap;
	}

	public void setThreadPersonMap(Map<Integer, Map<Person, Integer>> threadPersonMap)
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

	public String getCommitId()
	{
		return commitId;
	}

	public void setCommitId(String commitId)
	{
		this.commitId = commitId;
	}
}
