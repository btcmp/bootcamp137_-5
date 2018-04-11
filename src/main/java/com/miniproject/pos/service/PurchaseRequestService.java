package com.miniproject.pos.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.PurchaseRequestDAO;
import com.miniproject.pos.dao.PurchaseRequestDetailDAO;
import com.miniproject.pos.model.Employee;
import com.miniproject.pos.model.PurchaseOrderDetail;
import com.miniproject.pos.model.PurchaseRequest;
import com.miniproject.pos.model.PurchaseRequestDetail;
import com.miniproject.pos.model.PurchaseRequestHistory;

@Service
@Transactional
public class PurchaseRequestService {

	@Autowired
	private PurchaseRequestDAO purchaseRequestDAO;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private PurchaseRequestDetailDAO purchaseRequestDetailDAO;
	public PurchaseRequest get(String id) {
		PurchaseRequest pr = new PurchaseRequest();
		pr.setId(id);
		pr.setPrNo("-");
		pr.setStatus("-");
		return purchaseRequestDAO.getPurchaseRequest(pr);
	}
	
	public List<PurchaseRequest> getAll(){
		return purchaseRequestDAO.getAllPurchaseRequest();
	}
	
	public void save(PurchaseRequest pr) {
		pr.setPrNo("PR"+(purchaseRequestDAO.getSize() + 1));
		pr.setStatus("submitted");
		for (PurchaseRequestDetail prd : pr.getListPurchaseRequestDetail()) {
			prd.setPurchaseRequest(pr);
		}
		
		for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
			prh.setPurchaseRequest(pr);
		}
		purchaseRequestDAO.save(pr);
	}
	
	public void update(PurchaseRequest pr) {
		for (PurchaseRequestDetail prd : pr.getListPurchaseRequestDetail()) {
			prd.setPurchaseRequest(pr);
		}
		
		PurchaseRequest prevPR = get(pr.getId());
		for (PurchaseRequestDetail pprd : prevPR.getListPurchaseRequestDetail()) {
			purchaseRequestDetailDAO.delete(pprd);
		}
		
		if(pr.getListPurchaseRequestHistory() != null) {
			for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
				prh.setPurchaseRequest(pr);
			}
		}
		pr.setCreatedOn(get(pr.getId()).getCreatedOn());
		pr.setCreatedBy(get(pr.getId()).getCreatedBy());
		pr.setOutlet(get(pr.getId()).getOutlet());
		purchaseRequestDAO.update(pr);
	}
	
	public void delete(PurchaseRequest pr) {
		purchaseRequestDAO.delete(pr);
	}
	
	public void approve(PurchaseRequest pr) {
		pr.setStatus("approved");
		
		if(pr.getListPurchaseRequestHistory() != null) {
			for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
				prh.setPurchaseRequest(pr);
			}
		}
		pr.setCreatedOn(get(pr.getId()).getCreatedOn());
		pr.setCreatedBy(get(pr.getId()).getCreatedBy());
		pr.setOutlet(get(pr.getId()).getOutlet());
		purchaseRequestDAO.update(pr);
	}
	
	public void reject(PurchaseRequest pr) {
		pr.setStatus("rejected");
		
		if(pr.getListPurchaseRequestHistory() != null) {
			for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
				prh.setPurchaseRequest(pr);
			}
		}
		pr.setCreatedOn(get(pr.getId()).getCreatedOn());
		pr.setCreatedBy(get(pr.getId()).getCreatedBy());
		pr.setOutlet(get(pr.getId()).getOutlet());
		purchaseRequestDAO.update(pr);
	}
	
	public List<PurchaseRequest> getAllSubmittedPR(){
		return purchaseRequestDAO.getAllSubmittedPurchaseRequest();
	}
	
	public List<PurchaseRequest> getAllApprovedPR(){
		return purchaseRequestDAO.getAllApprovedPurchaseRequest();
	}
	
	public List<PurchaseRequest> getAllRejectedPR(){
		return purchaseRequestDAO.getAllRejectedPurchaseRequest();
	}

	public List<PurchaseRequest> getListPRBySearch(String search) {
		// TODO Auto-generated method stub
		return purchaseRequestDAO.getListPRBySearch(search);
	}

	public List<PurchaseRequest> getListPRByDate(String start, String end) {
		// TODO Auto-generated method stub
		return purchaseRequestDAO.getListPRByDate(start,end);
	}
}
