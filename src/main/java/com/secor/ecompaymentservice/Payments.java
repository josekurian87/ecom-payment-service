package com.secor.ecompaymentservice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Long orderId;
    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;

    // Getters and Setters
}
