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
		admin.setId(ApplicationConstant.ADMIN_ID);
		admin.setName("ROLE_ADMIN");
		
		Role seller = new Role();
		seller.setId(ApplicationConstant.SELLER_ID);
		seller.setName("ROLE_SELLER");
		
		Role customer = new Role();
		customer.setId(ApplicationConstant.CUSTOMER_ID);
		customer.setName("ROLE_CUSTOMER");
		
		if(this.roleRepo.count()==0) {
			this.roleRepo.save(admin);
			this.roleRepo.save(seller);
			this.roleRepo.save(customer);
		}
				
	}
}
