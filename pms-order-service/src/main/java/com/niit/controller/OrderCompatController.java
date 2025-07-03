package com.niit.controller;

import com.niit.entity.Order;
import com.niit.util.Result;
import com.niit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class OrderCompatController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order/student/{studentId}")
    public Result<List<Order>> getOrdersByStudentId(@PathVariable Integer studentId) {
        List<Order> orders = orderService.getOrdersByStudentId(studentId);
        return Result.success(orders);
    }
} 