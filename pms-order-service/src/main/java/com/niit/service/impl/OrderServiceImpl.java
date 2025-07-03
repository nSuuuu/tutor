package com.niit.service.impl;

import com.niit.entity.Order;
import com.niit.entity.Course;
import com.niit.entity.User;
import com.niit.repository.OrderRepository;
import com.niit.repository.CourseRepository;
import com.niit.repository.UserRepository;
import com.niit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Order createOrder(Integer courseId, Integer userId, Order.PayMethod payMethod) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("课程不存在"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("用户不存在"));
        Order order = new Order();
        order.setCourse(course);
        order.setUser(user);
        order.setAmount(course.getPrice());
        order.setPayMethod(payMethod);
        order.setStatus(Order.OrderStatus.待支付);
        return orderRepository.save(order);
    }

    @Override
    public Order payOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("订单不存在"));
        order.setStatus(Order.OrderStatus.已支付);
        order.setPayTime(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Integer orderId, String reason) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("订单不存在"));
        order.setStatus(Order.OrderStatus.已取消);
        order.setRefundReason(reason);
        return orderRepository.save(order);
    }

    @Override
    public Order refundOrder(Integer orderId, String reason) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("订单不存在"));
        order.setStatus(Order.OrderStatus.退款中);
        order.setRefundReason(reason);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public List<Order> getOrdersByUser(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUserAndStatus(Integer userId, Order.OrderStatus status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByCourseId(Integer courseId) {
        return orderRepository.findByCourseId(courseId);
    }

    @Override
    public List<Order> getOrdersByTeacherId(Integer teacherId) {
        return orderRepository.findByCourseTeacherId(teacherId);
    }

    @Override
    public List<Order> getOrdersByUserIdAndStatus(Integer userId, Order.OrderStatus status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public Order payOrder(Integer orderId, Order.PayMethod payMethod) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("订单不存在"));
        order.setStatus(Order.OrderStatus.已支付);
        order.setPayMethod(payMethod);
        order.setPayTime(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByStudentId(Integer studentId) {
        return orderRepository.findByStudentId(studentId);
    }
} 