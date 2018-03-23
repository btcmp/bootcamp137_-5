package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.Region;

public interface RegionDao {

	List<Region> selectAll();

	List<Region> getRegionsByIdPrvinsi(String idProvinsi);

}
