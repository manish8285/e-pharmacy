package com.multishop.serviceImples;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.multishop.config.ApplicationConstant;
import com.multishop.dtos.UserDto;
import com.multishop.entites.Role;
import com.multishop.entites.User;
import com.multishop.exceptions.ResourceNotFoundException;
import com.multishop.repositories.RoleRepo;
import com.multishop.repositories.UserRepo;
import com.multishop.services.SellerService;

@Service
public class SellerServiceImple implements SellerService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto registerNewSeller(UserDto userDto) {
		//this.roleRepo.findById(ApplicationConstant.SELLER_ID).orElseThrow(()-> new ResourceNotFoundException("User","Id",userDto.getId()));
		Role role = this.roleRepo.findById(ApplicationConstant.SELLER_ID).orElseThrow(()->new ResourceNotFoundException("Role", "role constant id", 0));
		User user = this.modelMapper.map(userDto, User.class);
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		String encodedPassword = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User user2 = this.userRepo.save(user);
		return this.modelMapper.map(user2, UserDto.class);
	}

}
