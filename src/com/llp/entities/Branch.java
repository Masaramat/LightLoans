package com.llp.entities;

import java.io.Serializable;

public class Branch implements Serializable{
	
	String branchId, branchName;

	public Branch() {
		
	}

	public Branch(String branchId, String branchName) {
		super();
		this.branchId = branchId;
		this.branchName = branchName;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	
	

}
