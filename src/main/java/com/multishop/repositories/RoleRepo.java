package com.multishop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multishop.entites.Role;


public interface RoleRepo extends JpaRepository<Role, Integer>{
	
}
