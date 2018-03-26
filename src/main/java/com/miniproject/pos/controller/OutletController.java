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

import com.miniproject.pos.model.District;
import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.model.Provinsi;
import com.miniproject.pos.model.Region;
import com.miniproject.pos.service.DistrictService;
import com.miniproject.pos.service.OutletService;
import com.miniproject.pos.service.ProvinsiService;
import com.miniproject.pos.service.RegionService;

@Controller
@RequestMapping("/outlet")
public class OutletController {

	@Autowired
	OutletService outletService;
	
	@Autowired
	ProvinsiService provinsiService;
	
	@Autowired
	RegionService regionService;
	
	@Autowired
	DistrictService districtService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		List<Outlet> outlets = outletService.selectAll();
		List<Provinsi> provinsi = provinsiService.selectAll();
		List<Region> region = regionService.selectAll();
		List<District> district = districtService.selectAll();
		model.addAttribute("outlets", outlets);
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("regions", region);
		model.addAttribute("districts", district);
		return "outlet/index";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody Outlet outlet) {
		outletService.save(outlet);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody Outlet outlet) {
		outletService.update(outlet);
	}
	
	@RequestMapping(value="/nonactive", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void nonActiveOutlet(@RequestBody Outlet outlet) {
		outletService.nonActiveOutlet(outlet);
	}
	
	@RequestMapping(value="/get-id/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Outlet getOne(@PathVariable String id) {
		return outletService.getOne(id);
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	@ResponseBody
	public void delete(@PathVariable String id) {
		Outlet otl = new Outlet();
		otl.setId(id);
		outletService.delete(otl);
	}
	
}
