package com.llp.entities;

import java.io.Serializable;

public class LoanOfferView implements Serializable{
	int clearanceLevel, tenorApproved;
	double facilityRequested, amountApproved, facilityRecommended, interestRate, managementFeeRate, monitoringFeeRate, riskPremiumRate, compulsorySavingsRate, upfrontInterest;
	String customerId, title, surname, othernames, dob, bvn, phoneNo, address, accountNo, applicationId, applicationDate, branch, business;
	String purpose, sor, marketingOfficer, applicationStatus, tenorTypeApproved, amountInWords, loanProduct, interestType, creditOfficer;
	String approvalDate, disbursementDate, maturity, referenceNo, gender, facilityRecommendedInWords, comment; 
	
	String fullName, principalIntroduction, facilityApplied, facilityApproved, tenor, pricingInterest, pricingMonFee, pricingRiskPremium, pricingMgtFee, defaultPenalty;
	String maturityDate, repaymentSource, termlyRepaymentHeader, fullRepaymentHeader, auditedBy, auditDate, disburseBy;
	
	String installmentPrincipal, installmentInterestCharges, installmentCompulsorySavings, totalInstallmentRepayment;
	String principal, totalInterestCharges, totalCompulsorySavings, totalRepayment, searchStatus, createdOn, grossTotal;
	String totalInterest, totalMgtFee, totalMonitoringFee, totalRiskPremium, grossTotalPercent; 
	
	public LoanOfferView() { //totalInterestCharges
		
		
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


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getGrossTotal() {
		return grossTotal;
	}
	


	public String getTotalInterest() {
		return totalInterest;
	}


	public void setTotalInterest(String totalInterest) {
		this.totalInterest = totalInterest;
	}


	public String getTotalMgtFee() {
		return totalMgtFee;
	}


	public void setTotalMgtFee(String totalMgtFee) {
		this.totalMgtFee = totalMgtFee;
	}


	public String getTotalMonitoringFee() {
		return totalMonitoringFee;
	}


	public void setTotalMonitoringFee(String totalMonitoringFee) {
		this.totalMonitoringFee = totalMonitoringFee;
	}


	public String getTotalRiskPremium() {
		return totalRiskPremium;
	}


	public void setTotalRiskPremium(String totalRiskPremium) {
		this.totalRiskPremium = totalRiskPremium;
	}


	public String getGrossTotalPercent() {
		return grossTotalPercent;
	}


	public void setGrossTotalPercent(String grossTotalPercent) {
		this.grossTotalPercent = grossTotalPercent;
	}


	public void setGrossTotal(String grossTotal) {
		this.grossTotal = grossTotal;
	}


	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public double getUpfrontInterest() {
		return upfrontInterest;
	}

	public void setUpfrontInterest(double upfrontInterest) {
		this.upfrontInterest = upfrontInterest;
	}

	public String getSearchStatus() {
		return searchStatus;
	}


	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}


	public String getDisburseBy() {
		return disburseBy;
	}

	public void setDisburseBy(String disburseBy) {
		this.disburseBy = disburseBy;
	}

	public String getAuditedBy() {
		return auditedBy;
	}


	public void setAuditedBy(String auditedBy) {
		this.auditedBy = auditedBy;
	}


