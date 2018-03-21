package com.miniproject.pos.utils;

import java.util.HashMap;

public class Breadcrumb {
	private String active;
	private HashMap<String, String> link;
	private String baseUrl;
	
	public Breadcrumb(String baseUrl, String active) {
		this.active = active;
		this.baseUrl = baseUrl;
	}
	
	public void setLink(HashMap<String, String> link) {
		this.link = link;
	}
	
	public String getData() {
		return "<ol class=\"breadcrumb\">\n" + 
				"        <li><a href='"+ this.baseUrl +"main/index'><i class=\"fa fa-dashboard\"></i> Home</a></li>\n" + 
				"        " + this.generateLink() + 
				"        <li class=\"active\">"+ this.active +"</li>\n" + 
				"      </ol>";
	}
	
	private String generateLink() {
		if(link != null) {
			
		}
		return "";
	}
}