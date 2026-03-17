package com.micro_core.order_service.service.impl;
import com.micro_core.order_service.dto.request.OrderItemRequest;
import com.micro_core.order_service.dto.response.OrderItemResponseDto;;
import com.micro_core.order_service.repo.OrderItemsRepo;
import com.micro_core.order_service.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemsRepo orderItemsRepo;


    @Override
    public void updateOrderItem(OrderItemRequest request, Long orderItemId) {

    }

    @Override
    public void deleteOrderItem(Long orderItemId) {

    }

    @Override
    public OrderItemResponseDto getOrderItemById(Long orderItemId) {
        return null;
    }

    @Override
    public Page<OrderItemResponseDto> getAllOrderItem(int page, int size) {
        return null;
    }
}