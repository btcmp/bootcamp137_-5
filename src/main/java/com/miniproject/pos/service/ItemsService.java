package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.ItemsDao;
import com.miniproject.pos.model.Items;

@Service
@Transactional
public class ItemsService {

	@Autowired
	ItemsDao itemsDao;
	
	public void save(Items items) {
		itemsDao.save(items);
	}
	
	public void update(Items items) {
		itemsDao.update(items);
	}
	
	public void delete(Items items) {
		itemsDao.delete(items);
	}
	
	public Items getItemsById(String id) {
		return itemsDao.getItemsById(id);
	}
	
	public List<Items> getAllItems(){
		return itemsDao.getAllItems();
	}
}
