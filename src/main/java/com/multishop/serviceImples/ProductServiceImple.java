package com.multishop.serviceImples;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.multishop.dtos.ProductDto;
import com.multishop.dtos.ProductResponse;
import com.multishop.entites.Category;
import com.multishop.entites.Image;
import com.multishop.entites.Product;
import com.multishop.exceptions.ResourceNotFoundException;
import com.multishop.repositories.CategoryRepo;
import com.multishop.repositories.ImageRepo;
import com.multishop.repositories.ProductRepo;
import com.multishop.services.FileUploadService;
import com.multishop.services.ProductService;

@Service
public class ProductServiceImple implements ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ImageRepo imageRepo;

	@Override
	public ProductDto getProductById(int productId) {
		Product product = this.productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "Id", productId));
		return this.modelMapper.map(product, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto) {
		Product product = this.productRepo.findById(productDto.getId()).orElseThrow(()->new ResourceNotFoundException("Product", "Id", productDto.getId()));
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setMrp(productDto.getMrp());
		product.setPrice(productDto.getPrice());
		product.setRack(productDto.getRack());
		Product product2 = this.productRepo.save(product);
		return this.modelMapper.map(product2, ProductDto.class);
	}

	@Override
	public void deleteProduct(int productId) {
		Product product = this.productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "Id", productId));
		this.productRepo.delete(product);
	}

	@Override
	public ProductDto setProductImage(int productId, MultipartFile file) {
		Product product = this.productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "Id", productId));
		String fileName=null;
		try {
			fileName = this.fileUploadService.saveFile("products", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image img = new Image();
		img.setName(fileName);
		Image image2 = imageRepo.save(img);
		List<Image> images = product.getImages();
		images.add(image2);
		product.setImages(images);
		Product product2 = this.productRepo.save(product);
		return this.modelMapper.map(product2, ProductDto.class);
	}

	//get all products by page number
	@Override
	public ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy,String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> pageProducts = this.productRepo.findAll(pageable);
		List<Product> products = pageProducts.getContent();
		
		ProductResponse pr = new ProductResponse();
		pr.setProducts(products.stream().map(p->this.modelMapper.map(p,ProductDto.class)).collect(Collectors.toList()));
		pr.setLastPage(pageProducts.isLast());
		pr.setPageNumber(pageProducts.getNumber());
		pr.setTotalPages(pageProducts.getTotalPages());
		pr.setTotalElements(pageProducts.getNumberOfElements());
		
		return pr;
	}
	
	


	@Override
	public ProductResponse getAllProductsByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		PageRequest request = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> pageProducts = this.productRepo.getProductsByCategory(categoryId, request);
		List<Product> products = pageProducts.getContent();
		
		ProductResponse pr = new ProductResponse();
		pr.setProducts(products.stream().map(p->this.modelMapper.map(p,ProductDto.class)).collect(Collectors.toList()));
		pr.setLastPage(pageProducts.isLast());
		pr.setPageNumber(pageProducts.getNumber());
		pr.setTotalPages(pageProducts.getTotalPages());
		pr.setTotalElements(pageProducts.getNumberOfElements());
		
		return pr;
	}

	@Override
	public ProductResponse searchProduct(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		PageRequest request = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> pageProducts = this.productRepo.searchProductsByKeyword(keyword, request);
		List<Product> products = pageProducts.getContent();
		
		ProductResponse pr = new ProductResponse();
		pr.setProducts(products.stream().map(p->this.modelMapper.map(p,ProductDto.class)).collect(Collectors.toList()));
		pr.setLastPage(pageProducts.isLast());
		pr.setPageNumber(pageProducts.getNumber());
		pr.setTotalPages(pageProducts.getTotalPages());
		pr.setTotalElements(pageProducts.getNumberOfElements());
		
		return pr;
	}

	@Override
	public ProductDto addProduct(ProductDto productDto) {
		Product product = this.productRepo.save(this.modelMapper.map(productDto, Product.class));
		Category category = this.categoryRepo.findById(productDto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Categorh", "Id", productDto.getCategoryId()));
		product.setCategory(category);
		Product product2 = this.productRepo.save(product);
		return this.modelMapper.map(product2, ProductDto.class);
	}

	@Override
	public ProductDto deleteProductImage(int productId, int imageId) {
		//finding product and removing image from product
		Product product = this.productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "Id", productId));
		List<Image> images = product.getImages();
		Image image = this.imageRepo.findById(imageId).orElseThrow(()->new ResourceNotFoundException("Image", "Id", imageId));
		images.remove(image);
		product.setImages(images);
		this.productRepo.save(product);
		
		// deleting imge from database
		String imageName = image.getName();
		
		// now deleting actual image file
		this.fileUploadService.deleteImage("products", imageName);
		this.imageRepo.delete(image);
		return this.modelMapper.map(product, ProductDto.class);
	}

}
