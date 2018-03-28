package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.Role;

public interface RoleDAO {

	Role getRole(Role r);
	List<Role> getAllRole();
	void save(Role r);
	void update(Role r);
	void delete(Role r);
	
}
