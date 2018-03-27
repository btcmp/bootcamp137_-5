package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.Role;

@Repository
public class RoleDAOImpl implements RoleDAO {

	@Autowired
	SessionFactory sessionFactory;

	public Role getRole(Role r) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(Role.class, r.getId());
	}

	public List<Role> getAllRole() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Role.class).list();
	}

	public void save(Role r) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(r);
		session.flush();
	}

	public void update(Role r) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(r);
		session.flush();
	}

	public void delete(Role r) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(r);
		session.flush();
	}
	
	
}
