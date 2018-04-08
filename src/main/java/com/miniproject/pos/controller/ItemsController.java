package com.miniproject.pos.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.model.Items;
import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.model.User;
import com.miniproject.pos.service.ItemInventoryService;
import com.miniproject.pos.service.ItemVariantService;
import com.miniproject.pos.service.ItemsService;
import com.miniproject.pos.service.KategoriService;
import com.miniproject.pos.utils.ExportPdf;
import com.miniproject.pos.utils.ResponseMessage;
import com.miniproject.pos.utils.UniqueException;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/items")
public class ItemsController {

	@Autowired
	ItemsService itemsService;
	
	@Autowired
	ItemVariantService itemVariantService;
	
	@Autowired
	ItemInventoryService itemInventoryService;
	
	@Autowired
	KategoriService ks;
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired 
	 private HttpSession httpSession;
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("category", ks.selectAll());
		model.addAttribute("title", "Data Items");
		return "items/index";
	}
	
	@RequestMapping("/get-all-data")
	@ResponseBody
	public ResponseMessage getAllData() {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		String outletId = httpSession.getAttribute("outletId").toString();
		rm.setData(itemVariantService.getAllItemVariant(outletId));
		return rm;
	}
	
	@RequestMapping("/get-all-data/{id}")
	@ResponseBody
	public ResponseMessage getAllData(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		String outletId = httpSession.getAttribute("outletId").toString();
		rm.setData(itemVariantService.getAllItemVariant(outletId, id));
		return rm;
	}
	
	@RequestMapping("/get-one/{id}")
	@ResponseBody
	public ResponseMessage getOne(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		String outletId = httpSession.getAttribute("outletId").toString();
		rm.setData(itemVariantService.getItemVariantByItem(id, outletId));
		return rm;
	}
	
	@RequestMapping("/get-all-variant")
	@ResponseBody
	public List<ItemVariant> getVariant() {
		String outletId = httpSession.getAttribute("outletId").toString();
		return itemVariantService.getAllItemVariant(outletId);
	}
	
	@RequestMapping(value="/delete-variant/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseMessage deleteVariant(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		ItemVariant iv = itemVariantService.getItemVariantById(id);
		if(itemVariantService.nonActiveVariant(iv)) {
			rm.setStatus("success");
			rm.setKeterangan("Data berhasil dihapus");
		}else {
			rm.setStatus("error");
			rm.setKeterangan("Gagal menghapus data");
		}
		return rm;
	}
	
	@RequestMapping(value="/delete-items/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseMessage deleteItems(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		Items items = itemsService.getItemsById(id);
		if(itemsService.nonActiveItems(items)) {
			rm.setStatus("success");
			rm.setKeterangan("Data berhasil dihapus");
		}else {
			rm.setStatus("error");
			rm.setKeterangan("Gagal menghapus data");
		}
		return rm;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(@RequestBody Items items) {
		ResponseMessage rm = new ResponseMessage();
		User user = new User();
		user.setId(httpSession.getAttribute("userId").toString());
		Outlet outlet = new Outlet();
		outlet.setId(httpSession.getAttribute("outletId").toString());
		try {
			itemsService.save(items, user, outlet);
			rm.setStatus("success");
			rm.setKeterangan("Data Berhasil Disimpan");
		}catch(UniqueException e) {
			rm.setError(e.error());
			rm.setStatus("error");
			rm.setKeterangan("Data gagal disimpan");
		}
		return rm;
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage upload(@RequestParam("file") MultipartFile file) {
	    ResponseMessage rm = new ResponseMessage();
		try {
			String tamp = file.getOriginalFilename().toString();
			String[] type = tamp.split("\\.");
			int len = type.length;
			String name = (System.currentTimeMillis())+"."+type[len-1];
			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
		    File destination = new File(servletContext.getRealPath("/assets/img/"+name));
		    ImageIO.write(src, type[len-1], destination);
		    rm.setData(name);
		    rm.setStatus("success");
		    rm.setKeterangan("Image berhasil diupload");
		    } catch(Exception e) {
		        e.printStackTrace();
		        rm.setStatus("error");
			    rm.setKeterangan("Image gagal diupload");
		    }
		return rm;
	}
	
	@RequestMapping(value="/report", method = RequestMethod.GET)
	public void getReport(HttpServletResponse response){
	    Map<String, Object> param = new HashMap();
	    param.put("title", "List Data Items");
	    ExportPdf ep = new ExportPdf();
	    String outletId = httpSession.getAttribute("outletId").toString();
	    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemVariantService.getAllItemVariant(outletId));
		JasperPrint jasperPrint = ep.getObjectPdf("items.jrxml", param, dataSource);
	    ep.sendPdfResponse(response, jasperPrint, "newbie");
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseMessage update(@RequestBody(required=false) Items items) {
		ResponseMessage rm = new ResponseMessage();
		User user = new User();
		user.setId(httpSession.getAttribute("userId").toString());
		Outlet outlet = new Outlet();
		outlet.setId(httpSession.getAttribute("outletId").toString());
		try {
			itemsService.update(items, user, outlet);
			rm.setStatus("success");
			rm.setKeterangan("Data Berhasil Diupdate");
		}catch(UniqueException e) {
			rm.setError(e.error());
			rm.setStatus("error");
			rm.setKeterangan("Data gagal diupdate");
		}
		return rm;
	}
	
//	@ExceptionHandler
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	public void handle(Exception e) {
//	    System.out.println(e);
//	}
}
