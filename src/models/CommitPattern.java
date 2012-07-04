package models;

import java.util.Map;

public class CommitPattern {
	private String commitId;
	private Map<UnorderedPair<String, String>, STPattern> stPatterns;
	private boolean isFailure;
	
	public CommitPattern(String commitId, Map<UnorderedPair<String, String>, STPattern> stPatterns, boolean isFailure) {
		super();
		this.commitId = commitId;
		this.stPatterns = stPatterns;
		this.isFailure = isFailure;
	}

	public CommitPattern() { }

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public Map<UnorderedPair<String, String>, STPattern> getStPatterns() {
		return stPatterns;
	}

	public void setStPatterns(Map<UnorderedPair<String, String>, STPattern> stPatterns) {
		this.stPatterns = stPatterns;
	}

	public boolean isFailure() {
		return isFailure;
	}

	public void setFailure(boolean isFailure) {
		this.isFailure = isFailure;
	}
}
