package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.AdjustmentDetail;

public interface AdjustmentDetailDao {

	public void save(AdjustmentDetail ad);
	
	public List<AdjustmentDetail> getAdjustmentDetailByAdjustmentId(String id);
}
