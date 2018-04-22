package com.miniproject.pos.dao;

import java.util.Date;
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

	public List<Adjustment> getAllAdjustment(String outletId) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select a from Adjustment as a where a.outletId.id=:outlet";
		return session.createQuery(hql).setParameter("outlet", outletId).list();
	}
	
	public List<Adjustment> getAllAdjustmentByDate(Date startDate, Date endDate, String outletId) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select a from Adjustment as a where a.createdOn between :startDate and :endDate and a.outletId.id=:outlet";
		return session.createQuery(hql).setParameter("endDate", endDate).setParameter("startDate", startDate).setParameter("outlet", outletId).list();
	}

	public int countAdjustment(String outletId) {
		Session session = sf.getCurrentSession();
		String hql = "select count(id) from Adjustment where outletId.id=:outletId and status='Approved'";
		List<Integer> data = session.createQuery(hql).setParameter("outletId", outletId).list();
		if(! data.isEmpty()) {
			return data.get(0);
		}else{
			return 0;
		}
	}
}
