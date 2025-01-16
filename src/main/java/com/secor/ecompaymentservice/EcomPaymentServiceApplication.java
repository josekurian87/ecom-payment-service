package com.secor.ecompaymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class EcomPaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcomPaymentServiceApplication.class, args);
    }

}
