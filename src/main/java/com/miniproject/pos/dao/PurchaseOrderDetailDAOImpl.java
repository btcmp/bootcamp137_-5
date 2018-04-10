package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.PurchaseOrderDetail;

@Repository
public class PurchaseOrderDetailDAOImpl implements PurchaseOrderDetailDAO{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public PurchaseOrderDetail getPurchaseOrderDetail(PurchaseOrderDetail pod) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(PurchaseOrderDetail.class,pod.getId());
	}

	@Override
	public List<PurchaseOrderDetail> getAllPurchaseOrderDetail() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(PurchaseOrderDetail.class).list();
	}

	@Override
	public void save(PurchaseOrderDetail pod) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(pod);
		session.flush();
	}

	@Override
	public void update(PurchaseOrderDetail pod) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(pod);
		session.flush();
	}

	@Override
	public void delete(PurchaseOrderDetail pod) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(pod);
		session.flush();
	}
}
