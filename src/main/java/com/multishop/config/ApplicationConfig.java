package com.multishop.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.multishop.entites.Role;
import com.multishop.entites.Status;
import com.multishop.repositories.RoleRepo;
import com.multishop.repositories.StatusRepo;

@Component
public class ApplicationConfig {
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private StatusRepo statusRepo;

	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		Role admin = new Role();
		admin.setId(ApplicationConstant.ADMIN_ID);
		admin.setName("ROLE_ADMIN");
		
		Role seller = new Role();
		seller.setId(ApplicationConstant.SELLER_ID);
		seller.setName("ROLE_SELLER");
		
		Role customer = new Role();
		customer.setId(ApplicationConstant.CUSTOMER_ID);
		customer.setName("ROLE_CUSTOMER");
		
		
		if(!this.roleRepo.existsById(ApplicationConstant.SELLER_ID)) {
			this.roleRepo.save(seller);
		}
		if(!this.roleRepo.existsById(ApplicationConstant.CUSTOMER_ID)) {
			this.roleRepo.save(customer);
		}
		if(!this.roleRepo.existsById(ApplicationConstant.ADMIN_ID)) {
			this.roleRepo.save(admin);
		}
		
				
	}
}
