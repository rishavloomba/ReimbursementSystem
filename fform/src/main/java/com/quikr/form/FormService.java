package com.quikr.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quikr.form.requests.FormAddRequest;
//import com.quikr.finance.FinanceRepository;
//import com.quikr.form.requests.EmpRequest;
//import com.quikr.form.requests.FinanceDetails;
import com.quikr.form.requests.FormDetails;
import com.quikr.form.requests.FormUpdate;

//import com.quikr.form.requests.MFinanceDetails;
import com.quikr.form.requests.MFormDetails;
import com.quikr.form.requests.MMFormDetails;
import com.quikr.form.requests.MTask;
import com.quikr.payments.PaymentHistory;
import com.quikr.payments.Payments;
import com.quikr.payments.PaymentsRepository;
//import com.quikr.requests.MTask;
import com.quikr.task.Task;
import com.quikr.task.TaskRepository;

@Service
public class FormService {
	@Autowired
	FormRepository formrepository;
	
	@Autowired 
	TaskRepository taskrepository;
	/*
	@Autowired
	FinanceRepository financerepository;
	*/
	@Autowired
	PaymentsRepository paymentsrepository;
	
	public void addForm(FormAddRequest req) {
		 Form f=new Form(req.getEmpid(),req.getManagerid(),req.getTotal_exp(),"pending");
		 f.setCreated_date(new Date()); 
		 formrepository.save(f);   														//save in Tform table
		 int formid= formrepository.getMaxFormId().getFormid();
		 //edited not my choice
		 toPublish(formid);
		 
		 for(MTask t:req.getTasks()) {
			 Task newt=new Task();
			 newt.setBusiness_meal(t.getBusiness_meal());
			 newt.setHotel_stay(t.getHotel_stay());
			 newt.setTravel_exp(t.getTravel_exp());
			 newt.setTelephone_exp(t.getTelephone_exp());
			 newt.setDescription(t.getDescription());
			 newt.setEmpid(req.getEmpid());
			 newt.setManagerid(req.getManagerid());
			 newt.setStatus("pending");
			 newt.setExpense_date(t.getExpense_date());
			 newt.setTotal_expense(t.getTotal_exp());
			// newt.setFormid(formrepository.getMaxFormId().getFormid());					//get formid for latest added form 
			 newt.setFormid(formid);
			 newt.setImage_url(t.getImage_url());
			 taskrepository.save(newt);													//save in Tasks table
			 System.out.println(newt.getEmpid()+"empid");
		 }
		 

		 
	}
	
	public Map<Integer,ArrayList<FormDetails>> getForms(int empid,String tofetch){
		ArrayList<Form> forms;
		
		if(tofetch.equals("all"))   forms=new ArrayList<Form>(formrepository.getAllForEmp(empid));        //get all forms of empid
		else				        forms=new ArrayList<Form>(formrepository.getAllForEmp(empid,tofetch));//get all forms acc to tofetch of empid
 		
	    Map<Integer,ArrayList<FormDetails>> modifiedforms =new HashMap<Integer,ArrayList<FormDetails>>(); //for returning desired output
	    int formid;
		for(int i=0;i<forms.size();i++) {		//traverse all forms														 
			
			formid=forms.get(i).getFormid();	//get formid 
			
			if(modifiedforms.containsKey(formid)==false) {		//if formid not present intialise arraylist
				modifiedforms.put(formid, new ArrayList<FormDetails>());
				
			}
			ArrayList<Task> tasks=taskrepository.getFormTasks(formid);		//get form tasks

			ArrayList<MTask> mtask=new ArrayList<MTask>();					// formating tasks
			for(int j=0;j<tasks.size();j++) {								
				MTask m=new MTask(tasks.get(j));
				mtask.add(m);
			}
			
			FormDetails f=new FormDetails(mtask,forms.get(i));		//formating form
			
			modifiedforms.get(formid).add(f);		//adding form to its formid
	
		}
		return modifiedforms;
		
	
	}
	
	public void updateForm(FormUpdate req) {
		
		
		int empid=formrepository.getEmpId(req.getFormid());//get corresponding empid for form id
		int managerid=formrepository.getManagerId(req.getFormid());
		taskrepository.deleteAllWithFormId(req.getFormid());
		for(MTask t:req.getTasks()) {
			 Task newt=new Task();
			 newt.setBusiness_meal(t.getBusiness_meal());
			 newt.setHotel_stay(t.getHotel_stay());
			 newt.setTravel_exp(t.getTravel_exp());
			 newt.setTelephone_exp(t.getTelephone_exp());
			 newt.setDescription(t.getDescription());
	
			 newt.setEmpid(empid);
			 newt.setManagerid(managerid);
			 newt.setTotal_expense(req.getTotal_exp());
			 
			 newt.setStatus("pending");
			 newt.setExpense_date(new Date());
			 newt.setFormid(req.getFormid());
			 
			 taskrepository.save(newt);
		}
		
	}
	
	public void toPublish(int formid) {
		Date publishdate=new Date();
		formrepository.publish(formid,publishdate);
	}
  
