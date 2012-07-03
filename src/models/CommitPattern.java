package models;

import java.util.Map;
import java.util.Set;

public class CommitPattern {
	private String newCommitId;
	private String oldCommitId;
	private Map<String, Set<STPattern>> stPatterns;
	private boolean isFailure;
	
	public CommitPattern(String newCommitId, String oldCommitId,
			Map<String, Set<STPattern>> stPatterns, boolean isFailure) {
		super();
		this.newCommitId = newCommitId;
		this.oldCommitId = oldCommitId;
		this.stPatterns = stPatterns;
		this.isFailure = isFailure;
	}

	public String getNewCommitId() {
		return newCommitId;
	}

	public void setNewCommitId(String newCommitId) {
		this.newCommitId = newCommitId;
	}

	public String getOldCommitId() {
		return oldCommitId;
	}

	public void setOldCommitId(String oldCommitId) {
		this.oldCommitId = oldCommitId;
	}

	public Map<String, Set<STPattern>> getStPatterns() {
		return stPatterns;
	}

	public void setStPatterns(Map<String, Set<STPattern>> stPatterns) {
		this.stPatterns = stPatterns;
	}

	public boolean isFailure() {
		return isFailure;
	}

	public void setFailure(boolean isFailure) {
		this.isFailure = isFailure;
	}
}
