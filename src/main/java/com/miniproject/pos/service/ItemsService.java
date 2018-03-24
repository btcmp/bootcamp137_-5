package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.ItemInventoryDao;
import com.miniproject.pos.dao.ItemVariantDao;
import com.miniproject.pos.dao.ItemsDao;
import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.model.Items;

@Service
@Transactional
public class ItemsService {

	@Autowired
	ItemsDao itemsDao;
	
	@Autowired
	ItemVariantDao ivDao;
	
	@Autowired
	ItemInventoryDao iiDao;
	
	public void save(Items items) {
		List<ItemVariant> itemVariants = items.getVariants();
		items.setVariants(null);
		itemsDao.save(items);
		ItemInventory itemI;
		for(ItemVariant iv : itemVariants) {
			itemI = iv.getInventory().get(0);
			iv.setInventory(null);
			iv.setItemId(items);
			ivDao.save(iv);
			itemI.setVariantId(iv);
			iiDao.save(itemI);
		}
	}
	
	public void update(Items items) {
		List<ItemVariant> itemVariants = items.getVariants();
		items.setVariants(null);
		itemsDao.update(items);
		ItemInventory itemI;
		for(ItemVariant iv : itemVariants) {
			itemI = iv.getInventory().get(0);
			iv.setInventory(null);
			iv.setItemId(items);
			if(iv.getId() == null) {
				ivDao.save(iv);
				itemI.setVariantId(iv);
				iiDao.save(itemI);
			}else {
				ivDao.update(iv);
				itemI.setVariantId(iv);
				iiDao.update(itemI);
			}
		}
	}
	
	public void delete(Items items) {
		itemsDao.delete(items);
	}
	
	public Items getItemsById(String id) {
		Items items =  itemsDao.getItemsById(id);
		/*List<ItemVariant> listVariant = ivDao.getItemVariantByItem(items.getId());
		items.setVariants(listVariant);*/
		return items;
	}
	
	public List<Items> getAllItems(){
		return itemsDao.getAllItems();
	}
}
