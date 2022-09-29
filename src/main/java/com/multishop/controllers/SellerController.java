package com.multishop.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multishop.dtos.UserDto;
import com.multishop.services.SellerService;
import com.multishop.services.UserService;

@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {
	
	@Autowired
	private UserService userService;
	
	private SellerService sellerService;
	
	@PostMapping("/signup")
	ResponseEntity<UserDto> registerNewSeller(
			@Valid @RequestBody UserDto userDto){
		UserDto createdUser = this.sellerService.registerNewSeller(userDto);
		return new ResponseEntity<>(createdUser,HttpStatus.OK);
		
	}
	
}
