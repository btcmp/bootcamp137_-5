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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.miniproject.pos.utils.Formatter;

@Entity
@Table(name="pos_t_adjustment")
public class Adjustment {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="outlet_id")
	private Outlet outletId;
	
	@Size(max=255)
	@Column(nullable=true)
	private String notes;
	
	@Size(max=20)
	private String status;
	
	@ManyToOne
	@JoinColumn(name="created_by", nullable=true)
	private User createdBy;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable=true)
	private Date createdOn;
	
	@ManyToOne
	@JoinColumn(name="modified_by", nullable=true)
	private User modifiedBy;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on", nullable=true)
	private Date modifiedOn;

	@JsonManagedReference
	@OneToMany(mappedBy="adjustmentId")
	private List<AdjustmentDetail> adjustmentDetail;
	
	@OneToMany(mappedBy="adjustmentId")
	private List<AdjustmentHistory> adjustmentHistory;
	
	public Adjustment() {
		this.status = "Submitted";
	}
	
	public List<AdjustmentDetail> getAdjustmentDetail() {
		return adjustmentDetail;
	}

	public void setAdjustmentDetail(List<AdjustmentDetail> adjustmentDetail) {
		this.adjustmentDetail = adjustmentDetail;
	}

	public List<AdjustmentHistory> getAdjustmentHistory() {
		return adjustmentHistory;
	}

	public void setAdjustmentHistory(List<AdjustmentHistory> adjustmentHistory) {
		this.adjustmentHistory = adjustmentHistory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Outlet getOutletId() {
		return outletId;
	}

	public void setOutletId(Outlet outletId) {
		this.outletId = outletId;
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

	public String getCreatedOnFormatted() {
		return Formatter.date(createdOn, "dd/MM/yyyy HH:mm");
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
