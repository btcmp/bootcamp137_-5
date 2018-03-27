package com.miniproject.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

	
	@RequestMapping
	public String index() {
		return "page-transaction-po/po-page";
	}
	
}
