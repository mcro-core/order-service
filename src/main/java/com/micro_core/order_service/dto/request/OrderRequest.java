package com.micro_core.order_service.dto.request;

import com.micro_core.order_service.entity.OrderItems;
import com.micro_core.order_service.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<OrderItemRequest> orderItems;
}
