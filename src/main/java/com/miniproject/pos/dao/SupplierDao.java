package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.Supplier;

public interface SupplierDao {

	public void save(Supplier supplier);
	
	public void delete(Supplier supplier);
	
	public void update(Supplier supplier);
	
	public Supplier getOne(String id);
	
	public List<Supplier> selectAlll();

	public List<Supplier> getSupplierBySearchName(String search);

	public List<Supplier> getAll();
}
