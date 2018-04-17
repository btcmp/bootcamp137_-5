package com.miniproject.pos.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.miniproject.pos.model.Items;
import com.miniproject.pos.model.Kategori;
import com.miniproject.pos.model.User;
import com.miniproject.pos.service.KategoriService;

@Controller
@RequestMapping("/kategori")
public class KategoriController {

	@Autowired
	KategoriService kategoriService;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping("/index")
	public String index(Model model) {
		List<Kategori> kat = kategoriService.selectAll();
		model.addAttribute("kats", kat);
		return "kategori/index";
	}
	
	@RequestMapping(value="/index/src", method = RequestMethod.GET)
	public String indexBySearchName(@RequestParam(value="search", defaultValue="") String search, Model model) {
		List<Kategori> kat = kategoriService.getKategoriBySearchName(search);
		model.addAttribute("kats", kat);
		return "kategori/index";
	}
	
	@RequestMapping(value="/save", method= RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody Kategori kategori) {
		User user = new User();
		user.setId(httpSession.getAttribute("userId").toString());
		kategoriService.save(kategori, user);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody Kategori kategori) {
		User user = new User();
		user.setId(httpSession.getAttribute("userId").toString());
		kategoriService.update(kategori, user);
	}
	
	@RequestMapping(value="/deactive", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void deactive(@RequestBody Kategori kategori) {
		User user = new User();
		user.setId(httpSession.getAttribute("userId").toString());
		kategoriService.deactive(kategori, user);
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
	
	@RequestMapping(value="/get-all-name", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAllName(){
		List<String> listname = new ArrayList<String>();
		List<Kategori> kat = kategoriService.selectAll();
		for (Kategori kategori : kat) {
			listname.add(kategori.getName());
		}
		return listname;
	}
	
	@RequestMapping(value="/get-all", method = RequestMethod.GET)
	@ResponseBody
	public List<Kategori> getAllKategori(){
		List<Kategori> listkat = kategoriService.getAllKategori();
		return listkat;
	}
}

