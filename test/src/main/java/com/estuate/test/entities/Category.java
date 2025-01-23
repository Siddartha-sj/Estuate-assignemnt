package com.estuate.test.entities;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "category")
public class Category {

	@Id
	@Column(name = "category_id")
	private Character Category_Id;

	@Column(name = "standard_percentage")
	private double Standard_Percentage;

	@Column(name = "actual_percentage")
	private double Actual_Percentage;

	public Category() {

	}

	public Category(Character category_Id, double standard_Percentage, double actual_Percentage) {
		super();
		Category_Id = category_Id;
		Standard_Percentage = standard_Percentage;
		Actual_Percentage = actual_Percentage;
	}

	public Character getCategory_Id() {
		return Category_Id;
	}

	public void setCategory_Id(Character category_Id) {
		Category_Id = category_Id;
	}

	public double getStandard_Percentage() {
		return Standard_Percentage;
	}

	public void setStandard_Percentage(double standard_Percentage) {
		Standard_Percentage = standard_Percentage;
	}

	public double getActual_Percentage() {
		return Actual_Percentage;
	}

	public void setActual_Percentage(double actual_Percentage) {
		Actual_Percentage = actual_Percentage;
	}

}
