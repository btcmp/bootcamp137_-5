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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pos_mst_region")
public class Region {

	//property
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	@Column(nullable=false, name="name_region")
	private String name;
	
	@ManyToOne
	@JoinColumn(nullable=true, name="created_by")
	private User createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on", nullable=true)
	private Date createdOn;
	
	@ManyToOne
	@JoinColumn(nullable=true, name="modified_by")
	private User modifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on", nullable=true)
	private Date modifiedOn;
	
	@Column(nullable=false)
	private boolean active;

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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	
	//relasi
	@ManyToOne
	private Provinsi provinsi;

	public Provinsi getProvinsi() {
		return provinsi;
	}

	public void setProvinsi(Provinsi provinsi) {
		this.provinsi = provinsi;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="region")
	private List<District> districts;

	public List<District> getDistricts() {
		return districts;
	}

	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="region")
	private List<Outlet> outlets;

	public List<Outlet> getOutlets() {
		return outlets;
	}

	public void setOutlets(List<Outlet> outlets) {
		this.outlets = outlets;
	}
	
	
	
}
