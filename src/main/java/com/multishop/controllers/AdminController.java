package com.multishop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multishop.dtos.OrderDto;
import com.multishop.services.OrderService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	ResponseEntity<List<OrderDto>> getAllOrders(){
		List<OrderDto> orders=this.orderService.getAllOrders();
		return new ResponseEntity<List<OrderDto>>(orders,HttpStatus.OK);
		
	}
	@GetMapping("/orders/order/{orderId}")
	ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId){
		//int oid = Integer.parseInt(orderId);
		OrderDto orders=this.orderService.getOrderByOrderId(orderId);
		return new ResponseEntity<OrderDto>(orders,HttpStatus.OK);
		
	}
	
	
	
}
