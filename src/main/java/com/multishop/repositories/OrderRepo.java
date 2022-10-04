package com.multishop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.multishop.entites.Customer;
import com.multishop.entites.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {
	
	List<Order> findAllByCustomer(Customer customer );
	
	@Query("SELECT coalesce(max(od.id), 0) FROM Order od")
	int getLastOrderId();
	
	List<Order> findAllByOrderByIdDesc();
}
