package com.micro_core.order_service.service;

import com.micro_core.order_service.dto.request.OrderItemRequest;
import com.micro_core.order_service.dto.response.OrderItemResponseDto;
import com.micro_core.order_service.model.OrderItem;
import org.springframework.data.domain.Page;

public interface OrderItemService {
    public void createOrderItem(OrderItem orderItemRequest);
    public void updateOrderItem(OrderItem orderItem, Long orderItemId);
    public void deleteOrderItem(Long orderId);
    public OrderItemResponseDto getOrderItemById(Long orderId);
    public Page<OrderItemResponseDto> getAllOrderItem(int page, int size);
}
