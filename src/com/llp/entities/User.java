package com.llp.entities;

import java.io.Serializable;

public class User implements Serializable{
	String userId, fullName, username, password, branch, userGroup, gender;
	int userStatus;
	
	public static String ipAddress = "";

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getBranch() {
		return branch;
	}


	public void setBranch(String branch) {
		this.branch = branch;
	}


	public String getUserGroup() {
		return userGroup;
	}


	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}


	public int getUserStatus() {
		return userStatus;
	}


	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	
	

}
