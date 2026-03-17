package com.micro_core.order_service.service;

import com.micro_core.order_service.dto.request.OrderItemRequest;
import com.micro_core.order_service.dto.response.OrderItemResponseDto;
import com.micro_core.order_service.entity.OrderItems;
import org.springframework.data.domain.Page;

public interface OrderItemService {
    public void createOrderItem(OrderItemRequest orderItemRequest);
    public void updateOrderItem(OrderItemRequest orderItem, Long orderItemId);
    public void deleteOrderItem(Long orderId);
    public OrderItemResponseDto getOrderItemById(Long orderId);
    public Page<OrderItemResponseDto> getAllOrderItem(int page, int size);
}
