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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ROLE")
public class Role {
	
	//properties
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	private String name;
	
	private String description;
	
	@JoinColumn(name = "created_by")
	@ManyToOne
	private User createdBy;
	
	@Column(name = "created_on")
	@Temporal(TemporalType.DATE)
	private Date createdOn;
	
	@JoinColumn(name = "modified_by")
	@ManyToOne
	private User modifiedBy;
	
	@Column(name = "modified_on")
	@Temporal(TemporalType.DATE)
	private Date modifiedOn;
	
	@Column(nullable = false)
	private boolean active;
	
	@OneToMany(fetch = FetchType.LAZY, cascade  =CascadeType.ALL, mappedBy = "role", orphanRemoval = true)
	private List<User> listUser;
	
	//setters and getters
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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

	public List<User> getListUser() {
		return listUser;
	}

	public void setListUser(List<User> listUser) {
		this.listUser = listUser;
	}
	
}
