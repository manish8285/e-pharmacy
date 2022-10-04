package com.multishop.services;

import com.multishop.dtos.CustomerDto;
import com.multishop.entites.Address;
import com.multishop.entites.Customer;
import com.multishop.entites.User;

public interface CustomerService {
	Customer registerNewCustomer(User user);
	
	CustomerDto getCustomerByUserId(int userId);
	
	Address addAddressToCustomer(int userId,Address Address);
	
	CustomerDto getCustomerById(int id);
	
	

}
