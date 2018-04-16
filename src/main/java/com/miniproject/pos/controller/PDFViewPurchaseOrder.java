package com.miniproject.pos.controller;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.miniproject.pos.model.PurchaseOrder;

public class PDFViewPurchaseOrder extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<PurchaseOrder> listPO = (List<PurchaseOrder>) model.get("listPO");
		PdfPTable table = new PdfPTable(5);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		table.addCell("Create Date");
		table.addCell("Supplier");
		table.addCell("Po No.");
		table.addCell("Total");
		table.addCell("Status");
		
		for (PurchaseOrder po : listPO) {
			table.addCell(po.getCreatedOnFormatted());
			if(po.getSupplier() != null) {
				table.addCell(po.getSupplier().getName());
			}
			table.addCell(po.getPoNo());
			table.addCell(po.getGrandTotalFormatted());
			table.addCell(po.getStatus());
		}
		
		HTMLWorker htmlWorker = new HTMLWorker(document);
		String head = "<html><body><h1 style='text-align:center'>Purchase Order</h1><br></body></html>";
		htmlWorker.parse(new StringReader(head));
		document.add(table);
	}

	
	
}
