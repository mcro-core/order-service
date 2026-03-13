package com.micro_core.order_service.dto.response;

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
public class OrderResponseDto {
    private Long id;
    private  String orderNumber;
    private Long userId;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal netAmount;
    private OrderStatus orderStatus;
    private List<OrderItems> orderItems;
}
