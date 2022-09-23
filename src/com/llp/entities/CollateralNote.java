package com.llp.entities;

import java.io.Serializable;

public class CollateralNote implements Serializable{
	String collateral, costumerBackground;
	int sno;

	public CollateralNote() {
		// TODO Auto-generated constructor stub
	}

	public String getCollateral() {
		return collateral;
	}

	public void setCollateral(String collateral) {
		this.collateral = collateral;
	}

	public String getCostumerBackground() {
		return costumerBackground;
	}

	public void setCostumerBackground(String costumerBackground) {
		this.costumerBackground = costumerBackground;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}
	

}
