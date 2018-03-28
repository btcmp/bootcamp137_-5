package com.miniproject.pos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="pos_item_inventory")
public class ItemInventory {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	@ManyToOne
    @JoinColumn(name = "variant_id")
	private ItemVariant variantId;
	
	@ManyToOne
	@JoinColumn(name="outlet_id")
	private Outlet outletId;
	
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
	
	@ManyToOne
	@JoinColumn(name="created_by", nullable=true)
	private User createdBy;
	
	@CreationTimestamp
	@Column(name="created_on", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@ManyToOne
	@JoinColumn(name="modified_by", nullable=true)
	private User modifiedBy;
	
	@UpdateTimestamp
	@Column(name="modified_on", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedOn;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public ItemVariant getVariantId() {
		return variantId;
	}

	public void setVariantId(ItemVariant variantId) {
		this.variantId = variantId;
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

	public Outlet getOutletId() {
		return outletId;
	}

	public void setOutletId(Outlet outletId) {
		this.outletId = outletId;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
}
