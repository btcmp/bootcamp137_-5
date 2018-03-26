package com.miniproject.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.miniproject.pos.model.Items;
import com.miniproject.pos.model.Kategori;
import com.miniproject.pos.service.KategoriService;

@Controller
@RequestMapping("/kategori")
public class KategoriController {

	@Autowired
	KategoriService kategoriService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		List<Kategori> kat = kategoriService.selectAll();
		model.addAttribute("kats", kat);
		return "kategori/index";
	}
	
		
	@RequestMapping(value="/save", method= RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody Kategori kategori) {
		kategoriService.save(kategori);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody Kategori kategori) {
		kategoriService.update(kategori);
	}
	
	@RequestMapping(value="/get-id/{id}", method= RequestMethod.GET)
	@ResponseBody
	public Kategori getOne(@PathVariable String id) {
		return kategoriService.getOne(id);
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	@ResponseBody
	public void delete(@PathVariable String id) {
		Kategori kat = new Kategori();
		kat.setId(id);
		kategoriService.delete(kat);
	}
}
