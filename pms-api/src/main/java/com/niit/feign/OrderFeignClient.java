package com.niit.feign;

import com.niit.entity.Order;
import com.niit.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "pms-order-service")
public interface OrderFeignClient {
    
    @GetMapping("/order/list")
    Result<List<Order>> getAllOrders();
    
    @GetMapping("/order/{id}")
    Result<Order> getOrderById(@PathVariable("id") Integer id);
    
    @GetMapping("/order/user/{userId}")
    Result<List<Order>> getOrdersByUserId(@PathVariable("userId") Integer userId);
    
    @GetMapping("/order/teacher/{teacherId}")
    Result<List<Order>> getOrdersByTeacherId(@PathVariable("teacherId") Integer teacherId);
    
    @GetMapping("/order/student/{studentId}")
    Result<List<Order>> getOrdersByStudentId(@PathVariable("studentId") Integer studentId);
    
    @PostMapping("/order/create")
    Result<Order> createOrder(@RequestBody Order order);
    
    @PutMapping("/order/update")
    Result<Order> updateOrder(@RequestBody Order order);
    
    @DeleteMapping("/order/{id}")
    Result<String> deleteOrder(@PathVariable("id") Integer id);
} 