package com.micro_core.order_service.client;

import com.micro_core.order_service.dto.request.OrderItemRequest;
import com.micro_core.order_service.dto.response.ResponseInventoryDto;
import com.micro_core.order_service.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name ="INVENTORY-SERVICE")
public interface InventoryClient {

    @PostMapping("/api/v1/get-stock-list")
    boolean checkProductStock(@RequestBody List<OrderItemRequest> orderItemRequestList);

    @PostMapping("/api/v1/update-stock")
    void updateInventory(@RequestBody List<OrderItemRequest> orderItemRequests);

}
