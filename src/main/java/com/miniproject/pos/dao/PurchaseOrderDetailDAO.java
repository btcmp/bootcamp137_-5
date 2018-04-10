package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.PurchaseOrderDetail;

public interface PurchaseOrderDetailDAO {

	PurchaseOrderDetail getPurchaseOrderDetail(PurchaseOrderDetail pod);
	List<PurchaseOrderDetail> getAllPurchaseOrderDetail();
	void save(PurchaseOrderDetail pod);
	void update(PurchaseOrderDetail pod);
	void delete(PurchaseOrderDetail pod);
	
}
