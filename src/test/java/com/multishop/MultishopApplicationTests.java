package com.multishop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.multishop.dtos.CustomerDto;
import com.multishop.dtos.ProductDto;
import com.multishop.dtos.UserDto;
import com.multishop.entites.Address;
import com.multishop.entites.Customer;
import com.multishop.entites.Order;
import com.multishop.entites.Product;
import com.multishop.entites.ProductList;
import com.multishop.entites.User;
import com.multishop.repositories.AddressRepo;
import com.multishop.repositories.CustomerRepo;
import com.multishop.repositories.OrderRepo;
import com.multishop.repositories.ProductListRepo;
import com.multishop.repositories.ProductRepo;
import com.multishop.repositories.UserRepo;
import com.multishop.services.CustomerService;
import com.multishop.services.ProductService;
import com.multishop.services.UserService;

@SpringBootTest
class MultishopApplicationTests {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ProductListRepo productListRepo;
//	@Test
//	void contextLoads() {
//		String url = "https://drive.google.com/file/d/1UVRo41c75FK6BEdeFdfmb626q8XTv7tN/view?usp=sharing";
//		String name = this.productService.getDriveImageName(url);
//		System.out.println(name);
//	}
	
	@SuppressWarnings("deprecation")
	@Test
	void orderTest() {
//		User user =this.userRepo.getById(1);
		
//		Customer customer = new Customer();
//		customer.setId(user.getId());
//		Customer customer=this.customerRepo.getById(1);
//		Address address = new Address();
//		address.setCity("Gurgaon");
//		Address address2 = this.addressRepo.save(address);
//		System.out.println("address = "+address2.toString());   //
		
//		customer.setAddress(List.of(this.addressRepo.getById(1)));
//		Customer customer2 = this.customerRepo.save(customer);
//		
//		
//		Order order = new Order();
//		order.setAddress(customer2.getAddress().get(0));
//		order.setCustomer(customer2);
		
		//Product product=this.productRepo.getById(1);
//		ProductList list = new ProductList();
//		list.setPrice(500);
//		list.setQuantity(2);
//		list.setProduct(product);
//		ProductList list2 = this.productListRepo.save(this.productListRepo.getById(1));
//	
//		System.out.println("item list ="+list2.toString());
//		
//		List<ProductList> items= new ArrayList<ProductList>();
//		items.add(list2);
//		
//		order.setItems(items);
//		Order order2 = this.orderRepo.save(order);
//		System.out.println("order = "+order2.toString());
//		List orders = new ArrayList<Order>();
//		orders.add(order2);
//		customer.setOrders(orders);
//		
//		System.out.println("customer ="+customer2.getOrders().toString());
		}
	
	@Test
	void userTest() {
//		UserDto userDto = new UserDto();
//		userDto.setName("Hari2");
//		userDto.setEmail("hari2@gmail.com");
//		UserDto userDto2 = this.userService.createUser(userDto);
//		System.out.println("2 = "+userDto2.toString());
	}
	
	void getC() {
		CustomerDto dto = this.customerService.getCustomerByUserId(18);
		System.out.println(dto);
	}
	
	

}
