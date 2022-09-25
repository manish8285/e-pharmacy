package com.multishop.dtos;

import lombok.Data;

@Data
public class JwtAuthRequest {
	//email
	private String username;
	private String password;
}
