package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.utils.Constants;

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
	
	public ItemInventory getInventoryByVariantId(String idVariant, String outlet) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select ii from ItemInventory  as ii where ii.variantId.id=:id and ii.outletId.id=:outlet";
		List<ItemInventory> list = session.createQuery(hql).setParameter("id", idVariant).setParameter("outlet", outlet).list();
		if(! list.isEmpty()) {
			return list.get(0);
		}
		return null;
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
		String hql = "select ii from ItemInventory as ii inner join ii.variantId as variant inner join variant.itemId as item where item.id=:id and item.active=:active and variant.active=:active";
		List<ItemInventory> list = session.createQuery(hql).setParameter("id", id).setParameter("active", Constants.ACTIVE).list();
		return list;
	}
	
	public List<ItemInventory> getInventoryAll(String outletId) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select ii from ItemInventory as ii inner join ii.variantId as variant inner join variant.itemId as item where ii.outletId.id=:outletid and item.active=:active and variant.active=:active";
		List<ItemInventory> list = session.createQuery(hql).setParameter("active", Constants.ACTIVE).setParameter("outletid", outletId).list();
		return list;
	}
	
	public List<ItemInventory> getInventory(String id) {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		String hql = "select ii from ItemInventory as ii inner join ii.variantId as variant inner join variant.itemId as item where item.active=:active and variant.active=:active and ii.id=:id";
		List<ItemInventory> list = session.createQuery(hql).setParameter("id", id).setParameter("active", Constants.ACTIVE).list();
		return list;
	}
	
	public Long getTotalStockByIdVariant(String idVariant) {
		Session session = sf.getCurrentSession();
		String sql = "select sum(ii.endingQty) from ItemInventory as ii where ii.variantId.id=:id";
		List<Long> list = session.createQuery(sql).setParameter("id", idVariant).list();
		return list.get(0);
	}
	
	public Long getTotalStockByIdItems(String idItems) {
		Session session = sf.getCurrentSession();
		String sql = "select sum(ii.endingQty) from ItemInventory as ii join ii.variantId as variant where variant.itemId.id=:id";
		List<Long> list = session.createQuery(sql).setParameter("id", idItems).list();
		return list.get(0);
	}

}
