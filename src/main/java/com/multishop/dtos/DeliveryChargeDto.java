package com.multishop.dtos;

import java.util.ArrayList;
import java.util.List;

import com.multishop.helper.DeliveryRate;

import lombok.Data;

@Data
public class DeliveryChargeDto {

	private String from_city;
	private String from_state;
	private List<DeliveryRate> rate_list = new ArrayList<DeliveryRate>();
}
