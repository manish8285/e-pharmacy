package com.multishop.serviceImples;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.multishop.dtos.UserDto;
import com.multishop.entites.Role;
import com.multishop.entites.User;
import com.multishop.exceptions.ResourceNotFoundException;
import com.multishop.repositories.RoleRepo;
import com.multishop.repositories.UserRepo;
import com.multishop.services.UserService;

import lombok.Data;

@Data
@Service
public class UserServiceImple implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto user) {
		// TODO Auto-generated method stub
		User user1 = this.dtoToUser(user);
		User user2 = userRepo.save(user1);
		UserDto userDto = this.userToDto(user2);
		return userDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, int userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User user2 = this.userRepo.save(user);
		UserDto userToDto = this.userToDto(user2);
		
		return userToDto;
	}

	@Override
	public UserDto getUserById(int userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		UserDto userDto = this.userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> list = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return list;
	}

	@Override
	public void deleteUser(int userId) {
		User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		this.roleRepo.deleteAll(user.getRoles());
		user.setRoles(null);
		this.userRepo.delete(user);

	}

	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		/*
		 * user.setId(userDto.getId()); user.setName(userDto.getName());
		 * user.setAbout(userDto.getAbout()); user.setEmail(userDto.getEmail());
		 * user.setPassword(userDto.getPassword());
		 */
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		/*
		 * userDto.setAbout(user.getAbout()); userDto.setEmail(user.getEmail());
		 * userDto.setId(user.getId()); userDto.setName(user.getName());
		 * userDto.setPassword(user.getPassword());
		 */
		return userDto;
	}

	@Override
	public UserDto registerNewRegister(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		Role role = this.roleRepo.findById(101).get();
		Set<Role> roles = user.getRoles();
		roles.add(role);
		user.setRoles(roles);
		String encodedPassword = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User user2 = this.userRepo.save(user);
		return this.userToDto(user2);
	}
}
