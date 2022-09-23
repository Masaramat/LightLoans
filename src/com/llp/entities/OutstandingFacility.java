package com.llp.entities;

import java.io.Serializable;

public class OutstandingFacility implements Serializable{
	String outstandingFacility, security, expiryDateString;
	Double loanAmount, outstandingBalance;
	int sno;

	public OutstandingFacility() {
		// TODO Auto-generated constructor stub
	}
	
	

	public OutstandingFacility(String outstandingFacility, String security, String expiryDateString, Double loanAmount,
			Double outstandingBalance) {
		super();
		this.outstandingFacility = outstandingFacility;
		this.security = security;
		this.expiryDateString = expiryDateString;
		this.loanAmount = loanAmount;
		this.outstandingBalance = outstandingBalance;
	}



	public String getOutstandingFacility() {
		return outstandingFacility;
	}

	public void setOutstandingFacility(String outstandingFacility) {
		this.outstandingFacility = outstandingFacility;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getExpiryDateString() {
		return expiryDateString;
	}

	public void setExpiryDateString(String expiryDateString) {
		this.expiryDateString = expiryDateString;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getOutstandingBalance() {
		return outstandingBalance;
	}

	public void setOutstandingBalance(Double outstandingBalanceDouble) {
		this.outstandingBalance = outstandingBalanceDouble;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}
	

}
