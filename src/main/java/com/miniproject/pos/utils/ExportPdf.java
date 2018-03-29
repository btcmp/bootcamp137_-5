package com.miniproject.pos.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ExportPdf {

	public JasperPrint getObjectPdf(String path, Map<String, Object> parameters, JRDataSource datasource) {
	    JasperPrint jasperPrint = null;

	    InputStream inStream = null;
	    try {
	        inStream = getClass().getClassLoader().getResourceAsStream(path);
	        JasperDesign jasperDesign = JRXmlLoader.load(inStream);
	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
	    } catch (JRException jre) {
	        System.out.println("error");
	    } finally {
	        if (inStream != null) {
	            try {
	                inStream.close();
	            } catch (IOException e) {
	                System.out.println("eroro");
	            }
	        }
	    }

	    return jasperPrint;
	}

	public void sendPdfResponse(HttpServletResponse response, JasperPrint jasperPrint, String fileName){

	    //Remove all whitespace from file name
	    fileName.replaceAll("\\s","");

	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {
	        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	    } catch (JRException e1) {
	        e1.printStackTrace();
	    }

	    byte[] data = out.toByteArray();

	    response.setContentType("application/pdf");
	    //To make it a download change "inline" to "attachment"
	    response.setHeader("Content-disposition", "inline; filename=" + fileName + ".pdf");
	    response.setContentLength(data.length);

	    try {
	        response.getOutputStream().write(data);
	        response.getOutputStream().flush();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
}
