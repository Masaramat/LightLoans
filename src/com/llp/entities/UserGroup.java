package com.llp.entities;

import java.io.Serializable;

public class UserGroup implements Serializable{
	
	String grouId, groupName;
	int acessLevel;


	public UserGroup() {
		
	}
	
	
	public UserGroup(String grouId, String groupName, int acessLevel) {
		super();
		this.grouId = grouId;
		this.groupName = groupName;
		this.acessLevel = acessLevel;
	}


	public String getGrouId() {
		return grouId;
	}

	public void setGrouId(String grouId) {
		this.grouId = grouId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public int getAcessLevel() {
		return acessLevel;
	}


	public void setAcessLevel(int acessLevel) {
		this.acessLevel = acessLevel;
	}

	
	
	
	

}
