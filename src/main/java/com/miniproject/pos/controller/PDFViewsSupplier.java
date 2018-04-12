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
import com.miniproject.pos.model.Supplier;

public class PDFViewsSupplier extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<Supplier> suppliers = (List<Supplier>) model.get("suppliers");
		   PdfPTable table = new PdfPTable(4);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

			table.addCell("Name");
			table.addCell("Address");
			table.addCell("Phone");
			table.addCell("Email");

			for (Supplier supp : suppliers) {
				table.addCell(supp.getName());
				table.addCell(supp.getAddress()); 
				table.addCell(supp.getPhone()); 
				table.addCell(supp.getEmail()); 
			}
			HTMLWorker htmlWorker = new HTMLWorker(doc);
		      String str = "<html><head></head><body>"+
		        "<h1 style='text-align: center;'>Supplier</h1>" +
		        "<br/>" +
		        "</body></html>";
		      htmlWorker.parse(new StringReader(str));
			doc.add(table);
	}
}
