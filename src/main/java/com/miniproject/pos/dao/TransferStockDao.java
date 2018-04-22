package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.TransferStock;

public interface TransferStockDao {

	public void save(TransferStock ts);
	
	public void update(TransferStock ts);
	
	public TransferStock getTransferStockById(String id);
	
	public List<TransferStock> getAllTransferStock(String outletId);
	
	public List<TransferStock> getAllTransferStockFilterToOutlet(String outletId, String toOutlet);
	
	public Long getCountTransferStock(String outletId);
}
