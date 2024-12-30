package com.secor.fdamenuservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class FdaMenuServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdaMenuServiceApplication.class, args);
    }

}
