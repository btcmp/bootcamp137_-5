package com.miniproject.pos.model;

import java.util.Date;
import java.util.List;


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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.miniproject.pos.utils.Formatter;


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
	
	@Column(nullable=false)
	private boolean active;

	@ManyToOne
    @JoinColumn(name = "item_id")
	private Items itemId;

	@OneToMany(mappedBy = "variantId", fetch = FetchType.LAZY)
	private List<ItemInventory> inventory;
	
	@Transient
	private ItemInventory singleInventory;
	
	public ItemVariant() {
		this.active = true;
	}
	
	//setter n getter
	public String getId() {
		return id;
	}

	public Items getItemId() {
		return itemId;
	}

	public void setItemId(Items itemId) {
		this.itemId = itemId;
	}
	
	public List<ItemInventory> getInventory() {
		return inventory;
	}

	public void setInventory(List<ItemInventory> inventory) {
		this.inventory = inventory;
	}

	@JsonProperty("singleInventory")
	public ItemInventory getSingleInventory() {
		if(singleInventory == null) {
			return null;
		}
		singleInventory.setVariantId(null);
		return singleInventory;
	}

	public void singleInventorySet(ItemInventory singleInventory) {
		this.singleInventory = singleInventory;
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

	public String getPriceFormatted() {
		return Formatter.currency(price);
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}	
}
