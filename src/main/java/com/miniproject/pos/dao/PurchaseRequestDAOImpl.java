package com.miniproject.pos.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.PurchaseRequest;

@Repository
public class PurchaseRequestDAOImpl implements PurchaseRequestDAO{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public PurchaseRequest getPurchaseRequest(PurchaseRequest pr) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(PurchaseRequest.class, pr.getId());
	}

	@Override
	public List<PurchaseRequest> getAllPurchaseRequest() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(PurchaseRequest.class).list();
	}

	@Override
	public void save(PurchaseRequest pr) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(pr);
		session.flush();
	}

	@Override
	public void update(PurchaseRequest pr) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.clear();
		session.saveOrUpdate(pr);
		session.flush();
	}

	@Override
	public void delete(PurchaseRequest pr) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(pr);
		session.flush();
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(PurchaseRequest.class).list().size();
	}

	@Override
	public List<PurchaseRequest> getAllSubmittedPurchaseRequest() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from PurchaseRequest pr where pr.status = 'submitted'";
		List<PurchaseRequest> listPR = session.createQuery(hql).list();
		
		if (listPR.isEmpty()) {
			return null;
		}  else {
			return listPR;
		}
	}

	@Override
	public List<PurchaseRequest> getAllApprovedPurchaseRequest() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from PurchaseRequest pr where pr.status = 'approved'";
		List<PurchaseRequest> listPR = session.createQuery(hql).list();
		
		if (listPR.isEmpty()) {
			return null;
		}  else {
			return listPR;
		}
	}

	@Override
	public List<PurchaseRequest> getAllRejectedPurchaseRequest() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from PurchaseRequest pr where pr.status = 'rejected'";
		List<PurchaseRequest> listPR = session.createQuery(hql).list();
		
		if (listPR.isEmpty()) {
			return null;
		}  else {
			return listPR;
		}
	}

	@Override
	public List<PurchaseRequest> getListPRBySearch(String search) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from PurchaseRequest where lower(prNo) like :search or lower(notes) like :search or lower(status) like :search";
		List<PurchaseRequest> listPR = session.createQuery(hql).setParameter("search","%"+search.toLowerCase()+"%").list();
		
		if (listPR.isEmpty()) {
			return null;
		}  else {
			return listPR;
		}
	}

	@Override
	public List<PurchaseRequest> getListPRByDate(String start, String end) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "from PurchaseRequest pr where pr.createdOn between '"+start+"' and '"+end+"'";
		List<PurchaseRequest> listPR = session.createQuery(hql).list();
		
		if (listPR.isEmpty()) {
			return null;
		}  else {
			return listPR;
		}
	}
	
}
