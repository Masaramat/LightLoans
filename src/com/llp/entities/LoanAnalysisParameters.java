package com.llp.entities;

import java.io.Serializable;

public class LoanAnalysisParameters implements Serializable{
	
	
	 String applicationId, parameter, value, analysisType; 
	public LoanAnalysisParameters() {
		// TODO Auto-generated constructor stub
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAnalysisType() {
		return analysisType;
	}
	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}
	
	
	

}
