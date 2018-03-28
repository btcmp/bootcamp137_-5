package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.OutletDao;
import com.miniproject.pos.model.Outlet;

@Service
@Transactional
public class OutletService {

	@Autowired
	OutletDao outletDao;
	
	public void save(Outlet outlet) {
		outletDao.save(outlet);
	}
	
	public void delete(Outlet outlet) {
		outletDao.delete(outlet);
	}
	
	public void update(Outlet outlet) {
		outletDao.update(outlet);
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
	
}
