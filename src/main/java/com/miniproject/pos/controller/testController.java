package com.miniproject.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tes-controller")
public class testController {
	
	@RequestMapping
	public String index() {
		return "tes-controller";
	}
	
}
