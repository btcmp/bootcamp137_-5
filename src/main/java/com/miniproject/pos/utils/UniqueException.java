package com.miniproject.pos.utils;

public class UniqueException extends org.springframework.dao.DataIntegrityViolationException{

	private String form;
	private String field;
	private String value;
	
	public UniqueException(String form, String field, String value) {
		super("");
		this.form = form;
		this.field = field;
		this.value = value;
		// TODO Auto-generated constructor stub
	}

	public String[][] error(){
		String[][] error = {{this.form, this.field, "duplicate "+this.form+" "+this.field+" with value "+this.value}};
		return error;
	}
}
