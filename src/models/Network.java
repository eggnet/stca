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
	private CommitPattern socialNetworkCommitPattern;
	private CommitPattern technicalNetworkCommitPattern;
	private String commitId;
	
	public Network() {
		this.socialNetworkCommitPattern = new CommitPattern();
		this.technicalNetworkCommitPattern = new CommitPattern();
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

	public CommitPattern getSocialNetworkCommitPattern()
	{
		return socialNetworkCommitPattern;
	}

	public void setSocialNetworkCommitPattern(CommitPattern networkCommitPattern)
	{
		this.socialNetworkCommitPattern = networkCommitPattern;
	}

	public CommitPattern getTechnicalNetworkCommitPattern()
	{
		return technicalNetworkCommitPattern;
	}

	public void setTechnicalNetworkCommitPattern(CommitPattern technicalNetworkCommitPattern)
	{
		this.technicalNetworkCommitPattern = technicalNetworkCommitPattern;
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
