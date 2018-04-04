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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="pos_t_adjustment_detail")
public class AdjustmentDetail {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="adjustment_id")
	private Adjustment adjustmentId;
	
	@ManyToOne
	@JoinColumn(name="variant_id")
	private ItemVariant variantId;
	
	@Column(name="in_stock")
	private int inStock;
	
	@Column(name="actual_stock")
	private int actualStock;
	
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
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on", nullable=true)
	private Date modifiedOn;

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

	public ItemVariant getVariantId() {
		return variantId;
	}

	public void setVariantId(ItemVariant variantId) {
		this.variantId = variantId;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public int getActualStock() {
		return actualStock;
	}

	public void setActualStock(int actualStock) {
		this.actualStock = actualStock;
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
