package com.niit.common.entity;

import lombok.Data;

@Data
public class Region {
    private Integer id;
    private String name;
    private String code;
    private String parentCode;
    private String type;
} 