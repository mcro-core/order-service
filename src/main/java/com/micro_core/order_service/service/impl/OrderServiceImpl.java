package com.micro_core.order_service.service.impl;

import com.micro_core.order_service.client.InventoryClient;
import com.micro_core.order_service.client.ProductClient;
import com.micro_core.order_service.dto.request.OrderItemRequest;
import com.micro_core.order_service.dto.request.OrderRequest;
import com.micro_core.order_service.dto.response.OrderResponseDto;
import com.micro_core.order_service.dto.response.ResponseInventoryDto;
import com.micro_core.order_service.dto.response.ResponseProductDto;
import com.micro_core.order_service.entity.Order;
import com.micro_core.order_service.entity.OrderItems;
import com.micro_core.order_service.enums.OrderStatus;
import com.micro_core.order_service.exceptions.InventoryStockValidationException;
import com.micro_core.order_service.exceptions.ResourceNotFoundException;
import com.micro_core.order_service.model.OrderCalculation;
import com.micro_core.order_service.repo.OrderRepo;
import com.micro_core.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    @Override


    public void createOrder(OrderRequest orderRequest) {

            OrderCalculation orderCalculation = calculateOrderDetails(orderRequest.getOrderItems());

                List<OrderItemRequest> stockRequestList = orderRequest.getOrderItems().stream()
                .map(item -> new OrderItemRequest(item.getProductId(), item.getQuantity()))
                .toList();

        List<OrderItemRequest> inventoryUpdateList = orderRequest.getOrderItems().stream()
                .map(item -> new OrderItemRequest(item.getProductId(), item.getQuantity() * -1))
                .toList();

            boolean isStockAvailable;
            if (inventoryClient.checkProductStock(stockRequestList)) isStockAvailable = true;
            else isStockAvailable = false;

        if(isStockAvailable){
                Order order = Order.builder()
                        .orderNumber(this.generateOrderNumber())
                        .userId(orderRequest.getUserId())
                        .totalAmount(orderCalculation.getTotalAmount())
                        .discountAmount(orderCalculation.getTotalDiscount())
                        .netAmount(orderCalculation.getNetAmount())
                        .orderStatus(OrderStatus.PENDING)
                        .orderItems(orderCalculation.getItems())
                        .build();

                if(order.getOrderItems() != null){
                    for(OrderItems orderItem : order.getOrderItems()){
                        orderItem.setOrder(order);
                    }
                }
                orderRepo.save(order);

                updateInventory(inventoryUpdateList);

                log.info("Order created successfully with number: {}", order.getOrderNumber());
            }

    }

    @Override
    @Transactional
    public void updateOrderStatus(String orderStatus, Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        try {

            OrderStatus status = OrderStatus.valueOf(orderStatus.toUpperCase());

            order.setOrderStatus(status);
            orderRepo.save(order);

            log.info("Order {} status updated to {}", orderId, status);

        } catch (IllegalArgumentException e) {
            log.error("Invalid status provided: {}", orderStatus);
            throw new RuntimeException(
                    String.format("Invalid status: '%s'. Valid statuses are: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED", orderStatus)
            );
        }
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

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order is already cancelled.");
        }

        if (order.getOrderStatus() == OrderStatus.SHIPPED || order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel an order that has already been shipped or delivered.");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepo.save(order);

        List<OrderItemRequest> restockItems = order.getOrderItems().stream()
                .map(item -> new OrderItemRequest(item.getProductId(), item.getQuantity()))
                .toList();

        inventoryClient.updateInventory(restockItems);

        log.info("Order {} has been cancelled and stock has been restored.", orderId);
    }

    public Order findOrder(Long orderId){
        return orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not found!"));
    }

    public OrderCalculation calculateOrderDetails(List<OrderItemRequest> orderItems){
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal netAmount = BigDecimal.ZERO;
        BigDecimal totalDiscountAmount = BigDecimal.ZERO;
        List<OrderItems> processedItem = new ArrayList<>();

        for( OrderItemRequest itemRequest :  orderItems){
            Long productId = itemRequest.getProductId();
            int quantity = itemRequest.getQuantity();
            BigDecimal unitDiscount = BigDecimal.ZERO;
            BigDecimal unitTotalDiscount = BigDecimal.ZERO;

            ResponseProductDto selectedProduct = getProduct(productId);

            totalAmount = totalAmount.add(selectedProduct.getPrice().multiply(BigDecimal.valueOf(quantity)));
            netAmount = netAmount.add(selectedProduct.getDiscountedPrice().multiply(BigDecimal.valueOf(quantity)));
            unitDiscount = selectedProduct.getPrice().subtract(selectedProduct.getDiscountedPrice());
            unitTotalDiscount = unitDiscount.multiply(BigDecimal.valueOf(quantity));
            totalDiscountAmount = totalDiscountAmount.add(unitTotalDiscount);

            OrderItems orderItem = OrderItems.builder()
                    .productId(selectedProduct.getId())
                    .unitPrice(selectedProduct.getPrice())
                    .discountValue(unitTotalDiscount)
                    .sellingPrice(selectedProduct.getDiscountedPrice())
                    .quantity(quantity)
                    .totalAmount(selectedProduct.getPrice().multiply(BigDecimal.valueOf(quantity)))
                    .netAmount(selectedProduct.getDiscountedPrice().multiply(BigDecimal.valueOf(quantity)))
                    .build();

            processedItem.add(orderItem);
        }

        return OrderCalculation.builder()
                .items(processedItem)
                .totalDiscount(totalDiscountAmount)
                .totalAmount(totalAmount)
                .netAmount(netAmount)
                .build();
    }

    public String generateOrderNumber() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String randomPart = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        return "ORD-" + datePart + "-" + randomPart; //ORD-260316-AF82D
    }

    public ResponseProductDto getProduct(Long productId){
        return productClient.getProductById(productId);
    }

    public void updateInventory(List<OrderItemRequest> orderItemRequests){
        inventoryClient.updateInventory(orderItemRequests);
    }
}
