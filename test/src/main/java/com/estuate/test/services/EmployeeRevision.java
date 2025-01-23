package com.estuate.test.services;

import com.estuate.test.entities.Employee;

public class EmployeeRevision {

	private Employee employee;
	private String action;

	public EmployeeRevision() {

	}

	public EmployeeRevision(Employee employee, String action) {
		this.employee = employee;
		this.action = action;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
