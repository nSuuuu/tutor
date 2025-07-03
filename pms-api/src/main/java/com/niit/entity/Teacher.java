package com.niit.entity;


import lombok.Data;
import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Table(name = "teachers")
@Data
public class Teacher {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 255)
    private String avatar;

    @Column(columnDefinition = "TEXT")
    private String subjects;

    @Column(length = 100)
    private String education;

    @Column(columnDefinition = "TEXT")
    private String style;

    @Column(columnDefinition = "FLOAT DEFAULT 0.0")
    private Float score;

    @Column(columnDefinition = "TEXT")
    private String experience;

    @Column(name = "grade_level", length = 50)
    private String gradeLevel;

    @Column(name = "price")
    private Integer price;

    public Integer getId() {
        return this.userId;
    }
} 