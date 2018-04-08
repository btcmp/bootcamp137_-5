package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.utils.UniqueException;

public interface ItemVariantDao {

	public void save(ItemVariant itemVariant)throws UniqueException;
	
	public void update(ItemVariant itemVariant);
	
	public void delete(ItemVariant itemVariant);
	
	public ItemVariant getItemVariantById(String id);
	
	public List<ItemVariant> getAllItemVariant();
	
	public List<ItemVariant> getAllItemVariant(String id);
	
	public List<ItemVariant> getItemVariantByItem(String itemId);
	
	public void nonActiveVariant(List<String> idVariants, String itemId);
}
