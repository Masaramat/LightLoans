package com.llp.entities;

import java.io.Serializable;

public class LoanOffer implements Serializable{
	
	String applicationId, referenceNo, amountInWords, customerId, tenorType, interestType, loanProduct, approvalDate, disbursementDate, maturityDate, staff, createdOn;
	double principal, interestRate, monitoringFeeRate, managementFeeRate, compulsorySavingsRate, riskPremiumRate;
	int tenor, clearanceLevel;

	public LoanOffer() {
		
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public String getTenorType() {
		return tenorType;
	}

	public void setTenorType(String tenorType) {
		this.tenorType = tenorType;
	}

	public String getInterestType() {
		return interestType;
	}

	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}

	public String getLoanProduct() {
		return loanProduct;
	}

	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(String disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getMonitoringFeeRate() {
		return monitoringFeeRate;
	}

	public void setMonitoringFeeRate(double monitoringFeeRate) {
		this.monitoringFeeRate = monitoringFeeRate;
	}

	public double getManagementFeeRate() {
		return managementFeeRate;
	}

	public void setManagementFeeRate(double managementFeeRate) {
		this.managementFeeRate = managementFeeRate;
	}

	public double getCompulsorySavingsRate() {
		return compulsorySavingsRate;
	}

	public void setCompulsorySavingsRate(double compulsorySavingsRate) {
		this.compulsorySavingsRate = compulsorySavingsRate;
	}

	public double getRiskPremiumRate() {
		return riskPremiumRate;
	}

	public void setRiskPremiumRate(double riskPremiumRate) {
		this.riskPremiumRate = riskPremiumRate;
	}

	public int getTenor() {
		return tenor;
	}

	public void setTenor(int tenor) {
		this.tenor = tenor;
	}

	public int getClearanceLevel() {
		return clearanceLevel;
	}

	public void setClearanceLevel(int clearanceLevel) {
		this.clearanceLevel = clearanceLevel;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
	
	
	
	

}
