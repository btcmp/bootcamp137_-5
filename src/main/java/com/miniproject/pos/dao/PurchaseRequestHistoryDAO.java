package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.PurchaseRequestHistory;

public interface PurchaseRequestHistoryDAO {

	PurchaseRequestHistory getPurchaseRequestHistory(PurchaseRequestHistory prh);
	List<PurchaseRequestHistory> getAllPurchaseRequestHistory();
	void save(PurchaseRequestHistory prh);
	void update(PurchaseRequestHistory prh);
	void delete(PurchaseRequestHistory prh);
}
