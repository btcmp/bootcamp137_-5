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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.miniproject.pos.model.District;
import com.miniproject.pos.model.Provinsi;
import com.miniproject.pos.model.Region;
import com.miniproject.pos.model.Supplier;
import com.miniproject.pos.service.DistrictService;
import com.miniproject.pos.service.ProvinsiService;
import com.miniproject.pos.service.RegionService;
import com.miniproject.pos.service.SupplierService;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	SupplierService supplierService;
	
	@Autowired
	ProvinsiService provinsiService;
	
	@Autowired
	RegionService regionService;
	
	@Autowired
	DistrictService districtService;
	
	@RequestMapping(value="/index")
	public String index(Model model) {
		List<Supplier> sups = supplierService.selectAll();
		List<Provinsi> prov = provinsiService.selectAll();
		List<Region> reg = regionService.selectAll();
		List<District> dis = districtService.selectAll();
		model.addAttribute("sups", sups);
		model.addAttribute("provs", prov);
		model.addAttribute("regs", reg);
		model.addAttribute("diss", dis);
		return "supplier/index";
	}
	
	@RequestMapping(value="/index/src", method = RequestMethod.GET)
	public String indexSearchByName(@RequestParam (value="search", defaultValue="")String search, Model model) {
		List<Supplier> sups = supplierService.getSupplierBySearchName(search);
		List<Provinsi> prov = provinsiService.selectAll();
		List<Region> reg = regionService.selectAll();
		List<District> dis = districtService.selectAll();
		model.addAttribute("sups", sups);
		model.addAttribute("provs", prov);
		model.addAttribute("regs", reg);
		model.addAttribute("diss", dis);
		return "supplier/index";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void save (@RequestBody Supplier supplier) {
		supplierService.save(supplier);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody Supplier supplier) {
		supplierService.update(supplier);
	}
	
	@RequestMapping(value="/get-id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Supplier getOne(@PathVariable String id, Model model) {
		List<Provinsi> prov = provinsiService.selectAll();
		List<Region> reg = regionService.selectAll();
		List<District> dis = districtService.selectAll();
		model.addAttribute("provs", prov);
		model.addAttribute("regs", reg);
		model.addAttribute("diss", dis);
		return supplierService.getOne(id);
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void delete(@PathVariable String id) {
		Supplier sup = new Supplier();
		sup.setId(id);
		supplierService.delete(sup);
	}
	
	@RequestMapping(value="/nonactive", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void nonactive(@RequestBody Supplier supplier) {
		supplierService.nonactive(supplier);
	}
	
}
