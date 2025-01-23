package com.estuate.test.services;

public class CategoryDeviation {

	private Character category;
	private double actualPercentage;
	private double standardPercentage;
	private double deviation;

	public CategoryDeviation() {

	}

	public CategoryDeviation(Character character, double actualPercentage, double standardPercentage,
			double deviation) {
		this.category = character;
		this.actualPercentage = actualPercentage;
		this.standardPercentage = standardPercentage;
		this.deviation = deviation;
	}

	public Character getCategory() {
		return category;
	}

	public void setCategory(Character category) {
		this.category = category;
	}

	public double getActualPercentage() {
		return actualPercentage;
	}

	public void setActualPercentage(double actualPercentage) {
		this.actualPercentage = actualPercentage;
	}

	public double getStandardPercentage() {
		return standardPercentage;
	}

	public void setStandardPercentage(double standardPercentage) {
		this.standardPercentage = standardPercentage;
	}

	public double getDeviation() {
		return deviation;
	}

	public void setDeviation(double deviation) {
		this.deviation = deviation;
	}

}
