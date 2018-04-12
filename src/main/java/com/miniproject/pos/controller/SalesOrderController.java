package com.miniproject.pos.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.miniproject.pos.model.Customer;
import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.model.Items;
import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.model.Provinsi;
import com.miniproject.pos.model.SalesOrder;
import com.miniproject.pos.model.SalesOrderDetail;
import com.miniproject.pos.model.User;
import com.miniproject.pos.service.CustomerService;
import com.miniproject.pos.service.ItemInventoryService;
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
	
	@Autowired
	ItemInventoryService itemInventoryService;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping(value="/index")
	public String index(Model model) {
		String outletId = httpSession.getAttribute("outletId").toString();
		List<SalesOrder> so = salesOrderService.selectAll();
		List<SalesOrderDetail> sod = salesOrderDetailService.selectAll();
		List<Customer> cus = customerService.selectAll();
		List<Provinsi> pr = provinsiService.selectAll();
		List<ItemVariant> iv = itemVariantService.getAllItemVariant(outletId);
		List<ItemInventory> listItems = itemInventoryService.getAllItemInventory();
		model.addAttribute("sods", sod);
		model.addAttribute("sos", so);
		model.addAttribute("cuss", cus);
		model.addAttribute("prs", pr);
		model.addAttribute("ivs", iv);
		model.addAttribute("listItems", listItems);
		return "salesorder/index";
	}
	
	@RequestMapping(value="/index/src-customer", method = RequestMethod.GET)
	@ResponseBody
	public List<Customer> customerBySearchName(@RequestParam(value="search", defaultValue="") String search, Model model) {
		List<Customer> cus = customerService.getCustomerBySearchName(search);
		model.addAttribute("cuss", cus);
		return cus;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(@RequestBody SalesOrder salesOrder) {
		User user = new User();
		user.setId(httpSession.getAttribute("userId").toString());
		Outlet outlet = new Outlet();
		outlet.setId(httpSession.getAttribute("outletId").toString());
		return salesOrderService.save(salesOrder, user, outlet);
	}
	
	@RequestMapping(value="/get-id/{id}", method=RequestMethod.GET)
	@ResponseBody
	public SalesOrder getOne(@PathVariable String id) {
		return salesOrderService.getOne(id);
	}
	
	@RequestMapping(value="/delete/{id}",  method = RequestMethod.GET)
	@ResponseBody
	public void delete(@PathVariable String id) {
		SalesOrder so = new SalesOrder();
		so.setId(id);
		salesOrderService.delete(so);
	}
	
	@RequestMapping(value="/get-all-email", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getEmailFromCustomer() {
		List<String> listcustomer = new ArrayList();
		List<Customer> list = customerService.selectAll();
		for (Customer customer : list) {
			listcustomer.add(customer.getEmail());
		}
		return listcustomer;
	}
	
	@RequestMapping(value="/get-all-phone", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getPhoneFromCustomer(){
		List<String> listphone = new ArrayList();
		List<Customer> telp = customerService.selectAll();
		for (Customer customer : telp) {
			listphone.add(customer.getPhone());
		}
		return listphone;
	}
	
	@RequestMapping(value="/update-stock", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void updateStockInventory(@RequestBody SalesOrder salesOrder, String outletId) {
		outletId = httpSession.getAttribute("outletId").toString();
		salesOrderService.update(salesOrder, outletId);
	}
	
}
