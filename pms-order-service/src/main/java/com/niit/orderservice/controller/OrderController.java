package com.niit.orderservice.controller;

import com.niit.common.entity.Order;
import com.niit.common.util.Result;
import com.niit.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // 获取所有订单
    @GetMapping("/list")
    public Result<List<Order>> getAllOrders() {
        return Result.success(orderService.getAllOrders());
    }

    // 根据ID获取订单
    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Integer id) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            return Result.success(order.get());
        } else {
            return Result.error("订单不存在");
        }
    }

    // 创建订单
    @PostMapping("/add")
    public Result<Order> addOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return Result.success("订单创建成功", savedOrder);
    }

    // 更新订单
    @PutMapping("/{id}")
    public Result<Order> updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        order.setId(id);
        Order savedOrder = orderService.saveOrder(order);
        return Result.success("订单更新成功", savedOrder);
    }

    // 删除订单
    @DeleteMapping("/{id}")
    public Result<?> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return Result.success("订单删除成功");
    }

    // 根据用户ID获取订单
    @GetMapping("/user/{userId}")
    public Result<List<Order>> getOrdersByUserId(@PathVariable Integer userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return Result.success(orders);
    }

    // 根据课程ID获取订单
    @GetMapping("/course/{courseId}")
    public Result<List<Order>> getOrdersByCourseId(@PathVariable Integer courseId) {
        List<Order> orders = orderService.getOrdersByCourseId(courseId);
        return Result.success(orders);
    }

    // 根据老师ID获取订单
    @GetMapping("/teacher/{teacherId}")
    public Result<List<Order>> getOrdersByTeacherId(@PathVariable Integer teacherId) {
        List<Order> orders = orderService.getOrdersByTeacherId(teacherId);
        return Result.success(orders);
    }

    // 根据用户ID和状态获取订单
    @GetMapping("/user/{userId}/status/{status}")
    public Result<List<Order>> getOrdersByUserIdAndStatus(@PathVariable Integer userId, @PathVariable Order.OrderStatus status) {
        List<Order> orders = orderService.getOrdersByUserIdAndStatus(userId, status);
        return Result.success(orders);
    }

    // 根据订单状态获取订单
    @GetMapping("/status/{status}")
    public Result<List<Order>> getOrdersByStatus(@PathVariable Order.OrderStatus status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return Result.success(orders);
    }

    // 支付订单
    @PutMapping("/{id}/pay")
    public Result<Order> payOrder(@PathVariable Integer id, @RequestParam Order.PayMethod payMethod) {
        try {
            Order order = orderService.payOrder(id, payMethod);
            return Result.success("订单支付成功", order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // 取消订单
    @PutMapping("/{id}/cancel")
    public Result<Order> cancelOrder(@PathVariable Integer id, @RequestParam String reason) {
        try {
            Order order = orderService.cancelOrder(id, reason);
            return Result.success("订单取消成功", order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // 退款订单
    @PutMapping("/{id}/refund")
    public Result<Order> refundOrder(@PathVariable Integer id, @RequestParam String reason) {
        try {
            Order order = orderService.refundOrder(id, reason);
            return Result.success("订单退款申请成功", order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // 兼容无/api前缀的FeignClient调用
    @GetMapping("/order/teacher/{teacherId}")
    public Result<List<Order>> getOrdersByTeacherIdV2(@PathVariable Integer teacherId) {
        return Result.success(orderService.getOrdersByTeacherId(teacherId));
    }
    @GetMapping("/order/user/{userId}")
    public Result<List<Order>> getOrdersByUserIdV2(@PathVariable Integer userId) {
        return Result.success(orderService.getOrdersByUserId(userId));
    }

    // 根据学生ID获取订单
    @GetMapping("/order/student/{studentId}")
    public Result<List<Order>> getOrdersByStudentIdCompat(@PathVariable Integer studentId) {
        List<Order> orders = orderService.getOrdersByStudentId(studentId);
        return Result.success(orders);
    }
} 