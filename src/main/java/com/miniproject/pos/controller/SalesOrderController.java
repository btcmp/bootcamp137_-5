package com.miniproject.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.miniproject.pos.model.Customer;
import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.model.Provinsi;
import com.miniproject.pos.model.SalesOrder;
import com.miniproject.pos.model.SalesOrderDetail;
import com.miniproject.pos.service.CustomerService;
import com.miniproject.pos.service.ItemVariantService;
import com.miniproject.pos.service.ProvinsiService;
import com.miniproject.pos.service.SalesOrderDetailService;
import com.miniproject.pos.service.SalesOrderService;

@Controller
@RequestMapping("/salesorder")
public class SalesOrderController {

	@Autowired
	SalesOrderService salesOrderService;
	
	@Autowired
	SalesOrderDetailService salesOrderDetailService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ProvinsiService provinsiService;
	
	@Autowired
	ItemVariantService itemVariantService;
	
	@RequestMapping(value="/index")
	public String index(Model model) {
		List<SalesOrder> so = salesOrderService.selectAll();
		List<SalesOrderDetail> sod = salesOrderDetailService.selectAll();
		List<Customer> cus = customerService.selectAll();
		List<Provinsi> pr = provinsiService.selectAll();
		List<ItemVariant> iv = itemVariantService.getAllItemVariant();
		model.addAttribute("sods", sod);
		model.addAttribute("sos", so);
		model.addAttribute("cuss", cus);
		model.addAttribute("prs", pr);
		model.addAttribute("ivs", iv);
		return "salesorder/index";
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody SalesOrderDetail salesOrderDetail) {
		salesOrderDetailService.save(salesOrderDetail);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody SalesOrderDetail salesOrderDetail) {
		salesOrderDetailService.update(salesOrderDetail);
	}
	
	@RequestMapping(value="/get-id/{id}", method=RequestMethod.GET)
	@ResponseBody
	public SalesOrderDetail getOne(@PathVariable String id) {
		return salesOrderDetailService.getOne(id);
	}
	
	@RequestMapping(value="/delete/{id}",  method = RequestMethod.GET)
	@ResponseBody
	public void delete(@PathVariable String id) {
		SalesOrderDetail sod = new SalesOrderDetail();
		sod.setId(id);
		salesOrderDetailService.delete(sod);
	}
	
}
