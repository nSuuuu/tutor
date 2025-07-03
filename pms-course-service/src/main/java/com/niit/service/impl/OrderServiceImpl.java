package com.niit.service.impl;

import com.niit.entity.Order;
import com.niit.entity.Course;
import com.niit.repository.OrderRepository;
import com.niit.repository.CourseRepository;
import com.niit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Order createOrder(Integer courseId, Integer userId, Order.PayMethod payMethod) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("课程不存在"));
        Order order = new Order();
        order.setCourse(course);
        order.getId();
        order.setAmount(course.getPrice());
        order.setPayMethod(payMethod);
        order.setStatus(Order.OrderStatus.待支付);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUserAndStatus(Integer userId, Order.OrderStatus status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }
} 