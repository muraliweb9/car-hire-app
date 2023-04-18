package com.interview.carhire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableFeignClients // Look for @FeignClient and configure these in app
public class CarHireAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarHireAppApplication.class, args);
    }
}
