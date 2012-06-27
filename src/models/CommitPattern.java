package models;

import java.util.List;
import java.util.Map;

public class CommitPattern {
	private String newCommitId;
	private String oldCommitId;
	private Map<String, List<STPattern>> stPatterns;
	private boolean isFailure;
	
	public CommitPattern(String newCommitId, String oldCommitId,
			Map<String, List<STPattern>> stPatterns, boolean isFailure) {
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

	public Map<String, List<STPattern>> getStPatterns() {
		return stPatterns;
	}

	public void setStPatterns(Map<String, List<STPattern>> stPatterns) {
		this.stPatterns = stPatterns;
	}

	public boolean isFailure() {
		return isFailure;
	}

	public void setFailure(boolean isFailure) {
		this.isFailure = isFailure;
	}
}
