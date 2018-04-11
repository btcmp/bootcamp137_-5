package com.miniproject.pos.utils;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class Menu {
	
	private String template = "<li class=\"{active}\"><a href=\"{link}\"><i class=\"{icon}\"></i> <span>{label}</span></a></li>";
	private String currentLink;
	private String baseUrl;
	private String[] struktur = {"{link}", "{icon}", "{label}","{active}"};
	private String[][] data;
	private String authorities;
	
	
	public Menu(String baseUrl, String currentLink, String[][] data) {
		this.currentLink = currentLink.replace(baseUrl, "");
		this.baseUrl = baseUrl;
		this.data = data;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> role = (List<GrantedAuthority>) authentication.getAuthorities();
		this.authorities = role.get(0).getAuthority();
	}
	
	public String renderMenu() {
		String menu ="", tamp;
		boolean display;
		for (int i = 0; i < data.length; i++) {
			tamp = template;
			display = false;
			for (int j = 0; j < data[i].length; j++) {
				if(j == 0) {
					tamp =tamp.replace(struktur[j], baseUrl+data[i][j]);
				}
				if(j == data[i].length -1) {
					if(currentLink.equalsIgnoreCase(data[i][0])) {
						tamp = tamp.replace(struktur[j], "active");
					}else {
						tamp =tamp.replace(struktur[j], "");
					}
					
					String[] roles =data[i][j].split(","); 
					for(int ix=0; ix<roles.length; ix++) {
						if(roles[ix].equalsIgnoreCase(authorities)) {
							display = true;
						}
					}
				}else {
					tamp =tamp.replace(struktur[j], data[i][j]);
				}
				
			}
			if(display)
				menu += tamp +"\n";
		}
		return menu;
	}
}