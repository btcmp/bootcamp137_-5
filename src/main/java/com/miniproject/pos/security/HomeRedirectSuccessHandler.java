package com.miniproject.pos.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class HomeRedirectSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		 HttpSession session = httpServletRequest.getSession();
	        User authUser = (User) authentication.getPrincipal();
	        session.setAttribute("username", authUser.getUsername());
	        session.setAttribute("authorities", authentication.getAuthorities());
	 
	        //set our response to OK status
	        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
	        httpServletResponse.sendRedirect("welcome");
	}
}
