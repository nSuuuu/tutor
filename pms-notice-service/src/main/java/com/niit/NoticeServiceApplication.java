package com.niit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = {"com.niit.entity"})
public class NoticeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoticeServiceApplication.class, args);
    }
} 