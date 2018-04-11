package com.miniproject.pos.dao;

import java.util.Date;
import java.util.List;

import com.miniproject.pos.model.PurchaseOrder;

public interface PurchaseOrderDAO {

	PurchaseOrder getPurchaseOrder(PurchaseOrder po);
	List<PurchaseOrder> getAllPurchaseOrder();
	void save(PurchaseOrder po);
	void update(PurchaseOrder po);
	void delete(PurchaseOrder po);
	int getSize();
	List<PurchaseOrder> getAllSubmittedPurchaseOrder();
	List<PurchaseOrder> getAllApprovedPurchaseOrder();
	List<PurchaseOrder> getAllRejectedPurchaseOrder();
	List<PurchaseOrder> getAllProcessedPurchaseOrder();
	List<PurchaseOrder> getListPOBySearch(String search);
	List<PurchaseOrder> getListPOByDate(Date start, Date end);
}
