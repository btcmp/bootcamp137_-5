package com.miniproject.pos.dao;

import java.util.Date;
import java.util.List;

import com.miniproject.pos.model.PurchaseRequest;

public interface PurchaseRequestDAO {

	PurchaseRequest getPurchaseRequest(PurchaseRequest pr);
	List<PurchaseRequest> getAllPurchaseRequest();
	int getSize();
	void save(PurchaseRequest pr);
	void update(PurchaseRequest pr);
	void delete(PurchaseRequest pr);
	List<PurchaseRequest> getAllSubmittedPurchaseRequest();
	List<PurchaseRequest> getAllApprovedPurchaseRequest();
	List<PurchaseRequest> getAllRejectedPurchaseRequest();
	List<PurchaseRequest> getListPRBySearch(String search);
	List<PurchaseRequest> getListPRByDate(String start, String end);
}
