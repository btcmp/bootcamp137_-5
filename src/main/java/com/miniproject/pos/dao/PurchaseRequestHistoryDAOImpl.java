package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.PurchaseRequestHistory;

@Repository
public class PurchaseRequestHistoryDAOImpl implements PurchaseRequestHistoryDAO{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public PurchaseRequestHistory getPurchaseRequestHistory(PurchaseRequestHistory prh) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(PurchaseRequestHistory.class, prh.getId());
	}

	@Override
	public List<PurchaseRequestHistory> getAllPurchaseRequestHistory() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(PurchaseRequestHistory.class).list();
	}

	@Override
	public void save(PurchaseRequestHistory prh) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(prh);
		session.flush();
	}

	@Override
	public void update(PurchaseRequestHistory prh) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(prh);
		session.flush();
	}

	@Override
	public void delete(PurchaseRequestHistory prh) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(prh);
		session.flush();
	}
	
}
