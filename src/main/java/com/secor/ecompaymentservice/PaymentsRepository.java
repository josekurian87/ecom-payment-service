package com.secor.ecompaymentservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    Payments findByOrderId(Long orderId);
}
