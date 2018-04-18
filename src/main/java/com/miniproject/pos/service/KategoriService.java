package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.ItemsDao;
import com.miniproject.pos.dao.KategoriDao;
import com.miniproject.pos.model.Items;
import com.miniproject.pos.model.Kategori;
import com.miniproject.pos.model.User;

@Service
@Transactional
public class KategoriService {

	@Autowired
	KategoriDao kategoriDao;
	
	@Autowired
	ItemsDao itemsDao;
	
	public void save(Kategori kategori, User user) {
		Kategori kat = new Kategori();
		kat.setId(kategori.getId());
		kat.setCreatedBy(user);
		kat.setCreatedOn(kategori.getCreatedOn());
		kat.setItemStock(kategori.getItemStock());
		kat.setModifiedOn(kategori.getModifiedOn());
		kat.setName(kategori.getName());
		kat.setItems(kategori.getItems());
		kat.setActive(true);
		kategoriDao.save(kat);
	}
	
	public void delete(Kategori kategori) {
		kategoriDao.delete(kategori);
	}
	
	public void update(Kategori kategori, User user) {
		Kategori kats = new Kategori();
		kats.setId(kategori.getId());
		kats.setName(kategori.getName());
		kats.setCreatedBy(kategori.getCreatedBy());
		kats.setCreatedOn(kategori.getCreatedOn());
		kats.setModifiedOn(kategori.getModifiedOn());
		kats.setModifiedBy(user);
		kats.setActive(true);
		kategoriDao.update(kats);
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
		}
		
		return kategoris;
	}
	
	public List<Kategori> getKategoriBySearchName(String search) {
		// TODO Auto-generated method stub
		List<Kategori> kategoris = kategoriDao.getKategoriBySearchName(search);
		if(kategoris == null) {
			return null;
		}else {
		for (Kategori kategori : kategoris) {
			List<Items> items = itemsDao.getItemByKategori(kategori);
			if(items == null) {
				kategori.setItemStock(0);
			}else
			kategori.setItemStock(items.size());
		}
		return kategoris;}
	}

	public List<Kategori> getAllKategori() {
		// TODO Auto-generated method stub
		return kategoriDao.getAllKategori();
	}

	public void deactive(Kategori kategori, User user) {
		// TODO Auto-generated method stub
		Kategori kats = new Kategori();
		kats.setId(kategori.getId());
		kats.setName(kategori.getName());
		kats.setCreatedBy(kategori.getCreatedBy());
		kats.setCreatedOn(kategori.getCreatedOn());
		kats.setModifiedOn(kategori.getModifiedOn());
		kats.setModifiedBy(user);
		kats.setActive(false);
		kategoriDao.update(kats);
		
	}
}
