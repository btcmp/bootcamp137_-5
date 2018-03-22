package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.Employee;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	public Employee getEmployee(Employee e) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(Employee.class, e.getId());
	}

	public List<Employee> getAllEmployee() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Employee.class).list();
	}

	public void save(Employee e) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(e);
		session.flush();
	}

	public void update(Employee e) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(e);
		session.flush();
	}

	public void delete(Employee e) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(e);
		session.flush();
	}

}
