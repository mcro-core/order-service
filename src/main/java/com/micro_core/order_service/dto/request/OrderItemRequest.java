package com.micro_core.order_service.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
}
