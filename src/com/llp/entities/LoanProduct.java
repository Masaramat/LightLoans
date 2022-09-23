package com.llp.entities;

import java.io.Serializable;

public class LoanProduct implements Serializable{
	String productId, productDescription;
	double intrestRate, monFeeRate, mgtFeeRate, riskPremRate, compSavingsRate;
	

	public LoanProduct() {
		
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getProductDescription() {
		return productDescription;
	}


	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}


	public double getIntrestRate() {
		return intrestRate;
	}


	public void setIntrestRate(double intrestRate) {
		this.intrestRate = intrestRate;
	}


	public double getMonFeeRate() {
		return monFeeRate;
	}


	public void setMonFeeRate(double monFeeRate) {
		this.monFeeRate = monFeeRate;
	}


	public double getMgtFeeRate() {
		return mgtFeeRate;
	}


	public void setMgtFeeRate(double mgtFeeRate) {
		this.mgtFeeRate = mgtFeeRate;
	}


	public double getRiskPremRate() {
		return riskPremRate;
	}


	public void setRiskPremRate(double riskPremRate) {
		this.riskPremRate = riskPremRate;
	}


	public double getCompSavingsRate() {
		return compSavingsRate;
	}


	public void setCompSavingsRate(double compSavingsRate) {
		this.compSavingsRate = compSavingsRate;
	}


	
	
	

}
