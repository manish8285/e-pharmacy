package com.multishop.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.multishop.dtos.UserDto;
import com.multishop.dtos.UserResponse;
import com.multishop.entites.User;



public interface UserService {

	UserDto createUser(UserDto user);
	UserDto registerNewRegister(UserDto user) throws SQLIntegrityConstraintViolationException;
	
	UserDto updateUser(UserDto user,int userId);
	UserDto getUserById(int userId);
	List<UserDto> getAllUsers();
	void deleteUser(int userId);
	
	User getUserByUsername(String username);
	
	//send OTP
	int sendOTPtoEmail(String email);
	
	void resetUserPassword(String email, String password);
	UserResponse getAllUsers(int pageNumber,int pageSize);
	
}
