package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.ItemInventoryDao;
import com.miniproject.pos.dao.TransferStockDao;
import com.miniproject.pos.dao.TransferStockDetailDao;
import com.miniproject.pos.dao.TransferStockHistoryDao;
import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.model.TransferStock;
import com.miniproject.pos.model.TransferStockDetail;
import com.miniproject.pos.model.TransferStockHistory;
import com.miniproject.pos.model.User;

@Service
@Transactional
public class TransferStockSevice {

	@Autowired
	TransferStockDao tsd;
	
	@Autowired
	TransferStockDetailDao tsdd;
	
	@Autowired
	TransferStockHistoryDao tshd;
	
	@Autowired
	ItemInventoryDao iid;
	
	public void save(TransferStock ts, String outletId, String userId) {
		List<TransferStockDetail> listTsd = ts.getTransferStockDetail();
		ts.setTransferStockDetail(null);
		Outlet fromOutlet = new Outlet();
		fromOutlet.setId(outletId);
		User createdBy = new User();
		createdBy.setId(userId);
		ts.setFromOutlet(fromOutlet);
		ts.setCreatedBy(createdBy);
		tsd.save(ts);
		TransferStockHistory tsh = new TransferStockHistory();
		tsh.setTransferId(ts);
		tsh.setStatus(ts.getStatus());
		tsh.setCreatedBy(createdBy);
		tshd.save(tsh);
		for(TransferStockDetail transferD:listTsd) {
			transferD.setTransferId(ts);
			transferD.setCreatedBy(createdBy);
			tsdd.save(transferD);
		}
	}
	
	public void update(TransferStock ts, String userId) {
		TransferStockHistory tsh = new TransferStockHistory();
		User user = new User();
		user.setId(userId);
		tsh.setTransferId(ts);
		tsh.setStatus(ts.getStatus());
		tsh.setCreatedBy(user);
		ts.setModifiedBy(user);
		tsd.update(ts);
		if(ts.getStatus().equalsIgnoreCase("Approved")) {
			updateStock(ts, user);
		}
		tshd.save(tsh);
	}
	
	public void updateStock(TransferStock ts, User user) {
		List<TransferStockDetail> listDetail = tsdd.getTransferStockDetailByIdTransfer(ts.getId());
		for(TransferStockDetail tsDetail:listDetail) {
			ItemInventory fromIi = iid.getInventoryByVariantId(tsDetail.getVariantId().getId(), ts.getFromOutlet().getId());
			int endingFrom = fromIi.getBegining() + fromIi.getPurchaseQty() - fromIi.getSalesOrderQty() - tsDetail.getTransferQty() + fromIi.getAdjustmentQty();
			fromIi.setEndingQty(endingFrom);
			fromIi.setTransferStockQty(tsDetail.getTransferQty());
			fromIi.setModifiedBy(user);
			iid.update(fromIi);
			
			ItemInventory toIi = iid.getInventoryByVariantId(tsDetail.getVariantId().getId(), ts.getToOutlet().getId());
			if(toIi != null) {
			int adjustTo = toIi.getAdjustmentQty() + tsDetail.getTransferQty();
			int endingTo = toIi.getBegining() + toIi.getPurchaseQty() - toIi.getSalesOrderQty() - toIi.getTransferStockQty() + adjustTo;
			toIi.setEndingQty(endingTo);
			toIi.setAdjustmentQty(adjustTo);
			toIi.setModifiedBy(user);
			iid.update(toIi);
			}else {
				ItemInventory iiBaru = new ItemInventory();
				iiBaru.setAdjustmentQty(tsDetail.getTransferQty());
				iiBaru.setAlertAtQty(1);
				iiBaru.setBegining(0);
				iiBaru.setCreatedBy(user);
				iiBaru.setEndingQty(tsDetail.getTransferQty());
				iiBaru.setOutletId(ts.getToOutlet());
				iiBaru.setPurchaseQty(0);
				iiBaru.setSalesOrderQty(0);
				iiBaru.setTransferStockQty(0);
				iiBaru.setVariantId(tsDetail.getVariantId());
				iid.save(iiBaru);
			}
		}
	}
	
	public List<TransferStock> getAllTransferStock(String outletId, String toOutlet){
		if(toOutlet.equalsIgnoreCase("all")) {
			return tsd.getAllTransferStock(outletId);
		}else {
			return tsd.getAllTransferStockFilterToOutlet(outletId, toOutlet);
		}
	}
	
	public TransferStock getTransferStockById(String id) {
		TransferStock ts = tsd.getTransferStockById(id);
		ts.setTransferStockDetail(tsdd.getTransferStockDetailByIdTransfer(id));
		ts.setTransferStockHistory(tshd.getTransferStockHistoryByIdTransfer(id));
		return ts;
	}
	
	public TransferStock getTransferStock(String id) {
		return tsd.getTransferStockById(id);
	}
	
	public Long getCountTransferStock(String outletId) {
		return tsd.getCountTransferStock(outletId);
	}
}
