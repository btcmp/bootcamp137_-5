package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.Region;

public interface RegionDao {

	List<Region> selectAll();
	
	public void save(Region region);
	
	public void delete(Region region);
	
	public void update(Region region);
	
	public Region getOne(String id);

	List<Region> getRegionsByIdPrvinsi(String idProvinsi);

}
