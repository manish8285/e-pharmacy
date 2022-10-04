package com.multishop.services;

import java.util.List;

import com.multishop.dtos.OrderDto;

public interface OrderService {
		OrderDto getOrderById(int orderId);
		
		OrderDto createOrder(OrderDto orderDto,int customerId);
		
		List<OrderDto> getAllOrders();
		
		List<OrderDto> getAllCustomerOrders(int userId);
		
		int getMaxOrderId();
		
		Long generateOrderId();
}
