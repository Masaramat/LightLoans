package com.llp.entities;

import java.io.Serializable;

public class BusinessCategory implements Serializable{
	String categoryId, categoryDecription;

	public BusinessCategory() {
	
	}

	public BusinessCategory(String categoryId, String categoryDecription) {
		super();
		this.categoryId = categoryId;
		this.categoryDecription = categoryDecription;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryDecription() {
		return categoryDecription;
	}

	public void setCategoryDecription(String categoryDecription) {
		this.categoryDecription = categoryDecription;
	}
	
	

}
