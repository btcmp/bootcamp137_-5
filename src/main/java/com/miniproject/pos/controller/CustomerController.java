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
import com.miniproject.pos.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value="/index")
	public String index(Model model) {
		List<Customer> customers = customerService.selectAll();
		model.addAttribute("custs", customers);
		return "customer/index";
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody Customer customer) {
		customerService.save(customer);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody Customer customer) {
		customerService.update(customer);
	}
	
	@RequestMapping(value="/get-id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Customer getOne(@PathVariable String id) {
		return customerService.getOne(id);
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void delete(@PathVariable String id) {
		Customer cus = new Customer();
		cus.setId(id);
		customerService.delete(cus);
	}
	
}
