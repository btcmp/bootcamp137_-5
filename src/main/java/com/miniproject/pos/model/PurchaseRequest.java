package com.miniproject.pos.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pos_t_pr")
public class PurchaseRequest {
	
	// properties
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	@ManyToOne
	private Outlet outlet;
	
	@Column(name="ready_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date readyTime;
	
	@Column(name="pr_no", nullable=false)
	private String prNo;
	
	private String notes;
	
	@Column(nullable=false)
	private String status;
	
	@ManyToOne
	@Column(name="created_by")
	private User createdBy;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@ManyToOne
	@Column(name="modified_by")
	private User modifiedBy;
	
	@Column(name="modified_on")
	private Date ModifiedOn;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="purchaseRequest", cascade = CascadeType.ALL, orphanRemoval = true)
	List<PurchaseRequestDetail> listPurchaseRequestDetail;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="purchaseRequest", cascade = CascadeType.ALL, orphanRemoval = true)
	List<PurchaseRequestHistory> listPurchaseRequestHistory;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy="purchaseRequest", cascade = CascadeType.ALL)
	private PurchaseOrder purchaseOrder;
	
	//setters and getters
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public Date getReadyTime() {
		return readyTime;
	}

	public void setReadyTime(Date readyTime) {
		this.readyTime = readyTime;
	}

	public String getPrNo() {
		return prNo;
	}

	public void setPrNo(String prNo) {
		this.prNo = prNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public List<PurchaseRequestDetail> getListPurchaseRequestDetail() {
		return listPurchaseRequestDetail;
	}

	public void setListPurchaseRequestDetail(List<PurchaseRequestDetail> listPurchaseRequestDetail) {
		this.listPurchaseRequestDetail = listPurchaseRequestDetail;
	}

	public List<PurchaseRequestHistory> getListPurchaseRequestHistory() {
		return listPurchaseRequestHistory;
	}

	public void setListPurchaseRequestHistory(List<PurchaseRequestHistory> listPurchaseRequestHistory) {
		this.listPurchaseRequestHistory = listPurchaseRequestHistory;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
}
