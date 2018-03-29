package com.miniproject.pos.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniproject.pos.model.Adjustment;
import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.service.AdjustmentService;
import com.miniproject.pos.utils.ExportPdf;
import com.miniproject.pos.utils.ResponseMessage;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/adjustment")
public class AdjustmentController {

	@Autowired
	AdjustmentService as;
	
	@RequestMapping("/index")
	public String index(Model model) {
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
		  rm.setData(as.getAllAdjustmentByDate(dateStart, dateEnd));
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
		rm.setData(as.getAllAdjustment());
		return rm;
	}
	
	@RequestMapping(value="/report", method = RequestMethod.GET)
	public void getReport(HttpServletResponse response){
	    Map<String, Object> param = new HashMap();
	    param.put("title", "List Data Adjustment");
	    ExportPdf ep = new ExportPdf();
	    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(as.getAllAdjustment());
		JasperPrint jasperPrint = ep.getObjectPdf("adjustment.jrxml", param, dataSource);
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
		as.save(adjustment);
		rm.setStatus("success");
		rm.setKeterangan("Data Berhasil Disimpan");
		return rm;
	}
	
	@RequestMapping(value="/status/{id}/{status}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(@PathVariable("id") String id, @PathVariable("status") String status) {
		ResponseMessage rm = new ResponseMessage();
		Adjustment adj = as.getAdjustment(id);
		adj.setStatus(status);
		as.update(adj);
		rm.setStatus("success");
		rm.setKeterangan("Status berhasil diubah");
		return rm;
	}
}
