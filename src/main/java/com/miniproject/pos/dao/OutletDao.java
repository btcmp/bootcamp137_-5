package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.Outlet;

public interface OutletDao {

	public void save(Outlet outlet);
	
	public void delete(Outlet outlet);
	
	public void update(Outlet outlet);
	
	public Outlet getOne(String id);
	
	public List<Outlet> selectAll();
}
