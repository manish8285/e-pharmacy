package com.multishop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multishop.entites.User;


public interface UserRepo extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);
	
	User findUserByEmail(String email);
}
