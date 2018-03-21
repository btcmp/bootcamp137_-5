package com.miniproject.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-controller")
public class testController {

	@RequestMapping
	public String index(Model model) {
		model.addAttribute("title", "coba title");
		return "test/index";
	}
}
