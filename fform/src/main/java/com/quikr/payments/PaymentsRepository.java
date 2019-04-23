package com.quikr.payments;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PaymentsRepository extends CrudRepository<Payments,Integer> {

	@Query(value="SELECT FORMID FROM Payments WHERE FINANCEID=?1",nativeQuery=true)
	public ArrayList<Integer> findAllByFinanceid(int financeid);
    
	@Query(value="SELECT * FROM Payments",nativeQuery=true)
	public ArrayList<Payments> getAllPayments();
	
	@Query(value="SELECT * FROM Payments WHERE FINANCEID=?1",nativeQuery=true)
	public ArrayList<Payments> getAllPayments(int financeid);
}
