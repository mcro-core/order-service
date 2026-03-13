package com.micro_core.order_service.repo;

import com.micro_core.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
