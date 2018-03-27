package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.SalesOrderDetail;

public interface SalesOrderDetailDao {

	public void save(SalesOrderDetail salesOrderDetail);
	
	public void delete(SalesOrderDetail salesOrderDetail);
	
	public void update(SalesOrderDetail salesOrderDetail);
	
	public SalesOrderDetail getOne(String id);
	
	public List<SalesOrderDetail> selectAll();
}
