package com.quikr.form.requests;

import java.util.Date;

import com.quikr.form.Form;
//for finance view
public class MMFormDetails {
	private int formid;
	private Date approved_date;
	private int total_exp;
	
	
	public MMFormDetails() {
		
	}
	
	public MMFormDetails(Form f){
		this.formid=f.getFormid();
		this.total_exp=f.getTotal_exp();
		this.approved_date=new Date();
	}

	public int getFormid() {
		return formid;
	}

	public void setFormid(int formid) {
		this.formid = formid;
	}

	public Date getApproved_date() {
		return approved_date;
	}

	public void setApproved_date(Date approved_date) {
		this.approved_date = approved_date;
	}

	public int getTotal_exp() {
		return total_exp;
	}

	public void setTotal_exp(int total_exp) {
		this.total_exp = total_exp;
	}
	
}
