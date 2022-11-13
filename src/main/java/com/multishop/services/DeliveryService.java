package com.multishop.services;

public interface DeliveryService {
	
	float calculateDeliveryCharge(int pincode,int weight);
	
	String requestPickup(long orderId);
	Boolean cancelPickupRequest(long orderId);
}
