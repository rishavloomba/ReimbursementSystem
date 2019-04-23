package com.quikr.form.requests;
//used
public class ManagerFormUpdate {

	private int managerid;
	private int formid;
	private String status;
	public ManagerFormUpdate() {
		
	}
	public ManagerFormUpdate(int managerid, int formid, String status) {
		super();
		this.managerid = managerid;
		this.formid = formid;
		this.status = status;
	}
	public int getManagerid() {
		return managerid;
	}
	public void setManagerid(int managerid) {
		this.managerid = managerid;
	}
	public int getFormid() {
		return formid;
	}
	public void setFormid(int formid) {
		this.formid = formid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