	public Map<Integer,Map<Integer,MFormDetails>> managerViewHelper(int id,String tofetch,int month,int year){
		ArrayList<Integer> formids;
		if(tofetch.equals("all") && month==0 && year==0) formids=new ArrayList<Integer>(formrepository.getAllFormIdsForManager(id));
		else if(tofetch.equals("all") && month!=0)       formids=new ArrayList<Integer>(formrepository.getAllFormIdsForManagerByMandy(id,month,year));
		else if(month==0 && year==0)  					 formids=new ArrayList<Integer>(formrepository.getAllFormIdsForManager(id,tofetch));	
		else         									 formids=new ArrayList<Integer>(formrepository.getByMonthAndYear(id,tofetch,month,year));		
		System.out.println("formids "+formids.toString()+id+" "+month);
		return getViewForManager(id,tofetch,formids);
	}
	
	public Map<Integer,Map<Integer,MFormDetails>> getViewForManager(int id, String tofetch,ArrayList<Integer> formids) {
		//ArrayList<Integer> formids;
		
		//if(tofetch.equals("all")) formids=new ArrayList<Integer>(formrepository.getAllFormIdsForManager(id));
		//else  					  formids=new ArrayList<Integer>(formrepository.getAllFormIdsForManager(id,tofetch));
		
		
		Map<Integer,Map<Integer,MFormDetails>> toreturn =new HashMap<Integer,Map<Integer,MFormDetails>>();
		for(int formid:formids) {
			int empid=formrepository.getEmpId(formid);
			if(toreturn.containsKey(empid)==false) {
			toreturn.put(empid,new HashMap<Integer,MFormDetails>());
			}
			ArrayList<Task> temp=new ArrayList<Task>(taskrepository.getFormTasks(formid));
			String s=formrepository.getStatus(formid);
			ArrayList<MTask> finaltemp=new ArrayList<MTask>();
			
			for(Task t:temp) {
				MTask toput=new MTask(t);
				finaltemp.add(toput);
			}		
			MFormDetails fd=new MFormDetails(s,finaltemp);
			
			toreturn.get(empid).put(formid,fd);
		}
		return toreturn;
	}
	
	public void managerFormUpdate(int formid, int managerid, String status) {
		formrepository.managerFormUpdate(formid,managerid,status);
	
	}
	
	public Map<Integer,Map<Integer,ArrayList<MMFormDetails>>> showFinanceView(String tofetch){
		
		//get all status =tofetch
		ArrayList<Form> formdetails=formrepository.getAccToStatus(tofetch);
		Map<Integer,Map<Integer,ArrayList<MMFormDetails>>> datatoreturn=new HashMap<Integer,Map<Integer,ArrayList<MMFormDetails>>>();
	
		for(int i=0;i<formdetails.size();i++) {
			int managerid=formdetails.get(i).getManagerid();
			if(datatoreturn.containsKey(managerid)==false) {
				datatoreturn.put(managerid,new HashMap<Integer,ArrayList<MMFormDetails>>());
			}
			
			int empid=formdetails.get(i).getEmpid();
			if(datatoreturn.get(managerid).containsKey(empid)==false) {
				datatoreturn.get(managerid).put(empid,new ArrayList<MMFormDetails>());
			}
			
			MMFormDetails ff=new MMFormDetails(formdetails.get(i));
			datatoreturn.get(managerid).get(empid).add(ff);
		}
		
		
		return datatoreturn;
	}
	
	public void payForm(int formid,int financeid) {
		formrepository.setPay(formid,new Date());
		
		Payments p=new Payments();
		p.setFinanceid(financeid);
		p.setFormid(formid);
		//p.setPayment_date(new Date());
		
		paymentsrepository.save(p);
	}
	
	public ArrayList<Form> payments(int financeid) {
		ArrayList<Integer> formids=paymentsrepository.findAllByFinanceid(financeid);
		ArrayList<Form> history=new ArrayList<Form>();
		
		for(int i=0;i<formids.size();i++) {
			history.add(formrepository.getForm(formids.get(i)));
		}
		return history;
	}

	public ArrayList<MTask> getAllTasks(int formid) {
		// TODO Auto-generated method stub
		ArrayList<Task> tasks=taskrepository.getFormTasks(formid);
	    ArrayList<MTask> mtasks=new ArrayList<MTask>();
	    
	    for(Task t:tasks) {
	    	MTask temp=new MTask(t);
	    	mtasks.add(temp);
	   }
	    return mtasks;
	}

	public ArrayList<PaymentHistory> getAllPayments(int financeid,int smonth,int emonth,int year) {
		// TODO Auto-generated method stub
		ArrayList<PaymentHistory> history=new ArrayList<PaymentHistory>();
		ArrayList<Payments> payments;
		if(financeid==0)  payments=paymentsrepository.getAllPayments(); //for all history
		else 			   payments=paymentsrepository.getAllPayments(financeid);
		System.out.println("size"+payments.size());

		for(int i=0;i<payments.size();i++) {
			PaymentHistory p;
			 if(smonth==0) {
				 p=new PaymentHistory(formrepository.getForm(payments.get(i).getFormid()));//for all history
				 p.setFinanceid(payments.get(i).getFinanceid());
					history.add(p);
			 }
			//else 		  p=new PaymentHistory(formrepository.getForm(payments.get(i).getFormid(),smonth,emonth,year));
			 else {
				 if(formrepository.getForm(payments.get(i).getFormid(),smonth,emonth,year)!=null) {
					 p=new PaymentHistory(formrepository.getForm(payments.get(i).getFormid(),smonth,emonth,year));
					 p.setFinanceid(payments.get(i).getFinanceid());
						history.add(p);
				 }
			 }
			
		}
		
		return history;
	}
	


}
