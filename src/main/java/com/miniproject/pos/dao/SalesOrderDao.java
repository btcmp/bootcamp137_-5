package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.SalesOrder;

public interface SalesOrderDao {

	public void save(SalesOrder salesOrder);
	
	public void delete(SalesOrder salesOrder);
	
	public void update(SalesOrder salesOrder);
	
	public SalesOrder getOne(String id);
	
	public List<SalesOrder> selectAll();
}
