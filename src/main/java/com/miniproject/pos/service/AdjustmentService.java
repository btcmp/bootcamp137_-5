package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.AdjustmentDao;
import com.miniproject.pos.dao.AdjustmentDetailDao;
import com.miniproject.pos.dao.AdjustmentHistoryDao;
import com.miniproject.pos.model.Adjustment;
import com.miniproject.pos.model.AdjustmentDetail;
import com.miniproject.pos.model.AdjustmentHistory;

@Service
@Transactional
public class AdjustmentService {

	@Autowired
	AdjustmentDao ad;
	
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
		ad.update(adjustment);
	}
	
	public List<Adjustment> getAllAdjustment(){
		return ad.getAllAdjustment();
	}
	
	public Adjustment getAdjustmentById(String id){
		Adjustment aj = ad.getAdjustmentById(id);
		return aj;
	}
}
