package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.SalesOrderDao;
import com.miniproject.pos.model.SalesOrder;

@Service
@Transactional
public class SalesOrderService {

	@Autowired
	SalesOrderDao salesOrderDao;
	
	public void save(SalesOrder salesOrder) {
		salesOrderDao.save(salesOrder);
	}
	
	public void delete(SalesOrder salesOrder) {
		salesOrderDao.delete(salesOrder);
	}
	
	public void update(SalesOrder salesOrder) {
		salesOrderDao.update(salesOrder);
	}
	
	public SalesOrder getOne(String id) {
		return salesOrderDao.getOne(id);
	}
	
	public List<SalesOrder> selectAll(){
		return salesOrderDao.selectAll();
	}	
}
