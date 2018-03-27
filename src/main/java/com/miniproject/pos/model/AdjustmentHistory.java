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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.miniproject.pos.utils.Formatter;

@Entity
@Table(name="pos_t_adjustment_history")
public class AdjustmentHistory {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="adjustment_id")
	private Adjustment adjustmentId;
	
	@Size(max=20)
	private String status;
	
	@ManyToOne
	@JoinColumn(name="created_by", nullable=true)
	private User createdBy;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable=true)
	private Date createdOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Adjustment getAdjustmentId() {
		return adjustmentId;
	}

	public void setAdjustmentId(Adjustment adjustmentId) {
		this.adjustmentId = adjustmentId;
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
	
	
}
