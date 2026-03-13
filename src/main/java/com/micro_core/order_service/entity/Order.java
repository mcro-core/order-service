package com.micro_core.order_service.entity;

import com.micro_core.order_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseEntity{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "order_number", nullable = false)
        private String orderNumber;

        @Column(name = "user_id", nullable = false)
        private Long userId;

        @Column(name = "total_amount", nullable = false)
        private BigDecimal totalAmount;

        @Column(name = "discount_amount", nullable = false)
        private BigDecimal discount_amount;

        @Column(name = "net_amount", nullable = false)
        private BigDecimal netAmount;

        @Column(name = "order_status", nullable = false)
        @Enumerated(EnumType.STRING)
        private OrderStatus orderStatus;

        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
        private List<OrderItems> orderItems;

}
