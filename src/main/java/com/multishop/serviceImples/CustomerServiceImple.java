package com.multishop.serviceImples;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multishop.dtos.CustomerDto;
import com.multishop.entites.Address;
import com.multishop.entites.Customer;
import com.multishop.entites.User;
import com.multishop.exceptions.ResourceNotFoundException;
import com.multishop.repositories.AddressRepo;
import com.multishop.repositories.CustomerRepo;
import com.multishop.services.CustomerService;


@Service
public class CustomerServiceImple implements CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AddressRepo addressRepo;

	
	//register new customer
	@Override
	public Customer registerNewCustomer(User user) {
		Customer customer = new Customer();
		customer.setUserId(user.getId());
		return this.customerRepo.save(customer);
	}


	@Override
	public CustomerDto getCustomerByUserId(int userId) {
		Customer customer = this.customerRepo.getCustomerByUserId(userId).get(0);
		System.out.println(customer.getId());
		return this.modelMapper.map(customer, CustomerDto.class);
	}


	@Override
	public Address addAddressToCustomer(int userId,Address address) {
		List<Customer> customers = this.customerRepo.getCustomerByUserId(userId);
		List<Address> addresslist = customers.get(0).getAddress();
		Address address2 = this.addressRepo.save(address);
		addresslist.add(address2);
		this.customerRepo.save(customers.get(0));
		return address2;
	}


	@Override
	public CustomerDto getCustomerById(int id) {
		Customer customer = this.customerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer", "customer id", id));
		return this.modelMapper.map(customer, CustomerDto.class);
	}

	
}
