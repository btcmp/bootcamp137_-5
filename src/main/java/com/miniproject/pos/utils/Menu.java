package com.miniproject.pos.utils;

public class Menu {
	
	private String template = "<li class=\"{active}\"><a href=\"{link}\"><i class=\"{icon}\"></i> <span>{label}</span></a></li>";
	private String currentLink;
	private String baseUrl;
	private String[] struktur = {"{link}", "{icon}", "{label}","{active}"};
	private String[][] data;
	
	public Menu(String baseUrl, String currentLink, String[][] data) {
		this.currentLink = currentLink.replace(baseUrl, "");
		this.baseUrl = baseUrl;
		this.data = data;
	}
	
	public String renderMenu() {
		String menu ="", tamp;
		for (int i = 0; i < data.length; i++) {
			tamp = template;
			for (int j = 0; j <= data[i].length; j++) {
				if(j == 0) {
					tamp =tamp.replace(struktur[j], baseUrl+data[i][j]);
				}
				if(j == data[i].length) {
					if(currentLink.equalsIgnoreCase(data[i][0])) {
						tamp = tamp.replace(struktur[j], "active");
					}else {
						tamp =tamp.replace(struktur[j], "");
					}
				}else {
					tamp =tamp.replace(struktur[j], data[i][j]);
				}
				
			}
			menu += tamp +"\n";
		}
		return menu;
	}
}