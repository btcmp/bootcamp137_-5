package com.miniproject.pos.controller;

import java.io.StringReader;
import java.util.Date;
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
import com.miniproject.pos.model.SalesOrderDetail;

public class PDFViewSalesOrder extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<SalesOrderDetail> sods = (List<SalesOrderDetail>) model.get("sos");
		
		HTMLWorker htmlWorker = new HTMLWorker(doc);
		String customer=null;
		Date date=null;
		String total = null;
		
		for(SalesOrderDetail sodox : sods) {
			customer=sodox.getSalesOrder().getCustomer().getName();
			date=sodox.getCreatedOn();
			total="Rp."+String.valueOf(sodox.getSalesOrder().getGrandTotal()) ;
		}
		
			PdfPTable table = new PdfPTable(4);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		      	table.addCell("Item Name");
		      	table.addCell("Price");
		      	table.addCell("Quantity");
		      	table.addCell("Total");
				for (SalesOrderDetail salesOrderDetail : sods) {
					table.addCell(salesOrderDetail.getItemVariant().getItemId().getName()+" - "+salesOrderDetail.getItemVariant().getName());
					table.addCell("Rp."+String.valueOf(salesOrderDetail.getUnitCost())); 
					table.addCell(String.valueOf(salesOrderDetail.getQty())); 
					table.addCell("Rp."+String.valueOf(salesOrderDetail.getSubTotal())); 
				}
				table.addCell("Total Pembayaran");
			    table.addCell("");
			    table.addCell("");
			    table.addCell(total);
				
				
				String str = "<html><head></head><body>"+
				        "<h1 style='text-align: center;'>Sales Order</h1>" +
				        "<br/>" +
				        "<p style='text-align: left ;'>Customer : "+customer+"</p>"+
				        "<p style=''text-align: right ;'>Date : "+date+"</p>"+
				        "<br/>" +
				        "</body></html>";
				htmlWorker.parse(new StringReader(str));
				doc.add(table);

	}
}
