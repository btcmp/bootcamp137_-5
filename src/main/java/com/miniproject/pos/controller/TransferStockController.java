package com.miniproject.pos.controller;

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
import com.miniproject.pos.model.TransferStock;
import com.miniproject.pos.service.OutletService;
import com.miniproject.pos.service.TransferStockSevice;
import com.miniproject.pos.utils.ExportPdf;
import com.miniproject.pos.utils.ResponseMessage;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/transfer-stock")
public class TransferStockController {

	@Autowired
	TransferStockSevice tss;
	
	@Autowired
	OutletService os;
	
	@Autowired 
	private HttpSession httpSession;
	
	@RequestMapping("/index")
	public String index(Model model) {
		String outletId = httpSession.getAttribute("outletId").toString();
		model.addAttribute("outletId", os.getOne(outletId));
		model.addAttribute("title", "Data Transfer Stock");
		model.addAttribute("outlet", os.selectAll());
		return "transfer-stock/index";
	}
	
	@RequestMapping("/get-all-data/{toOutlet}")
	@ResponseBody
	public ResponseMessage getAll(@PathVariable String toOutlet) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		String outletId = httpSession.getAttribute("outletId").toString();
		rm.setData(tss.getAllTransferStock(outletId, toOutlet));
		return rm;
	}
	
	@RequestMapping("/get-transfer-detail/{id}")
	@ResponseBody
	public ResponseMessage getone(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		rm.setData(tss.getTransferStockById(id));
		return rm;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(@RequestBody TransferStock ts) {
		ResponseMessage rm = new ResponseMessage();
		String outletId = httpSession.getAttribute("outletId").toString();
		String userId = httpSession.getAttribute("userId").toString();
		tss.save(ts, outletId, userId);
		rm.setStatus("success");
		rm.setKeterangan("Data Berhasil Disimpan");
		return rm;
	}
	
	@RequestMapping(value="/status/{id}/{status}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(@PathVariable("id") String id, @PathVariable("status") String status) {
		ResponseMessage rm = new ResponseMessage();
		TransferStock ts = tss.getTransferStock(id);
		ts.setStatus(status);
		String userId = httpSession.getAttribute("userId").toString();
		tss.update(ts, userId);
		rm.setStatus("success");
		rm.setKeterangan("Status berhasil diubah");
		return rm;
	}
	
	@RequestMapping(value="/report", method = RequestMethod.GET)
	public void getReport(HttpServletResponse response){
	    Map<String, Object> param = new HashMap();
	    param.put("title", "List Data Transferstock");
	    ExportPdf ep = new ExportPdf();
	    String outletId = httpSession.getAttribute("outletId").toString();
	    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tss.getAllTransferStock(outletId, "all"));
		JasperPrint jasperPrint = ep.getObjectPdf("transferstock.jrxml", param, dataSource);
	    ep.sendPdfResponse(response, jasperPrint, "newbie");
	}
	
	@RequestMapping(value="/print/{id}", method = RequestMethod.GET)
	public void print(@PathVariable String id, HttpServletResponse response){
	    Map<String, Object> param = new HashMap();
	    param.put("title", "Data Detail TransferStock");
	    ExportPdf ep = new ExportPdf();
	    JRBeanArrayDataSource dataSource = new JRBeanArrayDataSource(new TransferStock[] {tss.getTransferStockById(id)});
		JasperPrint jasperPrint = ep.getObjectPdf("transferstock-detail.jrxml", param, dataSource);
	    ep.sendPdfResponse(response, jasperPrint, "newbie");
	}
}
