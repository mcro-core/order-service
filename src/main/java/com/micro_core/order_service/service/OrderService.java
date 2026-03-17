package com.micro_core.order_service.service;

import com.micro_core.order_service.dto.request.OrderRequest;
import com.micro_core.order_service.dto.response.OrderResponseDto;
import org.springframework.data.domain.Page;

public interface OrderService {
    public void createOrder(OrderRequest orderRequest);
    public void updateOrderStatus(String orderStatus, Long orderId);
    public void deleteOrder(Long orderId);
    public OrderResponseDto getOrderById(Long orderId);
    public Page<OrderResponseDto> getAllOrder(int page, int size);
    public void cancelOrder(Long orderId);
}
