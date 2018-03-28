package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.ItemInventoryDao;
import com.miniproject.pos.dao.ItemVariantDao;
import com.miniproject.pos.model.ItemVariant;

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
	
	public void delete(ItemVariant itemVariant) {
		itemVariantDao.delete(itemVariant);
	}
	
	public ItemVariant getItemVariantById(String id) {
		return itemVariantDao.getItemVariantById(id);
	}
	
	public List<ItemVariant> getAllItemVariant(){
		List<ItemVariant> list = itemVariantDao.getAllItemVariant();
		return list;
	}
}
