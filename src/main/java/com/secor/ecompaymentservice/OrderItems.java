package com.secor.ecompaymentservice;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private Long orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "orderId", insertable = false, updatable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private ProductCatalog product;

    // Getters and Setters
}
