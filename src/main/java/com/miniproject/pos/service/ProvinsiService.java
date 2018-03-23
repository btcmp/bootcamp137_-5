package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.ProvinsiDao;
import com.miniproject.pos.model.Provinsi;

@Service
@Transactional
public class ProvinsiService {

	@Autowired
	ProvinsiDao provinsiDao;

	public List<Provinsi> selectAll() {
		// TODO Auto-generated method stub
		return provinsiDao.selectAll();
	}
	
}
