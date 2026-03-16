package com.micro_core.order_service.dto.response;

import com.micro_core.order_service.entity.Order;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponseDto {
    private Long id;
    private String productName;
    private BigDecimal unitPrice;
    private BigDecimal discountValue;
    private BigDecimal sellingPrice;
    private Integer quantity;
    private BigDecimal subTotal;
    private Order order;

}
