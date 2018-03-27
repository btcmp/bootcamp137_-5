package com.miniproject.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniproject.pos.model.Adjustment;
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
	
	@RequestMapping("get-adjustment-detail/{id}")
	@ResponseBody
	public ResponseMessage getAdjustmentDetail(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		rm.setData(as.getAdjustmentById(id));
		return rm;
	}
	
	@RequestMapping("get-adjustment/{id}")
	@ResponseBody
	public ResponseMessage getAdjustment(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		rm.setData(as.getAdjustmentById(id));
		return rm;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(@RequestBody Adjustment adjustment) {
		ResponseMessage rm = new ResponseMessage();
		as.save(adjustment);
		rm.setStatus("success");
		rm.setKeterangan("Data Berhasil Disimpan");
		return rm;
	}
	
	@RequestMapping(value="/status/{id}/{status}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(@PathVariable("id") String id, @PathVariable("status") String status) {
		ResponseMessage rm = new ResponseMessage();
		Adjustment adj = as.getAdjustment(id);
		adj.setStatus(status);
		as.update(adj);
		rm.setStatus("success");
		rm.setKeterangan("Data Berhasil Disimpan");
		return rm;
	}
}
