package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.TransferStockHistory;

@Repository
public class TransferStockHistoryDaoImpl implements TransferStockHistoryDao {

	@Autowired
	SessionFactory sf;
	
	public void save(TransferStockHistory tsh) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.save(tsh);
		session.flush();
	}

	public List<TransferStockHistory> getTransferStockHistoryByIdTransfer(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "from TransferStockHistory as tsh where tsh.transferId.id=:id order by tsh.createdOn ASC";
		return session.createQuery(hql).setParameter("id", id).list();
	}

}
