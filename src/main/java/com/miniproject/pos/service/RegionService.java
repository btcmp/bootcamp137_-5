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

	public List<Region> getRRegionsByIdProvinsi(String idProvinsi) {
		// TODO Auto-generated method stub
		return regionDao.getRegionsByIdPrvinsi(idProvinsi);
	}
}
