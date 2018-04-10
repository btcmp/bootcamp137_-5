package com.miniproject.pos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.miniproject.pos.model.Supplier;
import com.miniproject.pos.service.SupplierService;

@Controller
@RequestMapping("/generate")
public class PDFControllerr {
	
	@Autowired
	SupplierService supplierService;
	
	@RequestMapping(value = "/supplier", method = RequestMethod.GET)
	ModelAndView generatePdf(HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		System.out.println("Calling generatePdf()...");
		//user data
		response.setHeader("Content-Disposition", "attachment; filename=\"suppliers.pdf\"");
		response.setContentType("application/pdf");
		java.util.List<Supplier> suppliers = supplierService.selectAll();

	return new ModelAndView("pdfViewSupplier","suppliers",suppliers);
 	}
}
