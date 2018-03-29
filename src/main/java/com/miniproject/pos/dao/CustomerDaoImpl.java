package com.miniproject.pos.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miniproject.pos.model.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao{

	@Autowired
	SessionFactory sessionFactory;
	
	public void save(Customer customer) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(customer);
		session.flush();
	}

	public void delete(Customer customer) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(customer);
		session.flush();
	}

	public void update(Customer customer) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(customer);
		session.flush();
	}

	public Customer getOne(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.get(Customer.class, id);
	}

	public List<Customer> selectAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Customer.class).list();
	}

	public List<Customer> getCustomerBySearchName(String search) {
		// TODO Auto-generated method stub
		String hql = "from Customer cs where lower(cs.name) like lower(:nb)";
		Session session = sessionFactory.getCurrentSession();
		List<Customer> cuss = session.createQuery(hql).setParameter("nb", "%"+search+"%").list();
		if(cuss.isEmpty()) {
			return null;
		}
		return cuss;
	}

}