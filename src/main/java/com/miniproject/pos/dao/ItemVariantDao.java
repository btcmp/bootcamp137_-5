package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.ItemVariant;

public interface ItemVariantDao {

	public void save(ItemVariant itemVariant);
	
	public void update(ItemVariant itemVariant);
	
	public void delete(ItemVariant itemVariant);
	
	public ItemVariant getItemVariantById(String id);
	
	public List<ItemVariant> getAllItemVariant();
}
