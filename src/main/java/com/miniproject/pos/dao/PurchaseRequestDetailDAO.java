package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.PurchaseRequestDetail;

public interface PurchaseRequestDetailDAO {
	
	PurchaseRequestDetail getPurchaseRequestDetail(PurchaseRequestDetail prd);
	List<PurchaseRequestDetail> getAllPurchaseRequestDetail();
	void save(PurchaseRequestDetail prd);
	void update(PurchaseRequestDetail prd);
	void delete(PurchaseRequestDetail prd);
	
}
