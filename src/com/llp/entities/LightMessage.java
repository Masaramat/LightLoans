package com.llp.entities;

import java.io.Serializable;

public class LightMessage implements Serializable{
	
	String message, sender, receiver, date, time, status;

	public LightMessage() {
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
		
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LightMessage(String message, String sender, String receiver, String date, String time, String status) {
		super();
		this.message = message;
		this.sender = sender;
		this.receiver = receiver;
		this.date = date;
		this.time = time;
		this.status = status;
	}
	
	

}
