package com.llp.entities;

import java.io.Serializable;

public class LoanApplication implements Serializable{
	
	String applicationId, applicationDate, accountNumber, fullName, address, phone, branch;
	double loanFacility, facilityRecommended;
	String amountInWords, purpose, loanType, tenor, sourceOfRepayment, applicationStatus, marketingOfficer;
	String dateOfBirth, BVN, facilityApplied, bmGender, searchStatus, facilityRecommendedInWords, comment, customerId;
	int clearance;
	
	public LoanApplication() {
		
	}
	
	
	
	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public double getFacilityRecommended() {
		return facilityRecommended;
	}



	public void setFacilityRecommended(double facilityRecommended) {
		this.facilityRecommended = facilityRecommended;
	}



	public String getFacilityRecommendedInWords() {
		return facilityRecommendedInWords;
	}



	public void setFacilityRecommendedInWords(String facilityRecommendedInWords) {
		this.facilityRecommendedInWords = facilityRecommendedInWords;
	}



	public String getSearchStatus() {
		return searchStatus;
	}


	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}
	
	public int getClearance() {
		return clearance;
	}


	public void setClearance(int clearance) {
		this.clearance = clearance;
	}


	public String getFacilityApplied() {
		return facilityApplied;
	}

	public String getBmGender() {
		return bmGender;
	}

	public void setBmGender(String bmGender) {
		this.bmGender = bmGender;
	}

	public void setFacilityApplied(String facilityApplied) {
		this.facilityApplied = facilityApplied;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public double getLoanFacility() {
		return loanFacility;
	}

	public void setLoanFacility(double loanFacility) {
		this.loanFacility = loanFacility;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getSourceOfRepayment() {
		return sourceOfRepayment;
	}

	public void setSourceOfRepayment(String sourceOfRepayment) {
		this.sourceOfRepayment = sourceOfRepayment;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getMarketingOfficer() {
		return marketingOfficer;
	}

	public void setMarketingOfficer(String marketingOfficer) {
		this.marketingOfficer = marketingOfficer;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getBVN() {
		return BVN;
	}

	public void setBVN(String bVN) {
		BVN = bVN;
	}



	public String getCustomerId() {
		return customerId;
	}



	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
	
	

}
