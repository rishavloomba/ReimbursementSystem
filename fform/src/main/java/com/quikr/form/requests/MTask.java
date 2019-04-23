package com.quikr.form.requests;

import java.util.Date;

import com.quikr.task.Task;

public class MTask {
	private int travel_exp;
	private int telephone_exp;
	private int hotel_stay;
	private int business_meal;
	private int total_exp;
	private String description;
	private Date expense_date;
	private String image_url;
    
	public MTask() {
		
	}
	public MTask(Task t) {
		this.travel_exp=t.getTravel_exp();
		this.telephone_exp=t.getTelephone_exp();
		this.hotel_stay=t.getHotel_stay();
		this.business_meal=t.getBusiness_meal();
		this.total_exp=t.getTotal_expense();
		this.description=t.getDescription();
		this.expense_date=t.getExpense_date();
		this.image_url=t.getImage_url();
	}
	

	public MTask(int travel_exp, int telephone_exp, int hotel_stay, int business_meal,int total_exp ,String description,Date expense_date,String image_url) {
		super();
		this.travel_exp = travel_exp;
		this.telephone_exp = telephone_exp;
		this.hotel_stay = hotel_stay;
		this.business_meal = business_meal;
		this.total_exp=total_exp;
		this.description = description;
		this.expense_date=expense_date;
		this.image_url=image_url;
	}


	public int getTotal_exp() {
		return total_exp;
	}


	public void setTotal_exp(int total_exp) {
		this.total_exp = total_exp;
	}


	public int getTravel_exp() {
		return travel_exp;
	}

	public void setTravel_exp(int travel_exp) {
		this.travel_exp = travel_exp;
	}

	public int getTelephone_exp() {
		return telephone_exp;
	}

	public void setTelephone_exp(int telephone_exp) {
		this.telephone_exp = telephone_exp;
	}

	public int getHotel_stay() {
		return hotel_stay;
	}

	public void setHotel_stay(int hotel_stay) {
		this.hotel_stay = hotel_stay;
	}

	public int getBusiness_meal() {
		return business_meal;
	}

	public void setBusiness_meal(int business_meal) {
		this.business_meal = business_meal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Date getExpense_date() {
		return expense_date;
	}


	public void setExpense_date(Date expense_date) {
		this.expense_date = expense_date;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
	
	
    
}
