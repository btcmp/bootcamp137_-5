package com.miniproject.pos.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.PurchaseOrder;
import com.miniproject.pos.model.PurchaseRequest;

@Repository
public class PurchaseOrderDAOImpl implements PurchaseOrderDAO{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public PurchaseOrder getPurchaseOrder(PurchaseOrder po) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(PurchaseOrder.class,po.getId());
	}

	@Override
	public List<PurchaseOrder> getAllPurchaseOrder() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(PurchaseOrder.class).list();
	}

	@Override
	public void save(PurchaseOrder po) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(po);
		session.flush();
	}

	@Override
	public void update(PurchaseOrder po) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.clear();
		session.saveOrUpdate(po);
		session.flush();
	}

	@Override
	public void delete(PurchaseOrder po) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(po);
		session.flush();
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(PurchaseOrder.class).list().size();
	}

	@Override
	public List<PurchaseOrder> getAllSubmittedPurchaseOrder() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from PurchaseOrder po where po.status = 'submitted'";
		List<PurchaseOrder> listPO = session.createQuery(hql).list();
		
		if (listPO.isEmpty()) {
			return null;
		}  else {
			return listPO;
		}
	}

	@Override
	public List<PurchaseOrder> getAllApprovedPurchaseOrder() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from PurchaseOrder po where po.status = 'approved'";
		List<PurchaseOrder> listPO = session.createQuery(hql).list();
		
		if (listPO.isEmpty()) {
			return null;
		}  else {
			return listPO;
		}
	}

	@Override
	public List<PurchaseOrder> getAllRejectedPurchaseOrder() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from PurchaseOrder po where po.status = 'rejected'";
		List<PurchaseOrder> listPO = session.createQuery(hql).list();
		
		if (listPO.isEmpty()) {
			return null;
		}  else {
			return listPO;
		}
	}

	@Override
	public List<PurchaseOrder> getAllProcessedPurchaseOrder() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from PurchaseOrder po where po.status = 'processed'";
		List<PurchaseOrder> listPO = session.createQuery(hql).list();
		
		if (listPO.isEmpty()) {
			return null;
		}  else {
			return listPO;
		}
	}

	@Override
	public List<PurchaseOrder> getListPOBySearch(String search) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from PurchaseOrder po where lower(po.poNo) like :search or lower(po.supplier.name) like :search or lower(po.status) like :search or po.grandTotal like :search";
		List<PurchaseOrder> listPO = session.createQuery(hql).setParameter("search","%"+search.toLowerCase()+"%").list();
		
		if (listPO.isEmpty()) {
			return null;
		}  else {
			return listPO;
		}
	}

	@Override
	public List<PurchaseOrder> getListPOByDate(Date start, Date end) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from PurchaseOrder po where po.createdOn between :startDate and :endDate";
		List<PurchaseOrder> listPO = session.createQuery(hql).setParameter("startDate", start).setParameter("endDate", end).list();
		
		if (listPO.isEmpty()) {
			return null;
		}  else {
			return listPO;
		}
	}
	
	public Long countPurchaseOrder(String outletId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select count(id) from PurchaseOrder where purchaseRequest.outlet.id=:outletId and status='processed'";
		List<Long> data = session.createQuery(hql).setParameter("outletId", outletId).list();
		if(! data.isEmpty()) {
			return data.get(0);
		}else{
			return (long)0;
		}
	}
}
