package com.quikr.payments;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Payments")
public class Payments {
	
	@Column(name="financeid")
	private int financeid;
	
	@Id
	@Column(name="formid")
	private int formid;
	
	/*@Column(name="payment_date")
	private Date payment_date;
*/
	public Payments() {
		
	}
	
	public Payments(int financeid, int formid) {
		super();
		this.financeid = financeid;
		this.formid = formid;
		//this.payment_date = payment_date;
	}

	public int getFormid() {
		return formid;
	}
	public void setFormid(int formid) {
		this.formid = formid;
	}
	public int getFinanceid() {
		return financeid;
	}
	public void setFinanceid(int financeid) {
		this.financeid = financeid;
	}
	
	
	

}
