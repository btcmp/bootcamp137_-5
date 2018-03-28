package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.UserDAO;
import com.miniproject.pos.model.Employee;
import com.miniproject.pos.model.Role;
import com.miniproject.pos.model.User;

@Service
@Transactional
public class UserService {

	@Autowired
	UserDAO userDAO;
	
	public User get(String id) {
		User u = new User();
		u.setId(id);
		u.setUsername("-");
		u.setPassword("-");
		u.setRole(new Role());
		u.setEmployee(new Employee());
		u.setActive(true);
		return userDAO.getUser(u);
	}
	
	public List<User> getAll(){
		return userDAO.getAllUser();
	}
	
	public void save(User u) {
		userDAO.save(u);
	}
	
	public void update(User u) {
		userDAO.update(u);
	}
	
	public void delete(User u) {
		userDAO.delete(u);
	}
	
}
