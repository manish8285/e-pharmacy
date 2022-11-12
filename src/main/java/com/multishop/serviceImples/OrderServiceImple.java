package com.multishop.serviceImples;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.multishop.dtos.OrderDto;
import com.multishop.dtos.OrderResponse;
import com.multishop.entites.Address;
import com.multishop.entites.Customer;
import com.multishop.entites.Order;
import com.multishop.entites.Product;
import com.multishop.entites.ProductList;
import com.multishop.entites.Status;
import com.multishop.exceptions.ResourceNotFoundException;
import com.multishop.repositories.AddressRepo;
import com.multishop.repositories.CustomerRepo;
import com.multishop.repositories.OrderRepo;
import com.multishop.repositories.ProductListRepo;
import com.multishop.repositories.ProductRepo;
import com.multishop.repositories.StatusRepo;
import com.multishop.services.DeliveryService;
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
	
	@Autowired
	private StatusRepo statusRepo;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private DeliveryService deliveryService;
	

	@Override
	public OrderDto getOrderById(int orderId) {
		Order order = this.orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order", "id", orderId));
		
		return this.modelMapper.map(order, OrderDto.class);
	}


	@Override
	public List<OrderDto> getAllOrders() {
		List<Order> orders = this.orderRepo.findAllByOrderByIdDesc();
		return orders.stream().map((order)->this.modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
	}

	@Override
	public OrderResponse getAllCustomerOrders(int userId,int page,int size) {
		List<Customer> customer = this.customerRepo.getCustomerByUserId(userId);
		Customer customer1 = customer.get(0);
		PageRequest pageable = PageRequest.of(page, size);
		Page<Order> result = this.orderRepo.findAllByCustomerOrderByIdDesc(customer1,pageable);
		//System.out.println(orders2.get(0).getId());
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setLastPage(result.isLast());
		orderResponse.setPageNumber(result.getNumber());
		orderResponse.setPageSize(result.getSize()); //
		orderResponse.setTotalElements(result.getNumberOfElements()); //ok
		orderResponse.setTotalPages(result.getTotalPages()); //ok
		List<OrderDto> ordersList = result.getContent().stream().map((order)->this.modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
		orderResponse.setOrders(ordersList);
		return orderResponse;
	}

	@Override
	public OrderDto createOrder(OrderDto orderDto, int customerId) {
		float amount = 0f;

		int addressId=orderDto.getAddress().getId();
		Address address = this.addressRepo.findById(addressId).orElseThrow(()->new ResourceNotFoundException("Address", "id", addressId));
		Customer customer = this.customerRepo.getCustomerByUserId(customerId).get(0);
		
		List<ProductList> items = orderDto.getItems().stream().map((item)->this.modelMapper.map(item, ProductList.class)).collect(Collectors.toList());
		
		Status st = new Status();
		st.setDate(new Date().toString());
		st.setStatus("PLACED");
		
		//re verify products details with records

		for(int i=0;i<=items.size()-1;i++) {
			ProductList item = items.get(i);
			int productId=item.getProduct().getId();
			Product product = this.productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "id", productId));
			item.setProduct(product);
			amount = amount + product.getPrice()*item.getQuantity();
		}
		
		Order order = new Order();
		order.setAddress(address);
		order.setCustomer(customer);
		order.setOrderId(this.generateOrderId());
		//order.setItems(items);
		order.setDate(new Date());
		order.setOrdertype("POSTPAID");
		//set order delivery charge
		float charge = this.deliveryService.calculateDeliveryCharge(order.getAddress().getPincode(), 1);
		order.setDeliverycharge(charge);
		order.setAmount(amount+charge);
		
		Order order2 = this.orderRepo.save(order);
		
		st.setOrder(order2);
		Status st2 = this.statusRepo.save(st);
		List<Status> status= new ArrayList<Status>();
		status.add(st2);
		
		List<ProductList> items2 = this.productListRepo.saveAll(items);
		
		int i=0;
		for(ProductList item : items2) {
			System.out.println("order id ="+order2.getId());
			item.setOrder(order2);
			this.productListRepo.save(item);
			items2.set(0, item);
			i++;
		}
		
		this.productListRepo.saveAll(items2);
		
		order2.setItems(items2);
		
		order2.setStatus(status);
		this.orderRepo.save(order2);
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


	@Override
	public OrderDto getOrderByOrderId(Long orderId) {
		Order order = this.orderRepo.findOrderByOrderId(orderId);
		return this.modelMapper.map(order, OrderDto.class);
	}



	@Override
	public OrderDto getOrderStatus(int userId, long orderId) {
		Order order = this.orderRepo.findOrderByOrderId(orderId);
		Customer customer= this.customerRepo.getCustomerByUserId(userId).get(0);
		if(customer.equals(order.getCustomer())) {
			return this.modelMapper.map(order, OrderDto.class);
			
		}
		return null;
	}


	@Override
	public String requestPickup(long orderId) {
		Order order = this.orderRepo.findOrderByOrderId(orderId);
		
		return null;
	}

	//add cancel order status only
	@Override
	public boolean cancelOrderStatus(long orderId) {
		boolean f = false;
		Order order = this.orderRepo.findOrderByOrderId(orderId);
		Status st = new Status();
		st.setOrder(order);
		st.setDate(new Date().toString());
		st.setStatus("CANCELLED");
		this.statusRepo.save(st);
		f=true;
		return f;
	}
	
	//add cancel order status only
		@Override
		public boolean deliveredOrderStatus(long orderId) {
			boolean f = false;
			Order order = this.orderRepo.findOrderByOrderId(orderId);
			Status st = new Status();
			st.setOrder(order);
			st.setDate(new Date().toString());
			st.setStatus("DELIVERED");
			this.statusRepo.save(st);
			f=true;
			return f;
		}
		
		//add cancel order status only
		@Override
		public boolean outForDeliveryOrderStatus(long orderId) {
			boolean f = false;
			Order order = this.orderRepo.findOrderByOrderId(orderId);
			Status st = new Status();
			st.setOrder(order);
			st.setDate(new Date().toString());
			st.setStatus("OUT FOR DELIVERY");
			this.statusRepo.save(st);
			f=true;
			return f;
		}


		@Override
		public boolean cancelPickupRequest(long orderId) {
			return false;
		}
	
	
}
