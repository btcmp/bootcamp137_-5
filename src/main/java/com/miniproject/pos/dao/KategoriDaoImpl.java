package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.Kategori;

@Repository
public class KategoriDaoImpl implements KategoriDao{

	@Autowired
	SessionFactory sessionFactory;
	
	public void save(Kategori kategori) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(kategori);
		session.flush();
	}

	public void delete(Kategori kategori) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(kategori);
		session.flush();
	}

	public void update(Kategori kategori) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(kategori);
		session.flush();
	}

	public Kategori getOne(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(Kategori.class, id);
	}

	public List<Kategori> selectAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Kategori.class).list();
	}

}
