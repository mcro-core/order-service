package com.micro_core.order_service.service.impl;

import com.micro_core.order_service.client.ProductClient;
import com.micro_core.order_service.dto.request.OrderItemRequest;
import com.micro_core.order_service.dto.request.OrderRequest;
import com.micro_core.order_service.dto.response.OrderResponseDto;
import com.micro_core.order_service.dto.response.ResponseProductDto;
import com.micro_core.order_service.entity.Order;
import com.micro_core.order_service.entity.OrderItems;
import com.micro_core.order_service.exceptions.ResourceNotFoundException;
import com.micro_core.order_service.model.OrderCalculation;
import com.micro_core.order_service.repo.OrderRepo;
import com.micro_core.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final ProductClient productClient;
    @Override
    public void createOrder(OrderRequest orderRequest) {

            Order order = Order.builder().build();
    }

    @Override
    public void updateOrder(OrderRequest orderRequest, Long orderId) {

    }

    @Override
    public void deleteOrder(Long orderId) {

    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        return null;
    }

    @Override
    public Page<OrderResponseDto> getAllOrder(int page, int size) {
        return null;
    }

    public Order findOrder(Long orderId){
        return orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not found!"));
    }

    public OrderCalculation calculateOrderDetails(List<OrderItemRequest> orderItems){
        BigDecimal totalAmount;
        BigDecimal netAmount;

        for( OrderItemRequest orderItem :  orderItems){
            Long productId = orderItem.getProductId();
            int quantity = orderItem.getQuantity();

            ResponseProductDto selectedProduct = productClient.getProductById(productId);
        }
        return null;
    }



}
