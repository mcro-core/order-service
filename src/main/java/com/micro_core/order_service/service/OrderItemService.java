package com.micro_core.order_service.service;

import com.micro_core.order_service.dto.request.OrderItemRequest;
import com.micro_core.order_service.dto.response.OrderItemResponseDto;
import com.micro_core.order_service.entity.OrderItems;
import org.springframework.data.domain.Page;

public interface OrderItemService {
        void updateOrderItem(OrderItemRequest request, Long orderItemId);
        void deleteOrderItem(Long orderItemId);
        OrderItemResponseDto getOrderItemById(Long orderItemId);
        Page<OrderItemResponseDto> getAllOrderItem(int page, int size);
}
