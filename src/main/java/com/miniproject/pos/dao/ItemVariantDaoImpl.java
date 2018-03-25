package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.ItemVariant;

@Repository
public class ItemVariantDaoImpl implements ItemVariantDao {

	@Autowired
	SessionFactory sf;
	
	public void save(ItemVariant itemVariant) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.save(itemVariant);
		session.flush();
	}

	public void update(ItemVariant itemVariant) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.update(itemVariant);
		session.flush();
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
		String hql = "from ItemVariant";
		List<ItemVariant> list = session.createQuery(hql).list();
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
