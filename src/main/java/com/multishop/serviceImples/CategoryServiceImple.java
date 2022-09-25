package com.multishop.serviceImples;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.multishop.dtos.CategoryDto;
import com.multishop.entites.Category;
import com.multishop.exceptions.ResourceNotFoundException;
import com.multishop.repositories.CategoryRepo;
import com.multishop.services.CategoryService;
import com.multishop.services.FileUploadService;

@Service
public class CategoryServiceImple implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		return categories.stream().map(category->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
	}

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category category2 = this.categoryRepo.save(category);
		return this.modelMapper.map(category2, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto) {
		Category category = this.categoryRepo.findById(categoryDto.getId()).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryDto.getId()));
		category.setName(categoryDto.getName());
		category.setTags(categoryDto.getTags());

		Category category2 = this.categoryRepo.save(category);	
		return this.modelMapper.map(category2, CategoryDto.class);
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto updateCategoryImage(int categoryId, MultipartFile file) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		try {
			category.setImage(fileUploadService.saveFile("categories", file));
			Category category2 = categoryRepo.save(category);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.modelMapper.map(category, CategoryDto.class);
	}
	
	


}
