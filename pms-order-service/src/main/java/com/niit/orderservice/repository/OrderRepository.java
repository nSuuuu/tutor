package com.niit.orderservice.repository;

import com.niit.common.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByStudent_User_Id(Integer userId);
    List<Order> findByTeacher_User_Id(Integer teacherId);
    List<Order> findByStudent_User_IdAndOrderStatus(Integer userId, Order.OrderStatus status);
    List<Order> findByCourseId(Integer courseId);
    List<Order> findByStudentId(Integer studentId);
} 