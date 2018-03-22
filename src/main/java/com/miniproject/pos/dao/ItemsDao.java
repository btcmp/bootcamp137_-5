package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.Items;

public interface ItemsDao {

	public void save(Items items);
	
	public void update(Items items);
	
	public void delete(Items items);
	
	public Items getItemsById(String id);
	
	public List<Items> getAllItems();
}
