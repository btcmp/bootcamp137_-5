package com.miniproject.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-controller")
public class TestController {
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("title", "Coba Title");
		return "test/index";
	}
	
}
