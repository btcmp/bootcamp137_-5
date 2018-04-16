package com.miniproject.pos.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.miniproject.pos.model.PurchaseOrder;
import com.miniproject.pos.model.PurchaseOrderDetail;
import com.miniproject.pos.model.PurchaseOrderHistory;
import com.miniproject.pos.model.PurchaseRequest;
import com.miniproject.pos.model.PurchaseRequestDetail;
import com.miniproject.pos.model.PurchaseRequestHistory;
import com.miniproject.pos.model.User;
import com.miniproject.pos.service.EmployeeService;
import com.miniproject.pos.service.PurchaseOrderService;
import com.miniproject.pos.service.SupplierService;

@Controller
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

	@Autowired
	PurchaseOrderService poService;
	
	@Autowired
	SupplierService supplierService;
	
	@Autowired
	HttpSession httpSession; 
	
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping
	public String index(Model model) {
		model.addAttribute("listPO", poService.getAll());
		model.addAttribute("listSupplier", supplierService.selectAll());
		return "page-transaction-po/po-page";
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody String id) {
		User user = new User();
		user.setId(httpSession.getAttribute("userId").toString());
		poService.save(id, user);
	}
	
	@RequestMapping(value="/get-po/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PurchaseOrder getPO(@PathVariable String id) {
		PurchaseOrder po = poService.get(id);
		
		for (PurchaseOrderDetail pod : po.getListPurchaseOrderDetail()) {
			pod.setPurchaseOrder(null);
		}
		
		for (PurchaseOrderHistory poh : po.getListPurchaseOrderHistory()) {
			poh.setPurchaseOrder(null);
		}
		
		if (po.getPurchaseRequest() != null) {
			po.getPurchaseRequest().setListPurchaseRequestDetail(null);
			po.getPurchaseRequest().setListPurchaseRequestHistory(null);
			po.getPurchaseRequest().setPurchaseOrder(null);
			po.getCreatedBy().setEmployee(employeeService.getEmployeeByUsername(po.getCreatedBy().getUsername()));
			po.getCreatedBy().getEmployee().setUser(null);
		}
		
		return po;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void edit(@RequestBody PurchaseOrder po) {
		User editor = new User();
		editor.setId(httpSession.getAttribute("userId").toString());
		poService.update(po, editor);
	}
	
	
	@RequestMapping(value="/submit", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void submit(@RequestBody String id) {
		poService.submit(id);
	}
	
	@RequestMapping(value="/approve", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void approve(@RequestBody String id) {
		poService.approve(id);
	}
	
	@RequestMapping(value="/reject", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void reject(@RequestBody String id) {
		poService.reject(id);
	}
	
	@RequestMapping(value="/process", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void process(@RequestBody String id) {
		poService.process(id, httpSession.getAttribute("outletId").toString());
	}
	
	@RequestMapping(value="/get-all-po", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseOrder> getAllPO() {
		List<PurchaseOrder> listPO = poService.getAll();
		
		if (listPO == null) {
			return null;
		} else {
			for (PurchaseOrder po : listPO) {
				for (PurchaseOrderDetail pod : po.getListPurchaseOrderDetail()) {
					pod.setPurchaseOrder(null);
				}
				
				for (PurchaseOrderHistory poh : po.getListPurchaseOrderHistory()) {
					poh.setPurchaseOrder(null);
				}
				
				if (po.getPurchaseRequest() != null) {
					po.getPurchaseRequest().setListPurchaseRequestDetail(null);
					po.getPurchaseRequest().setListPurchaseRequestHistory(null);
					po.getPurchaseRequest().setPurchaseOrder(null);
					po.getCreatedBy().setEmployee(employeeService.getEmployeeByUsername(po.getCreatedBy().getUsername()));
					po.getCreatedBy().getEmployee().setUser(null);
				}
			}
			return listPO;
		}
	}
	
	@RequestMapping(value="/get-all-submitted-po", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseOrder> getAllSubmittedPO() {
		List<PurchaseOrder> listPO = poService.getSubmittedPO();
		
		if (listPO == null) {
			return null;
		} else {
			for (PurchaseOrder po : listPO) {
				for (PurchaseOrderDetail pod : po.getListPurchaseOrderDetail()) {
					pod.setPurchaseOrder(null);
				}
				
				for (PurchaseOrderHistory poh : po.getListPurchaseOrderHistory()) {
					poh.setPurchaseOrder(null);
				}
				
				if (po.getPurchaseRequest() != null) {
					po.getPurchaseRequest().setListPurchaseRequestDetail(null);
					po.getPurchaseRequest().setListPurchaseRequestHistory(null);
					po.getPurchaseRequest().setPurchaseOrder(null);
					po.getCreatedBy().setEmployee(employeeService.getEmployeeByUsername(po.getCreatedBy().getUsername()));
					po.getCreatedBy().getEmployee().setUser(null);
				}
			}
			return listPO;
		}
	}
	
	@RequestMapping(value="/get-all-approved-po", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseOrder> getAllApprovedPO() {
		List<PurchaseOrder> listPO = poService.getApprovedPO();
		
		if (listPO == null) {
			return null;
		} else {
			for (PurchaseOrder po : listPO) {
				for (PurchaseOrderDetail pod : po.getListPurchaseOrderDetail()) {
					pod.setPurchaseOrder(null);
				}
				
				for (PurchaseOrderHistory poh : po.getListPurchaseOrderHistory()) {
					poh.setPurchaseOrder(null);
				}
				
				if (po.getPurchaseRequest() != null) {
					po.getPurchaseRequest().setListPurchaseRequestDetail(null);
					po.getPurchaseRequest().setListPurchaseRequestHistory(null);
					po.getPurchaseRequest().setPurchaseOrder(null);
					po.getCreatedBy().setEmployee(employeeService.getEmployeeByUsername(po.getCreatedBy().getUsername()));
					po.getCreatedBy().getEmployee().setUser(null);
				}
			}
			return listPO;
		}
	}
	
	@RequestMapping(value="/get-all-rejected-po", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseOrder> getAllRejectedPO() {
		List<PurchaseOrder> listPO = poService.getRejectedPO();
		
		if (listPO == null) {
			return null;
		} else {
			for (PurchaseOrder po : listPO) {
				for (PurchaseOrderDetail pod : po.getListPurchaseOrderDetail()) {
					pod.setPurchaseOrder(null);
				}
				
				for (PurchaseOrderHistory poh : po.getListPurchaseOrderHistory()) {
					poh.setPurchaseOrder(null);
				}
				
				if (po.getPurchaseRequest() != null) {
					po.getPurchaseRequest().setListPurchaseRequestDetail(null);
					po.getPurchaseRequest().setListPurchaseRequestHistory(null);
					po.getPurchaseRequest().setPurchaseOrder(null);
					po.getCreatedBy().setEmployee(employeeService.getEmployeeByUsername(po.getCreatedBy().getUsername()));
					po.getCreatedBy().getEmployee().setUser(null);
				}
			}
			return listPO;
		}
	}
	
	@RequestMapping(value="/get-all-processed-po", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseOrder> getAllProcessedPO() {
		List<PurchaseOrder> listPO = poService.getProcessedPO();
		
		if (listPO == null) {
			return null;
		} else {
			for (PurchaseOrder po : listPO) {
				for (PurchaseOrderDetail pod : po.getListPurchaseOrderDetail()) {
					pod.setPurchaseOrder(null);
				}
				
				for (PurchaseOrderHistory poh : po.getListPurchaseOrderHistory()) {
					poh.setPurchaseOrder(null);
				}
				
				if (po.getPurchaseRequest() != null) {
					po.getPurchaseRequest().setListPurchaseRequestDetail(null);
					po.getPurchaseRequest().setListPurchaseRequestHistory(null);
					po.getPurchaseRequest().setPurchaseOrder(null);
					po.getCreatedBy().setEmployee(employeeService.getEmployeeByUsername(po.getCreatedBy().getUsername()));
					po.getCreatedBy().getEmployee().setUser(null);
				}
			}
			return listPO;
		}
	}
	
	@RequestMapping(value = "/get-list-by-search/{search}", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseOrder> getListPOBySearch(@PathVariable String search){
		List<PurchaseOrder> listPO = poService.getlistPOBySearch(search);
		
		if (listPO == null) {
			return null;
		} else {
			for (PurchaseOrder po : listPO) {
				for (PurchaseOrderDetail pod : po.getListPurchaseOrderDetail()) {
					pod.setPurchaseOrder(null);
				}
				
				for (PurchaseOrderHistory poh : po.getListPurchaseOrderHistory()) {
					poh.setPurchaseOrder(null);
				}
				
				if (po.getPurchaseRequest() != null) {
					po.getPurchaseRequest().setListPurchaseRequestDetail(null);
					po.getPurchaseRequest().setListPurchaseRequestHistory(null);
					po.getPurchaseRequest().setPurchaseOrder(null);
					po.getCreatedBy().setEmployee(employeeService.getEmployeeByUsername(po.getCreatedBy().getUsername()));
					po.getCreatedBy().getEmployee().setUser(null);
				}
			}
			return listPO;
		}
	}
	
	@RequestMapping(value="/search-by-date", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseOrder> getlistByDate(@RequestParam(value="start") String start, @RequestParam(value="end") String end){
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<PurchaseOrder> listPO = null;
		try {
			Date startDate = formatter.parse(start);
			Date endDate = formatter.parse(end);
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DATE, +1);
			Date endDateInc = cal.getTime();
			listPO = poService.getListPOByDate(startDate,endDateInc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (listPO == null) {
			return null;
		} else {
			for (PurchaseOrder po : listPO) {
				for (PurchaseOrderDetail pod : po.getListPurchaseOrderDetail()) {
					pod.setPurchaseOrder(null);
				}
				
				for (PurchaseOrderHistory poh : po.getListPurchaseOrderHistory()) {
					poh.setPurchaseOrder(null);
				}
				
				if (po.getPurchaseRequest() != null) {
					po.getPurchaseRequest().setListPurchaseRequestDetail(null);
					po.getPurchaseRequest().setListPurchaseRequestHistory(null);
					po.getPurchaseRequest().setPurchaseOrder(null);
					po.getCreatedBy().setEmployee(employeeService.getEmployeeByUsername(po.getCreatedBy().getUsername()));
					po.getCreatedBy().getEmployee().setUser(null);
				}
			}
			return listPO;
		}
		
	}
	
	
}
