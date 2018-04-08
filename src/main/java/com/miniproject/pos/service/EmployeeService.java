package com.miniproject.pos.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.EmployeeDAO;
import com.miniproject.pos.dao.OutletDao;
import com.miniproject.pos.dao.UserDAO;
import com.miniproject.pos.model.Employee;
import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.model.User;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	EmployeeDAO employeeDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	OutletDao outletDAO;
	
	public Employee get(String id) {
		Employee e = new Employee();
		e.setId(id);
		e.setFirstName("-");
		e.setLastName("-");
		e.setHaveAccount(true);
		e.setActive(true);
		return employeeDAO.getEmployee(e);
	}
	
	public List<Employee> getAll(){
		return employeeDAO.getAllEmployee();
	}
	
	public void save(Employee e) {
		e.setListOutlet(getListAssignedOutlet(e.getListOutlet()));
		if(e.isHaveAccount()) {
			e.getUser().setEmployee(e);
		}
		employeeDAO.save(e);
	}
	
	public void update(Employee e) {
		User u;
		e.setListOutlet(getListAssignedOutlet(e.getListOutlet()));
		if(e.getUser() != null) {
			u = userDAO.getUserByEmployee(e);
			if(u!=null) {
				e.getUser().setId(u.getId());
			}
			e.getUser().setEmployee(e);
		} else {
			u = userDAO.getUserByEmployee(e);
			if(u!=null) {
				u.setActive(false);
				e.setUser(u);
			}
		}
		e.setCreatedOn(get(e.getId()).getCreatedOn());
		e.setCreatedBy(get(e.getId()).getCreatedBy());
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
		
		if(e.getUser()!=null) {
			e.getUser().setEmployee(e);
			if	(e.getUser().isActive()) {
				e.getUser().setActive(false);
			}
		}
		e.setCreatedOn(get(e.getId()).getCreatedOn());
		e.setCreatedBy(get(e.getId()).getCreatedBy());
		employeeDAO.update(e);
	}
	
	public List<Outlet> getListAssignedOutlet(List<Outlet> listOutlet){
		List<Outlet> newListOutlet = new ArrayList<Outlet>();
		for (int i = 0; i < listOutlet.size(); i++) {
			newListOutlet.add(outletDAO.getOne(listOutlet.get(i).getId()));
		}
		return newListOutlet;
	}
	
	public Employee getEmployeeByUsername(String username) {
		return employeeDAO.getEmployeeByUsername(username);
	}
}
