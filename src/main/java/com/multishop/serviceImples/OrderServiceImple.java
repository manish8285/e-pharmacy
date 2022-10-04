package com.multishop.serviceImples;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multishop.dtos.OrderDto;
import com.multishop.entites.Customer;
import com.multishop.entites.Order;
import com.multishop.entites.ProductList;
import com.multishop.exceptions.ResourceNotFoundException;
import com.multishop.repositories.CustomerRepo;
import com.multishop.repositories.OrderRepo;
import com.multishop.repositories.ProductListRepo;
import com.multishop.services.OrderService;

@Service
public class OrderServiceImple implements OrderService {
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private ProductListRepo productListRepo;

	@Override
	public OrderDto getOrderById(int orderId) {
		Order order = this.orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order", "id", orderId));
		
		return this.modelMapper.map(order, OrderDto.class);
	}


	@Override
	public List<OrderDto> getAllOrders() {
		//List<Order> orders = this.orderRepo.findAll();
		//return orders.stream().map((order)->this.modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
		List<Order> orders = this.orderRepo.findAllByOrderByIdDesc();
		return orders.stream().map((order)->this.modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<OrderDto> getAllCustomerOrders(int userId) {
		List<Customer> customer = this.customerRepo.getCustomerByUserId(userId);
		Customer customer1 = customer.get(0);
		
		List<Order> orders2 = this.orderRepo.findAllByCustomer(customer1);
		System.out.println(orders2.get(0).getId());
		
		List<OrderDto> orderDtos = orders2.stream().map((order)->this.modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
		return orderDtos;
	}

	@Override
	public OrderDto createOrder(OrderDto orderDto, int customerId) {
		Order order = this.modelMapper.map(orderDto, Order.class);
		order.setDate(new Date());
		order.setCustomer(this.customerRepo.getCustomerByUserId(customerId).get(0));
		List<ProductList> list = this.productListRepo.saveAll(order.getItems());
		order.setItems(list);
		order.setStatus("placed");
		order.setOrderId(this.generateOrderId());
		Order order2 = this.orderRepo.save(order);
		for(ProductList item : list) {
			item.setOrder(order2);
			ProductList item2 = this.productListRepo.save(item);
		}
		
		return this.modelMapper.map(order2, OrderDto.class);
	}


	@Override
	public int getMaxOrderId() {
		int id = this.orderRepo.getLastOrderId();	
		return id;
	}


	@Override
	public Long generateOrderId() {
		int lastId = this.getMaxOrderId();
		String now = new SimpleDateFormat("yyyyMMddHH").format(new Date()).toString()+"00000";
		Long num=  Long.parseLong(now)+lastId+1;
		return num;
	}
	
	
}
