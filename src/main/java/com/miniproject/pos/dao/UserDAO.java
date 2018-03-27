package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.User;

public interface UserDAO {

	User getUser(User u);
	List<User> getAllUser();
	void save(User u);
	void update(User u);
	void delete(User u);
	
}
