package com.multishop.controllers;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.multishop.dtos.JwtAuthRequest;
import com.multishop.dtos.JwtAuthResponse;
import com.multishop.dtos.UserDto;
import com.multishop.entites.User;
import com.multishop.exceptions.ApiException;
import com.multishop.security.JwtTokenHelper;
import com.multishop.services.UserService;


@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private int otp;

	@PostMapping("login")
	ResponseEntity<JwtAuthResponse> createToken(
			@Valid @RequestBody JwtAuthRequest request) throws Exception{
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		UserDto userDto = this.modelMapper.map(userDetails, UserDto.class);
		//if(userDto.getPassword().equals(request.getPassword())) {
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setToken(token);
		authResponse.setUser(userDto);
		return new ResponseEntity<JwtAuthResponse>(authResponse,HttpStatus.OK);
		//}
		//return ResponseEntity.badRequest().build();
		
	}
	
	@PostMapping("signup")
	ResponseEntity<UserDto> registerNewUser(
			@Valid @RequestBody UserDto userDto){
		UserDto createdUser=null;
			try {
				createdUser = this.userService.registerNewRegister(userDto);
			} catch (SQLIntegrityConstraintViolationException e) {
				throw new ApiException("Email Already Exist");
			}

		return new ResponseEntity<>(createdUser,HttpStatus.OK);
		
	}
	
	@GetMapping("checkEmail")
	ResponseEntity<String> checkEmailUnique(@RequestParam("email") String email){
		System.out.println("email ="+email);
		if(this.userService.getUserByUsername(email)==null) {
			return ResponseEntity.ok("looks good");
		}
		return new ResponseEntity<String>("This email already exist",HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/sendOTP/{emailId}")
	ResponseEntity<String> sendOTPtoEmail(@PathVariable String emailId){
		User user = this.userService.getUserByUsername(emailId);
		if(user==null) {
			return new ResponseEntity<String>("Sorry this email is not associated with any user",HttpStatus.BAD_REQUEST);
		}
		int otp = this.userService.sendOTPtoEmail(emailId);
		this.otp=otp;
		return ResponseEntity.ok("OTP has been sent on email"+emailId);
	}
	
	@GetMapping("/verifyOTP/{otp}")
	ResponseEntity<Boolean> verifyOTP(@PathVariable Integer otp){
		if(this.otp==otp) {
		return ResponseEntity.ok(true);
		}
		return ResponseEntity.ok(false);
	}
	@PostMapping("/resetPassword")
	ResponseEntity<String> resetPassword(@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("otp") Integer otp){
		if(this.otp==otp) {
			this.userService.resetUserPassword(email, password);
		return ResponseEntity.ok("Your password has been changed successfully !!");
		}
		return new ResponseEntity<String>("Please Enter Valid Credential",HttpStatus.BAD_REQUEST);
	}
	
	

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
		this.authenticationManager.authenticate(authenticationToken);
		}catch(BadCredentialsException e) {
			//System.out.println("Invalid username or password");
			throw new ApiException("Invalid username or password");
			
		}
		
	}
	
}
