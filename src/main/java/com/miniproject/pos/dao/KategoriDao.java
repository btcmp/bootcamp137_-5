package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.Kategori;

public interface KategoriDao {

	public void save(Kategori kategori);
	
	public void delete(Kategori kategori);
	
	public void update(Kategori kategori);
	
	public Kategori getOne(String id);
	
	public List<Kategori> selectAll();
}
