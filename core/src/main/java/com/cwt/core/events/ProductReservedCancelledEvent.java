package com.cwt.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReservedCancelledEvent {
    private final String productId;
    private final int quantity;
    private final String orderId;
    private final String userId;
    private final String reason;
}
