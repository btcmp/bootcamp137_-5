package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.PurchaseRequestDetail;

@Repository
public class PurchaseRequestDetailDAOImpl implements PurchaseRequestDetailDAO{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public PurchaseRequestDetail getPurchaseRequestDetail(PurchaseRequestDetail prd) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(PurchaseRequestDetail.class, prd.getId());
	}

	@Override
	public List<PurchaseRequestDetail> getAllPurchaseRequestDetail() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(PurchaseRequestDetail.class).list();
	}

	@Override
	public void save(PurchaseRequestDetail prd) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(prd);
		session.flush();
	}

	@Override
	public void update(PurchaseRequestDetail prd) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(prd);
		session.flush();
	}

	@Override
	public void delete(PurchaseRequestDetail prd) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.clear();
		session.delete(prd);
		session.flush();
	}
	
}
