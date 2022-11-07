package com.multishop.dtos;

import lombok.Data;

@Data
public class OrderStatusDto {
	private int id;
	private String status;
	private String date;
}
