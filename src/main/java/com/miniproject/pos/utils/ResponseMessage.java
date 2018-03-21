package com.miniproject.pos.utils;

import java.util.List;

import org.springframework.validation.FieldError;

public class ResponseMessage {
	private String status;
	private String keterangan;
	private Object data;
	private String[][] error;
	private List<FieldError> fieldError;
	
	public ResponseMessage() {
		
	}
	
	public ResponseMessage(String status, String keterangan, Object data) {
		super();
		this.status = status;
		this.keterangan = keterangan;
		this.data = data;
	}
	
	
	
	public List<FieldError> getFieldError() {
		return fieldError;
	}

	public void setFieldError(List<FieldError> fieldError) {
		this.fieldError = fieldError;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getKeterangan() {
		return keterangan;
	}
	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public String[][] getError() {
		return error;
	}

	public void setError(String[][] error) {
		this.error = error;
	}
	
	
}
