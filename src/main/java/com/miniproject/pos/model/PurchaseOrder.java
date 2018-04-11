package com.miniproject.pos.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import com.miniproject.pos.utils.Formatter;

@Entity
@Table(name="pos_t_po")
public class PurchaseOrder {

	//properties
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	@ManyToOne
	private Supplier supplier;
	
	@Column(name="po_no", nullable=false)
	private String poNo;
	
	private String notes;
	
	@Column(name="grand_total", nullable=false)
	private float grandTotal;
	
	@Column(nullable=false)
	private String status;
	
	@ManyToOne
	@JoinColumn(name="created_by")
	private User createdBy;
	
	@CreationTimestamp
	@Column(name="created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@ManyToOne
	@JoinColumn(name="modified_by")
	private User modifiedBy;
	
	@UpdateTimestamp
	@Column(name="modified_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ModifiedOn;
	
	@OneToMany(mappedBy="purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	List<PurchaseOrderDetail> listPurchaseOrderDetail;
	
	@OneToMany(mappedBy="purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	List<PurchaseOrderHistory> listPurchaseOrderHistory;

	@OneToOne(fetch = FetchType.LAZY, mappedBy="purchaseOrder", cascade = CascadeType.ALL)
	private PurchaseRequest purchaseRequest;
	
	
	//setters and getters
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public float getGrandTotal() {
		return grandTotal;
	}
	
	public String getGrandTotalFormatted() {
		return Formatter.currency((double) grandTotal);
	}

	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}
	
	public String getCreatedOnFormatted() {
		return Formatter.date(createdOn, "dd/MM/yyyy");
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return ModifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		ModifiedOn = modifiedOn;
	}

	public List<PurchaseOrderDetail> getListPurchaseOrderDetail() {
		return listPurchaseOrderDetail;
	}

	public void setListPurchaseOrderDetail(List<PurchaseOrderDetail> listPurchaseOrderDetail) {
		this.listPurchaseOrderDetail = listPurchaseOrderDetail;
	}

	public List<PurchaseOrderHistory> getListPurchaseOrderHistory() {
		return listPurchaseOrderHistory;
	}

	public void setListPurchaseOrderHistory(List<PurchaseOrderHistory> listPurchaseOrderHistory) {
		this.listPurchaseOrderHistory = listPurchaseOrderHistory;
	}
}
