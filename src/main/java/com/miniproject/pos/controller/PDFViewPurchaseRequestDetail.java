package com.miniproject.pos.controller;

import java.io.StringReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.miniproject.pos.model.PurchaseRequest;
import com.miniproject.pos.model.PurchaseRequestDetail;
import com.miniproject.pos.model.PurchaseRequestHistory;

public class PDFViewPurchaseRequestDetail extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PurchaseRequest pr = (PurchaseRequest) model.get("pr");
		
		PdfPTable tabHistory = new PdfPTable(1);
		tabHistory.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		tabHistory.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		for (PurchaseRequestHistory prh : pr.getListPurchaseRequestHistory()) {
			tabHistory.addCell("On " + prh.getCreatedOnFormatted() + " - " + pr.getPrNo() + " is " + prh.getStatus());
		}
		
		PdfPTable tabDetail = new PdfPTable(2);
		tabHistory.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		tabHistory.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		tabDetail.addCell("Item");
		tabDetail.addCell("Request Quantity");
		for (PurchaseRequestDetail prd: pr.getListPurchaseRequestDetail()) {
			tabDetail.addCell(prd.getVariant().getItemId().getName() + " - " + prd.getVariant().getName());
			tabDetail.addCell(prd.getRequestQty()+"");
		}
		
		HTMLWorker htmlWorker = new HTMLWorker(document);
		String head = "<html><head></head>"
				+ "<body><h1 style='text-align:center'>Purchase Detail</h1><hr>"
				+ "<p>PR Number : "+ pr.getPrNo() +"</p>"
				+ "<p>Created By : "+ pr.getCreatedBy().getUsername() +"</p>"
				+ "<p>Target Waktu Item Ready : "+ pr.getReadyTime() +"</p>"
				+ "<p>PR Status : "+ pr.getStatus() +"</p>"
				+ "<p>Notes :</p>"
				+ "<textarea id='view-notes' style='height:100px; width:100%;'>"+ pr.getNotes() +"</textarea>"
				+ "<br><br><br>"
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
