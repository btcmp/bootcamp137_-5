package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.Region;

@Repository
public class RegionDaoImpl implements RegionDao{

	@Autowired
	SessionFactory sessionFactory;
	
	public List<Region> selectAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Region.class).list();
	}

	public List<Region> getRegionsByIdPrvinsi(String idProvinsi) {
		// TODO Auto-generated method stub
		String hql="from Region rg where rg.provinsi.id like :idprov";
		Session session = sessionFactory.getCurrentSession();
		List<Region> regs = session.createQuery(hql).setParameter("idprov", "%"+idProvinsi+"%").list();
		if(regs.isEmpty()) {
			return null;
		}
		return regs;
	}

	public void save(Region region) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(region);
		session.flush();
		
	}

	public void delete(Region region) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(region);
		session.flush();
	}

	public void update(Region region) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(region);
		session.flush();
	}

	public Region getOne(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(Region.class, id);
	}

}

