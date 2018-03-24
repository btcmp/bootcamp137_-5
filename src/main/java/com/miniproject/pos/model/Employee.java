package com.miniproject.pos.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="pos_mst_employee")
public class Employee {

	//properties
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy="uuid2")
	private String id;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Email
	private String email;
	
	@Column(name = "title")
	private String title;
	
	@Column(nullable = false)
	private boolean haveAccount;
	
//	@ManyToOne
//	@Column(name = "created_by", nullable = true)
//	private User createdBy;
	
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
//	@ManyToOne
//	@Column(name = "modified_by", nullable = true)
//	private User modifiedBy;
	
	@Column(name = "modified_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedOn;
	
	@Column
	private boolean active;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="employee", fetch =FetchType.LAZY)
	private User user;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "employee_outlet",
		joinColumns = {@JoinColumn(name = "employee_id")},
		inverseJoinColumns = {@JoinColumn(name = "outlet_id")}
	)
	List<Outlet> listOutlet;
	
	//setters and getters
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isHaveAccount() {
		return haveAccount;
	}
	
	public void setHaveAcount(boolean haveAccount) {
		this.haveAccount = haveAccount;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Outlet> getListOutlet() {
		return listOutlet;
	}

	public void setListOutlet(List<Outlet> listOutlet) {
		this.listOutlet = listOutlet;
	}

	public void setHaveAccount(boolean haveAccount) {
		this.haveAccount = haveAccount;
	}
	
}
