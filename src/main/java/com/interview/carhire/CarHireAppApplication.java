package com.interview.carhire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CarHireAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarHireAppApplication.class, args);
    }
}
