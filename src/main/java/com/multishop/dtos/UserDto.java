package com.multishop.dtos;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.multishop.entites.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	@NotEmpty(message="Name can not be blank")
	private String name;
	
	@NotEmpty(message="Enter valid Email Address")
	private String email;
	
	@Size(min=3,max=15,message="password must be 3 to 15 character long")
	private String password;

	private String about;
	
	private String gender;
	
	private String mobile;
	
	private Set<Role> roles = new HashSet<>();
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password=password;
	}
}
