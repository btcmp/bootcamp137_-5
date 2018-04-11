package com.miniproject.pos.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.model.PurchaseRequest;
import com.miniproject.pos.model.PurchaseRequestDetail;
import com.miniproject.pos.model.PurchaseRequestHistory;
import com.miniproject.pos.model.User;
import com.miniproject.pos.service.ItemInventoryService;
import com.miniproject.pos.service.OutletService;
import com.miniproject.pos.service.PurchaseRequestService;

@Controller
@RequestMapping("/purchase-request")
public class PurchaseRequestController {

	@Autowired
	PurchaseRequestService prService;
	
	@Autowired
	ItemInventoryService itemInvService;
	
	@Autowired
	OutletService outletService;
	
	@Autowired
	HttpSession httpSession;
	
	@RequestMapping
	public String index(Model model) {
		model.addAttribute("listPR", prService.getAll());
		model.addAttribute("listItem", itemInvService.getAllItemInventory());
		model.addAttribute("outlet", outletService.getOne(httpSession.getAttribute("outletId").toString()));
		return "page-transaction-pr/pr-page";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody PurchaseRequest pr) {
		User user = new User();
		user.setId(httpSession.getAttribute("userId").toString());
		pr.setCreatedBy(user);
		
		Outlet outlet = new Outlet();
		outlet.setId(httpSession.getAttribute("outletId").toString());
		pr.setOutlet(outlet);
		prService.save(pr);
	}
	
	@RequestMapping(value="/get-pr/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PurchaseRequest getPurchaseRequest(@PathVariable String id) {
		PurchaseRequest pr = prService.get(id);
		
		for (PurchaseRequestDetail prd : pr.getListPurchaseRequestDetail()) {
			prd.setPurchaseRequest(null);
		}
		
		for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
			prh.setPurchaseRequest(null);
		}
		
		if (pr.getPurchaseOrder()!= null) {
			pr.setPurchaseOrder(null);
		}
		
		return pr;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void edit(@RequestBody PurchaseRequest pr) {
		prService.update(pr);
	}
	
	
	@RequestMapping(value="/approve", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void approve(@RequestBody PurchaseRequest pr) {
		prService.approve(pr);
	}
	
	@RequestMapping(value="/reject", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void reject(@RequestBody PurchaseRequest pr) {
		prService.reject(pr);
	}
	
	@RequestMapping(value="/get-all-item", method = RequestMethod.GET)
	@ResponseBody
	public List<ItemInventory> getListItem(){
		return itemInvService.getAllItemInventory();
	}
	
	@RequestMapping(value="/get-all-pr", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseRequest> getListPR(){
		List<PurchaseRequest> listPR = prService.getAll();
		if (listPR == null) {
			return null;
		} else {
			for (PurchaseRequest pr : listPR) {
				
				for (PurchaseRequestDetail prd : pr.getListPurchaseRequestDetail()) {
					prd.setPurchaseRequest(null);
				}
				
				for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
					prh.setPurchaseRequest(null);
				}
				
				if (pr.getPurchaseOrder()!= null) {
					pr.setPurchaseOrder(null);
				}
			}
			return listPR;
		}
	}
	
	@RequestMapping(value="/get-all-submitted-pr", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseRequest> getSubmittedListPR(){
		List<PurchaseRequest> listPR = prService.getAllSubmittedPR();
		if (listPR == null) {
			return null;
		} else {
			for (PurchaseRequest pr : listPR) {
				
				for (PurchaseRequestDetail prd : pr.getListPurchaseRequestDetail()) {
					prd.setPurchaseRequest(null);
				}
				
				for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
					prh.setPurchaseRequest(null);
				}
				
				if (pr.getPurchaseOrder()!= null) {
					pr.setPurchaseOrder(null);
				}
			}
			return listPR;
		}
	}
	
	@RequestMapping(value="/get-all-approved-pr", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseRequest> getApprovedListPR(){
		List<PurchaseRequest> listPR = prService.getAllApprovedPR();
		if (listPR == null) {
			return null;
		} else {
			for (PurchaseRequest pr : listPR) {
				
				for (PurchaseRequestDetail prd : pr.getListPurchaseRequestDetail()) {
					prd.setPurchaseRequest(null);
				}
				
				for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
					prh.setPurchaseRequest(null);
				}
				
				if (pr.getPurchaseOrder()!= null) {
					pr.setPurchaseOrder(null);
				}
			}
			return listPR;
		}
	}
	
	@RequestMapping(value="/get-all-rejected-pr", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseRequest> getRejectedListPR(){
		List<PurchaseRequest> listPR = prService.getAllRejectedPR();
		if (listPR == null) {
			return null;
		} else {
			for (PurchaseRequest pr : listPR) {
				
				for (PurchaseRequestDetail prd : pr.getListPurchaseRequestDetail()) {
					prd.setPurchaseRequest(null);
				}
				
				for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
					prh.setPurchaseRequest(null);
				}
				
				if (pr.getPurchaseOrder()!= null) {
					pr.setPurchaseOrder(null);
				}
			}
			return listPR;
		}
	}
	
	@RequestMapping(value="/get-list-by-search/{search}", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseRequest> getListBySearch(@PathVariable String search){
		List<PurchaseRequest> listPR = prService.getListPRBySearch(search);
		if (listPR == null) {
			return null;
		} else {
			for (PurchaseRequest pr : listPR) {
				
				for (PurchaseRequestDetail prd : pr.getListPurchaseRequestDetail()) {
					prd.setPurchaseRequest(null);
				}
				
				for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
					prh.setPurchaseRequest(null);
				}
				
				if (pr.getPurchaseOrder()!= null) {
					pr.setPurchaseOrder(null);
				}
			}
			return listPR;
		}
	}
	
	@RequestMapping(value="/search-by-date", method = RequestMethod.GET)
	
	public List<PurchaseRequest> getlistByDate(@RequestParam(value="start") String start, @RequestParam(value="end") String end){
		List<PurchaseRequest> listPR = prService.getListPRByDate(start,end);
		if (listPR == null) {
			return null;
		} else {
			for (PurchaseRequest pr : listPR) {
				
				for (PurchaseRequestDetail prd : pr.getListPurchaseRequestDetail()) {
					prd.setPurchaseRequest(null);
				}
				
				for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
					prh.setPurchaseRequest(null);
				}
				
				if (pr.getPurchaseOrder()!= null) {
					pr.setPurchaseOrder(null);
				}
			}
			return listPR;
		}
	}
	
}
