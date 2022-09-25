package com.multishop.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.multishop.dtos.CategoryDto;

public interface CategoryService {
	
	//get all categories
	List<CategoryDto> getAllCategories();
	
	//create category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	
	//delete category
	void deleteCategory(int categoryId);

	CategoryDto updateCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategoryImage(int categoryId,MultipartFile file);
}
