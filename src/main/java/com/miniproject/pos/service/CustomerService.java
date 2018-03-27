package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.CustomerDao;
import com.miniproject.pos.model.Customer;

@Service
@Transactional
public class CustomerService {

	@Autowired
	CustomerDao customerDao;
	
	public void save(Customer customer) {
		customerDao.save(customer);
	}
	
	public void delete(Customer customer) {
		customerDao.delete(customer);
	}
	
	public void update(Customer customer) {
		customerDao.update(customer);
	}
	
	public Customer getOne(String id) {
		return customerDao.getOne(id);
	}
	
	public List<Customer> selectAll(){
		return customerDao.selectAll();
	}
}
