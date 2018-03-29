package com.miniproject.pos.dao;

import java.util.List;

import com.miniproject.pos.model.TransferStockHistory;

public interface TransferStockHistoryDao {
	
	public void save(TransferStockHistory tsh);
	
	public List<TransferStockHistory> getTransferStockHistoryByIdTransfer(String id);
}
