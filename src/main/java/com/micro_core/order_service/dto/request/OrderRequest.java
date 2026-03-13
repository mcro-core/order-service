package com.micro_core.order_service.dto.request;

import com.micro_core.order_service.entity.OrderItems;
import com.micro_core.order_service.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private Long userId;
    private List<OrderItemRequest> orderItems;
}
