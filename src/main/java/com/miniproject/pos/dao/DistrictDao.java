package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.District;

public interface DistrictDao {

	public List<District> selectAll();
	
	public void save (District district);
	
	public void delete(District district);
	
	public void update(District district);
	
	public District getOne(String id);

	List<District> getDistrictByIdRegion(String id);

}
