package com.multishop.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.multishop.entites.Address;
import com.multishop.entites.Order;

import lombok.Data;

@Data
public class CustomerDto {
	private int id;
	
	private int userId;
	
	
	private List<Address> address= new ArrayList<Address>();
	
	
	@JsonIgnore
	private List<OrderDto> orders = new ArrayList<OrderDto>();
	
	
	
}
