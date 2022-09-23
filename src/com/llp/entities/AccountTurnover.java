package com.llp.entities;

import java.io.Serializable;

public class AccountTurnover implements Serializable{
	String month;
	Double debitTurnover, creditTurnover, monthEndBalance, income;
	int snp;

	public AccountTurnover() {
		// TODO Auto-generated constructor stub
	}
	
	

	public AccountTurnover(String month, Double debitTurnover, Double creditTurnover, Double monthEndBalance,
			Double income) {
		super();
		this.month = month;
		this.debitTurnover = debitTurnover;
		this.creditTurnover = creditTurnover;
		this.monthEndBalance = monthEndBalance;
		this.income = income;
	}



	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getDebitTurnover() {
		return debitTurnover;
	}

	public void setDebitTurnover(Double debitTurnover) {
		this.debitTurnover = debitTurnover;
	}

	public Double getCreditTurnover() {
		return creditTurnover;
	}

	public void setCreditTurnover(Double creditTurnover) {
		this.creditTurnover = creditTurnover;
	}

	public Double getMonthEndBalance() {
		return monthEndBalance;
	}

	public void setMonthEndBalance(Double monthEndBalance) {
		this.monthEndBalance = monthEndBalance;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public int getSnp() {
		return snp;
	}

	public void setSnp(int snp) {
		this.snp = snp;
	}
	

}
