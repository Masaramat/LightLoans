package com.llp.entities;

import java.io.Serializable;

public class OtherBankAccount implements Serializable{
	
	String otherAccountName, otherAccountNo, otherAccountType, otherBank;

	public OtherBankAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOtherAccountName() {
		return otherAccountName;
	}

	public void setOtherAccountName(String otherAccountName) {
		this.otherAccountName = otherAccountName;
	}

	public String getOtherAccountNo() {
		return otherAccountNo;
	}

	public void setOtherAccountNo(String otherAccountNo) {
		this.otherAccountNo = otherAccountNo;
	}

	public String getOtherAccountType() {
		return otherAccountType;
	}

	public void setOtherAccountType(String otherAccountType) {
		this.otherAccountType = otherAccountType;
	}

	public String getOtherBank() {
		return otherBank;
	}

	public void setOtherBank(String otherBank) {
		this.otherBank = otherBank;
	}
	
	

}
