package com.miniproject.pos.controller;

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

import com.miniproject.pos.model.Employee;
import com.miniproject.pos.model.User;
import com.miniproject.pos.service.EmployeeService;
import com.miniproject.pos.service.OutletService;
import com.miniproject.pos.service.RoleService;
import com.miniproject.pos.service.UserService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OutletService outletService;
	
	@RequestMapping
	public String index(Model model) {
		model.addAttribute("listEmployee", employeeService.getAll());
		model.addAttribute("listRole", roleService.getAll());
		model.addAttribute("listUser", userService.getAll());
		model.addAttribute("listOutlet", outletService.selectAll());
		model.addAttribute("listActiveEmployee", employeeService.getAllActiveEmployee());
		return "page-master-employee/employee-page";
	}
	
	@RequestMapping(value="/get-employee/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Employee getEmployee(@PathVariable String id) {
		return employeeService.get(id);
	}
	
	@RequestMapping(value="/save-emp", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void saveEmpUsr(@RequestBody Employee e) {
		employeeService.save(e);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void edit(@RequestBody Employee e) {
		employeeService.update(e);
	}
	
	@RequestMapping(value="/deactivate", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void deactivate(@RequestBody Employee e) {
		employeeService.deactivate(e);
	}
	
}
