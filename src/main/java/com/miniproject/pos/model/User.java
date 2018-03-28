package com.miniproject.pos.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.UsesSunHttpServer;

@Entity
@Table(name = "pos_mst_user")
public class User {

	//properties
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy ="uuid2")
	private String id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
//	@Column(name  = "created_by", nullable = true)
//	@ManyToOne
//	private User createdBy;
	
	@Column(name  = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
//	@Column(name  = "modified_by", nullable = true)
//	@ManyToOne
//	private User modifiedBy;
	
	@Column(name  = "modified_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedOn;
	
	@Column
	private boolean active;

	@OneToOne(fetch = FetchType.LAZY)
	private Employee employee;
	
	@ManyToOne
	private Role role;
	
	
	//Setters and Getters
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public User getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(User createdBy) {
//		this.createdBy = createdBy;
//	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

//	public User getModifiedBy() {
//		return modifiedBy;
//	}
//
//	public void setModifiedBy(User modifiedBy) {
//		this.modifiedBy = modifiedBy;
//	}

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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
