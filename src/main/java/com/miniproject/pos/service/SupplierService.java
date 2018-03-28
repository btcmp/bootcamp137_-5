package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.SupplierDao;
import com.miniproject.pos.model.Supplier;

@Service
@Transactional
public class SupplierService {

	@Autowired
	SupplierDao supplierDao;
	
	public void save(Supplier supplier) {
		supplierDao.save(supplier);
	}
	
	public void delete(Supplier supplier) {
		supplierDao.delete(supplier);
	}
	
	public void update(Supplier supplier) {
		supplierDao.update(supplier);
	}
	
	public Supplier getOne(String id) {
		return supplierDao.getOne(id);
	}
	
	public List<Supplier> selectAll(){
		return supplierDao.selectAlll();
	}

	public void nonactive(Supplier supplier) {
		// TODO Auto-generated method stub
		supplierDao.update(supplier);
		
	}

	public List<Supplier> getSupplierBySearchName(String search) {
		// TODO Auto-generated method stub
		return supplierDao.getSupplierBySearchName(search);
	}
	
}
