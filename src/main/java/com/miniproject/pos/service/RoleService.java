package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.RoleDAO;
import com.miniproject.pos.model.Role;

@Service
@Transactional
public class RoleService {

	@Autowired
	RoleDAO roleDAO;
	
	public Role get(String id) {
		Role r = new Role();
		r.setId(id);
		r.setActive(true);
		return roleDAO.getRole(r);
	}
	
	public List<Role> getAll(){
		return roleDAO.getAllRole();
	}
	
	public void save(Role r) {
		roleDAO.save(r);
	}
	
	public void update(Role r) {
		roleDAO.update(r);
	}
	
	public void delete(Role r) {
		roleDAO.delete(r);
	}
	
}
