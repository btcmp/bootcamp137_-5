package com.miniproject.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniproject.pos.service.AdjustmentService;
import com.miniproject.pos.utils.ResponseMessage;

@Controller
@RequestMapping("/adjustment")
public class AdjustmentController {

	@Autowired
	AdjustmentService as;
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("title", "Data Adjustment");
		return "adjustment/index";
	}
	
	@RequestMapping("get-all-data")
	@ResponseBody
	public ResponseMessage getAll() {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		rm.setData(as.getAllAdjustment());
		return rm;
	}
}
