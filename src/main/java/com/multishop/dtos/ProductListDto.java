package com.multishop.dtos;



import lombok.Data;


//item
@Data
public class ProductListDto {
	private int id;

	private ProductDto product;
	private int quantity;
	private int price;

}
