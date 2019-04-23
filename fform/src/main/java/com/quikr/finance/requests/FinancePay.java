package com.quikr.finance.requests;

public class FinancePay {
	/*
	private int payid;
	private int empid;
	
	public FinancePay() {
		
	}
	
	public FinancePay(int payid,int empid) {
		this.payid=payid;
		this.empid=empid;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public int getPayid() {
		return payid;
	}

	public void setPayid(int payid) {
		this.payid = payid;
	}
	*/
	private int financeid;
	private int formid;
	
	public FinancePay() {
		
	}

	public FinancePay(int formid,int financeid) {
		super();
		this.formid = formid;
		this.financeid=financeid;
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