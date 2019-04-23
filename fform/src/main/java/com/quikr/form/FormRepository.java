package com.quikr.form;

import java.util.ArrayList;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

//import com.quikr.form.requests.FinanceDetails;


public interface FormRepository extends CrudRepository<Form,Integer>{
	
	
	
	@Query(value="SELECT * FROM Tform ORDER BY FORMID DESC LIMIT 1",nativeQuery=true)
	public Form getMaxFormId();
	
	@Query(value="SELECT FORMID FROM Tform WHERE EMPID=?1",nativeQuery=true)
	public ArrayList<Integer> getAllFormIdsForEmp(int empid);
	
	@Query(value="SELECT FORMID FROM Tform WHERE EMPID=?1 AND STATUS LIKE ?2 AND PUBLISHED=TRUE",nativeQuery=true)
	public ArrayList<Integer> getAllFormIdsForEmp(int empid,String tofetch);
	
	@Query(value="SELECT FORMID FROM Tform WHERE MANAGERID=?1 AND PUBLISHED=TRUE",nativeQuery=true)
	public ArrayList<Integer> getAllFormIdsForManager(int managerid);
	
	@Query(value="SELECT FORMID FROM Tform WHERE MANAGERID=?1 AND PUBLISHED=TRUE AND STATUS LIKE ?2",nativeQuery=true)
	public ArrayList<Integer> getAllFormIdsForManager(int mangerid,String tofetch);
	
	
	@Query(value="SELECT EMPID FROM Tform WHERE FORMID=?1",nativeQuery=true)
	public int getEmpId(int formid);
 
	@Modifying
	@Transactional
	@Query(value="UPDATE Tform SET PUBLISHED=TRUE , PUBLISHED_DATE=?2 WHERE FORMID=?1",nativeQuery=true)
	public void publish(int formid,Date publishdate);
	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE Tform SET STATUS=?3 WHERE MANAGERID=?2 AND FORMID=?1",nativeQuery=true)
	public void managerFormUpdate(int formid, int managerid, String status);
	
	
	@Query(value="SELECT * FROM Tform WHERE EMPID=?1",nativeQuery=true)
	public ArrayList<Form> getAllForEmp(int empid);
	
	@Query(value="SELECT * FROM Tform WHERE EMPID=?1 AND STATUS LIKE ?2 AND PUBLISHED=TRUE",nativeQuery=true)
	public ArrayList<Form> getAllForEmp(int empid,String tofetch);
	
	@Query(value="SELECT *  FROM Tform WHERE STATUS LIKE 'approved' ",nativeQuery=true)
	public ArrayList<Form> getAllApprovedForms();
   
	@Query(value="SELECT EMPID FROM Tform WHERE FORMID=?1",nativeQuery=true)
	public int getEmpid(int formid);
	
	@Query(value="SELECT TOTAL_EXP FROM Tform WHERE FORMID=?1",nativeQuery=true)
	public int getExp(int formid);
	
	@Query(value="SELECT MANAGERID FROM Tform WHERE FORMID=?1",nativeQuery=true)
	public int getManagerId(int formid);

	@Query(value="SELECT FORMID FROM Tform WHERE MANAGERID=?1 AND STATUS LIKE ?2 AND MONTH(PUBLISHED_DATE)=?3 AND YEAR(PUBLISHED_DATE)=?4",nativeQuery=true)
	public ArrayList<Integer> getByMonthAndYear(int id,String tofetch,int month,int year);
	
	@Query(value="SELECT STATUS FROM Tform WHERE FORMID=?1",nativeQuery=true)
	public String getStatus(int formid);

	@Query(value="SELECT FORMID FROM Tform WHERE MANAGERID=?1 AND MONTH(PUBLISHED_DATE)=?2 AND YEAR(PUBLISHED_DATE)=?3 ",nativeQuery=true)
	public ArrayList<Integer> getAllFormIdsForManagerByMandy(int id, int month, int year);

	@Modifying 
	@Transactional
	@Query(value="UPDATE Tform SET STATUS=?2 WHERE FORMID=?1",nativeQuery=true)
	public void payForms(int formid, String paid);
	
	
	@Query(value="SELECT * FROM Tform WHERE STATUS LIKE ?1",nativeQuery=true)
	public ArrayList<Form> getAccToStatus(String status);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE Tform SET STATUS='paid',PAYMENT_DATE=?2 WHERE FORMID=?1",nativeQuery=true)
	public void setPay(int formid,Date payment_date);

	@Query(value="SELECT * FROM Tform WHERE FORMID=?1",nativeQuery=true)
	public Form getForm(int formid);
	
	@Query(value="SELECT * FROM Tform WHERE FORMID=?1 AND MONTH(PAYMENT_DATE)>=?2 AND MONTH(PAYMENT_DATE)<=?3 AND YEAR(PAYMENT_DATE)=?4",nativeQuery=true)
	public Form getForm(int formid,int smonth,int emonth,int year);

}

