package com.niit.entity;

import lombok.Data;
import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Table(name = "hup_region")
@Data
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer pid;
    private String shortname;
    private String name;

    @Column(name = "merger_name")
    private String mergerName;

    private Integer level;
    private String pinyin;
    private String code;

    @Column(name = "zip_code")
    private String zipCode;
    
    private String first;
    private String lng;
    private String lat;
} 