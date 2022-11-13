package com.multishop.serviceImples;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.multishop.dtos.DeliveryChargeDto;
import com.multishop.dtos.PickupRequestDto;
import com.multishop.entites.Order;
import com.multishop.entites.ProductList;
import com.multishop.entites.Status;
import com.multishop.helper.CancelPickupDto;
import com.multishop.helper.PickupResponseDto;
import com.multishop.repositories.OrderRepo;
import com.multishop.repositories.StatusRepo;
import com.multishop.services.DeliveryService;

@Service
public class DeliveryServiceImple implements DeliveryService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private StatusRepo statusRepo;
	
	private String item_name;
	
	private boolean f=false;

	@Override
	public float calculateDeliveryCharge(int pincode, int weight) {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <DeliveryChargeDto> entity = new HttpEntity<DeliveryChargeDto>(headers);
	      //a6d41c39d0b91eec8aea9b2bcd3fc91c829248
	      String url="https://pickrr.com/api-v2/client/fetch-price-calculator-generic/?auth_token=a6d41c39d0b91eec8aea9b2bcd3fc91c829248&shipment_type=forward&pickup_pincode=122003&drop_pincode="+pincode+"&delivery_mode=heavy_surface&length=10&breadth=10&height=10&weight=1&payment_mode=prepaid";
	      DeliveryChargeDto resp= this.restTemplate.exchange(url, HttpMethod.GET, entity, DeliveryChargeDto.class).getBody();
		return resp.getRate_list().get(0).getDelivered_charges();
	}
	
	//pickup Request 
	public String requestPickup(long orderId) {
		Order order = this.orderRepo.findOrderByOrderId(orderId);
		PickupRequestDto pickupRequest = new PickupRequestDto();
		pickupRequest.setAuth_token("a6d41c39d0b91eec8aea9b2bcd3fc91c829248");
		pickupRequest.setClient_order_id(order.getOrderId().toString());
		this.item_name="";
		order.getItems().forEach((ProductList item)->{
			this.item_name= this.item_name+item.getProduct().getName()+",";
		});
		pickupRequest.setItem_name(this.item_name);
		
		pickupRequest.setFrom_name("Manish");
		pickupRequest.setFrom_phone_number("8285482825");
		pickupRequest.setFrom_address("H.No 618, Near Rama Tent House, Matachowk, Wazirabad, Sector 52, Gurgaon, Haryana");
		pickupRequest.setFrom_pincode("122003");
		
		pickupRequest.setTo_name(order.getAddress().getName());
		pickupRequest.setTo_phone_number(Long.toString(order.getAddress().getMobile()));
		pickupRequest.setTo_pincode(Integer.toString(order.getAddress().getPincode()));
		pickupRequest.setTo_address(order.getAddress().getAddress());
		
		pickupRequest.setInvoice_value(order.getAmount());
		float cod = 0;
		if(order.getOrdertype() != null && order.getOrdertype().equals("POSTPAID")) {
			cod=order.getAmount();
		}
		pickupRequest.setCod_amount(cod);
		
		pickupRequest.setClient_order_id(Long.toString(orderId));
		pickupRequest.setItem_weight(1);
		pickupRequest.setHas_dg(true);
		pickupRequest.setHas_surface(false);
		

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<PickupRequestDto> entity = new HttpEntity<PickupRequestDto>(pickupRequest,headers);
		
		String url ="https://pickrr.com/api/place-order/";
		PickupResponseDto resp = this.restTemplate.exchange(url,HttpMethod.POST,entity,PickupResponseDto.class).getBody();
		
		Status st = new Status();
		st.setOrder(order);
		st.setStatus("PICKUP REQUESTED");
		st.setDate(new Date().toString());
		Status st2 = this.statusRepo.save(st);
		
		order.setTrackingId(resp.getTracking_id());
		order.setCourier(resp.getCourier());
		order.setManifest_link(resp.getManifest_link());
		this.orderRepo.save(order);
		
		return resp.toString();	
	}
	
	

	@Override
	public Boolean cancelPickupRequest(long orderId) {
		
		
		Order order = this.orderRepo.findOrderByOrderId(orderId);
		List<Status> status_list = order.getStatus();
		status_list.forEach((Status st)->{
			if(st.getStatus().equals("PICKUP REQUESTED")) {
				this.f=true;
			}
		});
		
		if(f) {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			CancelPickupDto cancelPickup = new CancelPickupDto();
			cancelPickup.setAuth_token("a6d41c39d0b91eec8aea9b2bcd3fc91c829248");
			cancelPickup.setTracking_id(order.getTrackingId());
			HttpEntity<CancelPickupDto> entity = new HttpEntity<CancelPickupDto>(cancelPickup, headers);
			String url ="";
			this.restTemplate.exchange(url,HttpMethod.POST,entity, String.class).getBody();
			Status st = new Status();
			st.setDate(new Date().toString());
			st.setOrder(order);
			Status st2=this.statusRepo.save(st);
			status_list.add(st2);
			order.setStatus(status_list);
			return true;
		}
		
		return false;
	}
	
	

}
