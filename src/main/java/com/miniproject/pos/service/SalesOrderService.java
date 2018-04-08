package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.ItemInventoryDao;
import com.miniproject.pos.dao.ItemVariantDao;
import com.miniproject.pos.dao.SalesOrderDao;
import com.miniproject.pos.dao.SalesOrderDetailDao;
import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.ItemVariant;
import com.miniproject.pos.model.SalesOrder;
import com.miniproject.pos.model.SalesOrderDetail;

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
	
	public void save(SalesOrder salesOrder) {
		
		List<SalesOrderDetail> sodss = salesOrder.getSalesOrderDetails();
		salesOrder.setSalesOrderDetails(null);
		salesOrderDao.save(salesOrder);
		
		
		for(SalesOrderDetail sod : sodss) {
			sod.setSalesOrder(salesOrder);
			salesOrderDetailDao.save(sod);
		}
	
		
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
			int ending = ii.getBegining() + ii.getPurchaseQty() - so - ii.getTransferStockQty();
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
}
