package com.miniproject.pos.controller;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.Document;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;
import com.miniproject.pos.model.PurchaseRequest;

public class PDFViewPurchaseRequest extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<PurchaseRequest> listPR = (List<PurchaseRequest>) model.get("listPR");
		
		PdfPTable table = new PdfPTable(4);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		table.addCell("Create Date");
		table.addCell("PR No.");
		table.addCell("Notes");
		table.addCell("status");
		
		for (PurchaseRequest pr : listPR) {
			table.addCell(pr.getCreatedOnFormatted());
			table.addCell(pr.getPrNo());
			table.addCell(pr.getNotes());
			table.addCell(pr.getStatus());
		}
		
		HTMLWorker htmlWorker = new HTMLWorker(document);
		String head = "<html><head></head><body><h1 style='text-align:center'>Purchase Request</h1><br></body></html>";
		htmlWorker.parse(new StringReader(head));
		document.add(table);	
	}
}
