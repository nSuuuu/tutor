package com.niit.orderservice.service.impl;

import com.niit.common.entity.Order;
import com.niit.common.util.BusinessException;
import com.niit.orderservice.repository.OrderRepository;
import com.niit.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
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
        return orderRepository.findByStudent_User_Id(userId);
    }

    @Override
    public List<Order> getOrdersByCourseId(Integer courseId) {
        return orderRepository.findByCourseId(courseId);
    }

    @Override
    public List<Order> getOrdersByTeacherId(Integer teacherId) {
        return orderRepository.findByTeacher_User_Id(teacherId);
    }

    @Override
    public List<Order> getOrdersByUserIdAndStatus(Integer userId, Order.OrderStatus status) {
        return orderRepository.findByStudent_User_IdAndOrderStatus(userId, status);
    }

    @Override
    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getOrderStatus() == status)
                .toList();
    }

    @Override
    public Order payOrder(Integer orderId, Order.PayMethod payMethod) throws BusinessException {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (order.getOrderStatus() == Order.OrderStatus.PENDING) {
                order.setOrderStatus(Order.OrderStatus.PAID);
                order.setPayMethod(payMethod);
                order.setPayTime(LocalDateTime.now());
                return orderRepository.save(order);
            } else {
                throw new BusinessException("订单状态不正确，无法支付");
            }
        } else {
            throw new BusinessException("订单不存在");
        }
    }

    @Override
    public Order cancelOrder(Integer orderId, String reason) throws BusinessException {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (order.getOrderStatus() == Order.OrderStatus.PENDING) {
                order.setOrderStatus(Order.OrderStatus.CANCELLED);
                order.setRefundReason(reason);
                return orderRepository.save(order);
            } else {
                throw new BusinessException("订单状态不正确，无法取消");
            }
        } else {
            throw new BusinessException("订单不存在");
        }
    }

    @Override
    public Order refundOrder(Integer orderId, String reason) throws BusinessException {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (order.getOrderStatus() == Order.OrderStatus.PAID) {
                order.setOrderStatus(Order.OrderStatus.REFUNDED);
                order.setRefundReason(reason);
                return orderRepository.save(order);
            } else {
                throw new BusinessException("订单状态不正确，无法退款");
            }
        } else {
            throw new BusinessException("订单不存在");
        }
    }

    @Override
    public List<Order> getOrdersByStudentId(Integer studentId) {
        // 假设Order实体有studentId字段，或根据实际字段调整
        return orderRepository.findByStudentId(studentId);
    }
} 