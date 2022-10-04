package com.multishop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multishop.entites.Address;

public interface AddressRepo extends JpaRepository<Address, Integer>{
	
	

}
