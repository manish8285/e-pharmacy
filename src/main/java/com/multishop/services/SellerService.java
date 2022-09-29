package com.multishop.services;

import com.multishop.dtos.UserDto;
import com.multishop.entites.User;

public interface SellerService {
	
	UserDto registerNewSeller(UserDto user);
}
