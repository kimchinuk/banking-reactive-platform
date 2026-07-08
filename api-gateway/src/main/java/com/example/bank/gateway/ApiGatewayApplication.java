package com.example.bank.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Reactive API Gateway built with Spring Cloud Gateway.
 *
 * In microservice ecosystems the gateway is responsible for request routing,
 * and is the natural place to add auth, rate limiting, and trace propagation later.
 */
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
