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
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="pos_mst_item")
public class Items {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	@Column(nullable=true)
	
	@Size(max=255)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Kategori categoryId;
	
	@ManyToOne
	@JoinColumn(name="created_by", nullable=true)
	private User createdBy;
	
	@CreationTimestamp
	@Column(name="created_on", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
	
	@ManyToOne
	@JoinColumn(name="modified_by", nullable=true)
	private User modifiedBy;
	
	@UpdateTimestamp
	@Column(name="modified_on", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedOn;
	
	private boolean active;
	
	@OneToMany( fetch = FetchType.LAZY,mappedBy="itemId")
	private List<ItemVariant> variants;
	
	
	public Items() {
		this.active = true;
	}
	
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

	public Kategori getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Kategori categoryId) {
		this.categoryId = categoryId;
	}

	public List<ItemVariant> getVariants() {
		return variants;
	}

	public void setVariants(List<ItemVariant> variants) {
		this.variants = variants;
	}
}
