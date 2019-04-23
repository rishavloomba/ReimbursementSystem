package com.quikr.form.requests;

public class ManagerReq {
	private int managerid;
	private String tofetch;
	private int month;
	private int year;
	
	
	public ManagerReq() {
		
	}
	public ManagerReq(int managerid, String tofetch, int month, int year) {
		super();
		this.managerid = managerid;
		this.tofetch = tofetch;
		this.month = month;
		this.year = year;
	}
	public int getManagerid() {
		return managerid;
	}
	public void setManagerid(int managerid) {
		this.managerid = managerid;
	}
	public String getTofetch() {
		return tofetch;
	}
	public void setTofetch(String tofetch) {
		this.tofetch = tofetch;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	

}
