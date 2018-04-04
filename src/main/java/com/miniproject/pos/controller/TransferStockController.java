package com.miniproject.pos.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniproject.pos.model.TransferStock;
import com.miniproject.pos.service.OutletService;
import com.miniproject.pos.service.TransferStockSevice;
import com.miniproject.pos.utils.ResponseMessage;

@Controller
@RequestMapping("/transfer-stock")
public class TransferStockController {

	@Autowired
	TransferStockSevice tss;
	
	@Autowired
	OutletService os;
	
	@Autowired 
	private HttpSession httpSession;
	
	@RequestMapping("/index")
	public String index(Model model) {
		String outletId = httpSession.getAttribute("outletId").toString();
		model.addAttribute("outletId", os.getOne(outletId));
		model.addAttribute("title", "Data Transfer Stock");
		model.addAttribute("outlet", os.selectAll());
		return "transfer-stock/index";
	}
	
	@RequestMapping("/get-all-data")
	@ResponseBody
	public ResponseMessage getAll() {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		rm.setData(tss.getAllTransferStock());
		return rm;
	}
	
	@RequestMapping("/get-transfer-detail/{id}")
	@ResponseBody
	public ResponseMessage getone(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		rm.setData(tss.getTransferStockById(id));
		return rm;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(@RequestBody TransferStock ts) {
		ResponseMessage rm = new ResponseMessage();
		String outletId = httpSession.getAttribute("outletId").toString();
		String userId = httpSession.getAttribute("userId").toString();
		tss.save(ts, outletId, userId);
		rm.setStatus("success");
		rm.setKeterangan("Data Berhasil Disimpan");
		return rm;
	}
	
	@RequestMapping(value="/status/{id}/{status}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(@PathVariable("id") String id, @PathVariable("status") String status) {
		ResponseMessage rm = new ResponseMessage();
		TransferStock ts = tss.getTransferStock(id);
		ts.setStatus(status);
		String userId = httpSession.getAttribute("userId").toString();
		tss.update(ts, userId);
		rm.setStatus("success");
		rm.setKeterangan("Status berhasil diubah");
		return rm;
	}
}
