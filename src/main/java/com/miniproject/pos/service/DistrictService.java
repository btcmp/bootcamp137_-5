package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.DistrictDao;
import com.miniproject.pos.model.District;

@Service
@Transactional
public class DistrictService {

	@Autowired
	DistrictDao districtDao;

	public List<District> selectAll() {
		// TODO Auto-generated method stub
		return districtDao.selectAll();
	}
	
	public void save(District district) {
		districtDao.save(district);
	}
	
	public void delete(District district) {
		districtDao.delete(district);
	}
	
	public void update(District district) {
		districtDao.update(district);
	}
	
	public District getOne(String id) {
		return districtDao.getOne(id);
	}

	public List<District> getDistrictByIdRegion(String id) {
		// TODO Auto-generated method stub
		return districtDao.getDistrictByIdRegion(id);
	}
	
}

