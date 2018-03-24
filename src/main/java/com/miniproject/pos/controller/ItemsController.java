package com.miniproject.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.model.Items;
import com.miniproject.pos.service.ItemInventoryService;
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
	
	@Autowired
	ItemInventoryService itemInventoryService;
	
	@Autowired
	KategoriService ks;
	
	@RequestMapping("/index")
	public String index(Model model) {

		model.addAttribute("category", ks.selectAll());
		model.addAttribute("title", "Data Items");
		return "items/index";
	}
	
	@RequestMapping("/get-all-data")
	@ResponseBody
	public ResponseMessage getAllData() {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		rm.setData(itemInventoryService.getAllItemInventory());
		return rm;
	}
	
	@RequestMapping("/get-one/{id}")
	@ResponseBody
	public ResponseMessage getOne(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		rm.setData(itemInventoryService.getItemInventoryByIdItems(id));
		return rm;
	}
	
	@RequestMapping(value="/delete-variant/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseMessage deleteVariant(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		ItemVariant iv = itemVariantService.getItemVariantById(id);
		iv.setActive(false);
		itemVariantService.update(iv);
		rm.setKeterangan("Data berhasil dihapus");
		return rm;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(@RequestBody Items items) {
		ResponseMessage rm = new ResponseMessage();
		itemsService.save(items);
		rm.setStatus("success");
		rm.setKeterangan("Data Berhasil Disimpan");
		return rm;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseMessage update(@RequestBody Items items) {
		ResponseMessage rm = new ResponseMessage();
		itemsService.update(items);
		rm.setStatus("success");
		rm.setKeterangan("Data Berhasil Disimpan");
		return rm;
	}
}
