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
		String hql="from Region rg where rg.provinsi :id";
		Session sessoion = sessionFactory.getCurrentSession();
		return null;
	}

}
