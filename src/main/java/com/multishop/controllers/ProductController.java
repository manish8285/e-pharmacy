package com.multishop.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.multishop.dtos.CategoryDto;
import com.multishop.dtos.ProductDto;
import com.multishop.dtos.ProductResponse;
import com.multishop.services.CategoryService;
import com.multishop.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	//creating new product
	@PostMapping("/")
	ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto){
		return new ResponseEntity<ProductDto>(this.productService.addProduct(productDto),HttpStatus.CREATED);
	}
	
	//updating product
	@PutMapping("/")
	ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto){
		return new ResponseEntity<ProductDto>(this.productService.updateProduct(productDto),HttpStatus.OK);
	}
	
	//increase product quantity
	@PutMapping("/increase/{productId}")
	ResponseEntity<ProductDto> increaseProductQuantity(@PathVariable Integer productId){
		return new ResponseEntity<ProductDto>(this.productService.increaseProductQuantity(productId),HttpStatus.OK);
	}
	
	//decrease product quantity
		@PutMapping("/decrease/{productId}")
		ResponseEntity<ProductDto> decreaseProductQuantity(@PathVariable Integer productId){
			return new ResponseEntity<ProductDto>(this.productService.decreaseProductQuantity(productId),HttpStatus.OK);
		}
	 
	// deleting product
	@DeleteMapping("/{productId}")
	ResponseEntity<String> deleteProduct(@PathVariable("productId") Integer productId){
		this.productService.deleteProduct(productId);
		return new ResponseEntity<String>("Product has been deleted successfully",HttpStatus.OK);
	}
	
	//setting image to the product
	@PostMapping("/product/{productId}/image")
	ResponseEntity<ProductDto> setProductImage(
			@PathVariable("productId") Integer productId,
			@RequestParam("image") MultipartFile file){
		return new ResponseEntity<ProductDto>(this.productService.setProductImage(productId,file),HttpStatus.OK);
	}
	
	//setting image name to the product
		@PostMapping("/product/{productId}/Drive_image")
		ResponseEntity<ProductDto> setProductImageName(
				@PathVariable("productId") Integer productId,
				@RequestParam("imageName") String imageName){
			return new ResponseEntity<ProductDto>(this.productService.setProductImageName(productId,imageName),HttpStatus.OK);
		}
	
	
	//delete product's single image
	@DeleteMapping("/product/{productId}/image/{imageId}")
	ResponseEntity<ProductDto> setProductImage(
			@PathVariable("productId") Integer productId,
			@PathVariable("imageId") Integer imageId){
		return new ResponseEntity<ProductDto>(this.productService.deleteProductImage(productId, imageId),HttpStatus.OK);
	}
	
	
	@GetMapping("/")
	ResponseEntity<ProductResponse> getAllProducts(@RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize,
			@RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
			){
		ProductResponse products = this.productService.getAllProducts(pageNumber,pageSize, sortBy,sortDir);
		return ResponseEntity.ok(products);
	}
	
	// get products by category
	@GetMapping("/category/products/")
	ResponseEntity<ProductResponse> getAllProductsByCategory(
			@RequestParam(value="categoryId") Integer categoryId,
			@RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize,
			@RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
			){
		ProductResponse products = this.productService.getAllProductsByCategory(categoryId,pageNumber,pageSize, sortBy,sortDir);
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/search")
	ResponseEntity<ProductResponse> searchProducts(
			@RequestParam(value="keyword") String keyword,
			@RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize,
			@RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
			){
		ProductResponse products = this.productService.searchProduct(keyword,pageNumber,pageSize, sortBy,sortDir);
		return ResponseEntity.ok(products);
	}
	
	//get single product by id
	@GetMapping("/product/{productId}")
	ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Integer productId){
		return ResponseEntity.ok(this.productService.getProductById(productId));
	}
	
	@GetMapping("/categories/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> categories = this.categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
	
}
