package com.miniproject.pos.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.AdjustmentDao;
import com.miniproject.pos.dao.AdjustmentDetailDao;
import com.miniproject.pos.dao.AdjustmentHistoryDao;
import com.miniproject.pos.dao.ItemInventoryDao;
import com.miniproject.pos.model.Adjustment;
import com.miniproject.pos.model.AdjustmentDetail;
import com.miniproject.pos.model.AdjustmentHistory;
import com.miniproject.pos.model.ItemInventory;
import com.miniproject.pos.model.Outlet;
import com.miniproject.pos.model.User;

@Service
@Transactional
public class AdjustmentService {

	@Autowired
	AdjustmentDao ad;
	
	@Autowired
	ItemInventoryDao iid;
	
	@Autowired
	AdjustmentDetailDao add;
	
	@Autowired
	AdjustmentHistoryDao ahd;
	
	public void save(Adjustment adjustment, String outletId, String userId) {
		List<AdjustmentDetail> listAd = adjustment.getAdjustmentDetail();
		adjustment.setAdjustmentDetail(null);
		Outlet outlet = new Outlet();
		outlet.setId(outletId);
		User user = new User();
		user.setId(userId);
		adjustment.setCreatedBy(user);
		adjustment.setOutletId(outlet);
		ad.save(adjustment);
		AdjustmentHistory ah = new AdjustmentHistory();
		ah.setAdjustmentId(adjustment);
		ah.setCreatedBy(user);
		ah.setStatus(adjustment.getStatus());
		ahd.save(ah);
		for(AdjustmentDetail adjustmentD:listAd) {
			adjustmentD.setAdjustmentId(adjustment);
			adjustmentD.setCreatedBy(user);
			add.save(adjustmentD);
		}
	}
	
	public List<AdjustmentDetail> getAdjustmentDetailByAdjustmentId(String id){
		return add.getAdjustmentDetailByAdjustmentId(id);
	}
	
	public void update(Adjustment adjustment, String outletId, String userId) {
		AdjustmentHistory ah = new AdjustmentHistory();
		User user = new User();
		user.setId(userId);
		ah.setAdjustmentId(adjustment);
		ah.setStatus(adjustment.getStatus());
		ah.setCreatedBy(user);
		adjustment.setModifiedBy(user);
		ad.update(adjustment);
		if(adjustment.getStatus().equalsIgnoreCase("Approved")) {
			updateStock(adjustment, outletId, user);
		}
		ahd.save(ah);
	}
	
	public void updateStock(Adjustment adjustm, String outletId, User user) {
		List<AdjustmentDetail> listDetail = add.getAdjustmentDetailByAdjustmentId(adjustm.getId());
		for(AdjustmentDetail ad:listDetail) {
			ItemInventory ii = iid.getInventoryByVariantId(ad.getVariantId().getId(), outletId);
			int adjust = ad.getActualStock() - ad.getInStock() + ii.getAdjustmentQty();
			int ending = ii.getBegining() + ii.getPurchaseQty() - ii.getSalesOrderQty() - ii.getTransferStockQty() + adjust;
			ii.setEndingQty(ending);
			ii.setAdjustmentQty(adjust);
			ii.setModifiedBy(user);
			iid.update(ii);
		}
	}
	
	public List<Adjustment> getAllAdjustment(String outletId){
		return ad.getAllAdjustment(outletId);
	}
	
	public List<Adjustment> getAllAdjustmentByDate(Date startDate, Date endDate, String outletId){
		return ad.getAllAdjustmentByDate(startDate, endDate, outletId);
	}
	
	public Adjustment getAdjustmentById(String id){
		Adjustment aj = ad.getAdjustmentById(id);
		aj.setAdjustmentDetail(add.getAdjustmentDetailByAdjustmentId(id));
		aj.setAdjustmentHistory(ahd.getAdjustmentHistoryByAdjustmentId(id));
		return aj;
	}
	
	public Adjustment getAdjustment(String id){
		return ad.getAdjustment(id);
	}
	
	public int countAdjustment(String outletId) {
		return ad.countAdjustment(outletId);
	}
}
