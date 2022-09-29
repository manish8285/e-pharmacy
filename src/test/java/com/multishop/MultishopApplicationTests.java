package com.multishop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.multishop.services.ProductService;

@SpringBootTest
class MultishopApplicationTests {
	
	@Autowired
	private ProductService productService;

	@Test
	void contextLoads() {
		String url = "https://drive.google.com/file/d/1UVRo41c75FK6BEdeFdfmb626q8XTv7tN/view?usp=sharing";
		String name = this.productService.getDriveImageName(url);
		System.out.println(name);
	}

}
