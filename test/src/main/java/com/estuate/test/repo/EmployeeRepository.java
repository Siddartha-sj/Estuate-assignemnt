package com.estuate.test.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estuate.test.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
