package com.miniproject.pos.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.model.Items;
import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.model.User;
import com.miniproject.pos.service.ItemInventoryService;
import com.miniproject.pos.service.ItemVariantService;
import com.miniproject.pos.service.ItemsService;
import com.miniproject.pos.service.KategoriService;
import com.miniproject.pos.utils.ResponseMessage;

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
		rm.setData(itemInventoryService.getInventory(id));
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
	public List<ItemInventory> getVariant() {
		return itemInventoryService.getInventoryAll();
	}
	
	@RequestMapping(value="/delete-variant/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseMessage deleteVariant(@PathVariable String id) {
		ResponseMessage rm = new ResponseMessage();
		rm.setStatus("success");
		ItemVariant iv = itemVariantService.getItemVariantById(id);
		iv.setActive(false);
		itemVariantService.update(iv);
		rm.setKeterangan("Data berhasil dihapus");
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
		itemsService.save(items, user, outlet);
		rm.setStatus("success");
		rm.setKeterangan("Data Berhasil Disimpan");
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
		        rm.setStatus("danger");
			    rm.setKeterangan("Image gagal diupload");
		    }
		return rm;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseMessage update(@RequestBody(required=false) Items items) {
		ResponseMessage rm = new ResponseMessage();
		User user = new User();
		user.setId(httpSession.getAttribute("userId").toString());
		Outlet outlet = new Outlet();
		outlet.setId(httpSession.getAttribute("outletId").toString());
		itemsService.update(items, user, outlet);
		rm.setStatus("success");
		rm.setKeterangan("Data Berhasil Disimpan");
		return rm;
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handle(Exception e) {
	    System.out.println(e);
	}
}
