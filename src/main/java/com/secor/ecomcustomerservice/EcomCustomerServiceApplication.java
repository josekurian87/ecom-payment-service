package com.secor.ecomcustomerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class EcomCustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcomCustomerServiceApplication.class, args);
    }

}
