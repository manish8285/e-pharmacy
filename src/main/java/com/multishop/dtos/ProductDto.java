package com.multishop.dtos;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.multishop.entites.Image;

import lombok.Data;

@Data
public class ProductDto {
	
	private int id;
	@NotEmpty
	private String name;

	private float mrp;
	private float price;
	@Min(1)
	private int quantity;
	@NotEmpty
	private String rack;
	@NotEmpty
	private String origin;
	@NotEmpty
	private String brand;
	
	private String pathy;
	
	private String expiry;
	
	private String size;

	private String description;
	private String tags;
	private List<Image> images;
	@Min(1)
	private int categoryId;
}
