package com.miniproject.pos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE")
public class Employee {

	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String title;
	private boolean haveAcount;
	private long createdBy;
	private Date createdOn;
	private long modifiedBy;
	private Date modifiedOn;
	private boolean active;
	
}
