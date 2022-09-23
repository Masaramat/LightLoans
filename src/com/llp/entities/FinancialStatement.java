package com.llp.entities;

import java.io.Serializable;

public class FinancialStatement implements Serializable{
	String interviewId, financeType, item;
	double amount;
	int sno;

	public FinancialStatement() {
		// TODO Auto-generated constructor stub
	}

	public String getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(String interviewId) {
		this.interviewId = interviewId;
	}

	public String getFinanceType() {
		return financeType;
	}

	public void setFinanceType(String financeType) {
		this.financeType = financeType;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}
	

}
