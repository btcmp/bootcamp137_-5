package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.SalesOrderDetailDao;
import com.miniproject.pos.model.SalesOrderDetail;

@Service
@Transactional
public class SalesOrderDetailService {

	@Autowired
	SalesOrderDetailDao salesOrderDetailDao;
	
	public void save (SalesOrderDetail salesOrderDetail) {
		salesOrderDetailDao.save(salesOrderDetail);
	}
	
	public void delete (SalesOrderDetail salesOrderDetail) {
		salesOrderDetailDao.delete(salesOrderDetail);
	}
	
	public void update (SalesOrderDetail salesOrderDetail) {
		salesOrderDetailDao.update(salesOrderDetail);
	}
	
	public SalesOrderDetail getOne(String id) {
		return salesOrderDetailDao.getOne(id);
	}
	
	public List<SalesOrderDetail> selectAll(){
		return salesOrderDetailDao.selectAll();
	}
	
}
