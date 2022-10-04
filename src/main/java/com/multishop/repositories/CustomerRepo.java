package com.multishop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.multishop.entites.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	
	@Query("from Customer as c where c.userId=:id")
	List<Customer> getCustomerByUserId(int id);
}
