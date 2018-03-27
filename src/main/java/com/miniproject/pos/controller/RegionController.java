package com.miniproject.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniproject.pos.model.Region;
import com.miniproject.pos.service.RegionService;

@Controller
@RequestMapping("/region")
public class RegionController {

	@Autowired
	RegionService regionService;
	
	@RequestMapping(value="/get-region", method=RequestMethod.GET)
	@ResponseBody
	public List<Region> getRegionByIdProvinsi(@RequestParam(value="id", defaultValue="") String id, Model model){
		List<Region> regions = regionService.getRegionsByIdProvinsi(id);
		System.out.println("id: "+id);
		return regions;
	}
}
