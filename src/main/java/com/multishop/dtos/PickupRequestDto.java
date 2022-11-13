package com.multishop.dtos;

import lombok.Data;

@Data
public class PickupRequestDto {
	private String auth_token;
	private String item_name;
	private String from_name;
	private String from_phone_number;
	private String from_address;
	private String from_pincode;
	
	private String to_name;
	private String to_phone_number;
	private String to_pincode;
	private String to_address;
	
	private float invoice_value;
	private float cod_amount;
	private String client_order_id;
	private float item_weight;
	private boolean has_dg;
	private boolean has_surface;
}
