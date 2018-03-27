package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.Adjustment;

@Repository
public class AdjustmentDaoImpl implements AdjustmentDao {

	@Autowired
	SessionFactory sf;
	
	public void save(Adjustment adjustment) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.save(adjustment);
		session.flush();
	}

	public void update(Adjustment adjustment) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.update(adjustment);
		session.flush();
	}

	public void delete(Adjustment adjustment) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.delete(adjustment);
		session.flush();
	}

	public Adjustment getAdjustmentById(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select a from Adjustment as a left join a.adjustmentDetail left join a.adjustmentHistory where a.id=:id";
		List<Adjustment> list = session.createQuery(hql).setParameter("id", id).list();
		return list.get(0);
	}
	
	public Adjustment getAdjustment(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		return session.get(Adjustment.class, id);
	}

	public List<Adjustment> getAllAdjustment() {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select a from Adjustment as a";
		return session.createCriteria(Adjustment.class).list();
	}

}
