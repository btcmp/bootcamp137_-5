package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.PurchaseOrderHistory;

public interface PurchaseOrderHistoryDAO {

	PurchaseOrderHistory getPurchaseOrderHistory(PurchaseOrderHistory poh);
	List<PurchaseOrderHistory> getAllPurchaseOrderHistory();
	void save(PurchaseOrderHistory poh);
	void update(PurchaseOrderHistory poh);
	void delete(PurchaseOrderHistory poh);
	
}
