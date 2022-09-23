package com.llp.entities;

import java.io.Serializable;

public class Customer implements Serializable {
	String customerId, lmfbAccountNo, title, surname, gender, othernames, address, dob, bvn, phone, branchId, branch, user;
	int noOfWives, noOfChildren, noOfSchoolChildren;

	public Customer() {}	

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getLmfbAccountNo() {
		return lmfbAccountNo;
	}

	public void setLmfbAccountNo(String lmfbAccountNo) {
		this.lmfbAccountNo = lmfbAccountNo;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public int getNoOfWives() {
		return noOfWives;
	}

	public void setNoOfWives(int noOfWives) {
		this.noOfWives = noOfWives;
	}

	public int getNoOfChildren() {
		return noOfChildren;
	}

	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	public int getNoOfSchoolChildren() {
		return noOfSchoolChildren;
	}

	public void setNoOfSchoolChildren(int noOfSchoolChildren) {
		this.noOfSchoolChildren = noOfSchoolChildren;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	} 
	
	
	

}
