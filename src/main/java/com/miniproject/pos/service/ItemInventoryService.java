package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.ItemInventoryDao;
import com.miniproject.pos.model.ItemInventory;

@Service
@Transactional
public class ItemInventoryService {

	@Autowired
	ItemInventoryDao iiDao;
	
	public List<ItemInventory> getAllItemInventory(){
		return iiDao.getAllInventory();
	}
	
	public List<ItemInventory> getItemInventoryByIdItems(String id){
		return iiDao.getInventoryByIdItems(id);
	}
	
	public List<ItemInventory> getInventoryAll(String outletId){
		return iiDao.getInventoryAll(outletId);
	}
	
	public List<ItemInventory> getInventory(String id){
		return iiDao.getInventory(id);
	}
	
	public Long getCountRedStock(String outletId) {
		return iiDao.getCountRedStock(outletId);
	}
}
