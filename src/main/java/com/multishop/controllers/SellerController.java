package com.multishop.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multishop.dtos.OrderDto;
import com.multishop.dtos.UserDto;
import com.multishop.services.OrderService;
import com.multishop.services.SellerService;
import com.multishop.services.UserService;

@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {
	
	@Autowired
	private UserService userService;
	
	private SellerService sellerService;
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/signup")
	ResponseEntity<UserDto> registerNewSeller(
			@Valid @RequestBody UserDto userDto){
		UserDto createdUser = this.sellerService.registerNewSeller(userDto);
		return new ResponseEntity<>(createdUser,HttpStatus.OK);
		
	}
	
	@PostMapping("/orders/{sellerId}")
	ResponseEntity<List<OrderDto>> getAllOrders(@PathVariable String sellerId){
		List<OrderDto> orders=this.orderService.getAllOrders();
		return new ResponseEntity<List<OrderDto>>(orders,HttpStatus.OK);
		
	}
	
	
	
}
