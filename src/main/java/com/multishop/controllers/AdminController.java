package com.multishop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multishop.dtos.OrderDto;
import com.multishop.services.DeliveryService;
import com.multishop.services.OrderService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private DeliveryService deliveryService;

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
	
	//pickup request
	@PostMapping("/orders/pickup/order/{orderId}")
	ResponseEntity<String> pickupOrderRequest(@PathVariable Long orderId){
		//int oid = Integer.parseInt(orderId);
		String resp=this.deliveryService.requestPickup(orderId);
		return new ResponseEntity<String>(resp,HttpStatus.OK);
		
	}
	
	//cancel order status only
		@PutMapping("/orders/cancel/order/{orderId}")
		ResponseEntity<String> cancelOrderSatus(@PathVariable Long orderId){
			//int oid = Integer.parseInt(orderId);
			boolean resp=this.orderService.cancelOrderStatus(orderId);
			return new ResponseEntity<String>(""+resp,HttpStatus.OK);
			
		}
		//cancel out for delivery status only
		@PutMapping("/orders/out_for_delivery/order/{orderId}")
		ResponseEntity<String> outForDeliveryOrderSatus(@PathVariable Long orderId){
					boolean resp=this.orderService.outForDeliveryOrderStatus(orderId);
					return new ResponseEntity<String>(""+resp,HttpStatus.OK);
					
		}
		//Delivered order status only
		@PutMapping("/orders/delivered/order/{orderId}")
		ResponseEntity<String> DeliveredOrderSatus(@PathVariable Long orderId){
			boolean resp=this.orderService.deliveredOrderStatus(orderId);
			return new ResponseEntity<String>(""+resp,HttpStatus.OK);
		}
	
		//pickup request
		@PostMapping("/orders/cancel_pickup/order/{orderId}")
		ResponseEntity<String> cancelPickupRequest(@PathVariable Long orderId){
			boolean resp=this.deliveryService.cancelPickupRequest(orderId);
			return new ResponseEntity<String>(""+resp,HttpStatus.OK);	
		}
	
	
}
