package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.TransferStockDetail;

public interface TransferStockDetailDao {

	public void save(TransferStockDetail tsd);
	
	public List<TransferStockDetail> getTransferStockDetailByIdTransfer(String id);
}
