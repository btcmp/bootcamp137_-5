package com.miniproject.pos.controller;

import java.io.StringReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.miniproject.pos.model.Employee;
import com.miniproject.pos.model.PurchaseOrder;
import com.miniproject.pos.model.PurchaseOrderDetail;
import com.miniproject.pos.model.PurchaseOrderHistory;
import com.miniproject.pos.service.EmployeeService;

public class PDFViewPurchaseOrderDetail extends AbstractPdfView{

	@Autowired
	EmployeeService employeeService;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PurchaseOrder po = (PurchaseOrder) model.get("po");
		
		PdfPTable tabHistory = new PdfPTable(1);
		tabHistory.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		tabHistory.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		for (PurchaseOrderHistory poh: po.getListPurchaseOrderHistory()) {
			tabHistory.addCell("On "+ poh.getCreatedOnFormatted() + " - " + po.getPoNo() + " is " + poh.getStatus());
		}
		
		PdfPTable tabDetail= new PdfPTable(4);
		tabDetail.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		tabDetail.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		tabDetail.addCell("Item");
		tabDetail.addCell("Order Quantity");
		tabDetail.addCell("Unit Cost");
		tabDetail.addCell("Total");
		
		for (PurchaseOrderDetail pod : po.getListPurchaseOrderDetail()) {
			tabDetail.addCell(pod.getVariant().getItemId().getName() + " - " + pod.getVariant().getName());
			tabDetail.addCell(pod.getRequestQty()+"");
			tabDetail.addCell(pod.getUnitCost()+"");
			tabDetail.addCell(pod.getSubTotal()+"");
		}
		
		HTMLWorker htmlWorker = new HTMLWorker(document);
//		Employee creator = employeeService.getEmployeeByUsername(po.getCreatedBy().getUsername());
		String head = "<html><head></head>"
				+ "<body><h1>Purchase Detail</h1><br><br>"
				+ "<h2>"+ po.getSupplier().getName() +"</h2>"
				+ "<p>"+ po.getSupplier().getPhone() +"</p>"
				+ "<p>"+ po.getSupplier().getEmail() +"</p>"
				+ "<p>"+ po.getSupplier().getAddress() +"</p>"
				+ "<p>"+ po.getSupplier().getPostalCode() +"</p>"
				+ "<p> Notes :</p>"
				+ "<textarea id='view-notes' style='height:100px; width:100%;'>"+ po.getNotes() +"</textarea>"
				+ "<p> PO Number : " + po.getPoNo() + "</p>"
				+ "<p> Created By: " + po.getCreatedBy().getUsername() + "</p>"
//				+ "<p> Email : " + creator.getEmail() + "</p>"
				+ "<p> Outlet : " + po.getPurchaseRequest().getOutlet().getName() + "</p>"
				+ "<p> Phone : " + po.getPurchaseRequest().getOutlet().getPhone() + "</p>"
				+ "<p> Status : " + po.getStatus() + "</p>"
				+ "<p>"+ po.getSupplier().getPhone() +"</p>"
				+ "</body></html>";
		htmlWorker.parse(new StringReader(head));
		
		String headHistory = "<html><body><br><h2>Purchase History</h2><br></body></html>";
		htmlWorker.parse(new StringReader(headHistory));
		document.add(tabHistory);
		
		String headDetail = "<html><body><br><h2>Item List</h2><br></body></html>";
		htmlWorker.parse(new StringReader(headDetail));
		document.add(tabDetail);
	}

}
