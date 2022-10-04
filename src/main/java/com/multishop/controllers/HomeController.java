//home controller for multishop outlet page 19-09-2022
package com.multishop.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.multishop.services.OrderService;

@RestController
public class HomeController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/")
	public String getHomePage() {
		return "This is home page";
	}
	
	@ModelAttribute
	public void getAuthenticate() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("principal = "+name);
	}
	
	@GetMapping("/test")
	public Long testPage(@RequestHeader(name="Authorization") String token,Principal principal) {
		
		System.out.println("token ="+token);
		return this.orderService.generateOrderId();
	}
}
