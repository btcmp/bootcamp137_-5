package com.miniproject.pos.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pos_mst_item")
public class Items {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	private String name;
	//private long categoryId;
	@Column(name="created_by")
	private long createdBy;
	@Column(name="created_on")
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
	@Column(name="modified_by")
	private long modifiedBy;
	@Column(name="modified_on")
	private Date modifiedOn;
	private boolean active;
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
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(long modifiedBy) {
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
	
	/*//relasi
	@ManyToOne
	private Kategori kategori;
	public Kategori getKategori() {
		return kategori;
	}
	public void setKategori(Kategori kategori) {
		this.kategori = kategori;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="itemms")
	private List<ItemVariant> itemvarians;
	public List<ItemVariant> getItemvarians() {
		return itemvarians;
	}
	public void setItemvarians(List<ItemVariant> itemvarians) {
		this.itemvarians = itemvarians;
	}
	*/
	
	
}
