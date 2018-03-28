package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.Outlet;

@Repository
public class OutletDaoImpl implements OutletDao{

	@Autowired
	SessionFactory sessionFactory;
	
	public void save(Outlet outlet) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(outlet);
		session.flush();
	}

	public void delete(Outlet outlet) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(outlet);;
		session.flush();
	}

	public void update(Outlet outlet) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(outlet);
		session.flush();
	}

	public Outlet getOne(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(Outlet.class, id);
	}

	public List<Outlet> selectAll() {
		// TODO Auto-generated method stub
		String hql = "from Outlet ou where ou.active = 1"; 
		Session session = sessionFactory.getCurrentSession();
		List<Outlet> outs = session.createQuery(hql).list();
		return outs;
	}

	public List<Outlet> getOutletNameBySearch(String search) {
		// TODO Auto-generated method stub
		String hql = "from Outlet ou where lower(ou.name) like lower(:nb)";
		Session session = sessionFactory.getCurrentSession();
		List<Outlet> outlets = session.createQuery(hql).setParameter("nb", "%"+search+"%").list();
		if(outlets.isEmpty()) {
			return null;
		}
		return outlets;
	}

}
