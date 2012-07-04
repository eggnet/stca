package db;

import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.Item;
import models.Network;
import models.Person;
import models.STPattern;
import db.util.ISetter;
import db.util.ISetter.BooleanSetter;
import db.util.ISetter.IntSetter;
import db.util.ISetter.StringSetter;
import db.util.PreparedStatementExecutionItem;

public class SocialAnalyzerDb extends SocialDb
{
	public SocialAnalyzerDb(){
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
	
	public Set<Item> getAllLinkedItemsForCommit(String CommitId)
	{
		String sql = "SELECT * from items natural join links where commit_id=?;";
		ISetter[] parms = { new StringSetter(1, CommitId) };
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, parms);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
		
		Set<Item> itemSet = new HashSet<Item>();
		
		try 
		{
			ResultSet rs = ei.getResult();
			while(rs.next())
			{
				itemSet.add(new Item(
					rs.getInt("p_id"),
					rs.getTimestamp("item_date"),
					rs.getInt("item_id"),
					rs.getString("body"),
					rs.getString("title"),
					Resources.CommType.valueOf(rs.getString("type"))
				));
			}
			return itemSet;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets all the threads as a <code>java.util.Set</code> of {@link models.Item}.
	 * @param CommitId
	 * @return
	 */
	public Set<Item> getAllLinkedThreadsForCommit(String CommitId)
	{
		String sql = 
			"select p_id, item_date, item_id, body, title, type from " +
					"(select thread_id from links join threads on links.item_id=threads.thread_id where commit_id=?) as foo" +
			"join items on bar.thread_id=items.item_id";
		ISetter[] parms = { new StringSetter(1, CommitId) };
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, parms);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
		Set<Item> itemSet = new HashSet<Item>();
		try 
		{
			ResultSet rs = ei.getResult();
			while(rs.next())
			{
				itemSet.add(new Item(
					rs.getInt("p_id"),
					rs.getTimestamp("item_date"),
					rs.getInt("item_id"),
					rs.getString("body"),
					rs.getString("title"),
					Resources.CommType.valueOf(rs.getString("type"))
				));
			}
			return itemSet;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets all the information on a commit, based on the links made by the <code>com2pgsql</code> project <br>
	 * found at <a href="https://github.com/eggnet/com2pgsql">github</a>.  It constructs a {@link models.Network} and and sets <br>
	 * the {@link models.Network.threadItemMap} and {@link models.Network.threadPersonMap} objects.
	 * @param commitId
	 * @return
	 */
	public Network getNetwork(String commitId)
	{
		String sql = 
			"select name, email, items.p_id, item_date, items.item_id, body, title, type, thread_id from items join " +
				"(select item_id as t_item_id, thread_id from threads where thread_id IN " +
					"(select thread_id from " +
						"(select distinct thread_id from links join threads on links.item_id=threads.thread_id where commit_id=?) as thread_links" +
					"join items on thread_links.thread_id=items.item_id)" +
				") as thread_items on items.item_id=thread_items.item_id join people on items.p_id=people.p_id order by thread_id";
		ISetter[] parms = { new StringSetter(1, commitId) };
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, parms);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
		
		Network network = new Network();
		Map<Integer, Set<Item>> threadItemMap = new HashMap<Integer, Set<Item>>();
		Map<Integer, Set<Person>> threadPersonMap = new HashMap<Integer, Set<Person>>();
		try 
		{
			ResultSet rs = ei.getResult();
			Set<Item> itemSet = null;
			Set<Person> personSet = null;
			int currentThreadId = -1;
			while(rs.next())
			{
				int nextThreadId = rs.getInt("thread_id");
				if (currentThreadId != nextThreadId)
				{
					if (itemSet != null && itemSet.size() > 0)
						threadItemMap.put(currentThreadId, itemSet);
					if (personSet != null && personSet.size() > 0)
						threadPersonMap.put(currentThreadId, personSet);
					personSet = new HashSet<Person>();
					itemSet = new HashSet<Item>();
				}
				itemSet.add(new Item(
					rs.getInt("p_id"),
					rs.getTimestamp("item_date"),
					rs.getInt("item_id"),
					rs.getString("body"),
					rs.getString("title"),
					Resources.CommType.valueOf(rs.getString("type"))
				));
				personSet.add(new Person(
					rs.getInt("p_id"),
					rs.getString("name"),
					rs.getString("email")
				));
			}
			network.setThreadItemMap(threadItemMap);
			network.setThreadPersonMap(threadPersonMap);
			network.setCommitId(commitId);
			return network;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, Set<Item>> getAllThreadItemsForCommit(String commitId)
	{
		String sql = 
			"select name, email, items.p_id, item_date, items.item_id, body, title, type, thread_id from items join " +
				"(select item_id as t_item_id, thread_id from threads where thread_id IN " +
					"(select thread_id from " +
						"(select distinct thread_id from links join threads on links.item_id=threads.thread_id where commit_id=?) as thread_links" +
					"join items on thread_links.thread_id=items.item_id)" +
				") as thread_items on items.item_id=thread_items.item_id join people on items.p_id=people.p_id order by thread_id";
		ISetter[] parms = { new StringSetter(1, commitId) };
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, parms);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
		Map<Integer, Set<Item>> threadItemMap = new HashMap<Integer, Set<Item>>();
		try 
		{
			ResultSet rs = ei.getResult();
			Set<Item> itemSet = null;
			int currentThreadId = -1;
			while(rs.next())
			{
				int nextThreadId = rs.getInt("thread_id");
				if (currentThreadId != nextThreadId)
				{
					if (itemSet != null && itemSet.size() > 0)
						threadItemMap.put(currentThreadId, itemSet);
					
					itemSet = new HashSet<Item>();
				}
				itemSet.add(new Item(
					rs.getInt("p_id"),
					rs.getTimestamp("item_date"),
					rs.getInt("item_id"),
					rs.getString("body"),
					rs.getString("title"),
					Resources.CommType.valueOf(rs.getString("type"))
				));
			}
			return threadItemMap;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean insertSTPattern(STPattern pattern, boolean isPassingCommit)
	{
		try
		{
			String sql = "SELECT * from patterns where p_id1=? and p_id2=?";
			ISetter[] parms = {
					new StringSetter(1, pattern.getPerson1Id()), 
					new StringSetter(2, pattern.getPerson2Id()), 
			};
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, parms);
			this.addExecutionItem(ei);
			ei.waitUntilExecuted();
			ResultSet rs = ei.getResult();
			
			if (rs.next())
			{
				int countPassed = rs.getInt("passed");
				int countFailed = rs.getInt("failed");
				if (isPassingCommit)
					countPassed++;
				else
					countFailed++;
				sql = "UPDATE patterns SET passed=? and failed=? where p_id1=? and p_id2=?";
				ISetter[] innerParms = {
						new StringSetter(1, pattern.getPerson1Id()), 
						new StringSetter(2, pattern.getPerson2Id()), 
						new IntSetter(3, countPassed), 
						new IntSetter(4, countFailed)
				};
				ei = new PreparedStatementExecutionItem(sql, innerParms);
				this.addExecutionItem(ei);
			}
			else
			{
				sql = "INSERT INTO patterns VALUES (default, ?, ?, ?, ?, ?)";
				ISetter[] innerParms = {
						new StringSetter(1, pattern.getPerson1Id()), 
						new StringSetter(2, pattern.getPerson2Id()), 
						new StringSetter(3, pattern.getCommitId()), 
						new StringSetter(4, pattern.getPatternType().toString()),
						new BooleanSetter(5, isPassingCommit)
				};
				ei = new PreparedStatementExecutionItem(sql, innerParms);
				this.addExecutionItem(ei);
			}
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean insertNetwork(Network network)
	{	
		// TODO @braden
		// Go through all patterns in the social
		// 		check if the current pattern is in technical pattern
		//		if so, 
		//			insert with both network type
		//		else 
		//			insert into social
		// Then iterate over technical - social
		// 		insert into technical only.
		for (STPattern patt : network.getSocialNetworkCommitPattern().getStPatterns().values())
		{
			insertSTPattern(patt, network.isPass());
		}
		return true;
	}
}
