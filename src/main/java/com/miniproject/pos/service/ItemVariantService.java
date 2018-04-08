package com.miniproject.pos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.ItemInventoryDao;
import com.miniproject.pos.dao.ItemVariantDao;
import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.utils.Constants;

@Service
@Transactional
public class ItemVariantService {

	@Autowired
	ItemVariantDao itemVariantDao;
	
	@Autowired
	ItemInventoryDao itemInventoryDao;
	
	public void save(ItemVariant itemVariant) {
		itemVariantDao.save(itemVariant);
	}
	
	public void update(ItemVariant itemVariant) {
		itemVariantDao.update(itemVariant);
	}
	
	public boolean nonActiveVariant(ItemVariant iv) {
		if(itemInventoryDao.getTotalStockByIdVariant(iv.getId()) <= 0) {
			iv.setActive(Constants.NONACTIVE);
			itemVariantDao.update(iv);
			return true;
		}
		return false;
	}
	
	public void delete(ItemVariant itemVariant) {
		itemVariantDao.delete(itemVariant);
	}
	
	public ItemVariant getItemVariantById(String id) {
		return itemVariantDao.getItemVariantById(id);
	}
		
	public List<ItemVariant> getAllItemVariant(String outletId){
		List<ItemVariant> list = itemVariantDao.getAllItemVariant();
		for(ItemVariant iv:list) {
			ItemInventory ii = itemInventoryDao.getInventoryByVariantId(iv.getId(), outletId);
			iv.setInventory(null);
			iv.singleInventorySet(ii);
		}
		return list;
	}
	
	public List<ItemVariant> getAllItemVariant(String outletId, String id){
		List<ItemVariant> list = itemVariantDao.getAllItemVariant(id);
		for(ItemVariant iv:list) {
			ItemInventory ii = itemInventoryDao.getInventoryByVariantId(iv.getId(), outletId);
			iv.setInventory(null);
			iv.singleInventorySet(ii);
		}
		return list;
	}
	
	public List<ItemVariant> getItemVariantByItem(String itemId, String outletId) {
		List<ItemVariant> list = itemVariantDao.getItemVariantByItem(itemId);
		boolean removeItems = (itemInventoryDao.getTotalStockByIdItems(itemId) <= 0)?true:false;
		for(ItemVariant iv:list) {
			ItemInventory ii = itemInventoryDao.getInventoryByVariantId(iv.getId(), outletId);
			if(itemInventoryDao.getTotalStockByIdVariant(iv.getId()) <= 0) {
				iv.setRemoveable(true);
			}
			iv.getItemId().setRemoveable(removeItems);
			iv.setInventory(null);
			iv.singleInventorySet(ii);
		}
		return list;
	}
}
