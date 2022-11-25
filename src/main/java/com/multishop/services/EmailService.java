package com.multishop.services;

import com.multishop.dtos.EmailDto;

public interface EmailService {
	String sendEmail(EmailDto emailDto);
}
