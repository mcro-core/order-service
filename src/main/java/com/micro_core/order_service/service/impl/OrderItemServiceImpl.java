package com.micro_core.order_service.service.impl;

import com.micro_core.order_service.dto.request.OrderItemRequest;
import com.micro_core.order_service.dto.response.OrderItemResponseDto;
import com.micro_core.order_service.entity.OrderItems;
import com.micro_core.order_service.exceptions.ResourceNotFoundException;
import com.micro_core.order_service.repo.OrderItemsRepo;
import com.micro_core.order_service.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemsRepo orderItemsRepo;

    @Override
    public void createOrderItem(OrderItemRequest orderItem) {

        OrderItems orderItems = OrderItems.builder()
                .productName(orderItem.getProductName())
                .unitPrice(orderItem.getUnitPrice())
                .discountValue(orderItem.getDiscountValue())
                .sellingPrice(orderItem.getSellingPrice())
                .quantity(orderItem.getQuantity())
                .subTotal(orderItem.getSubTotal())
                .order(orderItem.getOrder())
                .build();

        orderItemsRepo.save(orderItems);
    }

    @Override
    public void updateOrderItem(OrderItem orderItem, Long orderItemId) {

        OrderItems orderItems = orderItemsRepo.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found!"));

        orderItems.setProductName(orderItem.getProductName());
        orderItems.setUnitPrice(orderItem.getUnitPrice());
        orderItems.setDiscountValue(orderItem.getDiscountValue());
        orderItems.setSellingPrice(orderItem.getSellingPrice());
        orderItems.setQuantity(orderItem.getQuantity());
        orderItems.setSubTotal(orderItem.getSubTotal());
        orderItems.setOrder(orderItem.getOrder());

        orderItemsRepo.save(orderItems);

    }

    @Override
    public void deleteOrderItem(Long orderId) {
        OrderItems orderItems = orderItemsRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item Not Found !"));
        orderItemsRepo.delete(orderItems);
    }

    @Override
    public OrderItemResponseDto getOrderItemById(Long orderId) {
        OrderItems orderItems = orderItemsRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item Not Found !"));
        return null;
    }

    @Override
    public Page<OrderItemResponseDto> getAllOrderItem(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderItems> orderItemsPage = orderItemsRepo.findAll(pageable);
        return orderItemsPage.map(this::mapToResponseDto);
    }

    private OrderItemResponseDto mapToResponseDto(OrderItems orderItems){
        return OrderItemResponseDto.builder()
                .id(orderItems.getId())
                .productName(orderItems.getProductName())
                .unitPrice(orderItems.getUnitPrice())
                .discountValue(orderItems.getDiscountValue())
                .sellingPrice(orderItems.getSellingPrice())
                .quantity(orderItems.getQuantity())
                .subTotal(orderItems.getSubTotal())
                .order(orderItems.getOrder())
                .build();
    }

}
