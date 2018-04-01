package com.miniproject.pos.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class LoginController {

	@Autowired 
	 private HttpSession httpSession;
	
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
	@ResponseBody
	public String getUsernameWithUserDetail(Authentication authentication){
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		httpSession.setAttribute("coba", "buat program kog coba coba");
		return userDetails.getUsername();
	}
}
