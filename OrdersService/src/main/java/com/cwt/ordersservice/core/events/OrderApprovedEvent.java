package com.cwt.ordersservice.core.events;

import com.cwt.ordersservice.core.model.OrderStatus;

import lombok.Value;

@Value
public class OrderApprovedEvent {

	private final String orderId;
	private final OrderStatus orderStatus = OrderStatus.APPROVED;
	
}
