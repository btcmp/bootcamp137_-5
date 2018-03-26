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
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pos_t_transfer_stock")
public class TransferStock {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="from_outlet")
	private Outlet fromOutlet;
	
	@ManyToOne
	@JoinColumn(name="to_outlet")
	private Outlet toOutlet;
	
	@Column(nullable=true)
	@Size(max=255)
	private String notes;
	
	@Size(max=20)
	private String status;
	
	@ManyToOne
	@JoinColumn(name="created_by", nullable=true)
	private User createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable=true)
	private Date createdOn;
	
	@ManyToOne
	@JoinColumn(name="modified_by", nullable=true)
	private User modifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on", nullable=true)
	private Date modifiedOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Outlet getFromOutlet() {
		return fromOutlet;
	}

	public void setFromOutlet(Outlet fromOutlet) {
		this.fromOutlet = fromOutlet;
	}

	public Outlet getToOutlet() {
		return toOutlet;
	}

	public void setToOutlet(Outlet toOutlet) {
		this.toOutlet = toOutlet;
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
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
}
