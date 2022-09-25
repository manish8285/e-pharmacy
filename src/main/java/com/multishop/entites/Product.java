package com.multishop.entites;

import java.util.ArrayList;
import java.util.List;

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
	private int quantity;
	@Column(length = 1000)
	private String description;
	private String tags;

	@OneToMany
	private List<Image> images= new ArrayList<Image>();
	
	@ManyToOne
	private Category category;
	
}
