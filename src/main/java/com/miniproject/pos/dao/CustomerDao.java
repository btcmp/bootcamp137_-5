
package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.Customer;

public interface CustomerDao {

	public void save(Customer customer);
	
	public void delete(Customer customer);
	
	public void update(Customer customer);
	
	public Customer getOne(String id);
	
	public List<Customer> selectAll();
}
