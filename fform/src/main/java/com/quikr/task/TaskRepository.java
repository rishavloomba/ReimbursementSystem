package com.quikr.task;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task,Integer>{

	public List<Task> findByManagerid(int id) ;
	
	public List<Task> findAllByEmpid(int id);

	public Task findByTaskid(int taskid);

	public List<Task> findAllByManagerid(int id);
	
	
	@Query(value="SELECT * FROM Tasks WHERE MANAGERID=?1 AND MONTH(PUBLISHED_DATE)=?2 AND YEAR(PUBLISHED_DATE)=?3 ",nativeQuery=true) //group by pending
	public List<Task> filterBy(int managerid, int month,int year);
	
	@Query(value="SELECT * FROM Tasks WHERE FORMID=?1",nativeQuery=true)
	public ArrayList<Task> getFormTasks(int formid);
	
	@Modifying
	@Transactional 
	  @Query(value="DELETE FROM Tasks WHERE FORMID=?1 ",nativeQuery=true)
	   public void deleteAllWithFormId(int formid);
}
