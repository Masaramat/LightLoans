package com.llp.entities;

import java.io.Serializable;

public class LoanOfferPrice implements Serializable{
	double principal;
	double interest ;
	double tempInterest ;
	double managementFee; 
	double riskPremium ; 
	double monitoringFee; 
	double compulsorySavings;	
	
	double upfrontInterest;
	
	double installmentPaymentPrincipal; 
	double installmentPaymentInterestCharges ;  
	double totalInstallmentRepayment ;	
	
	double totalInterestCharges ;
	double totalCompulsorySavings ;
	double totalRepayment ;	
	
	double totalInterest  ; 
	double totalMonitoringFee ;  
	double totalRiskPremium;
	double grossTotal ;
	double percentageGrossTotal  ;

	public LoanOfferPrice() {
		
	}

	public double getUpfrontInterest() {
		return upfrontInterest;
	}

	public void setUpfrontInterest(double upfrontInterest) {
		this.upfrontInterest = upfrontInterest;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getManagementFee() {
		return managementFee;
	}

	public void setManagementFee(double managementFee) {
		this.managementFee = managementFee;
	}

	public double getRiskPremium() {
		return riskPremium;
	}

	public void setRiskPremium(double riskPremium) {
		this.riskPremium = riskPremium;
	}

	public double getMonitoringFee() {
		return monitoringFee;
	}

	public void setMonitoringFee(double monitoringFee) {
		this.monitoringFee = monitoringFee;
	}

	public double getCompulsorySavings() {
		return compulsorySavings;
	}

	public void setCompulsorySavings(double compulsorySavings) {
		this.compulsorySavings = compulsorySavings;
	}

	public double getInstallmentPaymentPrincipal() {
		return installmentPaymentPrincipal;
	}

	public void setInstallmentPaymentPrincipal(double installmentPaymentPrincipal) {
		this.installmentPaymentPrincipal = installmentPaymentPrincipal;
	}

	public double getInstallmentPaymentInterestCharges() {
		return installmentPaymentInterestCharges;
	}

	public void setInstallmentPaymentInterestCharges(double installmentPaymentInterestCharges) {
		this.installmentPaymentInterestCharges = installmentPaymentInterestCharges;
	}

	public double getTotalInstallmentRepayment() {
		return totalInstallmentRepayment;
	}

	public void setTotalInstallmentRepayment(double totalInstallmentRepayment) {
		this.totalInstallmentRepayment = totalInstallmentRepayment;
	}

	public double getTotalInterestCharges() {
		return totalInterestCharges;
	}

	public void setTotalInterestCharges(double totalInterestCharges) {
		this.totalInterestCharges = totalInterestCharges;
	}

	public double getTotalCompulsorySavings() {
		return totalCompulsorySavings;
	}

	public void setTotalCompulsorySavings(double totalCompulsorySavings) {
		this.totalCompulsorySavings = totalCompulsorySavings;
	}

	public double getTotalRepayment() {
		return totalRepayment;
	}

	public void setTotalRepayment(double totalRepayment) {
		this.totalRepayment = totalRepayment;
	}

	public double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public double getTotalMonitoringFee() {
		return totalMonitoringFee;
	}

	public void setTotalMonitoringFee(double totalMonitoringFee) {
		this.totalMonitoringFee = totalMonitoringFee;
	}

	public double getTotalRiskPremium() {
		return totalRiskPremium;
	}

	public void setTotalRiskPremium(double totalRiskPremium) {
		this.totalRiskPremium = totalRiskPremium;
	}

	public double getGrossTotal() {
		return grossTotal;
	}

	public void setGrossTotal(double grossTotal) {
		this.grossTotal = grossTotal;
	}

	public double getPercentageGrossTotal() {
		return percentageGrossTotal;
	}

	public void setPercentageGrossTotal(double percentageGrossTotal) {
		this.percentageGrossTotal = percentageGrossTotal;
	}

	public double getTempInterest() {
		return tempInterest;
	}

	public void setTempInterest(double tempInterest) {
		this.tempInterest = tempInterest;
	}
	
	
	

}
