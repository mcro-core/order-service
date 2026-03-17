package com.micro_core.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseInventoryDto {
    private Long productId;
    private Integer quantity;
    private boolean isInStock;
}
