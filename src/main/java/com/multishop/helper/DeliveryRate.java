package com.multishop.helper;

import lombok.Data;

@Data
public class DeliveryRate {
	
	private String chargeable_weight;
	private String courier;
	private float delivered_charges;

}
