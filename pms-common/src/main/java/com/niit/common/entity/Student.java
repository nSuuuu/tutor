package com.niit.common.entity;

import javax.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "students")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student {
    @Id
    @Column(name = "user_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "student_number", unique = true)
    private String studentNumber;

    @Column(name = "grade")
    private String grade;

    @Column(columnDefinition = "TEXT")
    private String needs;
} 