package com.llp.entities;

import java.io.Serializable;

public class RejectedLoan implements Serializable{
	String applicationID, rejectedBy, rejectionType, comment, rejectionDate;
	public RejectedLoan() {
		// TODO Auto-generated constructor stub
	}
	public String getApplicationID() {
		return applicationID;
	}
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}
	public String getRejectedBy() {
		return rejectedBy;
	}
	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
	public String getRejectionType() {
		return rejectionType;
	}
	public void setRejectionType(String rejectionType) {
		this.rejectionType = rejectionType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getRejectionDate() {
		return rejectionDate;
	}
	public void setRejectionDate(String rejectionDate) {
		this.rejectionDate = rejectionDate;
	}
	
	
	
	

}
