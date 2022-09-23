package com.llp.entities;

import java.io.Serializable;

public class AppUserGroup implements Serializable{
	String groupName;
	int count;
	public AppUserGroup() {
		// TODO Auto-generated constructor stub
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public AppUserGroup(String groupName, int count) {
		super();
		this.groupName = groupName;
		this.count = count;
	}
	
	

}
