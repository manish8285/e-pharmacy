package com.multishop.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length=50)
	private String name;
	
	@Column(length = 25)
	private String city;
	
	@Column(length = 25)
	private String state;
	
	private int pincode;
	
	private long mobile;
	
	@Column(length = 400)
	private String address;
	
}
