package com.micro_core.order_service.repo;

import com.micro_core.order_service.entity.Order;
import com.micro_core.order_service.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
