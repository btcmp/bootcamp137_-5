package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.Supplier;

@Repository
public class SupplierDaoImpl implements SupplierDao{

	@Autowired
	SessionFactory sessionFactory;
	
	public void save(Supplier supplier) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(supplier);
		session.flush();
	}

	public void delete(Supplier supplier) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(supplier);
		session.flush();
	}

	public void update(Supplier supplier) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(supplier);
		session.flush();
	}

	public Supplier getOne(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(Supplier.class, id);
	}

	public List<Supplier> selectAlll() {
		// TODO Auto-generated method stub
		String hql  = "from Supplier sup where sup.active = 1";
		Session session = sessionFactory.getCurrentSession();
		List<Supplier> sps = session.createQuery(hql).list();
		return sps;
	}

	public List<Supplier> getSupplierBySearchName(String search) {
		// TODO Auto-generated method stub
		String hql  = "from Supplier sp where lower(sp.name) like  lower(:nb) and sp.active = 1";
		Session session = sessionFactory.getCurrentSession();
		List<Supplier> sups = session.createQuery(hql).setParameter("nb", "%"+search+"%").list();
		if (sups.isEmpty()) {
			return null;
		}
		return sups;
	}

	@Override
	public List<Supplier> getAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Supplier.class).list();
	}

}