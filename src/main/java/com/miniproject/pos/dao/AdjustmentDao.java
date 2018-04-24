package com.miniproject.pos.dao;

import java.util.Date;
import java.util.List;

import com.miniproject.pos.model.Adjustment;

public interface AdjustmentDao {

	public void save(Adjustment adjustment);
	
	public void update(Adjustment adjustment);
	
	public void delete(Adjustment adjustment);
	
	public Adjustment getAdjustmentById(String id);
	
	public List<Adjustment> getAllAdjustment(String outletId);
	
	public List<Adjustment> getAllAdjustmentByDate(Date startDate, Date endDate, String outletId);
	
	public Adjustment getAdjustment(String id);
	
	public Long countAdjustment(String outletId);
}
