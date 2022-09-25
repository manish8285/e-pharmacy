//home controller for multishop outlet page 19-09-2022
package com.multishop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/")
	public String getHomePage() {
		return "This is home page";
	}
}
