package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.AdjustmentDetail;
import com.miniproject.pos.model.ItemInventory;

@Repository
public class AdjustmentDetailDaoImpl implements AdjustmentDetailDao{

	@Autowired
	SessionFactory sf;
	
	public void save(AdjustmentDetail ad) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.save(ad);
		session.flush();
	}
	
	public List<AdjustmentDetail> getAdjustmentDetailByAdjustmentId(String id){
		Session session = sf.getCurrentSession();
		String hql = "from AdjustmentDetail as ad where ad.adjustmentId.id=:id";
		List<AdjustmentDetail> list = session.createQuery(hql).setParameter("id", id).list();
		return list;
	}
}
