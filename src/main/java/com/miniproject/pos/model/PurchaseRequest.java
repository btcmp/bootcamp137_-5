package com.miniproject.pos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pos_t_pr")
public class PurchaseRequest {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid2")
	private String id;
	
	@ManyToOne
	private Outlet outlet;
	
	@Column(name="ready_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date readyTime;
	
	@Column(name="pr_no")
	private String prNo;
	
	private String notes;
	
	private String status;
	
	@Column(name="created_by")
	private User createdBy;
	
	private Date createdOn;
	
	private User modifiedBy;
	
	private Date ModifiedOn;
}
