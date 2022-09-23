package com.llp.entities;

import java.io.Serializable;

public class AORecommendationNote implements Serializable{
	String recommendationNote, applicationId, repaymentType;
	public AORecommendationNote() {
		// TODO Auto-generated constructor stub
	}
	public String getRecommendationNote() {
		return recommendationNote;
	}
	public void setRecommendationNote(String recommendationNote) {
		this.recommendationNote = recommendationNote;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
	
	

}
