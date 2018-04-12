package com.miniproject.pos.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.miniproject.pos.model.SalesOrder;
import com.miniproject.pos.model.SalesOrderDetail;
import com.miniproject.pos.model.Supplier;
import com.miniproject.pos.service.SalesOrderDetailService;
import com.miniproject.pos.service.SalesOrderService;
import com.miniproject.pos.service.SupplierService;

@Controller
@RequestMapping("/generate")
public class PDFControllerr {
	
	@Autowired
	SupplierService supplierService;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	SalesOrderService salesOrderService;
	
	@Autowired
	SalesOrderDetailService salesOrderDetailService;
	
	@RequestMapping(value = "/suplier", method = RequestMethod.GET)
	ModelAndView generatePdf(HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		System.out.println("Calling generatePdf()...");
		//user data
		response.setHeader("Content-Disposition", "attachment; filename=\"suppliers.pdf\"");
		response.setContentType("application/pdf");
		java.util.List<Supplier> suppliers = supplierService.selectAll();

	return new ModelAndView("ViewSupplierpdf","suppliers",suppliers);
 	}
	
	@RequestMapping(value="/sales-order/{id}", method = RequestMethod.GET)
	ModelAndView generatePdfSO(HttpServletRequest request , @PathVariable String id,HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"sales-order.pdf\"");
		response.setContentType("application/pdf");
		List<SalesOrderDetail> sos = salesOrderService.getSalesOrderDetailByIdSalesOrder(id);

	return new ModelAndView("ViewSalesOrderpdf","sos",sos);
	}
}
