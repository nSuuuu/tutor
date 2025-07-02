package com.niit.common.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teachers")
@Data
public class Teacher {
    @Id
    @Column(name = "user_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "teacher_number", unique = true)
    private String teacherNumber;

    @Column(name = "subject")
    private String subject;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "education")
    private String education;

    @Column(name = "certification")
    private String certification;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @Column(name = "status")
    private String status;

    // 评分
    @Column(name = "rating")
    private Double rating;

    // 专业
    @Column(name = "major")
    private String major;

    // 地区ID
    @Column(name = "region_id")
    private Long regionId;

    // 头像
    @Column(name = "avatar")
    private String avatar;

    // 年级
    @Column(name = "grade_level")
    private String gradeLevel;

    // 科目（多个用逗号分隔）
    @Column(name = "subjects")
    private String subjects;

    // 教学风格
    @Column(name = "style")
    private String style;

    // 经验描述
    @Column(name = "experience")
    private String experience;

    // 省份
    @Column(name = "province")
    private String province;

    // 城市
    @Column(name = "city")
    private String city;

    // 价格（小时单价）
    @Column(name = "price")
    private java.math.BigDecimal price;

    // 用户ID（页面跳转辅助）
    @Transient
    private Integer userId;
} 