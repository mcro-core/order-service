package com.micro_core.order_service.api;

import com.micro_core.order_service.dto.request.OrderRequest;
import com.micro_core.order_service.dto.response.OrderResponseDto;
import com.micro_core.order_service.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
        return new ResponseEntity<>("Order placed successfully!", HttpStatus.CREATED);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @NotBlank(message = "Status cannot be empty") @RequestParam String status,
            @Min(value = 1, message = "Order ID must be a positive number") @PathVariable Long orderId) {
        orderService.updateOrderStatus(status, orderId);
        return ResponseEntity.ok("Order status updated successfully.");
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(
            @Min(value = 1, message = "Invalid Order ID") @PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled and inventory restored.");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @Min(value = 1, message = "Invalid Order ID") @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(
            @Min(value = 0, message = "Page index cannot be negative") @RequestParam(defaultValue = "0") int page,
            @Min(value = 1, message = "Page size must be at least 1") @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.getAllOrder(page, size));
    }
}