package com.miniproject.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniproject.pos.service.ItemVariantService;
import com.miniproject.pos.service.ItemsService;
import com.miniproject.pos.service.KategoriService;
import com.miniproject.pos.utils.ResponseMessage;

@Controller
@RequestMapping("/items")
public class ItemsController {

	@Autowired
	ItemsService itemsService;
	
	@Autowired
	ItemVariantService itemVariantService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		KategoriService ks = new KategoriService();
		/*model.addAttribute("category", ks.getAllKategori());*/
		model.addAttribute("title", "Data Items");
		return "items/index";
	}
	
	@RequestMapping("/get-all-data")
	@ResponseBody
	public ResponseMessage getAllData() {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		rm.setData(itemVariantService.getAllItemVariant());
		return rm;
	}
}
