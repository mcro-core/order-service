package com.micro_core.order_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProductDto {
    private long id;
    private String productName;
    private String sku;
    private BigDecimal price;
    private Boolean hasDiscount;
    private BigDecimal discountedPrice;
}
