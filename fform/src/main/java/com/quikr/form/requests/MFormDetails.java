package com.quikr.form.requests;

import java.util.ArrayList;

//import com.quikr.requests.MTask;
//for manager view
public class MFormDetails {
	private String status;
	private ArrayList<MTask> tasks;
	
	public MFormDetails() {
		
	}
	
	public MFormDetails(String status, ArrayList<MTask> tasks) {
		super();
		this.status = status;
		this.tasks = tasks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<MTask> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<MTask> tasks) {
		this.tasks = tasks;
	}
	///pending
	
}
