package com.miniproject.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniproject.pos.model.District;
import com.miniproject.pos.service.DistrictService;

@Controller
@RequestMapping("/kecamatan")
public class DistrictController {

	@Autowired
	DistrictService districtService;
	
	@RequestMapping(value="/get-kecamatan", method = RequestMethod.GET)
	@ResponseBody
	public List<District> getDistrictByIdRegion(@RequestParam(value="id", defaultValue="") String id, Model model){
		List<District> districts = districtService.getDistrictByIdRegion(id);
		return districts;
	}
	
	
}
