package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.District;

@Repository
public class DistrictDaoImpl implements DistrictDao{

	@Autowired
	SessionFactory sessionFactory;
	
	public List<District> selectAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(District.class).list();
	}

	public void save(District district) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(district);
		session.flush();
	}

	public void delete(District district) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(district);
		session.flush();
	}

	public void update(District district) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(district);
		session.flush();
	}

	public District getOne(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(District.class, id);
	}

	public List<District> getDistrictByIdRegion(String id) {
		// TODO Auto-generated method stub
		String hql = "from District ds where ds.region.id like :idreg";
		Session session = sessionFactory.getCurrentSession();
		List<District> districts = session.createQuery(hql).setParameter("idreg", "%"+id+"%").list();
		if(districts.isEmpty()) {
			return null;
		}
		return districts;
	}

}
