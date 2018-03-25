package com.miniproject.pos.dao;

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

}
