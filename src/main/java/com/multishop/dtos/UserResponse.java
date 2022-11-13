package com.multishop.dtos;

import java.util.List;

import lombok.Data;

@Data
public class UserResponse {

	private int pageNumber;
	private int pageSize;
	private int totalElements;
	private int totalPages;
	private boolean lastPage;
	private List<UserDto> users;
}
