package models;

public class STPattern {
	private String person1Id;
	private String person2Id;
	private patternTypes patternType;
	private weightLevels weightLevel;
	private float socialWeight;
	private float technicalWeight;
	
	public enum weightLevels{
		LOW,
		MEDIUM,
		HIGH,
	}
	public enum patternTypes{
		SOCIAL_ONLY,
		TECHNICAL_ONLY,
		SOCIAL_TECHNICAL,
	}
	
	public STPattern(String person1Id, String person2Id,
			patternTypes patternType, weightLevels weightLevel,
			float socialWeight, float technicalWeight) {
		super();
		this.person1Id = person1Id;
		this.person2Id = person2Id;
		this.patternType = patternType;
		this.weightLevel = weightLevel;
		this.socialWeight = socialWeight;
		this.technicalWeight = technicalWeight;
	}
	
	public String getPerson1Id() {
		return person1Id;
	}
	public void setPerson1Id(String person1Id) {
		this.person1Id = person1Id;
	}
	public String getPerson2Id() {
		return person2Id;
	}
	public void setPerson2Id(String person2Id) {
		this.person2Id = person2Id;
	}
	public patternTypes getPatternType() {
		return patternType;
	}
	public void setPatternType(patternTypes patternType) {
		this.patternType = patternType;
	}
	public weightLevels getWeightLevel() {
		return weightLevel;
	}
	public void setWeightLevel(weightLevels weightLevel) {
		this.weightLevel = weightLevel;
	}
	public float getSocialWeight() {
		return socialWeight;
	}
	public void setSocialWeight(float socialWeight) {
		this.socialWeight = socialWeight;
	}
	public float getTechnicalWeight() {
		return technicalWeight;
	}
	public void setTechnicalWeight(float technicalWeight) {
		this.technicalWeight = technicalWeight;
	}
		
}
