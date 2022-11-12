package com.multishop.controllers;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.multishop.dtos.CustomerDto;
import com.multishop.dtos.OrderDto;
import com.multishop.dtos.OrderResponse;
import com.multishop.dtos.UserDto;
import com.multishop.entites.Address;
import com.multishop.entites.User;
import com.multishop.services.CustomerService;
import com.multishop.services.DeliveryService;
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
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DeliveryService deliveryService;
	
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
	//update user
	@PutMapping("/user/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable Integer userId,
			@RequestBody UserDto userDto){
		//System.out.println("user id = "+userId);
		if(this.user.getId() != userId) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(this.userService.updateUser(userDto, userId));
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
	
	// get order by  order id
		@GetMapping("/myorder/order/{orderId}")
		public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId){
			return ResponseEntity.ok(this.orderService.getOrderByOrderId(orderId));
		}
	
	// get all orders by user id
	@GetMapping("/order/myorders/{userId}/")
	public ResponseEntity<OrderResponse> getAllOrders(@PathVariable Integer userId,
			@RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize){
		if(this.user.getId() != userId) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(this.orderService.getAllCustomerOrders(userId,pageNumber,pageSize));
	}
	
//	@CrossOrigin(allowedHeaders = "*",allowCredentials = "true",originPatterns = "http://localhost:3000")
//	// get order status by order id
//		@GetMapping("/status/order/{orderId}")
//		public ResponseEntity<String> getOrderStatus(@PathVariable Long orderId){
//			
//			OrderDto orderDto = this.orderService.getOrderStatus(this.user.getId(), orderId);
//			if(orderDto ==null) {
//			//	return ResponseEntity.badRequest().build();
//			}
//			
//			//if(orderDto.getTrackingId() != null) {
//				String url = "https://async.pickrr.com/track/tracking/?tracking_id="+orderDto.getTrackingId()+"&auth_token=a6d41c39d0b91eec8aea9b2bcd3fc91c829248";
//				return this.restTemplate.getForEntity(url, String.class);
//			//}
//			//return ResponseEntity.ok(orderDto.getStatus().toString());
//			
//		}
	
	@GetMapping("/status/order")
	public ResponseEntity<String> getOrderStatus(){
		String url = "https://async.pickrr.com/track/tracking/?tracking_id="+"D51829058"+"&auth_token=a6d41c39d0b91eec8aea9b2bcd3fc91c829248";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET,requestEntity,String.class);
		return response;
		//return ResponseEntity.ok("Nice joke ok");
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
	
	@GetMapping("/delivery/{pincode}")
	public ResponseEntity<String> getOrderDeliveryCharge(@PathVariable Integer pincode){
//		HttpHeaders headers = new HttpHeaders();
//	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//	      HttpEntity <String> entity = new HttpEntity<String>(headers);
//	      //a6d41c39d0b91eec8aea9b2bcd3fc91c829248
//	      String url="https://pickrr.com/api-v2/client/fetch-price-calculator-generic/?auth_token=a6d41c39d0b91eec8aea9b2bcd3fc91c829248&shipment_type=forward&pickup_pincode=122003&drop_pincode="+pincode+"&delivery_mode=heavy_surface&length=10&breadth=10&height=10&weight=1&payment_mode=prepaid";
//	      String resp= this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
		float charge = this.deliveryService.calculateDeliveryCharge(pincode, 1);
	     return ResponseEntity.ok(""+charge);
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
