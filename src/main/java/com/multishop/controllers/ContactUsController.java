package com.multishop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multishop.dtos.EmailDto;
import com.multishop.services.EmailService;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactUsController {
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/us")
	ResponseEntity<String> contactForm(@RequestBody EmailDto emailDto){
		emailDto.setMessage("Name :"+emailDto.getFrom_name()+"\nEmail:"+emailDto.getFrom_email()+"\n"+emailDto.getMessage());
		String resp =this.emailService.sendEmail(emailDto);
		return ResponseEntity.ok(resp);
	}
	
	@PostMapping("/email")
	ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto){
		String resp =this.emailService.sendEmail(emailDto);
		return ResponseEntity.ok(resp);
	}
	

}
