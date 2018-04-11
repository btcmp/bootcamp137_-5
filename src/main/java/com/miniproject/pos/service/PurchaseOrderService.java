package com.miniproject.pos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.ItemInventoryDao;
import com.miniproject.pos.dao.PurchaseOrderDAO;
import com.miniproject.pos.dao.PurchaseRequestDAO;
import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.PurchaseOrder;
import com.miniproject.pos.model.PurchaseOrderDetail;
import com.miniproject.pos.model.PurchaseOrderHistory;
import com.miniproject.pos.model.PurchaseRequest;
import com.miniproject.pos.model.PurchaseRequestDetail;
import com.miniproject.pos.model.User;

@Service
@Transactional
public class PurchaseOrderService {

	@Autowired
	private PurchaseOrderDAO purchaseOrderDAO;
	
	@Autowired 
	private PurchaseRequestDAO purchaseRequestDAO;
	
	@Autowired
	private ItemInventoryDao itemInventoryDAO;
	
	@Autowired
	private PurchaseRequestService purchaseRequestService;
	
	@Autowired
	private SupplierService supplierService;
	
	public PurchaseOrder get(String id) {
		PurchaseOrder po = new PurchaseOrder();
		po.setId(id);
		po.setPoNo("-");
		po.setStatus("-");
		return purchaseOrderDAO.getPurchaseOrder(po);
	}
	
	public List<PurchaseOrder> getAll(){
		return purchaseOrderDAO.getAllPurchaseOrder();
	}
	
	public void save(String id, User user) {
		PurchaseOrder po = new PurchaseOrder();
		PurchaseRequest pr = purchaseRequestService.get(id);
		po.setPoNo("PO"+(purchaseOrderDAO.getSize() + 1));
		po.setStatus("created");
		po.setPurchaseRequest(pr);
		
		float grandTotal = 0;
		List<PurchaseOrderDetail> listPod = new ArrayList<>();
		List<PurchaseOrderHistory> listPoh = new ArrayList<>();
		
		PurchaseOrderHistory poh = new PurchaseOrderHistory();
		
		for (PurchaseRequestDetail prd : pr.getListPurchaseRequestDetail()) {
			PurchaseOrderDetail pod = new PurchaseOrderDetail();
			pod.setRequestQty(prd.getRequestQty());
			pod.setPurchaseOrder(po);
			pod.setVariant(prd.getVariant());
			pod.setUnitCost((float) prd.getVariant().getPrice());
			pod.setSubTotal((float) prd.getVariant().getPrice() * prd.getRequestQty());
			System.out.println(pod.getVariant().getItemId().getName());
			grandTotal += (float) prd.getVariant().getPrice() * prd.getRequestQty();
			listPod.add(pod);
		}
		System.out.println(listPod.size());
		poh.setPurchaseOrder(po);
		poh.setStatus("created");
		listPoh.add(poh);
		po.setGrandTotal(grandTotal);
		po.setListPurchaseOrderDetail(listPod);
		po.setListPurchaseOrderHistory(listPoh);
		po.setCreatedBy(user);
		purchaseOrderDAO.save(po);
		
		pr.setPurchaseOrder(po);
		pr.setStatus("PO created");
		purchaseRequestDAO.update(pr);
	}
	
	public void update(PurchaseOrder po) {
		PurchaseOrder prevPO = get(po.getId());
		
		for (PurchaseOrderDetail ppod : prevPO.getListPurchaseOrderDetail()) {
			for (PurchaseOrderDetail pod : po.getListPurchaseOrderDetail()) {
				if(ppod.getVariant().getId().equals(pod.getVariant().getId())) {
					ppod.setUnitCost(pod.getUnitCost());
					ppod.setSubTotal(pod.getSubTotal());
				}
			}
		}
		prevPO.setGrandTotal(po.getGrandTotal());
		prevPO.setSupplier(supplierService.getOne(po.getSupplier().getId()));
		prevPO.setNotes(po.getNotes());
		System.out.println(prevPO.getNotes()+" ini note");
		purchaseOrderDAO.update(prevPO);
	}
	
	public void submit(String id) {
		PurchaseOrder po = get(id);
		po.setStatus("submitted");
		
		PurchaseOrderHistory poh = new PurchaseOrderHistory();
		poh.setPurchaseOrder(po);
		poh.setStatus("submitted");
		
		po.getListPurchaseOrderHistory().add(poh);
		purchaseOrderDAO.update(po);
	}
	
	public void approve(String id) {
		PurchaseOrder po = get(id);
		po.setStatus("approved");
		
		PurchaseOrderHistory poh = new PurchaseOrderHistory();
		poh.setPurchaseOrder(po);
		poh.setStatus("approved");
		
		po.getListPurchaseOrderHistory().add(poh);
		purchaseOrderDAO.update(po);
	}
	
	public void reject(String id) {
		PurchaseOrder po = get(id);
		po.setStatus("rejected");
		
		PurchaseOrderHistory poh = new PurchaseOrderHistory();
		poh.setPurchaseOrder(po);
		poh.setStatus("rejected");
		
		po.getListPurchaseOrderHistory().add(poh);
		purchaseOrderDAO.update(po);
	}
	
	public void process(String id, String outletId) {
		PurchaseOrder po = get(id);
		po.setStatus("processed");
		
		PurchaseOrderHistory poh = new PurchaseOrderHistory();
		poh.setPurchaseOrder(po);
		poh.setStatus("processed");
		
		for (PurchaseOrderDetail pod : po.getListPurchaseOrderDetail()) {
			ItemInventory inv = itemInventoryDAO.getInventoryByVariantId(pod.getVariant().getId(), outletId);
			int purchaseQty = inv.getPurchaseQty() + pod.getRequestQty();
			int endingQty = inv.getBegining() + purchaseQty + inv.getAdjustmentQty() - (inv.getTransferStockQty() + inv.getSalesOrderQty());
			inv.setPurchaseQty(purchaseQty);
			inv.setEndingQty(endingQty);
			itemInventoryDAO.update(inv);
		}
		
		po.getListPurchaseOrderHistory().add(poh);
		purchaseOrderDAO.update(po);
	}
	
	public void delete(PurchaseOrder po) {
		purchaseOrderDAO.delete(po);
	}
	
	public List<PurchaseOrder> getSubmittedPO(){
		return purchaseOrderDAO.getAllSubmittedPurchaseOrder();
	}
	
	public List<PurchaseOrder> getApprovedPO(){
		return purchaseOrderDAO.getAllApprovedPurchaseOrder();
	}
	
	public List<PurchaseOrder> getRejectedPO(){
		return purchaseOrderDAO.getAllRejectedPurchaseOrder();
	}
	
	public List<PurchaseOrder> getProcessedPO(){
		return purchaseOrderDAO.getAllProcessedPurchaseOrder();
	}

	public List<PurchaseOrder> getlistPOBySearch(String search) {
		// TODO Auto-generated method stub
		return purchaseOrderDAO.getListPOBySearch(search);
	}
}
