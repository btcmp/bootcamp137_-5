package com.miniproject.pos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pos_t_pr_history")
public class PurchaseRequestHistory {
	
	//properties
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	@ManyToOne
	private PurchaseRequest purchaseRequest;
	
	@Column(nullable=false)
	private String status;
	
	@ManyToOne
	@Column(name="created_by")
	private User createdBy;
	
	@Column(name="created_on")
	private Date createdOn;

	
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
	
}
