package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.Adjustment;

public interface AdjustmentDao {

	public void save(Adjustment adjustment);
	
	public void update(Adjustment adjustment);
	
	public void delete(Adjustment adjustment);
	
	public Adjustment getAdjustmentById(String id);
	
	public List<Adjustment> getAllAdjustment();
	
	public Adjustment getAdjustment(String id);
}
