package com.miniproject.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchase-request")
public class PurchaseRequestController {

	
	@RequestMapping
	public String index() {
		return "page-transaction-pr/pr-page";
	}
	
}
