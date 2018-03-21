package com.miniproject.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miniproject.pos.service.EmployeeService;
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
	
	@RequestMapping
	public String index(Model model) {
		model.addAttribute("listEmployee", employeeService.getAll());
		model.addAttribute("listRole", roleService.getAll());
		model.addAttribute("listUser", userService.getAll());
		return "test/employee";
	}
	
}
