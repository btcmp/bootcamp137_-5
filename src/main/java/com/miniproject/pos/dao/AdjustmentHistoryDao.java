package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.AdjustmentHistory;

public interface AdjustmentHistoryDao {

	public void save(AdjustmentHistory ah);
	
	public List<AdjustmentHistory> getAdjustmentHistoryByAdjustmentId(String id);
}
