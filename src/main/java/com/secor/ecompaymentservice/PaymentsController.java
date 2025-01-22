package com.secor.ecompaymentservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final Logger LOG = LoggerFactory.getLogger(PaymentsController.class);

    @Autowired
    private PaymentsRepository paymentsRepository;

    @GetMapping
    public List<Payments> getAllPayments() {
        LOG.info("getAllPayments");
        return paymentsRepository.findAll();
    }

    @GetMapping("/order/{orderId}")
    public Payments getPaymentForOrderId(@PathVariable Long orderId) {
        LOG.info("getPaymentForOrderId  for orderId {}", orderId);
        return paymentsRepository.findByOrderId(orderId);
    }

    @GetMapping("/{id}")
    public Payments getPaymentById(@PathVariable Long id) {
        LOG.info("getPaymentById  for id {}", id);
        return paymentsRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Payments addPayment(@RequestBody Payments payment) {
        LOG.info("addPayment for payment {}", payment);
        return paymentsRepository.save(payment);
    }

    @PutMapping("/{id}")
    public Payments updatePayment(@PathVariable Long id, @RequestBody Payments paymentDetails) {
        LOG.info("updatePayment for id {}", id);
        Payments payment = paymentsRepository.findById(id).orElseThrow();
        payment.setOrderId(paymentDetails.getOrderId());
        payment.setPaymentDate(paymentDetails.getPaymentDate());
        payment.setAmount(paymentDetails.getAmount());
        payment.setPaymentMethod(paymentDetails.getPaymentMethod());
        payment.setStatus(paymentDetails.getStatus());
        return paymentsRepository.save(payment);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        LOG.info("deletePayment for id {}", id);
        paymentsRepository.deleteById(id);
    }
}
