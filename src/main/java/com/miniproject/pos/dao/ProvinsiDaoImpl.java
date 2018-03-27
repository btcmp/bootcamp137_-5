package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.Provinsi;

@Repository
public class ProvinsiDaoImpl implements ProvinsiDao{

	@Autowired
	SessionFactory sessionFactory;
	
	public List<Provinsi> selectAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Provinsi.class).list();
	}

}
