package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.utils.Constants;
import com.miniproject.pos.utils.UniqueException;

@Repository
public class ItemVariantDaoImpl implements ItemVariantDao {

	@Autowired
	SessionFactory sf;
	
	public void save(ItemVariant itemVariant) throws UniqueException{
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		try {
		session.save(itemVariant);
		session.flush();
		}catch(org.hibernate.exception.ConstraintViolationException e) {
			throw new UniqueException("variant", "sku", itemVariant.getSku());
		}
	}

	public void update(ItemVariant itemVariant) throws UniqueException{
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		try {
			session.update(itemVariant);
			session.flush();
		}catch(org.hibernate.exception.ConstraintViolationException e) {
			throw new UniqueException("variant", "sku", itemVariant.getSku());
		}
	}

	public void delete(ItemVariant itemVariant) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.delete(itemVariant);
		session.flush();
	}

	public ItemVariant getItemVariantById(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		return session.get(ItemVariant.class, id);
	}

	public List<ItemVariant> getAllItemVariant() {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "Select iv from ItemVariant iv join iv.itemId as i where iv.active=:active and i.active=:active";
		List<ItemVariant> list = session.createQuery(hql).setParameter("active", Constants.ACTIVE).list();
		return list;
	}
	
	public List<ItemVariant> getAllItemVariant(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "Select iv from ItemVariant iv join iv.itemId as i where iv.id=:id and iv.active=:active and i.active=:active";
		List<ItemVariant> list = session.createQuery(hql).setParameter("active", Constants.ACTIVE).setParameter("id", id).list();
		return list;
	}
	
	public List<ItemVariant> getItemVariantByItem(String itemId) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "from ItemVariant where itemId.id=:id";
		List<ItemVariant> list = session.createQuery(hql).setParameter("id", itemId).list();
		session.flush();
		return list;
	}

}
