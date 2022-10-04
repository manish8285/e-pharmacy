package com.multishop.entites;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
@Table(name="ORDERS")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Long orderId;
	
	@OneToMany(mappedBy="order",cascade = CascadeType.ALL)
	private List<ProductList> items=new ArrayList<ProductList>();
	
	private Date date;
	
	@OneToOne
	private Address address;
	
	private String ordertype;
	
	float amount;
	
	private String status;
	
	@ManyToOne
	private Customer customer;
	
	@JsonIgnore
	public Customer getCustomer() {
		return this.customer;
	}

	
}
