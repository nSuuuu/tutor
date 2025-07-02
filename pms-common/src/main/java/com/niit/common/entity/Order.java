package com.niit.common.entity;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "order_number", unique = true)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_method")
    private PayMethod payMethod;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "course_hours")
    private Integer courseHours;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "appointment_id")
    private Integer appointmentId;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @Column(name = "pay_time")
    private LocalDateTime payTime;

    @Column(name = "remark")
    private String remark;

    @Column(columnDefinition = "TEXT")
    private String refundReason;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public enum OrderStatus {
        PENDING, PAID, CANCELLED, REFUNDED
    }

    public enum PayMethod {
        支付宝, 微信, 银行卡
    }
} 