package com.micro_core.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItems extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "discount_vale", nullable = false)
    private BigDecimal discountValue;

    @Column(name = "selling_price", nullable = false)
    private BigDecimal sellingPrice;

    @Column(name = "quantity", nullable = false)
    private  Integer quantity;

    @Column(name = "sub_tota", nullable = false)
    private BigDecimal subTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
}
