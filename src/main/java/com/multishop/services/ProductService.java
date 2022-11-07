package com.multishop.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.multishop.dtos.ProductDto;
import com.multishop.dtos.ProductResponse;

public interface ProductService {
	
	//add product
	ProductDto addProduct(ProductDto productDto);
	
	
	
	ProductDto getProductById(int productId); 
	
	//update product
	ProductDto updateProduct(ProductDto productDto);
	
	//increase product quantity
		ProductDto increaseProductQuantity(int productId);
		
	//decrease product 
	ProductDto decreaseProductQuantity(int productId);
	
	//delete product
	void deleteProduct(int productId);
	
	// add image to producgt
	ProductDto setProductImage(int productId,MultipartFile file);
	
	// add image to producgt image name
		ProductDto setProductImageName(int productId,String fileName);
	
	// delete image of product
	ProductDto deleteProductImage(int productId,int imageId);
	
	//show all products
	ProductResponse getAllProducts(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	//search product by keyword
	ProductResponse searchProduct(String keyword,int pageNumber,int pageSize,String sortBy,String sortDir);
	
	//get all product by category
	ProductResponse getAllProductsByCategory(int categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
	
	// retrive image name from url
	String getDriveImageName(String imageName);
}
