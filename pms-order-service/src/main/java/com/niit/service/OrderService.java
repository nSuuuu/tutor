package com.niit.service;

import com.niit.entity.Order;
import com.niit.util.BusinessException;

import java.util.List;

public interface OrderService {
    /**
     * 获取所有订单
     */
    List<Order> getAllOrders();
    
    /**
     * 根据ID获取订单
     */
    Order getOrderById(Integer id);
    
    /**
     * 保存订单
     */
    Order saveOrder(Order order);
    
    /**
     * 删除订单
     */
    void deleteOrder(Integer id);
    
    /**
     * 根据用户ID获取订单
     */
    List<Order> getOrdersByUserId(Integer userId);
    
    /**
     * 根据课程ID获取订单
     */
    List<Order> getOrdersByCourseId(Integer courseId);
    
    /**
     * 根据老师ID获取订单
     */
    List<Order> getOrdersByTeacherId(Integer teacherId);
    
    /**
     * 根据用户ID和状态获取订单
     */
    List<Order> getOrdersByUserIdAndStatus(Integer userId, Order.OrderStatus status);
    
    /**
     * 根据订单状态获取订单
     */
    List<Order> getOrdersByStatus(Order.OrderStatus status);
    
    /**
     * 支付订单
     */
    Order payOrder(Integer orderId, Order.PayMethod payMethod) throws BusinessException;
    
    /**
     * 取消订单
     */
    Order cancelOrder(Integer orderId, String reason) throws BusinessException;
    
    /**
     * 退款订单
     */
    Order refundOrder(Integer orderId, String reason) throws BusinessException;
    
    /**
     * 根据学生ID获取订单
     */
    List<Order> getOrdersByStudentId(Integer studentId);

    Order createOrder(Integer courseId, Integer userId, Order.PayMethod payMethod);
    Order payOrder(Integer orderId);

    List<Order> getOrdersByUser(Integer userId);
    List<Order> getOrdersByUserAndStatus(Integer userId, Order.OrderStatus status);
} 