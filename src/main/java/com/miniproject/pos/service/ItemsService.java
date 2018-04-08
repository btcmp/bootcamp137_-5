package com.miniproject.pos.service;

import java.util.ArrayList;
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
import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.model.User;
import com.miniproject.pos.utils.Constants;
import com.miniproject.pos.utils.UniqueException;

@Service
@Transactional
public class ItemsService {

	@Autowired
	ItemsDao itemsDao;
	
	@Autowired
	ItemVariantDao ivDao;
	
	@Autowired
	ItemInventoryDao iiDao;
	
	public void save(Items items, User user, Outlet outlet) throws UniqueException{
		List<ItemVariant> itemVariants = items.getVariants();
		items.setVariants(null);
		items.setCreatedBy(user);
		itemsDao.save(items);
		ItemInventory itemI;
		for(ItemVariant iv : itemVariants) {
			itemI = iv.getInventory().get(0);
			iv.setInventory(null);
			iv.setItemId(items);
			iv.setCreatedBy(user);
			ivDao.save(iv);
			itemI.setVariantId(iv);
			itemI.setEndingQty(itemI.getBegining());
			itemI.setCreatedBy(user);
			itemI.setOutletId(outlet);
			iiDao.save(itemI);
		}
	}
	
	public void update(Items items, User user, Outlet outlet) throws UniqueException{
		List<ItemVariant> itemVariants = items.getVariants();
		items.setVariants(null);
		items.setModifiedBy(user);
		itemsDao.update(items);
		ItemInventory itemI = null;
		List<String> idVariants = new ArrayList();
		for(ItemVariant iv : itemVariants) {
			itemI = iv.getInventory().get(0);
			iv.setInventory(null);
			iv.setItemId(items);
			if(iv.getId() == null) {
				iv.setCreatedBy(user);
				ivDao.save(iv);
				idVariants.add(iv.getId());
				itemI.setVariantId(iv);
				itemI.setEndingQty(itemI.getBegining());
				itemI.setCreatedBy(user);
				itemI.setOutletId(outlet);
				iiDao.save(itemI);
			}else {
				iv.setModifiedBy(user);
				ivDao.update(iv);
				idVariants.add(iv.getId());
				if(itemI != null) {
					if(itemI.getId() == null) {
						itemI.setVariantId(iv);
						itemI.setEndingQty(itemI.getBegining());
						itemI.setCreatedBy(user);
						itemI.setOutletId(outlet);
						iiDao.save(itemI);
					}else {
						itemI.setVariantId(iv);
						itemI.setModifiedBy(user);
						iiDao.update(itemI);
					}
				}
			}
		}
		ivDao.nonActiveVariant(idVariants, items.getId());
	}
	
	public void delete(Items items) {
		itemsDao.delete(items);
	}
	
	public boolean nonActiveItems(Items items) {
		if(iiDao.getTotalStockByIdItems(items.getId()) <= 0) {
			items.setActive(Constants.NONACTIVE);
			itemsDao.update(items);
			return true;
		}
		return false;
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
