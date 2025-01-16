package com.secor.ecomcustomerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;

@Configuration
public class AppConfig {

    @Autowired
    private EurekaDiscoveryClient discoveryClient;

    public ServiceInstance getServiceInstance(String serviceName)
    {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        if (instances.isEmpty()) {
            throw new RuntimeException("No instances found for "+serviceName);
        }
        return instances.get(0); // LOAD BALANCING ALGORITHM WILL GO HERE
    }

//    @Bean("profile_service_get_restros")
//    public WebClient webClientProfileService(WebClient.Builder webClientBuilder, RetryTemplate retryTemplate)
//    {
//
//        return retryTemplate.execute(context -> {
//            try {
//                ServiceInstance instance = getServiceInstance("fda-profile-service");
//                String hostname = instance.getHost();
//                int port = instance.getPort();
//
//                return webClientBuilder
//                        .baseUrl("http://"+hostname+":"+port+"/api/v1/get/restros")
//                        .filter(new LoggingWebClientFilter())
//                        .build();
//            } catch (UnsatisfiedDependencyException e) {
//                throw new RetryException("Dependency not available yet", e);
//            }
//        });
//    }

//    @Bean("profile_service_get_restros")
//    public WebClient webClientProfileService( WebClient.Builder webClientBuilder)
//    {
//                ServiceInstance instance = getServiceInstance("fda-profile-service");
//                String hostname = instance.getHost();
//                int port = instance.getPort();
//
//                return webClientBuilder
//                        .baseUrl("http://"+hostname+":"+port+"/api/v1/get/restros")
//                        .filter(new LoggingWebClientFilter())
//                        .build();
//    }
//
//
//    @Bean
//    public RetryTemplate retryTemplate() {
//        RetryTemplate retryTemplate = new RetryTemplate();
//
//        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
//        backOffPolicy.setBackOffPeriod(2000); // 2 seconds
//
//        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
//        retryPolicy.setMaxAttempts(150); // Will try for 1 minute
//
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        retryTemplate.setRetryPolicy(retryPolicy);
//
//        return retryTemplate;
//    }


}
