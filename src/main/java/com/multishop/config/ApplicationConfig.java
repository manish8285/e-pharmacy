package com.multishop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.multishop.entites.Role;
import com.multishop.repositories.RoleRepo;

@Component
public class ApplicationConfig {
	
	@Autowired
	private RoleRepo roleRepo;

	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		Role admin = new Role();
		admin.setId(101);
		admin.setName("ROLE_ADMIN");
		
		Role seller = new Role();
		seller.setId(102);
		seller.setName("ROLE_SELLER");
		
		Role customer = new Role();
		customer.setId(103);
		customer.setName("ROLE_CUSTOMER");
		
		if(this.roleRepo.count()==0) {
			this.roleRepo.save(admin);
			this.roleRepo.save(seller);
			this.roleRepo.save(customer);
		}
				
	}
}
