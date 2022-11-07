package com.multishop.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class JwtAuthRequest {
	//email
	@NotEmpty(message="User name can not be empty")
	private String username;
	@NotEmpty(message="Password can not be empty")
	private String password;
}
