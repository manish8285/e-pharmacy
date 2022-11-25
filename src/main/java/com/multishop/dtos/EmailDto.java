package com.multishop.dtos;

import lombok.Data;

@Data
public class EmailDto {

	private String to;
	private String subject;
	private String from_name;
	private String from_email;
	private String message;
}
