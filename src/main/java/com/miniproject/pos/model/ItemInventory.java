package com.miniproject.pos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pos_item_inventory")
public class ItemInventory {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	//private String variantId;
	//private String outletId;
	private int begining;
	
	@Column(name="purchase_qty", nullable=true)
	private int purchaseQty;
	
	@Column(name="sales_order_qty", nullable=true)
	private int salesOrderQty;
	
	@Column(name="transfer_stock_qty", nullable=true)
	private int transferStockQty;
	
	@Column(name="adjustment_qty", nullable=true)
	private int adjustmentQty;
	
	@Column(name="ending_qty")
	private int endingQty;
	
	@Column(name="alert_at_qty")
	private int alertAtQty;
	
	//@Column(name="created_by", nullable=true)
	//private long createdBy;
	
	@Column(name="created_on", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	//@Column(name="modified_by", nullable=true)
	//private long modifiedBy;
	
	@Column(name="modified_on", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedOn;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getBegining() {
		return begining;
	}
	
	public void setBegining(int begining) {
		this.begining = begining;
	}
	
	public int getPurchaseQty() {
		return purchaseQty;
	}
	
	public void setPurchaseQty(int purchaseQty) {
		this.purchaseQty = purchaseQty;
	}
	
	public int getSalesOrderQty() {
		return salesOrderQty;
	}
	
	public void setSalesOrderQty(int salesOrderQty) {
		this.salesOrderQty = salesOrderQty;
	}
	
	public int getTransferStockQty() {
		return transferStockQty;
	}
	
	public void setTransferStockQty(int transferStockQty) {
		this.transferStockQty = transferStockQty;
	}
	
	public int getAdjustmentQty() {
		return adjustmentQty;
	}
	
	public void setAdjustmentQty(int adjustmentQty) {
		this.adjustmentQty = adjustmentQty;
	}
	
	public int getEndingQty() {
		return endingQty;
	}
	
	public void setEndingQty(int endingQty) {
		this.endingQty = endingQty;
	}
	
	public int getAlertAtQty() {
		return alertAtQty;
	}
	
	public void setAlertAtQty(int alertAtQty) {
		this.alertAtQty = alertAtQty;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public Date getModifiedOn() {
		return modifiedOn;
	}
	
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
}
