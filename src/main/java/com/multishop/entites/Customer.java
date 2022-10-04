package com.multishop.entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Customer implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonIgnore
	private int userId;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Address> address= new ArrayList<Address>();
	

	@OneToMany(mappedBy="customer",cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<Order>();
	
}
