package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.miniproject.pos.model.TransferStock;

@Repository
public class TransferStockDaoImpl implements TransferStockDao {

	@Autowired
	SessionFactory sf;
	
	public void save(TransferStock ts) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.save(ts);
		session.flush();
	}

	public void update(TransferStock ts) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.update(ts);
		session.flush();
	}

	public TransferStock getTransferStockById(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		return session.get(TransferStock.class, id);
	}

	public List<TransferStock> getAllTransferStock(String outletId) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "from TransferStock as ts where ts.fromOutlet.id=:id";
		return session.createQuery(hql).setParameter("id", outletId).list();
	}
	public List<TransferStock> getAllTransferStockFilterToOutlet(String outletId, String toOutlet){
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "from TransferStock as ts where ts.fromOutlet.id=:id and ts.toOutlet.id=:toId";
		return session.createQuery(hql).setParameter("id", outletId).setParameter("toId", toOutlet).list();
	}
}
