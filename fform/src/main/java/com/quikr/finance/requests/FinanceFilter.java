package com.quikr.finance.requests;

public class FinanceFilter {

	private int financeid;
	private int smonth;
	private int emonth;
	private int year;
	
	public FinanceFilter() {
		
	}
	
	public FinanceFilter(int financeid, int smonth, int emonth, int year) {
		super();
		this.financeid = financeid;
		this.smonth = smonth;
		this.emonth = emonth;
		this.year = year;
	}
	public int getFinanceid() {
		return financeid;
	}
	public void setFinanceid(int financeid) {
		this.financeid = financeid;
	}
	public int getSmonth() {
		return smonth;
	}
	public void setSmonth(int smonth) {
		this.smonth = smonth;
	}
	public int getEmonth() {
		return emonth;
	}
	public void setEmonth(int emonth) {
		this.emonth = emonth;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
	
}
