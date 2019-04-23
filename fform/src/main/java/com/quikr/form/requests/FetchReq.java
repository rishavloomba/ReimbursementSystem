package com.quikr.form.requests;

public class FetchReq {
	private int id;
	private String tofetch;
	
	public FetchReq() {
		
	}
	public FetchReq(int id, String tofetch) {
		super();
		this.id = id;
		this.tofetch = tofetch;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTofetch() {
		return tofetch;
	}
	public void setTofetch(String tofetch) {
		this.tofetch = tofetch;
	}
	
	

}

