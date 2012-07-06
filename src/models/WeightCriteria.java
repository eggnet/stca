package models;

import models.STPattern.patternTypes;

public class WeightCriteria{
	
	static float TECHNICAL_LOWER_BOUND 			= 0;
	static float TECHNICAL_UPPER_BOUND 			= 1000;
	static float SOCIAL_LOWER_BOUND 			= 0;
	static float SOCIAL_UPPER_BOUND 			= 1000;
	static float TECHNICAL_FUZZY_LOWER_BOUND 	= 0;
	static float TECHNICAL_FUZZY_UPPER_BOUND 	= 1000;
	
	public float technicalLower;
	public float technicalUpper;
	public float socialLower;
	public float socialUpper;
	public float technicalFuzzyLower;
	public float technicalFuzzyUpper;
	
	public WeightCriteria()
	{
		this.technicalLower = TECHNICAL_LOWER_BOUND;
		this.technicalUpper = TECHNICAL_UPPER_BOUND;
		this.socialLower = SOCIAL_LOWER_BOUND;
		this.socialUpper = SOCIAL_UPPER_BOUND;
		this.technicalFuzzyLower = TECHNICAL_FUZZY_LOWER_BOUND;
		this.technicalFuzzyUpper = TECHNICAL_FUZZY_UPPER_BOUND;
	}
	
	public WeightCriteria(float technicalLower, float technicalUpper,
			float socialLower, float socialUpper,
			float technicalFuzzyLower, float technicalFuzzyUpper)
	{
		this.technicalLower 	= (technicalLower!=-1) ? technicalLower : TECHNICAL_LOWER_BOUND;
		this.technicalUpper 	= (technicalUpper!=-1) ? technicalUpper : TECHNICAL_UPPER_BOUND;
		this.socialLower 		= (socialLower!=-1) ? socialLower : SOCIAL_LOWER_BOUND;
		this.socialUpper 		= (socialUpper!=-1) ? socialUpper : SOCIAL_UPPER_BOUND;
		this.technicalFuzzyLower = (technicalFuzzyLower!=-1) ? technicalFuzzyLower : TECHNICAL_FUZZY_LOWER_BOUND;
		this.technicalFuzzyUpper = (technicalFuzzyUpper!=-1) ? technicalFuzzyUpper : TECHNICAL_FUZZY_UPPER_BOUND;
	}
	
	public boolean satisfiedCriteria(STPattern pattern)
	{
		if(pattern.getPatternType() == patternTypes.SOCIAL_ONLY &&
		   pattern.getSocialWeight() >= socialLower &&
		   pattern.getSocialWeight() <= socialUpper)
		{
			return true;
		}
		else
		if(pattern.getPatternType() == patternTypes.TECHNICAL_ONLY)

		{
			float techW = pattern.getTechnicalWeight();
			float fuzzyW = pattern.getTechnicalFuzzyWeight();
			if( techW > 0 && techW >= technicalLower && techW <= technicalUpper)
				return true;
			if( fuzzyW > 0 && fuzzyW >= technicalFuzzyLower && fuzzyW <= technicalFuzzyUpper)
				return true;

			return false;
		}
		else
		if(pattern.getPatternType() == patternTypes.SOCIAL_TECHNICAL &&
		   pattern.getSocialWeight() >= socialLower &&
		   pattern.getSocialWeight() <= socialUpper && 	
		   pattern.getTechnicalWeight() >= technicalLower &&
		   pattern.getTechnicalWeight() <= technicalUpper)
		{
			return true;
		}
			
		return false;
	}
}
