package com.llp.entities;

import java.io.Serializable;

public class FinanceItem implements Serializable{
	String financeType, item;
	int sno;

	public FinanceItem() {
		// TODO Auto-generated constructor stub
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

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}
	
	

}
