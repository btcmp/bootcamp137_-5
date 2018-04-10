package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.Items;
import com.miniproject.pos.model.Kategori;
import com.miniproject.pos.utils.UniqueException;

public interface ItemsDao {

	public void save(Items items) throws UniqueException;
	
	public void update(Items items) throws UniqueException;
	
	public void delete(Items items);
	
	public Items getItemsById(String id);
	
	public List<Items> getAllItems();

	public List<Items> getItemByKategori(Kategori kategori);
}
