package com.multishop.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.multishop.dtos.CategoryDto;
import com.multishop.entites.Category;
import com.multishop.services.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> categories = this.categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping("/categories/")
	public ResponseEntity<List<CategoryDto>> publicGetAllCategories(){
		List<CategoryDto> categories = this.categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
	
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto category = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(category,HttpStatus.CREATED);
	}
	
	@PostMapping(value="/{categoryId}/image")
	public ResponseEntity<CategoryDto> updateCategory(
			@PathVariable("categoryId") Integer categoryId,
			@RequestParam("image") MultipartFile file){
		return ResponseEntity.ok(this.categoryService.updateCategoryImage(categoryId, file ));
	}
	
	@PutMapping("/")
	public ResponseEntity<CategoryDto> updateCategory(
			@RequestBody CategoryDto categoryDto){
		return ResponseEntity.ok(this.categoryService.updateCategory(categoryDto ));
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Integer catId){
		this.categoryService.deleteCategory(catId);
		return ResponseEntity.ok("Category has been deleted successfully");
	}
	
	
}
