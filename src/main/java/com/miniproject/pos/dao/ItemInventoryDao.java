package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.ItemInventory;

public interface ItemInventoryDao {

	public void save(ItemInventory itemInventory);
	
	public void update(ItemInventory itemInventory);
	
	public void delete(ItemInventory itemInventory);
	
	public ItemInventory getIventoryById(String id);
	
	public List<ItemInventory> getAllInventory();
	
	public List<ItemInventory> getInventoryByIdItems(String id);
	
	public List<ItemInventory> getInventoryAll();
	
	public List<ItemInventory> getInventory(String id);
	
	public ItemInventory getInventoryByVariantId(String idVariant) ;
	
	public void updateStock(ItemInventory ii);
}
