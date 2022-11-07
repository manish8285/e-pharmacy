package com.multishop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multishop.entites.Product;
import com.multishop.entites.ProductList;

public interface ProductListRepo extends JpaRepository<ProductList, Integer> {
	
	List<ProductList> findAllByProduct(Product product);

}
