package com.multishop.dtos;

import java.util.List;

import com.multishop.entites.Image;

import lombok.Data;

@Data
public class ProductDto {
	private int id;
	private String name;
	private float mrp;
	private float price;
	private int quantity;
	private String rack;
	private String description;
	private String tags;
	private List<Image> images;
	private int categoryId;
}
