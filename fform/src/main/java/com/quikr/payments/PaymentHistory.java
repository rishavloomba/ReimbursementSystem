package com.quikr.payments;

import java.util.Date;

import com.quikr.form.Form;

public class PaymentHistory {
	private int financeid;
	private int formid;
	private int total_exp;
	private int empid;
	private int managerid;
	private Date payment_date;
	
	
	public PaymentHistory() {
		
	}
	public PaymentHistory(Form f) {
		this.formid=f.getFormid();
		this.total_exp=f.getTotal_exp();
		this.payment_date=f.getPayment_date();
		this.empid=f.getEmpid();
		this.managerid=f.getManagerid();
	}
	public PaymentHistory(int financeid, int formid, int total_exp, int empid,int managerid,Date payment_date) {
		super();
		this.financeid = financeid;
		this.formid = formid;
		this.total_exp = total_exp;
		this.payment_date = payment_date;
		this.empid=empid;
		this.managerid=managerid;
		
	}
	
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
	public int getFinanceid() {
		return financeid;
	}
	public void setFinanceid(int financeid) {
		this.financeid = financeid;
	}
	public int getFormid() {
		return formid;
	}
	public void setFormid(int formid) {
		this.formid = formid;
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
