package com.miniproject.pos.service;

import java.util.ArrayList;
import java.util.List;

import com.miniproject.pos.model.Kategori;

public class KategoriService {

	
	public List<Kategori> getAllKategori(){
		List<Kategori> lKategori = new ArrayList<Kategori>();
		Kategori kat = new Kategori();
		kat.setId("xx3324328");
		kat.setName("Kendaraan Roda 2");
		Kategori kat2 = new Kategori();
		kat2.setId("xx3324444");
		kat2.setName("Kendaraan Roda 4");
		lKategori.add(kat);
		lKategori.add(kat2);
		return lKategori;
	}
}
