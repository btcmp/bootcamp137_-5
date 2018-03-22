package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.KategoriDao;
import com.miniproject.pos.model.Kategori;

@Service
@Transactional
public class KategoriService {

	@Autowired
	KategoriDao kategoriDao;
	
	public void save(Kategori kategori) {
		kategoriDao.save(kategori);
	}
	
	public void delete(Kategori kategori) {
		kategoriDao.delete(kategori);
	}
	
	public void update(Kategori kategori) {
		kategoriDao.update(kategori);
	}
	
	public Kategori getOne(String id) {
		return kategoriDao.getOne(id);
	}
	
	public List<Kategori> selectAll(){
		return kategoriDao.selectAll();
	}
}
