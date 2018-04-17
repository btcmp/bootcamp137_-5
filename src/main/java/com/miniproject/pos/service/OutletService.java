package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.OutletDao;
import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.model.User;

@Service
@Transactional
public class OutletService {

	@Autowired
	OutletDao outletDao;
	
	public void save(Outlet outlet, User user ) {
		Outlet out = new Outlet();
		out.setId(outlet.getId());
		out.setName(outlet.getName());
		out.setCreatedBy(user);
		out.setCreatedOn(outlet.getCreatedOn());
		out.setAddress(outlet.getAddress());
		out.setDistrict(outlet.getDistrict());
		out.setEmail(outlet.getEmail());
		out.setPhone(outlet.getPhone());
		out.setPostalCode(outlet.getPostalCode());
		out.setProvinsi(outlet.getProvinsi());
		out.setRegion(outlet.getRegion());
		out.setActive(true);
		out.setModifiedBy(null);
		out.setModifiedOn(null);
		outletDao.save(out);
	}
	
	public void delete(Outlet outlet) {
		outletDao.delete(outlet);
	}
	
	public void update(Outlet outlet, User user) {
		outlet.setModifiedBy(user);
		outletDao.update(outlet);
	}
	
	public void deactive(Outlet outlet, User user) {
		// TODO Auto-generated method stub
		outlet.setActive(false);
		outlet.setModifiedBy(user);
		update(outlet,user);
	}
	
	public Outlet getOne(String id) {
		return outletDao.getOne(id);
	}
	
	public List<Outlet> selectAll(){
		return outletDao.selectAll();
	}

	public void nonActiveOutlet(Outlet outlet) {
		// TODO Auto-generated method stub
		outlet.setActive(true);
		outletDao.update(outlet);
	}

	public List<Outlet> getOutletNameBySearch(String search) {
		// TODO Auto-generated method stub
		return outletDao.getOutletNameBySearch(search);
	}

	public List<Outlet> getAll() {
		// TODO Auto-generated method stub
		return outletDao.getAll();
	}
	
}
