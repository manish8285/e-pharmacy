package com.multishop.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.multishop.entites.Address;
import com.multishop.entites.Customer;
import com.multishop.entites.ProductList;

import lombok.Data;

@Data
public class OrderDto {

private int id;
	
	private Long orderId;
	private List<ProductListDto> items=new ArrayList<ProductListDto>();
	
	private Date date;
	
	private Address address;
	
	private String ordertype;
	
	private float amount;
	
	private String trackingId;
	
	private List<OrderStatusDto> status;
	
}
