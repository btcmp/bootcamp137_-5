package com.miniproject.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miniproject.pos.service.ItemsService;

@Controller
@RequestMapping("/items")
public class ItemsController {

	@Autowired
	ItemsService itemsService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("title", "Data Items");
		model.addAttribute("items", itemsService.getAllItems());
		return "items/index";
	}
}
