package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.AdjustmentDao;
import com.miniproject.pos.model.Adjustment;

@Service
@Transactional
public class AdjustmentService {

	@Autowired
	AdjustmentDao ad;
	
	public void save(Adjustment adjustment) {
		ad.save(adjustment);
	}
	
	public void update(Adjustment adjustment) {
		ad.update(adjustment);
	}
	
	public List<Adjustment> getAllAdjustment(){
		return ad.getAllAdjustment();
	}
}
