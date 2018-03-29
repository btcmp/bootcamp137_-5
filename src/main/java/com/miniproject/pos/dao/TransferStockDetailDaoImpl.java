package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.TransferStockDetail;

@Repository
public class TransferStockDetailDaoImpl implements TransferStockDetailDao {

	@Autowired
	SessionFactory sf;
	
	public void save(TransferStockDetail tsd) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.save(tsd);
		session.flush();
	}

	public List<TransferStockDetail> getTransferStockDetailByIdTransfer(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "from TransferStockDetail as tsd where tsd.transferId.id=:id";
		return session.createQuery(hql).setParameter("id", id).list();
	}

}
