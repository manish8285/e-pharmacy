package com.multishop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multishop.entites.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
