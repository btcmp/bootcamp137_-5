package com.miniproject.pos.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.util.Calendar;
import com.miniproject.pos.dao.ItemInventoryDao;
import com.miniproject.pos.dao.ItemVariantDao;
import com.miniproject.pos.dao.SalesOrderDao;
import com.miniproject.pos.dao.SalesOrderDetailDao;
import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.model.SalesOrder;
import com.miniproject.pos.model.SalesOrderDetail;
import com.miniproject.pos.model.User;

@Service
@Transactional
public class SalesOrderService {

	@Autowired
	SalesOrderDao salesOrderDao;
	
	@Autowired
	SalesOrderDetailDao salesOrderDetailDao;
	
	@Autowired
	ItemVariantDao itemVariantDao;
	
	@Autowired
	ItemInventoryDao itemInveentoryDao;
	
	public String save(SalesOrder salesOrder, User user, Outlet outlet) {
		
		List<SalesOrderDetail> sodss = salesOrder.getSalesOrderDetails();
		salesOrder.setSalesOrderDetails(null);
		salesOrder.setModifiedOn(null);
		salesOrder.setCreatedBy(user);
		salesOrderDao.save(salesOrder);
	
		for(SalesOrderDetail sod : sodss) {
			ItemVariant iv = new ItemVariant();
			iv.setId(sod.getItemVariant().getId());
			SalesOrderDetail salesOrderDetails = new SalesOrderDetail();
			salesOrderDetails.setItemVariant(iv);
			salesOrderDetails.setSalesOrder(salesOrder);
			salesOrderDetails.setModifiedOn(null);
			salesOrderDetails.setCreatedOn(sod.getCreatedOn());
			salesOrderDetails.setCreatedBy(user);
			salesOrderDetails.setUnitCost(sod.getUnitCost());
			salesOrderDetails.setSubTotal(sod.getSubTotal());
			salesOrderDetails.setQty(sod.getQty());
			salesOrderDetailDao.save(salesOrderDetails);
		}
	
		return salesOrder.getId();
	}
	
	public void delete(SalesOrder salesOrder) {
		salesOrderDao.delete(salesOrder);
	}
	
	public void update(SalesOrder salesOrder, String outletId) {
		List<SalesOrderDetail> salesOrderDetail = salesOrder.getSalesOrderDetails();
		System.out.println("sod= "+salesOrderDetail);
		for (SalesOrderDetail salesOrderDetail2 : salesOrderDetail) {
			ItemInventory ii = itemInveentoryDao.getInventoryByVariantId(salesOrderDetail2.getItemVariant().getId(), outletId);
			int so = ii.getSalesOrderQty() + salesOrderDetail2.getQty();
			int ending = ii.getBegining() + ii.getPurchaseQty() - so - ii.getTransferStockQty() + ii.getAdjustmentQty();
			ii.setSalesOrderQty(so);
			ii.setEndingQty(ending);
			itemInveentoryDao.update(ii);
		}
	}
	
	public SalesOrder getOne(String id) {
		return salesOrderDao.getOne(id);
	}
	
	public List<SalesOrder> selectAll(){
		return salesOrderDao.selectAll();
	}

	public List<SalesOrderDetail> getSalesOrderDetailByIdSalesOrder(String soId) {
		// TODO Auto-generated method stub
		return salesOrderDao.getSalesOrderDetailByIdSalesOrder(soId);
	}	
	
	public Map<String, List> getTotalSalesLast7Day(){
		Map<String, Double> data = new HashMap<>();
		Date date = new Date();
		salesOrderDao.getTotalSalesLast7Day(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for(int i=0;i<6;i++) {
			cal.add(Calendar.DATE, -1);
			Date dateEnd = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			System.out.println(sdf.format(dateEnd));
		}
		return null;
	}
}
