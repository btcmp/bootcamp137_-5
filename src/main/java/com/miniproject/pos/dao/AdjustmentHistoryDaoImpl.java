package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.AdjustmentHistory;

@Repository
public class AdjustmentHistoryDaoImpl implements AdjustmentHistoryDao {

	@Autowired
	SessionFactory sf;
	
	public void save(AdjustmentHistory ah) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.save(ah);
		session.flush();
	}

	public List<AdjustmentHistory> getAdjustmentHistoryByAdjustmentId(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "from AdjustmentHistory ah where ah.adjustmentId.id=:id";
		return session.createQuery(hql).setParameter("id", id).list();
	}

}
