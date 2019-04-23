package com.quikr.form;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="Tform")
public class Form {
	@Id
	@Column(name="formid")
	private int formid;
	
	@Column(name="empid")
	private int empid;
	
	@Column(name="managerid")
	private int managerid;
	
	@Column(name="total_exp")
	private int total_exp;

	@Column(name="status")
	private String status;
	
	@Column(name="published")
	private boolean published;
	
	@Column(name="created_date")
	private Date created_date;
	
	@Column(name="published_date")
	private Date published_date;
	
	@Column(name="payment_date")
	private Date payment_date;
	
	public Form() {
		
	}
    public Form(int empid,int managerid,int total_exp,String status) {
    	this.empid=empid;
    	this.managerid=managerid;
    	this.total_exp=total_exp;
    	this.status=status;
    		
    }

	public Form(int formid, int empid, int managerid, int total_exp, String status, boolean published,
			Date created_date, Date published_date,Date payment_date) {
		super();
		this.formid = formid;
		this.empid = empid;
		this.managerid = managerid;
		this.total_exp = total_exp;
		this.status = status;
		this.published = published;
		this.created_date = created_date;
		this.published_date = published_date;
		this.payment_date=payment_date;
	}
	
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public Date getPublished_date() {
		return published_date;
	}
	public void setPublished_date(Date published_date) {
		this.published_date = published_date;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getFormid() {
		return formid;
	}

	public void setFormid(int formid) {
		this.formid = formid;
	}
   //@JsonProperty("empid")
	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public int getManagerid() {
		return managerid;
	}

	public void setManagerid(int managerid) {
		this.managerid = managerid;
	}

	public int getTotal_exp() {
		return total_exp;
	}

	public void setTotal_exp(int total_exp) {
		this.total_exp = total_exp;
	}
	public Date getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}
	
	
	

}
