package models;

import java.util.Map;

public class CommitPattern {
	private String commitId;
	private Map<String,STPattern> stPatterns;
	private boolean isFailure;
	
	public CommitPattern(String commitId, Map<String, STPattern> stPatterns, boolean isFailure) {
		super();
		this.commitId = commitId;
		this.stPatterns = stPatterns;
		this.isFailure = isFailure;
	}

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public Map<String, STPattern> getStPatterns() {
		return stPatterns;
	}

	public void setStPatterns(Map<String, STPattern> stPatterns) {
		this.stPatterns = stPatterns;
	}

	public boolean isFailure() {
		return isFailure;
	}

	public void setFailure(boolean isFailure) {
		this.isFailure = isFailure;
	}
}
