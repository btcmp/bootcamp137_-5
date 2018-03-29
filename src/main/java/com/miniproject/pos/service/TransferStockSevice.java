package com.miniproject.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.pos.dao.TransferStockDao;
import com.miniproject.pos.dao.TransferStockDetailDao;
import com.miniproject.pos.dao.TransferStockHistoryDao;
import com.miniproject.pos.model.TransferStock;
import com.miniproject.pos.model.TransferStockDetail;
import com.miniproject.pos.model.TransferStockHistory;

@Service
@Transactional
public class TransferStockSevice {

	@Autowired
	TransferStockDao tsd;
	
	@Autowired
	TransferStockDetailDao tsdd;
	
	@Autowired
	TransferStockHistoryDao tshd;
	
	public void save(TransferStock ts) {
		List<TransferStockDetail> listTsd = ts.getTransferStockDetail();
		ts.setTransferStockDetail(null);
		tsd.save(ts);
		TransferStockHistory tsh = new TransferStockHistory();
		tsh.setTransferId(ts);
		tsh.setStatus(ts.getStatus());
		tshd.save(tsh);
		for(TransferStockDetail transferD:listTsd) {
			transferD.setTransferId(ts);
			tsdd.save(transferD);
		}
	}
	
	public void update(TransferStock ts) {
		TransferStockHistory tsh = new TransferStockHistory();
		tsh.setTransferId(ts);
		tsh.setStatus(ts.getStatus());
		tshd.save(tsh);
		tsd.update(ts);
	}
	
	public List<TransferStock> getAllTransferStock(){
		return tsd.getAllTransferStock();
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
}
