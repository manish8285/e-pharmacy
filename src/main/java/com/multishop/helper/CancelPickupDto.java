package com.multishop.helper;

import lombok.Data;

@Data
public class CancelPickupDto {
	private String auth_token; // "YOUR_AUTH_TOKEN",
    private String tracking_id; // "7456987654"
}
