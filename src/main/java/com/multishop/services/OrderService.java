package com.multishop.services;

import java.util.List;

import com.multishop.dtos.OrderDto;
import com.multishop.dtos.OrderResponse;
import com.multishop.dtos.OrderStatusDto;

public interface OrderService {
		OrderDto getOrderById(int orderId);
		OrderDto getOrderByOrderId(Long orderId);
		
		OrderDto createOrder(OrderDto orderDto,int customerId);
		
		List<OrderDto> getAllOrders();
		
		OrderResponse getAllCustomerOrders(int userId,int page , int size);
		
		int getMaxOrderId();
		
		Long generateOrderId();
		
		OrderDto getOrderStatus(int userId,long orderId);
}
