package com.multishop.dtos;

import lombok.Data;

@Data
public class JwtAuthResponse {
	String token;
	UserDto user;
}
