package com.multishop.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private float mrp;
	private float price;
	private String rack;
	@Column(length=60)
	private String brand;
	
	@Column(length=60)
	private String size;
	
	@Column(length=40)
	private String origin;
	private int quantity;
	@Column(length = 1000)
	private String description;
	private String tags;
	@Column(length=50)
	private String pathy;
	@Column(length=50)
	private String expiry;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Image> images= new ArrayList<Image>();
	
	@ManyToOne(cascade = CascadeType.ALL)

	private Category category;
	
}
