package com.quikr.finance.requests;

public class FinanceRequest {
	private String tofetch;
	
	public FinanceRequest() {
		
	}
	public FinanceRequest(String tofetch) {
		this.tofetch=tofetch;
	}
	public String getTofetch() {
		return tofetch;
	}
	public void setTofetch(String tofetch) {
		this.tofetch = tofetch;
	}
	
	

}
