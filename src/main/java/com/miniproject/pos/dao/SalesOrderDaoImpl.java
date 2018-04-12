package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.SalesOrder;
import com.miniproject.pos.model.SalesOrderDetail;

@Repository
public class SalesOrderDaoImpl implements SalesOrderDao{

	@Autowired
	SessionFactory sessionFactory;
	
	public void save(SalesOrder salesOrder) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(salesOrder);
		session.flush();
	}

	public void delete(SalesOrder salesOrder) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(salesOrder);
		session.flush();
	}

	public void update(SalesOrder salesOrder) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(salesOrder);
		session.flush();
	}

	public SalesOrder getOne(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(SalesOrder.class, id);
	}

	public List<SalesOrder> selectAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(SalesOrder.class).list();
	}

	@Override
	public List<SalesOrderDetail> getSalesOrderDetailByIdSalesOrder(String soId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from SalesOrderDetail sod where sod.salesOrder.id = :id";
		List<SalesOrderDetail> sods = session.createQuery(hql).setParameter("id", soId).list();
		if (sods.isEmpty()) {
			return null;
		}
		return sods;
	}

}