	public String getAuditDate() {
		return auditDate;
	}


	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}


	public String getGender() { 
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	public String getFacilityApproved() {
		return facilityApproved;
	}
	
	public void setFacilityApproved(String facilityApproved) {
		this.facilityApproved = facilityApproved;
	}
	
	public String getFacilityApplied() {
		return facilityApplied;
	}

	public void setFacilityApplied(String facilityApplied) {
		this.facilityApplied = facilityApplied;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPrincipalIntroduction() {
		return principalIntroduction;
	}

	public void setPrincipalIntroduction(String principalIntroduction) {
		this.principalIntroduction = principalIntroduction;
	}

	public int getClearanceLevel() {
		return clearanceLevel;
	}

	public void setClearanceLevel(int clearanceLevel) {
		this.clearanceLevel = clearanceLevel;
	}

	public int getTenorApproved() {
		return tenorApproved;
	}

	public void setTenorApproved(int tenorApproved) {
		this.tenorApproved = tenorApproved;
	}

	public double getFacilityRequested() {
		return facilityRequested;
	}

	public void setFacilityRequested(double facilityRequested) {
		this.facilityRequested = facilityRequested;
	}

	public double getAmountApproved() {
		return amountApproved;
	}

	public void setAmountApproved(double amountApproved) {
		this.amountApproved = amountApproved;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getManagementFeeRate() {
		return managementFeeRate;
	}

	public void setManagementFeeRate(double managementFeeRate) {
		this.managementFeeRate = managementFeeRate;
	}

	public double getMonitoringFeeRate() {
		return monitoringFeeRate;
	}

	public void setMonitoringFeeRate(double monitoringFeeRate) {
		this.monitoringFeeRate = monitoringFeeRate;
	}

	public double getRiskPremiumRate() {
		return riskPremiumRate;
	}

	public void setRiskPremiumRate(double riskPremiumRate) {
		this.riskPremiumRate = riskPremiumRate;
	}

	public double getCompulsorySavingsRate() {
		return compulsorySavingsRate;
	}

	public void setCompulsorySavingsRate(double compulsorySavingsRate) {
		this.compulsorySavingsRate = compulsorySavingsRate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getOthernames() {
		return othernames;
	}

	public void setOthernames(String othernames) {
		this.othernames = othernames;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getBvn() {
		return bvn;
	}

	public void setBvn(String bvn) {
		this.bvn = bvn;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getSor() {
		return sor;
	}

	public void setSor(String sor) {
		this.sor = sor;
	}

	public String getMarketingOfficer() {
		return marketingOfficer;
	}

	public void setMarketingOfficer(String marketingOfficer) {
		this.marketingOfficer = marketingOfficer;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getTenorTypeApproved() {
		return tenorTypeApproved;
	}

	public void setTenorTypeApproved(String tenorTypeApproved) {
		this.tenorTypeApproved = tenorTypeApproved;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public String getLoanProduct() {
		return loanProduct;
	}

	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}

	public String getInterestType() {
		return interestType;
	}

	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}

	public String getCreditOfficer() {
		return creditOfficer;
	}

	public void setCreditOfficer(String creditOfficer) {
		this.creditOfficer = creditOfficer;
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

	public String getMaturity() {
		return maturity;
	}

	public void setMaturity(String maturity) {
		this.maturity = maturity;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getPricingInterest() {
		return pricingInterest;
	}

	public void setPricingInterest(String pricingInterest) {
		this.pricingInterest = pricingInterest;
	}

	public String getPricingMonFee() {
		return pricingMonFee;
	}

	public void setPricingMonFee(String pricingMonFee) {
		this.pricingMonFee = pricingMonFee;
	}

	public String getPricingRiskPremium() {
		return pricingRiskPremium;
	}

	public void setPricingRiskPremium(String pricingRiskPremium) {
		this.pricingRiskPremium = pricingRiskPremium;
	}

	public String getPricingMgtFee() {
		return pricingMgtFee;
	}

	public void setPricingMgtFee(String pricingMgtFee) {
		this.pricingMgtFee = pricingMgtFee;
	}

	public String getDefaultPenalty() {
		return defaultPenalty;
	}

	public void setDefaultPenalty(String defaultPenalty) {
		this.defaultPenalty = defaultPenalty;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getRepaymentSource() {
		return repaymentSource;
	}

	public void setRepaymentSource(String repaymentSource) {
		this.repaymentSource = repaymentSource;
	}


	public String getTermlyRepaymentHeader() {
		return termlyRepaymentHeader;
	}


	public void setTermlyRepaymentHeader(String termlyRepaymentHeader) {
		this.termlyRepaymentHeader = termlyRepaymentHeader;
	}


	public String getFullRepaymentHeader() {
		return fullRepaymentHeader;
	}


	public void setFullRepaymentHeader(String fullRepaymentHeader) {
		this.fullRepaymentHeader = fullRepaymentHeader;
	}


	public String getInstallmentPrincipal() {
		return installmentPrincipal;
	}


	public void setInstallmentPrincipal(String installmentPrincipal) {
		this.installmentPrincipal = installmentPrincipal;
	}


	public String getInstallmentInterestCharges() {
		return installmentInterestCharges;
	}


	public void setInstallmentInterestCharges(String installmentInterestCharges) {
		this.installmentInterestCharges = installmentInterestCharges;
	}


	public String getInstallmentCompulsorySavings() {
		return installmentCompulsorySavings;
	}


	public void setInstallmentCompulsorySavings(String installmentCompulsorySavings) {
		this.installmentCompulsorySavings = installmentCompulsorySavings;
	}


	public String getTotalInstallmentRepayment() {
		return totalInstallmentRepayment;
	}


	public void setTotalInstallmentRepayment(String totalInstallmentRepayment) {
		this.totalInstallmentRepayment = totalInstallmentRepayment;
	}


	public String getTotalInterestCharges() {
		return totalInterestCharges;
	}


	public void setTotalInterestCharges(String totalInterestCharges) {
		this.totalInterestCharges = totalInterestCharges;
	}


	public String getTotalCompulsorySavings() {
		return totalCompulsorySavings;
	}


	public void setTotalCompulsorySavings(String totalCompulsorySavings) {
		this.totalCompulsorySavings = totalCompulsorySavings;
	}


	public String getTotalRepayment() {
		return totalRepayment;
	}


	public void setTotalRepayment(String totalRepayment) {
		this.totalRepayment = totalRepayment;
	}
	
		

}
