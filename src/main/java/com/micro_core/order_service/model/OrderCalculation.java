package com.micro_core.order_service.model;

import com.micro_core.order_service.entity.OrderItems;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCalculation {
    private List<OrderItems> items;
    private BigDecimal totalAmount;
    private BigDecimal totalDiscount;
    private BigDecimal netAmount;
}
