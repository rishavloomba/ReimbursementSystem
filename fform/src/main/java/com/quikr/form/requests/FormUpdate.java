package com.quikr.form.requests;

import java.util.ArrayList;

//import com.quikr.requests.MTask;

public class FormUpdate {
	private int formid;
	private ArrayList<MTask> tasks;
	private int total_exp;
	public FormUpdate() {
		
	}
	public FormUpdate(int formid, ArrayList<MTask> tasks,int total_exp) {
		super();
		this.formid = formid;
		this.tasks = tasks;
		this.total_exp=total_exp;
	}
	
	public int getTotal_exp() {
		return total_exp;
	}
	public void setTotal_exp(int total_exp) {
		this.total_exp = total_exp;
	}
	public int getFormid() {
		return formid;
	}
	public void setFormid(int formid) {
		this.formid = formid;
	}
	public ArrayList<MTask> getTasks() {
		return tasks;
	}
	public void setTasks(ArrayList<MTask> tasks) {
		this.tasks = tasks;
	}
	
	
}
