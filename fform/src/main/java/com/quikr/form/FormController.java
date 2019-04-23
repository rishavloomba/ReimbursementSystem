package com.quikr.form;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.quikr.finance.requests.FinanceFilter;
import com.quikr.finance.requests.FinanceId;
import com.quikr.finance.requests.FinancePay;
//import com.quikr.form.requests.EmpRequest;
import com.quikr.form.requests.FetchReq;
import com.quikr.form.requests.FormAddRequest;
//import com.quikr.form.requests.FinanceDetails;
//import com.quikr.form.requests.FinanceFilter;
//import com.quikr.form.requests.FinanceId;
//import com.quikr.form.requests.FinancePay;
//import com.quikr.form.requests.FinanceRequest;
import com.quikr.form.requests.FormDetails;
import com.quikr.form.requests.FormId;
import com.quikr.form.requests.FormPublish;
import com.quikr.form.requests.FormUpdate;
//import com.quikr.form.requests.GroupBy;
//import com.quikr.form.requests.MFinanceDetails;
import com.quikr.form.requests.MFormDetails;
import com.quikr.form.requests.MMFormDetails;
import com.quikr.form.requests.MTask;
import com.quikr.form.requests.ManagerFormUpdate;
//import com.quikr.requests.FetchReq;
import com.quikr.form.requests.ManagerReq;
import com.quikr.payments.PaymentHistory;
//import com.quikr.platform.sso.Person;
//import com.quikr.platform.sso.exceptions.SSOInvalidResponseException;
//import com.quikr.platform.sso.exceptions.SSOServiceNotReachableException;
//import com.quikr.platform.sso.exceptions.SSOServiceTimedOutException;


@RestController
public class FormController {
	@Autowired
	FormService formservice;

	
	/*
	@RequestMapping("reimbursement")
    public void get(HttpServletRequest request) {
    	String address[]=request.getParameterValues("auth_code");
    	
    	StringBuffer temp=new StringBuffer();
    	for(String str:address) {
    		temp.append(str);
    	}
    	System.out.println(temp);
    	Person person=new Person();
    	try {
			person=ssoService.getUserFromToken(temp.toString());
			System.out.println("personid"+person.getId());
			
		} catch (SSOServiceNotReachableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SSOServiceTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SSOInvalidResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	*/
	@RequestMapping("/formhi")
	public String formhi() {
		return "formhi";
	}
	
	@RequestMapping("/Forms")  //gives FORMS  in form of formid:ArrayList<ArrayList<FormDetails>>
	public Map<Integer,ArrayList<FormDetails>> Forms(@RequestBody FetchReq req)
	{
		return formservice.getForms(req.getId(),req.getTofetch());
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/Forms/AddForm")
	public void addForm(@RequestBody FormAddRequest req) {
		formservice.addForm(req);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/Form/UpdateForm")
	public void updateForm(@RequestBody FormUpdate req) {
		formservice.updateForm(req);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/Form/Publish")
	public void publishForm(@RequestBody FormPublish topublish) {
		formservice.toPublish(topublish.getFormid());
	}
/*
	@RequestMapping("/Forms/Manager")
	public Map<Integer,Map<Integer,ArrayList<MTask>>>  managerView(@RequestBody FetchReq req){
		
		return formservice.getViewForManager(req.getId(), req.getTofetch());
	}
	*/
	@RequestMapping("/Forms/Manager")
	public Map<Integer,Map<Integer,MFormDetails>> groupBy(@RequestBody ManagerReq g) {
		return formservice.managerViewHelper(g.getManagerid(),g.getTofetch(),g.getMonth(),g.getYear());
	}

	@RequestMapping(method=RequestMethod.PUT,value="Forms/Manager/UpdateForm")
	public void managerUpdate(@RequestBody ManagerFormUpdate req) {
		formservice.managerFormUpdate(req.getFormid(),req.getManagerid(),req.getStatus());
	}
	

	
	@RequestMapping("/Forms/Finance")//get all unpaid forms
	public Map<Integer,Map<Integer,ArrayList<MMFormDetails>>> getFinanceView(){
		return formservice.showFinanceView("approved");
	}
	
	@RequestMapping("/Forms/Finance/Pay")//pay forms
	public void pay(@RequestBody FinancePay f) {
		formservice.payForm(f.getFormid(),f.getFinanceid());
	}
	/*
	@RequestMapping("/Forms/Finance/History")//get all paid forms by financeid
	public ArrayList<Form> getHistory(@RequestBody FinanceId f){
		return formservice.payments(f.getFinanceid());
	}*/
	
	@RequestMapping("Forms/Finance/History")//all history 
	public ArrayList<PaymentHistory> getHistory(@RequestBody FinanceFilter f){
		return formservice.getAllPayments(f.getFinanceid(),f.getSmonth(),f.getEmonth(),f.getYear());
		
	}
	
	@RequestMapping("/Forms/Tasks")
	public ArrayList<MTask> getAllTasks(@RequestBody FormId f){
		return formservice.getAllTasks(f.getFormid());
	}
	
}
