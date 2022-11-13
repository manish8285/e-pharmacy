package com.multishop.helper;

import lombok.Data;

@Data
public class PickupResponseDto {

	private boolean success; 
    private String order_id;
    //"order_pk": 236781,
    private String tracking_id;
    private String manifest_link; //: "https://www.pickrr.com/order/generate-user-order-manifest/YOUR_AUTH_TOKEN/ORDER_PK/",
    //"routing_code": "PCK/NB",
    //"client_order_id": "WAYNE007",
    private String courier; // "Delhivery",
    private String dispatch_mode; 
}
