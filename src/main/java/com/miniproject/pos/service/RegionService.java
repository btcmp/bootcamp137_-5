package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.RegionDao;
import com.miniproject.pos.model.Region;

@Service
@Transactional
public class RegionService {

	@Autowired
	RegionDao regionDao;

	public List<Region> selectAll() {
		// TODO Auto-generated method stub
		return regionDao.selectAll();
	}

	public List<Region> getRegionsByIdProvinsi(String idProvinsi) {
		// TODO Auto-generated method stub
		return regionDao.getRegionsByIdPrvinsi(idProvinsi);
	}
	
	public void save(Region region) {
		regionDao.save(region);
	}
	
	public void delete(Region region) {
		regionDao.delete(region);
	}
	
	public void update(Region region) {
		regionDao.update(region);
	}
	
	public Region getOne(String id) {
		return regionDao.getOne(id);
	}
}
