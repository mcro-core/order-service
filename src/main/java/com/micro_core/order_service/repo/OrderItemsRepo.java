package com.micro_core.order_service.repo;

import com.micro_core.order_service.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepo extends JpaRepository<OrderItems, Long> {
}
