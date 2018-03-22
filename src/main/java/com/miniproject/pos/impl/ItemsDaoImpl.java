package com.miniproject.pos.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.dao.ItemsDao;
import com.miniproject.pos.model.Items;

@Repository
public class ItemsDaoImpl implements ItemsDao {

	@Autowired
	SessionFactory sf;
	
	public void save(Items items) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.save(items);
		session.flush();
	}

	public void update(Items items) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.update(items);
		session.flush();
	}

	public void delete(Items items) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.delete(items);
		session.flush();
	}

	public Items getItemsById(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		return session.get(Items.class, id);
	}

	public List<Items> getAllItems() {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "from Items";
		List<Items> list = session.createQuery(hql).list();
		return list;
	}

}
