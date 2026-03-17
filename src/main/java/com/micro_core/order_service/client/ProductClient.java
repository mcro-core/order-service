package com.micro_core.order_service.client;

import com.micro_core.order_service.dto.response.ResponseProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/api/v1/products/order-product/{productId}")
    ResponseProductDto getProductById(@PathVariable("productId") Long productId);

}
