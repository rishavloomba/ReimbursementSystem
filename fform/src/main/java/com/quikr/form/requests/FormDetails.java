package com.quikr.form.requests;

import java.util.ArrayList;
import java.util.Date;

import com.quikr.form.Form;
//import com.quikr.requests.MTask;

public class FormDetails {
	
	private Date published_date;
	private Date created_date;
	private String status;
	private boolean published;
	private ArrayList<MTask> tasks;
	
	public FormDetails() {
		
	}
	public FormDetails(ArrayList<MTask> t,Form f) {
		
		this.tasks=t;
		this.published_date=f.getPublished_date();
		this.created_date=f.getCreated_date();
		this.status=f.getStatus();
		this.published=f.isPublished();
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public FormDetails( Date published_date, Date created_date, String status, boolean published) {
		super();
		
		this.published_date = published_date;
		this.created_date = created_date;
		this.status = status;
		this.published = published;
		//this.tasks = tasks;
	}

	public FormDetails( Date published_date, Date created_date, String status, boolean published,
			ArrayList<MTask> tasks) {
		super();
		
		this.published_date = published_date;
		this.created_date = created_date;
		this.status = status;
		this.published = published;
		this.tasks = tasks;
	}

	
	public Date getPublished_date() {
		return published_date;
	}
	public void setPublished_date(Date published_date) {
		this.published_date = published_date;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public ArrayList<MTask> getTasks() {
		return tasks;
	}
	public void setTasks(ArrayList<MTask> tasks) {
		this.tasks = tasks;
	}
	
	

}
