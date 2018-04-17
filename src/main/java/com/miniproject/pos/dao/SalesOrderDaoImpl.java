package com.miniproject.pos.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public Map<String, Double> getTotalSalesLast7Day(Date date) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "select sum(so.grandTotal), to_char(so.createdOn, 'DD-MM-YYYY') from SalesOrder so group by , to_char(so.createdOn, 'DD-MM-YYYY')";
		List<Object[]> data = session.createQuery(hql).list();
		Map<String, Double> mapping = new HashMap<String, Double>();
		for(Object[] tamp:data) {
			Double total = (Double) tamp[0];
			String tgl = (String) tamp[1];
			System.out.println(tgl+" "+total);
		}
		return null;
	}

	
}