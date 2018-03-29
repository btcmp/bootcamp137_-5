package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.ItemInventory;

@Repository
public class ItemInventoryDaoImpl implements ItemInventoryDao {

	@Autowired
	SessionFactory sf;
	
	public void save(ItemInventory itemInventory) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.save(itemInventory);
		session.flush();
	}

	public void update(ItemInventory itemInventory) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.update(itemInventory);
		session.flush();
	}

	public void delete(ItemInventory itemInventory) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		session.delete(itemInventory);
		session.flush();
	}

	public ItemInventory getIventoryById(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		return session.get(ItemInventory.class, id);
	}

	public void updateStock(ItemInventory ii) {
		// TODO Auto-generated method stub
		
		this.update(ii);
	}
	
	public ItemInventory getInventoryByVariantId(String idVariant) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select ii from ItemInventory  as ii where ii.variantId.id=:id";
		ItemInventory ii = (ItemInventory) session.createQuery(hql).setParameter("id", idVariant).list().get(0);
		return ii;
	}
	
	public List<ItemInventory> getAllInventory() {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select ii from ItemInventory as ii inner join ii.variantId as variant inner join variant.itemId as item";
		List<ItemInventory> list = session.createQuery(hql).list();
		return list;
	}
	
	public List<ItemInventory> getInventoryByIdItems(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select ii from ItemInventory as ii inner join ii.variantId as variant inner join variant.itemId as item where item.id=:id and item.active=1 and variant.active=1";
		List<ItemInventory> list = session.createQuery(hql).setParameter("id", id).list();
		return list;
	}
	
	public List<ItemInventory> getInventoryAll() {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select ii from ItemInventory as ii inner join ii.variantId as variant inner join variant.itemId as item where item.active=1 and variant.active=1";
		List<ItemInventory> list = session.createQuery(hql).list();
		return list;
	}
	
	public List<ItemInventory> getInventory(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select ii from ItemInventory as ii inner join ii.variantId as variant inner join variant.itemId as item where item.active=1 and variant.active=1 and ii.id=:id";
		List<ItemInventory> list = session.createQuery(hql).setParameter("id", id).list();
		return list;
	}

}
