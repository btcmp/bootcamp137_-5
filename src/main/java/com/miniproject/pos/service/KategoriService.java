package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.ItemsDao;
import com.miniproject.pos.dao.KategoriDao;
import com.miniproject.pos.model.Items;
import com.miniproject.pos.model.Kategori;

@Service
@Transactional
public class KategoriService {

	@Autowired
	KategoriDao kategoriDao;
	
	@Autowired
	ItemsDao itemsDao;
	
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
		List<Kategori> kategoris = kategoriDao.selectAll();
		for(Kategori kategori : kategoris) {
			List<Items> items = itemsDao.getItemByKategori(kategori);
			if(items == null) {
				kategori.setItemStock(0);
			}else
			kategori.setItemStock(items.size());
			System.out.println(kategori.getName()+" item= "+ kategori.getItemStock());
		}
		
		return kategoris;
	}
	
	public List<Kategori> getAll(){
		return kategoriDao.selectAll();
	}
}
