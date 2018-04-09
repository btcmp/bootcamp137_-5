package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.Employee;
import com.miniproject.pos.model.User;

@Repository
public class UserDAOImpl implements UserDAO{

	@Autowired
	SessionFactory sessionFactory;

	public User getUser(User u) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(User.class, u.getId());
	}

	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(User.class).list();
	}

	public void save(User u) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(u);
		session.flush();
	}

	public void update(User u) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(u);
		session.flush();
	}

	public void delete(User u) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(u);
		session.flush();
	}

	public User getUserByEmployee(Employee e) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from User u where u.employee.id like :empid";
		List<User> listUser = session.createQuery(hql).setParameter("empid", e.getId()).list();
		
		if(listUser.isEmpty()) {
			return null;
		} else {
			User u = listUser.get(0);
			return u;
		}
	}
	
}