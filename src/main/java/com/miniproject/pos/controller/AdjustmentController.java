package com.miniproject.pos.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniproject.pos.model.Adjustment;
import com.miniproject.pos.service.AdjustmentService;
import com.miniproject.pos.service.OutletService;
import com.miniproject.pos.utils.ExportPdf;
import com.miniproject.pos.utils.ResponseMessage;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/adjustment")
public class AdjustmentController {

	@Autowired
	AdjustmentService as;
	
	@Autowired
	OutletService os;

	@Autowired 
	private HttpSession httpSession;
	
	@RequestMapping("/index")
	public String index(Model model) {
		String outletId = httpSession.getAttribute("outletId").toString();
		model.addAttribute("outlet", os.getOne(outletId));
		model.addAttribute("title", "Data Adjustment");
		return "adjustment/index";
	}
	
	@RequestMapping("get-all-data/{startDate}/{endDate}")
	@ResponseBody
	public ResponseMessage getAll(@PathVariable String startDate, @PathVariable String endDate) {
		ResponseMessage rm = new ResponseMessage();
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
		  Date dateStart = formatter.parse(startDate);
		  Date dateEnd = formatter.parse(endDate);
		  rm.setStatus("success");
		  String outletId = httpSession.getAttribute("outletId").toString();
		  rm.setData(as.getAllAdjustmentByDate(dateStart, dateEnd, outletId));
		} catch (ParseException e) {
		  e.printStackTrace();
		}
		return rm;
	}
	
	@RequestMapping("get-all-data")
	@ResponseBody
	public ResponseMessage getAll() {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		String outletId = httpSession.getAttribute("outletId").toString();
		rm.setData(as.getAllAdjustment(outletId));
		return rm;
	}
	
	@RequestMapping(value="/report", method = RequestMethod.GET)
	public void getReport(HttpServletResponse response){
	    Map<String, Object> param = new HashMap();
	    param.put("title", "List Data Adjustment");
	    ExportPdf ep = new ExportPdf();
	    String outletId = httpSession.getAttribute("outletId").toString();
	    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(as.getAllAdjustment(outletId));
		JasperPrint jasperPrint = ep.getObjectPdf("adjustment.jrxml", param, dataSource);
	    ep.sendPdfResponse(response, jasperPrint, "newbie");
	}
	
	@RequestMapping(value="/print/{id}", method = RequestMethod.GET)
	public void print(@PathVariable String id, HttpServletResponse response){
	    Map<String, Object> param = new HashMap();
	    param.put("title", "List Data Adjustment");
	    ExportPdf ep = new ExportPdf();
	    JRBeanArrayDataSource dataSource = new JRBeanArrayDataSource(new Adjustment[] {as.getAdjustmentById(id)});
		JasperPrint jasperPrint = ep.getObjectPdf("adjustment-detail.jrxml", param, dataSource);
	    ep.sendPdfResponse(response, jasperPrint, "newbie");
	}
	
	@RequestMapping("get-adjustment-detail/{id}")
	@ResponseBody
	public ResponseMessage getAdjustmentDetail(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		rm.setData(as.getAdjustmentById(id));
		return rm;
	}
	
	@RequestMapping("get-adjustment/{id}")
	@ResponseBody
	public ResponseMessage getAdjustment(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		rm.setData(as.getAdjustmentById(id));
		return rm;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(@RequestBody Adjustment adjustment) {
		ResponseMessage rm = new ResponseMessage();
		String outletId = httpSession.getAttribute("outletId").toString();
		String userId = httpSession.getAttribute("userId").toString();
		as.save(adjustment, outletId, userId);
		rm.setStatus("success");
		rm.setKeterangan("Data Berhasil Disimpan");
		return rm;
	}
	
	@RequestMapping(value="/status/{id}/{status}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(@PathVariable("id") String id, @PathVariable("status") String status) {
		ResponseMessage rm = new ResponseMessage();
		Adjustment adj = as.getAdjustment(id);
		String outletId = httpSession.getAttribute("outletId").toString();
		String userId = httpSession.getAttribute("userId").toString();
		adj.setStatus(status);
		as.update(adj, outletId, userId);
		rm.setStatus("success");
		rm.setKeterangan("Status berhasil diubah");
		return rm;
	}
}
