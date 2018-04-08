package com.miniproject.pos.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
	@Temporal(TemporalType.DATE)
	private Date readyTime;
	
	@Column(name="pr_no", nullable=false)
	private String prNo;
	
	private String notes;
	
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

	@OneToMany(mappedBy="purchaseRequest", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	List<PurchaseRequestDetail> listPurchaseRequestDetail;
	
	@OneToMany(mappedBy="purchaseRequest", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	List<PurchaseRequestHistory> listPurchaseRequestHistory;
	
	@OneToOne(fetch = FetchType.EAGER)
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
