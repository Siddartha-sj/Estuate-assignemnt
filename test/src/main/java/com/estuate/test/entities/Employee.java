package com.estuate.test.entities;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@Column(name = "employee_id")
	private int Employee_Id;

	@Column(name = "employee_name")
	private String Employee_Name;

	@Column(name = "rating")
	private Character Rating;

	public Employee() {

	}

	public Employee(int employee_Id, String employee_Name, Character rating) {
		super();
		Employee_Id = employee_Id;
		Employee_Name = employee_Name;
		Rating = rating;
	}

	public int getEmployee_Id() {
		return Employee_Id;
	}

	public void setEmployee_Id(int employee_Id) {
		Employee_Id = employee_Id;
	}

	public String getEmployee_Name() {
		return Employee_Name;
	}

	public void setEmployee_Name(String employee_Name) {
		Employee_Name = employee_Name;
	}

	public Character getRating() {
		return Rating;
	}

	public void setRating(Character rating) {
		Rating = rating;
	}

}
