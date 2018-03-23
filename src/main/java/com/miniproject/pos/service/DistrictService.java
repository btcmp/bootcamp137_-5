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
}
