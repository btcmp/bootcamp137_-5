package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.EmployeeDAO;
import com.miniproject.pos.model.Employee;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	EmployeeDAO employeeDAO;
	
	public Employee get(String id) {
		Employee e = new Employee();
		e.setId(id);
		e.setFirstName("-");
		e.setLastName("-");
		e.setHaveAcount(true);
		e.setActive(true);
		return employeeDAO.getEmployee(e);
	}
	
	public List<Employee> getAll(){
		return employeeDAO.getAllEmployee();
	}
	
	public void save(Employee e) {
		if(e.getUser() != null) {
			e.getUser().setEmployee(e);
		}
		employeeDAO.save(e);
	}
	
	public void update(Employee e) {
		employeeDAO.update(e);
	}
	
	public void delete(Employee e) {
		employeeDAO.delete(e);
	}
	
	public List<Employee> getAllActiveEmployee(){
		return employeeDAO.getAllActiveEmployee();
	}
	
	public void deactivate(Employee e) {
		if(e.isActive()) {
			e.setActive(false);
		}
		employeeDAO.update(e);
	}
}
