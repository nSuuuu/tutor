package com.niit.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Data
public class Student {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 20)
    private String grade;

    @Column(columnDefinition = "TEXT")
    private String needs;

    @Version
    private Integer version;

    public Integer getId() {
        return this.userId;
    }
} 