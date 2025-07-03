package com.niit.service;

import com.niit.entity.Order;
import java.util.List;

public interface OrderService {
    Order createOrder(Integer courseId, Integer userId, Order.PayMethod payMethod);
    List<Order> getOrdersByUserAndStatus(Integer userId, Order.OrderStatus status);
} 