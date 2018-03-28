package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.Employee;

public interface EmployeeDAO {
	
	Employee getEmployee(Employee e);
	List<Employee> getAllEmployee();
	void save(Employee e);
	void update(Employee e);
	void delete(Employee e);
	List<Employee> getAllActiveEmployee();
}
