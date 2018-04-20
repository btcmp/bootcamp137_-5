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
		salesOrder.setOutletId(outlet);
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
	
	public Map<String, String> getTotalSalesLast7Day(String outletId){
		Map<String, Double> data = new HashMap<>();
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -7);
		Map<String, Double> tampung = salesOrderDao.getTotalSalesLast7Day(cal.getTime(), outletId);
		StringBuilder keyString = new StringBuilder();
		StringBuilder valueString = new StringBuilder();
		for(int i=0;i<7;i++) {
			cal.add(Calendar.DATE, 1);
			Date dateEnd = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			if(tampung.containsKey(sdf.format(dateEnd))) {
				valueString.append(tampung.get(sdf.format(dateEnd))).append(",");
				keyString.append("'").append(sdf.format(dateEnd)).append("',");
			}else {
				valueString.append("0").append(",");
				keyString.append("'").append(sdf.format(dateEnd)).append("',");
			}
		}
		valueString.deleteCharAt(valueString.length()-1);
		keyString.deleteCharAt(keyString.length()-1);
		Map<String, String> hasil = new HashMap();
		hasil.put("kategori", keyString.toString());
		hasil.put("total", valueString.toString());
		return hasil;
	}
	
	
}
