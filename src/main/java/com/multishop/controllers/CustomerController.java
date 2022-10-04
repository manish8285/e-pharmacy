package com.multishop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multishop.dtos.CustomerDto;
import com.multishop.dtos.OrderDto;
import com.multishop.dtos.UserDto;
import com.multishop.entites.Address;
import com.multishop.entites.User;
import com.multishop.services.CustomerService;
import com.multishop.services.OrderService;
import com.multishop.services.UserService;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
	
	@Autowired
	public CustomerService customerService;
	
	@Autowired
	public OrderService orderService;
	
	@Autowired
	public UserService userService;
	
	private User user;
	
	private CustomerDto customerDto;
	
	// authenticate customer
	
	@ModelAttribute
	public void authenticateCustomer() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
	//	System.out.println("name =="+name);
		User user = this.userService.getUserByUsername(name);
		this.customerDto = this.customerService.getCustomerByUserId(user.getId());
		this.user=user;
		//System.out.println("user email id = "+user.getEmail());
	}
	
	
	
	//get customer by user id
	@GetMapping("/{userId}")
	public ResponseEntity<CustomerDto> getCustomer(@PathVariable Integer userId){
		//System.out.println("user id = "+userId);
		if(this.user.getId() != userId) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(this.customerService.getCustomerByUserId(userId));
	}
	
	//get customer by user id
		@GetMapping("/customer/{cid}")
		public ResponseEntity<CustomerDto> getCustomerByCustomerId(@PathVariable Integer cid){
			//System.out.println("user id = "+cid);
			if(this.customerDto.getId() != cid) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.ok(this.customerService.getCustomerById(cid));
		}
	
	// get order by id
	@GetMapping("/order/{orderId}")
	public ResponseEntity<OrderDto> getOrder(@PathVariable Integer orderId){
		return ResponseEntity.ok(this.orderService.getOrderById(orderId));
	}
	
	// get all orders by user id
	@GetMapping("/order/myorders/{userId}")
	public ResponseEntity<List<OrderDto>> getAllOrders(@PathVariable Integer userId){
		if(this.user.getId() != userId) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(this.orderService.getAllCustomerOrders(userId));
	}
	
	//place order with user id
	@PostMapping("/order/{userId}")
	public ResponseEntity<OrderDto> placeOrder(@PathVariable Integer userId,
			@RequestBody OrderDto orderDto){
		if(this.user.getId() != userId) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(this.orderService.createOrder(orderDto, userId));
	}
	
	@PostMapping("/address/{userId}")
	public ResponseEntity<Address> addAddress(@PathVariable Integer userId,
			@RequestBody Address address){
		if(this.user.getId() != userId) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(this.customerService.addAddressToCustomer(userId,address));
	}
	
}
