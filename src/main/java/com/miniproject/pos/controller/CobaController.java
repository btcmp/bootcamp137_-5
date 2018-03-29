package com.miniproject.pos.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.service.ItemInventoryService;
import com.miniproject.pos.service.ItemsService;
import com.miniproject.pos.utils.ExportPdf;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

@Controller
@RequestMapping("/coba")
public class CobaController {

	private JasperReport jasperReport;

    private JasperPrint jasperPrint;
	
	@Autowired
	ItemInventoryService iis;
	
	@RequestMapping(value="/report", method = RequestMethod.GET)
	public void getReport(HttpServletResponse response){
	    Map<String, Object> coba = new HashMap();
	    List<ItemInventory> ii = iis.getAllItemInventory();
	    ItemVariant vari = new ItemVariant();
	    vari.setName("saityem");
	    for(ItemInventory item:ii) {
	    	item.setVariantId(vari);
	    }
	    coba.put("title", "ini Judul");
	    ExportPdf ep = new ExportPdf();
	    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ii);
		JasperPrint jasperPrint = ep.getObjectPdf("coba.jrxml", coba, dataSource);

	    ep.sendPdfResponse(response, jasperPrint, "Object-123");
	}

	
}
