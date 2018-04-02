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
	
	public void save(Adjustment adjustment) {
		List<AdjustmentDetail> listAd = adjustment.getAdjustmentDetail();
		adjustment.setAdjustmentDetail(null);
		ad.save(adjustment);
		AdjustmentHistory ah = new AdjustmentHistory();
		ah.setAdjustmentId(adjustment);
		ah.setStatus(adjustment.getStatus());
		ahd.save(ah);
		for(AdjustmentDetail adjustmentD:listAd) {
			adjustmentD.setAdjustmentId(adjustment);
			add.save(adjustmentD);
		}
	}
	
	public List<AdjustmentDetail> getAdjustmentDetailByAdjustmentId(String id){
		return add.getAdjustmentDetailByAdjustmentId(id);
	}
	
	public void update(Adjustment adjustment) {
		AdjustmentHistory ah = new AdjustmentHistory();
		ah.setAdjustmentId(adjustment);
		ah.setStatus(adjustment.getStatus());
		ad.update(adjustment);
		if(adjustment.getStatus().equalsIgnoreCase("Approved")) {
			updateStock(adjustment);
		}
		ahd.save(ah);
	}
	
	public void updateStock(Adjustment adjustm) {
		List<AdjustmentDetail> listDetail = add.getAdjustmentDetailByAdjustmentId(adjustm.getId());
		for(AdjustmentDetail ad:listDetail) {
			ItemInventory ii = iid.getInventoryByVariantId(ad.getVariantId().getId());
			int adjust = ad.getActualStock() - ad.getInStock() + ii.getAdjustmentQty();
			int ending = ii.getBegining() + ii.getPurchaseQty() - ii.getSalesOrderQty() - ii.getTransferStockQty() + adjust;
			ii.setEndingQty(ending);
			ii.setAdjustmentQty(adjust);
			iid.update(ii);
		}
	}
	
	public List<Adjustment> getAllAdjustment(){
		return ad.getAllAdjustment();
	}
	
	public List<Adjustment> getAllAdjustmentByDate(Date startDate, Date endDate){
		return ad.getAllAdjustmentByDate(startDate, endDate);
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
}
