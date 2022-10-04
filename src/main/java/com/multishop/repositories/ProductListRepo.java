package com.multishop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multishop.entites.ProductList;

public interface ProductListRepo extends JpaRepository<ProductList, Integer> {

}
