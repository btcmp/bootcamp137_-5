package com.miniproject.pos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pos_item_variant")
public class ItemVariant {

	//properties
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	@Column(nullable=false)
	@Size(max=255)
	private String name;
	
	@Column(nullable=false)
	@Size(max=50)
	private String sku;
	
	@Column(nullable=false)
	private double price;
	
	/*@Column(name="created_by", nullable=true)
	private long createdBy;*/
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable=true)
	private Date createdOn;
	
	/*@Column(name="modified_by", nullable=true)
	private long modifiedBy;*/
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on", nullable=true)
	private Date modifiedOn;
	
	@Column(nullable=false)
	private boolean active;

	@ManyToOne
	@JoinColumn(name="item_id")
	private Items itemId;

	@OneToOne(mappedBy = "variantId", fetch = FetchType.LAZY, optional = false)
	private ItemInventory inventory;
	
	//setter n getter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	

	/*public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}*/

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/*public long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}*/

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Items getItems() {
		return itemId;
	}

	public void setItems(Items items) {
		this.itemId = items;
	}
	
	
	
}
