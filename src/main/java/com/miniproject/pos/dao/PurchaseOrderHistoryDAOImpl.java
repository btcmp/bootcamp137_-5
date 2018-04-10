package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.PurchaseOrderHistory;

@Repository
public class PurchaseOrderHistoryDAOImpl implements PurchaseOrderHistoryDAO{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public PurchaseOrderHistory getPurchaseOrderHistory(PurchaseOrderHistory poh) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(PurchaseOrderHistory.class,poh.getId());
	}

	@Override
	public List<PurchaseOrderHistory> getAllPurchaseOrderHistory() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(PurchaseOrderHistory.class).list();
	}

	@Override
	public void save(PurchaseOrderHistory poh) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(poh);
		session.flush();
	}

	@Override
	public void update(PurchaseOrderHistory poh) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(poh);
		session.flush();
	}

	@Override
	public void delete(PurchaseOrderHistory poh) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(poh);
		session.flush();
	}
}
