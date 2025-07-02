package com.niit.common.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "hup_region")
@Data
public class HupRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "parent_id")
    private Long parentId;

    private String parentCode;

    private String type;

    private Integer status;

    @Column(name = "level")
    private Integer level;
} 