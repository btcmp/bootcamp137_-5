package com.miniproject.pos.controller;


import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.service.AdjustmentService;
import com.miniproject.pos.service.EmployeeService;
import com.miniproject.pos.service.ItemInventoryService;
import com.miniproject.pos.service.OutletService;
import com.miniproject.pos.service.PurchaseOrderService;
import com.miniproject.pos.service.SalesOrderService;
import com.miniproject.pos.service.TransferStockSevice;

@Controller
@RequestMapping
public class LoginController {

	@Autowired 
	 private HttpSession httpSession;
	
	@Autowired 
	 private EmployeeService empService;
	
	@Autowired 
	 private SalesOrderService soService;
	
	@Autowired
	private OutletService os;
	
	@Autowired
	private PurchaseOrderService pos;
	
	@Autowired
	private AdjustmentService as;
	
	@Autowired
	private TransferStockSevice tss;
	
	@Autowired
	private ItemInventoryService iis;
	
	@RequestMapping("/login")
	public String doLogin(Model model, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout){
		Map<String, String> pesan = new HashMap<>();
		if(error != null){
			pesan.put("status", "error");
			pesan.put("message", "Login gagal. Pastikan username dan password benar!!!");
		} 
		if(logout != null){
			pesan.put("status", "success");
			pesan.put("message", "Anda telah berhasil logout");
		}
		model.addAttribute("pesan", pesan);
		return "security/login";
	}
	
	@RequestMapping("/welcome")
	public String selectOutlet(Model model, Principal principal){
		model.addAttribute("employee", empService.getEmployeeByUsername(principal.getName()));
		return "security/asign-outlet";
	}
	
	@RequestMapping(value = "/welcome", method=RequestMethod.POST)
	public RedirectView proccessSession(@RequestParam("outlet") String outlet, @RequestParam("id") String id, Model model){
		Outlet outletModel = os.getOne(outlet);
		httpSession.setAttribute("userId", id);
		httpSession.setAttribute("outletId", outlet);
		httpSession.setAttribute("outletName", outletModel.getName());
		return new RedirectView("home");
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		model.addAttribute("title", "Dashboard");
		String outletId = httpSession.getAttribute("outletId").toString();
		Map<String, String> tamp = soService.getTotalSalesLast7Day(outletId);
		model.addAttribute("purchase", pos.countPurchaseOrder(outletId));
		model.addAttribute("item", iis.getCountRedStock(outletId));
		model.addAttribute("transfer", tss.getCountTransferStock(outletId));
		model.addAttribute("adjustment", as.countAdjustment(outletId));
		model.addAttribute("kategori", tamp.get("kategori"));
		model.addAttribute("total", tamp.get("total"));
		return "security/home";
	}
}
