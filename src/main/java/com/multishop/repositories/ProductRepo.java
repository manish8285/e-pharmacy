package com.multishop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.multishop.entites.Category;
import com.multishop.entites.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
	
	@Query("from Product as p where p.category.id=:catId")
	Page<Product> getProductsByCategory(@Param("catId") int categoryId,Pageable pageable);
	
	@Query("from Product as p where p.name like %:key%")
	Page<Product> searchProductsByKeyword(@Param("key") String key,Pageable pageable);	
	
	@Query("select count(*) from Product p where p.category=:category")
	int getTotalProducts(@Param("category") Category category);
}
