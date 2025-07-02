package com.niit.regionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 地区服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = {"com.niit.common.entity"})
public class RegionServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(RegionServiceApplication.class, args);
    }
} 