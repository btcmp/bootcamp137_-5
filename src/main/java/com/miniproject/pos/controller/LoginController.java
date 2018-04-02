package com.miniproject.pos.controller;


import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.miniproject.pos.service.EmployeeService;

@Controller
@RequestMapping
public class LoginController {

	@Autowired 
	 private HttpSession httpSession;
	
	@Autowired 
	 private EmployeeService empService;
	
	@RequestMapping("/login")
	public String doLogin(Model model, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout){
		if(error != null){
			model.addAttribute("error", "user or password invalid..!!");
		} 
		if(logout != null){
			model.addAttribute("logout", "anda sudah keluar..!!");
		}
		
		return "security/login";
	}
	
	@RequestMapping("/welcome")
	public String selectOutlet(Model model, Principal principal){
		model.addAttribute("employee", empService.getEmployeeByUsername(principal.getName()));
		return "security/asign-outlet";
	}
	
	@RequestMapping(value = "/welcome", method=RequestMethod.POST)
	public RedirectView proccessSession(@RequestParam("outlet") String outlet, @RequestParam("id") String id, Model model){
		httpSession.setAttribute("userId", id);
		httpSession.setAttribute("outletId", outlet);
		return new RedirectView("home");
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		model.addAttribute("title", "Dashboard");
		return "security/home";
	}
}
